package br.com.joshua.baseprojectexpense.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.joshua.baseprojectexpense.request.PersonRequest;
import br.com.joshua.baseprojectexpense.service.PersonService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PersonListener {

	@Autowired
	private PersonService personService;
	
	@Autowired
	private ObjectMapper objectMapper;

	@RabbitListener(queues = "${app-config.rabbit.queue.person-create}")
	public void savePersonMessage(PersonRequest personRequest) throws JsonProcessingException {
		log.info("Recieving message with data: {}", objectMapper.writeValueAsString(personRequest));
		personService.save(personRequest);
	}

	@RabbitListener(queues = "${app-config.rabbit.queue.person-update}")
	public void updatePersonMessage(PersonRequest personRequest) throws JsonProcessingException {
		log.info("Recieving message with data: {}", objectMapper.writeValueAsString(personRequest));
		personService.update(personRequest);
	}

	@RabbitListener(queues = "${app-config.rabbit.queue.person-delete}")
	public void deletePersonMessage(Long idPerson) throws JsonProcessingException {
		log.info("Recieving message with data: {}", objectMapper.writeValueAsString(idPerson));
		personService.delete(idPerson);
	}

	@RabbitListener(queues = "${app-config.rabbit.queue.start}")
	public void startMessage(String start) throws JsonProcessingException {
		log.info("Start message: {}", start);
	}

}
