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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

import br.com.joshua.baseproject.controller.ExpenseControlController;
import br.com.joshua.baseproject.domain.Address;
import br.com.joshua.baseproject.domain.ExpenseControl;
import br.com.joshua.baseproject.domain.Person;
import br.com.joshua.baseproject.enums.ExpenseEnum;
import br.com.joshua.baseproject.enums.GenderEnum;
import br.com.joshua.baseproject.request.ExpenseControlRequest;
import br.com.joshua.baseproject.response.ExpenseControlResponse;
import br.com.joshua.baseproject.response.ExpenseSumResponse;
import br.com.joshua.baseproject.service.ExpenseControlService;

@WebMvcTest(ExpenseControlController.class)
class ExpenseControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	ExpenseControlService service;

	private ModelMapper modelMapper;

	private ObjectMapper objectMapper;

	private Person person = null;
	private Address address = null;
	private ExpenseControl expenseControl = null;
	private ExpenseControlResponse expenseControlResponse = null;
	private ExpenseControlRequest expenseControlRequest = null;
	List<ExpenseSumResponse> expenseSumDto = null;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		modelMapper = new ModelMapper();
		objectMapper = new ObjectMapper();

		expenseSumDto = new ArrayList<ExpenseSumResponse>();
		expenseSumDto.add(new ExpenseSumResponse() {

			@Override
			public BigDecimal getValue() {
				return new BigDecimal(579.00);
			}

			@Override
			public String getExpense() {
				return "EXPENSE";
			}
		});
		expenseSumDto.add(new ExpenseSumResponse() {

			@Override
			public BigDecimal getValue() {
				return new BigDecimal(65.00);
			}

			@Override
			public String getExpense() {
				return "REVENUE";
			}
		});

		address = new Address("Street Test", "District Test", 123);
		address.setId(1L);
		person = new Person("Gina", "gina@gmail.com", LocalDate.now(), GenderEnum.MALE, address);
		person.setId(1L);
		expenseControl = new ExpenseControl(ExpenseEnum.EXPENSE, "Test", LocalDate.now(), new BigDecimal("50.0"),
				person);
		expenseControl.setId(1L);
		expenseControlRequest = new ExpenseControlRequest();
		expenseControlRequest.setId(1L);
		expenseControlResponse = modelMapper.map(expenseControl, ExpenseControlResponse.class);

	}

	@Test
	void testUpdate() throws Exception {
		when(service.save(any(ExpenseControlRequest.class))).then(new Answer<ExpenseControlResponse>() {

			@Override
			public ExpenseControlResponse answer(InvocationOnMock invocation) throws Throwable {
				return expenseControlResponse;
			}
		});

		mvc.perform(post("/api/expense-controls").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(expenseControlRequest))).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.description").value("Test"))
				.andExpect(jsonPath("$.value").value(new BigDecimal("50.0")));

		verify(service).save(any(ExpenseControlRequest.class));
	}

	@Test
	void testGetOneById() throws Exception {
		when(service.findOne(1L)).thenReturn(expenseControlResponse);

		mvc.perform(get("/api/expense-controls/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.description").value("Test"))
				.andExpect(jsonPath("$.value").value(new BigDecimal("50.0")));

		verify(service).findOne(1L);
	}

	@Test
	void testDelete() throws Exception {

		mvc.perform(delete("/api/expense-controls/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

		verify(service, times(1)).delete(1L);
	}

}
