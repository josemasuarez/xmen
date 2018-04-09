package challenge.meli.model;

import java.util.Arrays;

import org.springframework.data.annotation.Id;

public class DNA {

	@Id
	private String id;

	private DNAType dnaType;

	public DNA(String[] dna) {
		this.id = Arrays.toString(dna);
	}

	public DNA() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DNAType getDnaType() {
		return dnaType;
	}

	public void setDnaType(DNAType dnaType) {
		this.dnaType = dnaType;
	}

}
