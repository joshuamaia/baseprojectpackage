package br.com.joshua.baseproject.businessrule.usecase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joshua.baseproject.businessrule.FindOnePerson;
import br.com.joshua.baseproject.domain.Person;
import br.com.joshua.baseproject.interfaceadapter.repository.PersonRepository;
import br.com.joshua.baseproject.interfaceadapter.repository.model.PersonModel;
import br.com.joshua.baseproject.interfaceadapter.repository.model.PersonModelMapper;

@Service
public class FindOnePersonUC implements FindOnePerson {

	@Autowired
	PersonRepository personRepository;

	@Autowired
	PersonModelMapper personModelMapper;

	@Override
	public Person findOnePerson(Long id) {
		Optional<PersonModel> e = personRepository.findById(id);
		if (!e.isPresent()) {
			throw new RuntimeException("Entity not present!");
		}
		return personModelMapper.modelToEntity(e.get());
	}

}
