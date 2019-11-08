package com.example.demo.startup;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.model.Continent;
import com.example.demo.repo.ContinentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;



@Slf4j
//@Component  //Enable this if you have DB and you want to insert Data from Json to DB, via spring data.
public class CommandLineJsonToDbStartup implements CommandLineRunner{

    @Autowired
	ContinentRepository continentRepo;
    
    @Override
    public void run(String... args) throws Exception {
    	
    	ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<Continent>> typeReference = new TypeReference<List<Continent>>(){};
		InputStream inputStream = TypeReference.class.getResourceAsStream("/json/continents.json");
		try {
			List<Continent> continentsList = mapper.readValue(inputStream,typeReference);
			for(Continent continent: continentsList) {
				continent.getCountries().stream().forEach(s -> s.setContinent(continent));
			}
			continentRepo.saveAll(continentsList);
			log.info("Records persisted");
		} catch (IOException e){
			log.error("Unable to save records: " + e.getMessage());
		}
    }
}
