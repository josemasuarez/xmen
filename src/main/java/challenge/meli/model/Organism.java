package challenge.meli.model;

import java.util.Arrays;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Organism {

	@JsonIgnore
	@Id
	private String id;

	private String[] dna;

	@JsonIgnore
	private OrganismType organismType;

	public String getId() {
		return id;
	}

	public Organism(String[] dna) {
		this.dna = dna;
		this.id = Arrays.toString(dna);
	}
	
	public Organism() {
		
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getDna() {
		return dna;
	}

	public void setDna(String[] dna) {
		this.dna = dna;
	}

	public OrganismType getOrganismType() {
		return organismType;
	}

	public void setOrganismType(OrganismType organismType) {
		this.organismType = organismType;
	}

}
