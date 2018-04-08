package challenge.meli.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import challenge.meli.MutantChecker;
import challenge.meli.model.Organism;
import challenge.meli.model.OrganismType;
import challenge.meli.model.Statistics;
import challenge.meli.repository.OrganismRepository;

@RestController
public class MutantController {

	@Autowired
	private OrganismRepository organismRepository;

	@PostMapping(value = "/mutant")
	public ResponseEntity<Organism> checkMutantDna(@RequestBody Organism organism) {
		MutantChecker mutantChecker = new MutantChecker();

		try {
			if (!mutantChecker.isMutant(organism.getDna())) {
				organism.setOrganismType(OrganismType.HUMAN);
				organism.setId(Arrays.toString(organism.getDna()));
				organismRepository.save(organism);
				return new ResponseEntity<Organism>(HttpStatus.FORBIDDEN);
			}
			organism.setOrganismType(OrganismType.MUTANT);
			organism.setId(Arrays.toString(organism.getDna()));
			organismRepository.save(organism);
			
			return new ResponseEntity<Organism>(HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<Organism>(HttpStatus.FORBIDDEN);
		}

	}
	
	@RequestMapping(value = "/stats")
	public ResponseEntity<Statistics> generateStatics(){
		
		Long humanCount = organismRepository.countByOrganismType(OrganismType.HUMAN.name());
		Long mutantCount = organismRepository.countByOrganismType(OrganismType.MUTANT.name()); 
		
		return new ResponseEntity<Statistics>(new Statistics(mutantCount, humanCount),HttpStatus.OK);
		
	}

}
