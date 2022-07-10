package br.com.joshua.baseproject.interfaceadapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.joshua.baseproject.businessrule.DeletePerson;
import br.com.joshua.baseproject.businessrule.FilterPerson;
import br.com.joshua.baseproject.businessrule.FindOnePerson;
import br.com.joshua.baseproject.businessrule.GetAllPerson;
import br.com.joshua.baseproject.businessrule.SavePerson;
import br.com.joshua.baseproject.businessrule.SearchAllPagePerson;
import br.com.joshua.baseproject.businessrule.UpdatePerson;
import br.com.joshua.baseproject.domain.Person;
import br.com.joshua.baseproject.interfaceadapter.controller.model.GetPersonResponse;
import br.com.joshua.baseproject.interfaceadapter.controller.model.GetPersonResponseMapper;

@Service
public class PersonGatewayImpl implements PersonGateway {

	@Autowired
	private SavePerson savePerson;

	@Autowired
	private UpdatePerson updatePerson;

	@Autowired
	private GetAllPerson getAllPerson;

	@Autowired
	private FindOnePerson findOnePerson;

	@Autowired
	private FilterPerson filterPerson;

	@Autowired
	private DeletePerson deletePerson;

	@Autowired
	private SearchAllPagePerson searchAllPagePerson;

	@Autowired
	GetPersonResponseMapper getPersonResponseMapper;

	@Override
	public GetPersonResponse savePerson(Person person) {
		return getPersonResponseMapper.modelToEntity(savePerson.savePerson(person));
	}

	@Override
	public GetPersonResponse updatePerson(Person person) {
		return getPersonResponseMapper.modelToEntity(updatePerson.updatePerson(person));
	}

	@Override
	public List<GetPersonResponse> getAllPerson() {
		return getAllPerson.getAllPerson().stream().map(getPersonResponseMapper::modelToEntity)
				.collect(Collectors.toList());
	}

	@Override
	public GetPersonResponse findOnePerson(Long id) {
		return getPersonResponseMapper.modelToEntity(findOnePerson.findOnePerson(id));
	}

	@Override
	public Page<GetPersonResponse> filter(String name, String email, Integer page, Integer size) {
		return filterPerson.filter(name, email, page, size).map(getPersonResponseMapper::modelToEntity);
	}

	@Override
	public void deletePerson(Long id) {
		deletePerson.deletePerson(id);
	}

	@Override
	public Page<GetPersonResponse> searchAllPage(Integer page, Integer size, String wordSearch) {
		return searchAllPagePerson.searchAllPage(page, size, wordSearch).map(getPersonResponseMapper::modelToEntity);
	}

}
