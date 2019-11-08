package com.example.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.utils.SearchTracker;
import com.example.demo.utils.Tracker;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value="/admin")
public class AdminController {
	
	private SearchTracker tracker;
	
	@Autowired
	public AdminController(SearchTracker tracker) {
		this.tracker = tracker;
	}
	
	@GetMapping("/tracker")
	@Tracker
	public ResponseEntity<String> getApiTracker() {
		log.info("Request recevied for Apitracker");
		String json = tracker.getApiTracker();
		if(json.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(json);
		
	}

}
