package br.com.joshua.baseproject.interfaceadapter;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import br.com.joshua.baseproject.domain.Person;
import br.com.joshua.baseproject.interfaceadapter.controller.model.GetPersonResponse;

public interface PersonGateway {
	
	GetPersonResponse savePerson(Person person);
	
	GetPersonResponse updatePerson(Person person);
	
	List<GetPersonResponse> getAllPerson();
	
	GetPersonResponse findOnePerson(Long id);
	
	Page<GetPersonResponse> filter(
			@Nullable String name,
			@Nullable String email,
			@Nullable Integer page,
			@Nullable Integer size);
	
	void deletePerson(Long id);
	
	public Page<GetPersonResponse> searchAllPage(Integer page, Integer size, String wordSearch);

}
