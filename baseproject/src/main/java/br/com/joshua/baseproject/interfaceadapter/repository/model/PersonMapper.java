package br.com.joshua.baseproject.interfaceadapter.repository.model;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.joshua.baseproject.domain.Person;

@Component
public class PersonMapper {

	@Autowired
	private ModelMapper modelMapper;

	public PersonModel modelToEntity(Person model) {
		return modelMapper.map(model, PersonModel.class);
	}

}
