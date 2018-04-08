package challenge.meli.model;

public enum OrganismType {

	HUMAN("human"),
	MUTANT("mutant");
	
	private String organismType;
	
	OrganismType(String organismType){
		this.organismType = organismType;
	}
	
	public String organismType(){
		return organismType;
	}
}
