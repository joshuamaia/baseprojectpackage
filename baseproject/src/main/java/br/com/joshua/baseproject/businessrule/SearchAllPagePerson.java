package br.com.joshua.baseproject.businessrule;

import org.springframework.data.domain.Page;

import br.com.joshua.baseproject.domain.Person;

public interface SearchAllPagePerson {
	
	public Page<Person> searchAllPage(Integer page, Integer size, String wordSearch);

}
