package br.com.joshua.baseproject.interfaceadapter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.joshua.baseproject.interfaceadapter.repository.model.PersonModel;

public interface PersonRepository extends JpaRepository<PersonModel, Long>, JpaSpecificationExecutor<PersonModel> {

	@Query("FROM PersonModel p WHERE LOWER(p.name) like %:wordSearch% OR LOWER(p.email) like %:wordSearch%")
	Page<PersonModel> searchAllPage(@Param("wordSearch") String wordSearch, Pageable pageable);

}
