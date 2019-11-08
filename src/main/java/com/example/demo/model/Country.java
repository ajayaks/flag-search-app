package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "country")
/*
  
  ALTER TABLE country CHANGE flag flag VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
 */
public class Country implements Serializable {
	

	private static final long serialVersionUID = 1L;
	@Id
	private String name;
	private String flag;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONT_ID")
	@JsonIgnore
	private Continent continent;
	
	public Country(String name, String flag) {
		this.name = name;
		this.flag = flag;
	}

}
