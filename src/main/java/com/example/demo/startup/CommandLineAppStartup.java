package com.example.demo.startup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.example.demo.api.SearchController;
import com.example.demo.model.Continent;
import com.example.demo.model.Country;
import com.example.demo.repo.ContinentRepository;

import lombok.extern.slf4j.Slf4j;


//@Component  // Enable this if you want to to have dummy data in DB manually added.
@Slf4j
public class CommandLineAppStartup implements CommandLineRunner {
    public static int counter;
    @Autowired
	ContinentRepository continentRepo;
    
    @Override
    public void run(String... args) throws Exception {
    	
    	Continent continent1 = new Continent("Asia");    	
    	Country count11 = new Country("India","🇮🇳");
    	count11.setFlag("IF");
    	count11.setContinent(continent1);
    	
    	Country count12 = new Country("China","🇨🇳");
    	count12.setFlag("AF");
    	count12.setContinent(continent1);
    	
    	List<Country> countriesList1 = new ArrayList<Country>();
    	countriesList1.add(count11);
    	countriesList1.add(count12);
    	
    	continent1.setCountries(countriesList1);
    	
    	
    	Continent continent2 = new Continent("America");    	
    	Country count21 = new Country("USA","🇺🇸");
    	count21.setFlag("UF");
    	count21.setContinent(continent2);
    	
    	Country count22 = new Country("Brazil","🇧🇷");
    	count22.setFlag("BF");
    	count22.setContinent(continent2);
    	
    	List<Country> countriesList2 = new ArrayList<Country>();
    	countriesList2.add(count21);
    	countriesList2.add(count22);
    	
    	continent2.setCountries(countriesList2);

    	continentRepo.saveAll((Arrays.asList(continent1, continent2)));
        log.info("Records inserted");
    }
}