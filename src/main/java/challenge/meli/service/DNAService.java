package challenge.meli.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import challenge.meli.MutantChecker;
import challenge.meli.controller.dto.StatisticsDTO;
import challenge.meli.model.DNA;
import challenge.meli.model.DNAType;
import challenge.meli.repository.DnaRepository;

@Service
public class DNAService {

	@Autowired
	private DnaRepository dnaRepository;

	public Boolean isMutant(String[] aDNA) throws Exception {
		MutantChecker mutantChecker = new MutantChecker();
		DNA dna = new DNA();
		dna.setId(Arrays.toString(aDNA));
		try {
			if (!mutantChecker.isMutant(aDNA)) {
				dna.setDnaType(DNAType.HUMAN);
				dnaRepository.save(dna);
				return false;
			}
				
		} catch (Exception e) {
			throw e;
		}
		dna.setDnaType(DNAType.MUTANT);
		dnaRepository.save(dna);
		return true;
	
	}
	
	public StatisticsDTO generateStatistics(){
		StatisticsDTO statisticsDTO = new StatisticsDTO(dnaRepository.countByDnaType(DNAType.MUTANT.name()), dnaRepository.countByDnaType(DNAType.HUMAN.name()));
	
		return statisticsDTO;
		
	}

}
