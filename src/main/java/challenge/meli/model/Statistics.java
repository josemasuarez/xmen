package challenge.meli.model;

public class Statistics {

	private Long count_mutant_dna = new Long(Long.MIN_VALUE);
	private Long count_human_dna = new Long(Long.MIN_VALUE);
	private Double ratio = new Double(Double.MIN_VALUE);

	public Long getCount_mutant_dna() {
		return count_mutant_dna;
	}

	public void setCount_mutant_dna(Long count_mutant_dna) {
		this.count_mutant_dna = count_mutant_dna;
	}

	public Long getCount_human_dna() {
		return count_human_dna;
	}

	public void setCount_human_dna(Long count_human_dna) {
		this.count_human_dna = count_human_dna;
	}

	public Double getRatio() {
		return ratio;
	}

	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}

	public Statistics(Long count_mutant_dna, Long count_human_dna ) {
		this.count_human_dna = count_human_dna;
		this.count_mutant_dna = count_mutant_dna;
		this.ratio = (double) ((this.count_human_dna > 0)?(this.count_mutant_dna/this.count_human_dna):0);
	}
}
