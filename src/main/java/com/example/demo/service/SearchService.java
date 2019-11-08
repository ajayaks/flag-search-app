package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Continent;
import com.example.demo.model.Country;

public interface SearchService {
	List<Continent> getAll();
	List<Country> getCountries(String continent);
	String getFlag(String country);

}
