package challenge.meli.model;

public enum DNAType {

	HUMAN("human"),
	MUTANT("mutant");
	
	private String dnaType;
	
	DNAType(String dnaType){
		this.dnaType = dnaType;
	}
	
	public String organismType(){
		return dnaType;
	}
}
