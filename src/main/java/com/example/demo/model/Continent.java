package com.example.demo.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "continent")
public class Continent implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CONT_ID")
    private String continent;
	
	@OneToMany(mappedBy = "continent",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Country> countries;
	
	public Continent(String continent) {
		this.continent = continent;
	}
}
