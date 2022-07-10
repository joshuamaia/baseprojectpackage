package br.com.joshua.baseproject.businessrule.usecase;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joshua.baseproject.businessrule.GetAllPerson;
import br.com.joshua.baseproject.domain.Person;
import br.com.joshua.baseproject.interfaceadapter.repository.PersonRepository;
import br.com.joshua.baseproject.interfaceadapter.repository.model.PersonModelMapper;

@Service
public class GetAllPersonUC implements GetAllPerson {

	@Autowired
	PersonRepository personRepository;

	@Autowired
	PersonModelMapper personModelMapper;

	@Override
	public List<Person> getAllPerson() {
		return personRepository.findAll().stream().map(personModelMapper::modelToEntity).collect(Collectors.toList());
	}

}
