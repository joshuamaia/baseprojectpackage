package br.com.joshua.baseproject.interfaceadapter.repository.specification;

import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import br.com.joshua.baseproject.interfaceadapter.repository.model.PersonModel;

public class PersonSpecification extends SpecificationBase<PersonModel> {

	private static final long serialVersionUID = 1L;

	public Specification<PersonModel> findByName(@Nullable String nome) {
		return Optional.ofNullable(nome).map(n -> prepareLikeSpecification("name", n)).orElse(null);
	}

	public Specification<PersonModel> findByEmail(@Nullable String email) {
		return Optional.ofNullable(email).map(n -> prepareLikeSpecification("email", n)).orElse(null);
	}

}
