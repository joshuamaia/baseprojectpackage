package br.com.joshua.baseproject.service.impl;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.joshua.baseproject.domain.Person;
import br.com.joshua.baseproject.repository.PersonRepository;
import br.com.joshua.baseproject.repository.specification.PersonSpecification;
import br.com.joshua.baseproject.request.PersonRequest;
import br.com.joshua.baseproject.response.PersonResponse;
import br.com.joshua.baseproject.service.PersonService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PersonServiceImpl extends ServiceBaseImpl<PersonResponse, PersonRequest, Long, Person, PersonRepository>
		implements PersonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("${app-config.rabbit.exchange.baseproject}")
	private String personTopicExchange;

	@Value("${app-config.rabbit.routingKey.person-create}")
	private String personCreateKey;

	@Value("${app-config.rabbit.routingKey.person-update}")
	private String personUpdateKey;

	@Value("${app-config.rabbit.routingKey.person-delete}")
	private String personDeleteKey;

	@Override
	@Transactional
	public PersonResponse save(PersonRequest request) {
		PersonResponse toReturn = super.save(request);

		try {
			log.info("Sending message: {}", new ObjectMapper().writeValueAsString(request));
			request.setId(toReturn.getId());
			request.getAddress().setId(toReturn.getAddress().getId());
			rabbitTemplate.convertAndSend(personTopicExchange, personCreateKey, request);
			log.info("Message was sent successfully!");
		} catch (Exception ex) {
			log.info("Error while trying to send create confirmation message: ", ex);
			throw new RuntimeException(ex);
		}

		return toReturn;
	}
	
	@Override
	@Transactional
	public PersonResponse update(PersonRequest request) {
		PersonResponse toReturn = super.update(request);

		try {
			log.info("Sending message: {}", new ObjectMapper().writeValueAsString(request));
			rabbitTemplate.convertAndSend(personTopicExchange, personUpdateKey, request);
			log.info("Message was sent successfully!");
		} catch (Exception ex) {
			log.info("Error while trying to send update confirmation message: ", ex);
			throw new RuntimeException(ex);
		}

		return toReturn;
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		super.delete(id);
		try {
			log.info("Sending message: {}", new ObjectMapper().writeValueAsString(id));
			rabbitTemplate.convertAndSend(personTopicExchange, personDeleteKey, id);
			log.info("Message was sent successfully!");
		} catch (Exception ex) {
			log.info("Error while trying to send delete confirmation message: ", ex);
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Page<PersonResponse> searchAllPage(Integer page, Integer size, String wordSearch) {
		PageRequest pageRequest = PageRequest.of(page, size);
		if (wordSearch == null || wordSearch.trim().isEmpty()) {
			return repository.findAll(pageRequest).map(this::convertFromResponse);
		}
		wordSearch = wordSearch.toLowerCase();
		return repository.searchAllPage(wordSearch, pageRequest).map(this::convertFromResponse);

	}

	@Override
	public Page<PersonResponse> filter(@Nullable String name, @Nullable String email, @Nullable Integer page,
			@Nullable Integer size) {
		var specification = this.prepareSpecification(name, email);
		return this.repository.findAll(specification, this.preparePageable(PageRequest.of(page, size)))
				.map(this::convertFromResponse);
	}

	@NotNull
	private Specification<Person> prepareSpecification(@Nullable String name, @Nullable String email) {
		final var specification = new PersonSpecification();

		return specification.and(specification.findByName(name)).and(specification.findByEmail(email));
	}

	private Pageable preparePageable(@Nullable Pageable pageable) {
		return Optional.ofNullable(pageable).orElse(Pageable.unpaged());
	}

}
