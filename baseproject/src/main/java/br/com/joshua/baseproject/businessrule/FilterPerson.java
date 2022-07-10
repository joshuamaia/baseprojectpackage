package br.com.joshua.baseproject.businessrule;

import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import br.com.joshua.baseproject.domain.Person;

public interface FilterPerson {
	
	public Page<Person> filter(
			@Nullable String name,
			@Nullable String email,
			@Nullable Integer page,
			@Nullable Integer size);

}
