package com.example.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.api.SearchController;
import com.example.demo.model.Continent;
import com.example.demo.model.Country;
import com.example.demo.repo.ContinentRepository;
import com.example.demo.repo.CountryRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {
	private static List<Continent> continentsList = null ;
	static {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<Continent>> typeReference = new TypeReference<List<Continent>>(){};
		InputStream inputStream = TypeReference.class.getResourceAsStream("/json/continents.json");
		try {
			continentsList = mapper.readValue(inputStream,typeReference);
			for(Continent continent: continentsList) {
				continent.getCountries().stream().forEach(s -> s.setContinent(continent));
			}
			log.info("Records loaded into cache");
		} catch (IOException e){
			log.error("Unable to load the records: " + e.getMessage());
		}
	}
	
	@Autowired
	ContinentRepository continentRepo;

	@Autowired
	CountryRepository countryRepo;

	//Given support to work with DB and without DB
	public List<Continent> getAll() {
		log.info("Serving all data");

		//return continentRepo.findAll(); //Uncomment this for DB
		return continentsList; //Comment this if using DB.
	};

	//Given support to work with DB and without DB
	public List<Country> getCountries(String continentName) {
		log.info("Serving all countries");
		List<Country> countriesList = new ArrayList<Country>();
		
		//Continent  continent = continentRepo.findByContinent(continentName); //Uncomment this for DB
		Continent  continent =continentsList.stream().filter(s -> s.getContinent().equalsIgnoreCase(continentName)).findFirst().orElse(null); //Comment this if using DB.
		
		if( continent != null) {
			//In case @JsonIgore not in use on countries property
			//countriesList = continent.getCountries().stream().map(s -> new Country(s.getName(), s.getFlag())).collect(Collectors.toList());
			countriesList =  continent.getCountries();
		}
		return countriesList;
	};

	public String getFlag(String countryName) {
		String flag = null;
		log.info("Serving country flag");
		//Country country = countryRepo.findFlagByName(countryName); //Uncomment this for DB
		
		//Comment these 3 lines if using DB.
		List<Country>  countriesList = new ArrayList<Country>();
		continentsList.stream().forEach(s -> countriesList.addAll(s.getCountries()));
		Country country = countriesList.stream().
				filter(s -> s.getName().equalsIgnoreCase(countryName)).findFirst().orElse(null); 
		
		if(country != null) {
			flag = country.getFlag();
		}
		return flag;
	};
}
