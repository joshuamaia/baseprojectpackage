package br.com.joshua.baseproject.businessrule.usecase;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.joshua.baseproject.businessrule.DeletePerson;
import br.com.joshua.baseproject.interfaceadapter.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DeletePersonUC implements DeletePerson{
	
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("${app-config.rabbit.exchange.baseproject}")
	private String personTopicExchange;

	@Value("${app-config.rabbit.routingKey.person-delete}")
	private String personDeleteKey;

	@Override
	@Transactional
	public void deletePerson(Long id) {
		personRepository.deleteById(id);
		try {
			log.info("Sending message: {}", new ObjectMapper().writeValueAsString(id));
			rabbitTemplate.convertAndSend(personTopicExchange, personDeleteKey, id);
			log.info("Message was sent successfully!");
		} catch (Exception ex) {
			log.info("Error while trying to send delete confirmation message: ", ex);
			throw new RuntimeException(ex);
		}		
	}

}
