package com.example.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Continent;
import com.example.demo.model.Country;
import com.example.demo.service.SearchService;
import com.example.demo.utils.Tracker;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value="/search")
public class SearchController {
	/*@Autowired
	private  SearchService searchService;*/
	
	private  SearchService searchService;

	@Autowired
	public SearchController(SearchService searchService) {
		this.searchService = searchService;
	}
	
	@GetMapping
	@Tracker
	public ResponseEntity<List<Continent>> getAll() {
		log.info("Request recevied for all");
		List<Continent> counterisList = searchService.getAll();
		if(counterisList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(counterisList);
		
	}
	
	@GetMapping("/continent/{continent}")
	@Tracker
	public ResponseEntity<List<Country>> getCountries(@PathVariable(value = "continent") String continent) {
		log.info("Request recevied for Countries");
		List<Country> countriesList = searchService.getCountries(continent);
		if(countriesList.isEmpty()) {
			log.info("No country found for: "+continent);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(countriesList);
	}
	
	@GetMapping("/country/{country}")
	@Tracker
	public ResponseEntity<String> getFlag(@PathVariable(value = "country") String country) {
		log.info("Request recevied for Flag");
		String flag = searchService.getFlag(country);
		if(flag == null) {
			log.info("No flag found for: "+country);
            return ResponseEntity.notFound().build();
        }
		return ResponseEntity.ok().body(flag);
	}

}
