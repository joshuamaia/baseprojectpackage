package br.com.joshua.baseprojectexpense.service;

import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import br.com.joshua.baseprojectexpense.request.PersonRequest;
import br.com.joshua.baseprojectexpense.response.PersonResponse;

public interface PersonService extends ServiceBase<PersonResponse, PersonRequest, Long> {

	public Page<PersonResponse> filter(
			@Nullable String name,
			@Nullable String email,
			@Nullable Integer page,
			@Nullable Integer size);
	
}
