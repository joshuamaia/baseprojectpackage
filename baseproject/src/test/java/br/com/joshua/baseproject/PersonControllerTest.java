package br.com.joshua.baseproject;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.joshua.baseproject.controller.PersonController;
import br.com.joshua.baseproject.domain.Address;
import br.com.joshua.baseproject.domain.Person;
import br.com.joshua.baseproject.enums.GenderEnum;
import br.com.joshua.baseproject.request.PersonRequest;
import br.com.joshua.baseproject.response.PersonResponse;
import br.com.joshua.baseproject.service.PersonService;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	PersonService service;

	private ModelMapper modelMapper;

	private ObjectMapper objectMapper;

	private Person person = null;
	private Address address = null;
	private PersonResponse personResponse = null;
	private PersonRequest personRequest = null;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		modelMapper = new ModelMapper();
		objectMapper = new ObjectMapper();

		address = new Address("Street Test", "District Test", 123);
		address.setId(1L);
		person = new Person("Gina", "gina@gmail.com", LocalDate.now(), GenderEnum.MALE, address);
		person.setId(1L);
		personResponse = modelMapper.map(person, PersonResponse.class);
		personRequest = new PersonRequest();
		personRequest.setId(1L);

	}

	@Test
	void testUpdate() throws Exception {
		when(service.save(any(PersonRequest.class))).then(new Answer<PersonResponse>() {

			@Override
			public PersonResponse answer(InvocationOnMock invocation) throws Throwable {
				return personResponse;
			}
		});

		mvc.perform(post("/api/persons").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personRequest))).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("Gina")).andExpect(jsonPath("$.email").value("gina@gmail.com"))
				.andExpect(jsonPath("$.address.street").value("Street Test"))
				.andExpect(jsonPath("$.address.district").value("District Test"));

		verify(service).save(any(PersonRequest.class));
	}

	@Test
	void testGetOneById() throws Exception {
		when(service.findOne(1L)).thenReturn(personResponse);

		mvc.perform(get("/api/persons/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("Gina")).andExpect(jsonPath("$.email").value("gina@gmail.com"))
				.andExpect(jsonPath("$.address.street").value("Street Test"))
				.andExpect(jsonPath("$.address.district").value("District Test"));

		verify(service).findOne(1L);
	}

	@Test
	void testDelete() throws Exception {

		mvc.perform(delete("/api/persons/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

		verify(service, times(1)).delete(1L);
	}

}
