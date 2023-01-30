package br.com.joshua.baseproject.businessrule.usecase;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.joshua.baseproject.businessrule.SavePerson;
import br.com.joshua.baseproject.domain.Person;
import br.com.joshua.baseproject.interfaceadapter.repository.PersonRepository;
import br.com.joshua.baseproject.interfaceadapter.repository.model.PersonMapper;
import br.com.joshua.baseproject.interfaceadapter.repository.model.PersonModel;
import br.com.joshua.baseproject.interfaceadapter.repository.model.PersonModelMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SavePersonUC implements SavePerson {
	
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	PersonMapper personMapper;
	
	@Autowired
	PersonModelMapper personModelMapper;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("${app-config.rabbit.exchange.baseproject}")
	private String personTopicExchange;

	@Value("${app-config.rabbit.routingKey.person-create}")
	private String personCreateKey;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	@Transactional
	public Person savePerson(Person person) {
		PersonModel toReturn = personRepository.save(personMapper.modelToEntity(person));

		try {
			log.info("Sending message: {}", objectMapper.writeValueAsString(person));
			person.setId(toReturn.getId());
			person.getAddress().setId(toReturn.getAddress().getId());
			rabbitTemplate.convertAndSend(personTopicExchange, personCreateKey, person);
			log.info("Message was sent successfully!");
		} catch (Exception ex) {
			log.info("Error while trying to send create confirmation message: ", ex);
			throw new RuntimeException(ex);
		}

		return personModelMapper.modelToEntity(toReturn);
	}

}
