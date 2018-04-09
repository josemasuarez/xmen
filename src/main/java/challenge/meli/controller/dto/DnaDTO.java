package challenge.meli.controller.dto;

public class DnaDTO {
	
	private String[] dna;

	public String[] getDna() {
		return dna;
	}

	public void setDna(String[] dna) {
		this.dna = dna;
	}
	
	public DnaDTO(String[] dna) {
		this.dna = dna;
	}
	
	public DnaDTO() {
	
	}
	
}
