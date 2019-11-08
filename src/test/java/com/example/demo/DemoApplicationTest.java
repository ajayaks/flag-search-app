package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.api.AdminController;
import com.example.demo.api.SearchController;
import com.example.demo.model.Continent;
import com.example.demo.model.Country;
import com.example.demo.repo.ContinentRepository;
import com.example.demo.repo.CountryRepository;
import com.example.demo.service.SearchService;
import com.example.demo.utils.SearchTracker;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
@Slf4j

public class DemoApplicationTest {

	@MockBean
	private ContinentRepository continentRepo;

	@MockBean
	private CountryRepository countryRepo;

	@MockBean
	private SearchService searchService;

	@Autowired
	SearchController searchController;
	@MockBean
	private SearchTracker tracker;
	
	@Autowired
	AdminController adminController;

	List<Continent> continentsList;

	@Autowired
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<Continent>> typeReference = new TypeReference<List<Continent>>(){};
		InputStream inputStream = TypeReference.class.getResourceAsStream("/json/continents.json");
		try {
			List<Continent> continentsList = mapper.readValue(inputStream,typeReference);
			for(Continent continent: continentsList) {
				continent.getCountries().stream().forEach(s -> s.setContinent(continent));
			}
			this.continentsList= continentsList;
			log.info("Records loaded into cache");
		} catch (IOException e){
			log.error("Unable to load the records: " + e.getMessage());
		}
	}

	@Test
	public void testGetAllData() throws Exception {

		Mockito.when(searchService.getAll()).thenReturn(continentsList);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/search").accept(MediaType.APPLICATION_JSON);
		
		ResultActions resultActions = mockMvc.perform(requestBuilder);
		MvcResult result = resultActions.andReturn();

		resultActions.andExpect(MockMvcResultMatchers.status().isOk())
				// .andExpect(MockMvcResultMatchers.content().)
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void testGetCountries() throws Exception {
		
		Continent  continent  = continentsList.stream().filter(s -> s.getContinent().equalsIgnoreCase("Asia")).findFirst().orElse(null);
		
		Mockito.when(searchService.getCountries(Mockito.anyString())).thenReturn(continent.getCountries());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/search/continent/{continent}", "Asia")
				.accept(MediaType.APPLICATION_JSON);

		ResultActions resultActions = mockMvc.perform(requestBuilder);
		MvcResult result = resultActions.andReturn();
		
		resultActions.andExpect(MockMvcResultMatchers.status().isOk())
				// .andExpect(MockMvcResultMatchers.content().)
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void testGetFlag() throws Exception {

		Mockito.when(searchService.getFlag(Mockito.anyString())).thenReturn("🇮🇳");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/search/country/{country}", "India")
				.accept(MediaType.APPLICATION_JSON);

		ResultActions resultActions = mockMvc.perform(requestBuilder);
		MvcResult result = resultActions.andReturn();
		System.out.println("Response >>>>" + result.getResponse().getContentAsString());

	}

}
