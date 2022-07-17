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

import br.com.joshua.baseproject.domain.Person;
import br.com.joshua.baseproject.enums.GenderEnum;
import br.com.joshua.baseproject.interfaceadapter.PersonGateway;
import br.com.joshua.baseproject.interfaceadapter.controller.PersonController;
import br.com.joshua.baseproject.interfaceadapter.controller.model.GetPersonResponse;
import br.com.joshua.baseproject.interfaceadapter.repository.model.AddressModel;
import br.com.joshua.baseproject.interfaceadapter.repository.model.PersonModel;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	PersonGateway gateway;

	private ModelMapper modelMapper;

	private ObjectMapper objectMapper;

	private PersonModel person = null;
	private AddressModel address = null;
	private GetPersonResponse personResponse = null;
	private Person Person = null;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		modelMapper = new ModelMapper();
		objectMapper = new ObjectMapper();

		address = new AddressModel("Street Test", "District Test", 123);
		address.setId(1L);
		person = new PersonModel("Gina", "gina@gmail.com", LocalDate.now(), GenderEnum.MALE, address);
		person.setId(1L);
		personResponse = modelMapper.map(person, GetPersonResponse.class);
		Person = new Person();
		Person.setId(1L);

	}

	@Test
	void testUpdate() throws Exception {
		when(gateway.savePerson(any(Person.class))).then(new Answer<GetPersonResponse>() {

			@Override
			public GetPersonResponse answer(InvocationOnMock invocation) throws Throwable {
				return personResponse;
			}
		});

		mvc.perform(post("/api/persons").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(Person))).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("Gina")).andExpect(jsonPath("$.email").value("gina@gmail.com"))
				.andExpect(jsonPath("$.address.street").value("Street Test"))
				.andExpect(jsonPath("$.address.district").value("District Test"));

		verify(gateway).savePerson(any(Person.class));
	}

	@Test
	void testGetOneById() throws Exception {
		when(gateway.findOnePerson(1L)).thenReturn(personResponse);

		mvc.perform(get("/api/persons/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("Gina")).andExpect(jsonPath("$.email").value("gina@gmail.com"))
				.andExpect(jsonPath("$.address.street").value("Street Test"))
				.andExpect(jsonPath("$.address.district").value("District Test"));

		verify(gateway).findOnePerson(1L);
	}

	@Test
	void testDelete() throws Exception {

		mvc.perform(delete("/api/persons/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

		verify(gateway, times(1)).deletePerson(1L);
	}

}
