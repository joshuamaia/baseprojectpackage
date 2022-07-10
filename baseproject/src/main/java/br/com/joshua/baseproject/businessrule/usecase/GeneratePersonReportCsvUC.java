package br.com.joshua.baseproject.businessrule.usecase;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joshua.baseproject.businessrule.GeneratePersonReportCsv;
import br.com.joshua.baseproject.interfaceadapter.repository.PersonRepository;
import br.com.joshua.baseproject.interfaceadapter.repository.model.PersonModel;

@Service
public class GeneratePersonReportCsvUC implements GeneratePersonReportCsv {
	
	@Autowired
	private PersonRepository repository;
	
	@Override
	public byte[] generatePersonReportCsv() {
		StringBuilder str = new StringBuilder();		
		List<PersonModel> persons = repository.findAll();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		str.append("Name,E-mail,BirthDate");
		
		for (PersonModel person : persons) {
			str.append(System.lineSeparator());
			
			str.append(person.getName()).append(",").append(person.getEmail()).append(",").append(person.getBirthDate().format(formatter));
		}
		
		Charset charset = StandardCharsets.UTF_8;
		return str.toString().getBytes(charset);
	}


}
