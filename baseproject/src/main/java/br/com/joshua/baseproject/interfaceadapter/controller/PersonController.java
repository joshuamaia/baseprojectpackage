package br.com.joshua.baseproject.interfaceadapter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.joshua.baseproject.domain.Person;
import br.com.joshua.baseproject.interfaceadapter.PersonGateway;
import br.com.joshua.baseproject.interfaceadapter.controller.model.GetPersonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

	@Autowired
	private PersonGateway personGateway;

	@Operation(summary = "Search all Persons")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the List of PersonModel", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetPersonResponse.class))) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content) })
	@GetMapping("/list")
	public ResponseEntity<List<GetPersonResponse>> getAll() {
		List<GetPersonResponse> persons = personGateway.getAllPerson();
		return new ResponseEntity<List<GetPersonResponse>>(persons, HttpStatus.OK);
	}

	@Operation(summary = "Search all Persons pagened")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the List of PersonModel", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonPage.class))),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content) })
	@GetMapping(value = { "/{page}/{size}", "/{page}/{size}/{wordSearch}" })
	public ResponseEntity<Page<GetPersonResponse>> getAll(@PathVariable Integer page, @PathVariable Integer size,
			@PathVariable(required = false) String wordSearch) {
		Page<GetPersonResponse> persons = personGateway.searchAllPage(page, size, wordSearch);
		return new ResponseEntity<Page<GetPersonResponse>>(persons, HttpStatus.OK);
	}

	@Operation(summary = "Search all Persons pagened")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the List of PersonModel", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonPage.class))),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content) })
	@GetMapping("/filter")
	public ResponseEntity<Page<GetPersonResponse>> filter(@RequestParam(required = false) String name,
			@RequestParam(required = false) String email, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) {

		var persons = this.personGateway.filter(name, email, page, size);
		return ResponseEntity.ok().body(persons);
	}

	@Operation(summary = "Create PersonModel")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "PersonModel created with sucessful", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = GetPersonResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content) })
	@PostMapping
	public ResponseEntity<GetPersonResponse> create(@RequestBody Person person) {
		GetPersonResponse personSave = personGateway.savePerson(person);
		return new ResponseEntity<GetPersonResponse>(personSave, HttpStatus.CREATED);
	}

	@Operation(summary = "Update PersonModel")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "PersonModel updated with sucessful", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = GetPersonResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content) })
	@PutMapping
	public ResponseEntity<GetPersonResponse> update(@RequestBody Person person) {
		GetPersonResponse personUpdate = personGateway.updatePerson(person);
		return new ResponseEntity<GetPersonResponse>(personUpdate, HttpStatus.OK);
	}

	@Operation(summary = "Search PersonModel By id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the PersonModel", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Person.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content) })
	@GetMapping("/{id}")
	public ResponseEntity<GetPersonResponse> getOneById(@PathVariable Long id) {
		GetPersonResponse person = personGateway.findOnePerson(id);
		return new ResponseEntity<GetPersonResponse>(person, HttpStatus.OK);
	}

	@Operation(summary = "Delete PersonModel By id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "No Content", description = "Delete PersonModel", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content) })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		personGateway.deletePerson(id);
		return ResponseEntity.noContent().build();
	}

	class PersonPage extends PageImpl<GetPersonResponse> {
		public PersonPage(List<GetPersonResponse> content, Pageable pageable, long total) {
			super(content, pageable, total);
		}
	}
}
