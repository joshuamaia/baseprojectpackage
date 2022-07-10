package br.com.joshua.baseproject.businessrule.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.joshua.baseproject.businessrule.SearchAllPagePerson;
import br.com.joshua.baseproject.domain.Person;
import br.com.joshua.baseproject.interfaceadapter.repository.PersonRepository;
import br.com.joshua.baseproject.interfaceadapter.repository.model.PersonModelMapper;

@Service
public class SearchAllPagePersonUC implements SearchAllPagePerson {

	@Autowired
	PersonRepository personRepository;

	@Autowired
	PersonModelMapper personModelMapper;

	@Override
	public Page<Person> searchAllPage(Integer page, Integer size, String wordSearch) {
		PageRequest pageRequest = PageRequest.of(page, size);
		if (wordSearch == null || wordSearch.trim().isEmpty()) {
			return personRepository.findAll(pageRequest).map(personModelMapper::modelToEntity);
		}
		wordSearch = wordSearch.toLowerCase();
		return personRepository.searchAllPage(wordSearch, pageRequest).map(personModelMapper::modelToEntity);

	}

}
