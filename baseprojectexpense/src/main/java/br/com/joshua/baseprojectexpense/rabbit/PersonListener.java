package br.com.joshua.baseprojectexpense.rabbit;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import br.com.joshua.baseprojectexpense.request.PersonRequest;
import br.com.joshua.baseprojectexpense.service.PersonService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PersonListener {

	@Autowired
	private PersonService personService;

	@RabbitListener(queues = "${app-config.rabbit.queue.person-create}")
	public void savePersonMessage(PersonRequest personRequest) throws JsonProcessingException {
		log.info("Recieving message with data: {}",
				new ObjectMapper().writeValueAsString(personRequest));
		personService.save(personRequest);
	}
	
	@RabbitListener(queues = "${app-config.rabbit.queue.person-update}")
	public void updatePersonMessage(PersonRequest personRequest) throws JsonProcessingException {
		log.info("Recieving message with data: {}",
				new ObjectMapper().writeValueAsString(personRequest));
		personService.update(personRequest);
	}
	
	@RabbitListener(queues = "${app-config.rabbit.queue.person-delete}")
	public void deletePersonMessage(Long idPerson) throws JsonProcessingException {
		log.info("Recieving message with data: {}",
				new ObjectMapper().writeValueAsString(idPerson));
		personService.delete(idPerson);
	}

}
