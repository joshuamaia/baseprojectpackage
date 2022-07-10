package br.com.joshua.baseproject.businessrule.usecase;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import br.com.joshua.baseproject.businessrule.FilterPerson;
import br.com.joshua.baseproject.domain.Person;
import br.com.joshua.baseproject.interfaceadapter.repository.PersonRepository;
import br.com.joshua.baseproject.interfaceadapter.repository.model.PersonModel;
import br.com.joshua.baseproject.interfaceadapter.repository.model.PersonModelMapper;
import br.com.joshua.baseproject.interfaceadapter.repository.specification.PersonSpecification;

@Service
public class FilterPersonUC implements FilterPerson {

	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	PersonModelMapper personModelMapper;

	@Override
	public Page<Person> filter(@Nullable String name, @Nullable String email, @Nullable Integer page,
			@Nullable Integer size) {
		var specification = this.prepareSpecification(name, email);
		return this.personRepository.findAll(specification, this.preparePageable(PageRequest.of(page, size)))
				.map(personModelMapper::modelToEntity);
	}

	@NotNull
	private Specification<PersonModel> prepareSpecification(@Nullable String name, @Nullable String email) {
		final var specification = new PersonSpecification();

		return specification.and(specification.findByName(name)).and(specification.findByEmail(email));
	}

	private Pageable preparePageable(@Nullable Pageable pageable) {
		return Optional.ofNullable(pageable).orElse(Pageable.unpaged());
	}

}
