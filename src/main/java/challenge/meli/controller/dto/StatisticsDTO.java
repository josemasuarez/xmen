package challenge.meli.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatisticsDTO {
	
	private Long mutantDnaCount = Long.MIN_VALUE;
	private Long humanDnaCount = Long.MIN_VALUE;
	private Double ratio = Double.MIN_VALUE;


	@JsonProperty("count_mutant_dna")
	public Long getMutantDnaCount() {
		return mutantDnaCount;
	}


	public void setMutantDnaCount(Long mutantDnaCount) {
		this.mutantDnaCount = mutantDnaCount;
	}


	@JsonProperty("count_human_dna")
	public Long getHumanDnaCount() {
		return humanDnaCount;
	}


	public void setHumanDnaCount(Long humanDnaCount) {
		this.humanDnaCount = humanDnaCount;
	}


	public Double getRatio() {
		return ratio;
	}


	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}


	public StatisticsDTO(Long mutantDnaCount, Long humanDnaCount ) {
		this.humanDnaCount = humanDnaCount;
		this.mutantDnaCount = mutantDnaCount;
		this.ratio = ((this.humanDnaCount > 0)?(new Double(this.mutantDnaCount)/new Double(this.humanDnaCount)):0);
	}

}
