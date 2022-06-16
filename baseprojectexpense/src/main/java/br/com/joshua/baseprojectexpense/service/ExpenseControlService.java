package br.com.joshua.baseprojectexpense.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import br.com.joshua.baseprojectexpense.request.ExpenseControlRequest;
import br.com.joshua.baseprojectexpense.response.ExpenseControlResponse;
import br.com.joshua.baseprojectexpense.response.ExpenseSumResponse;

public interface ExpenseControlService extends ServiceBase<ExpenseControlResponse, ExpenseControlRequest, Long> {

	List<ExpenseSumResponse> searchSumExpense(Long personId);
	
	List<ExpenseSumResponse> searchSumExpenseTotal();
	
	public Page<ExpenseControlResponse> filter(
			@Nullable String description,
			@Nullable String name,
			@Nullable String email,
			@Nullable Integer page,
			@Nullable Integer size);
	
}
