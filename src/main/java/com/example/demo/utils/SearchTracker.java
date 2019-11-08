package com.example.demo.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SearchTracker {

	private static Map<String, Integer> apisTracker = new HashMap<String, Integer>();

	public void setApiTracker(String api) {
		if (apisTracker.get(api) != null) {
			apisTracker.put(api, apisTracker.get(api) + 1);
		} else {
			apisTracker.put(api, 1);
		}

	}

	public String getApiTracker() {
		String json = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			json = objectMapper.writeValueAsString(apisTracker);
		} catch (Exception e) {
			log.error("Error in converting to json");
		}
		return json;
	}

}
