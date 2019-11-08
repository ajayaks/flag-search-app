package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Continent;
import com.example.demo.model.Country;

@Repository  
public interface ContinentRepository extends JpaRepository<Continent, String>{
		  public List<Continent> findAll();
		  
		  public Continent findByContinent(String continentName);


}
