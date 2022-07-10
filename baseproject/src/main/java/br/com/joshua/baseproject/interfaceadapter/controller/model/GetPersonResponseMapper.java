package br.com.joshua.baseproject.interfaceadapter.controller.model;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.joshua.baseproject.domain.Person;

@Component
public class GetPersonResponseMapper {

	@Autowired
	private ModelMapper modelMapper;

	public GetPersonResponse modelToEntity(Person model) {
		return modelMapper.map(model, GetPersonResponse.class);
	}

}
