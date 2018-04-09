package mutant;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import challenge.meli.MutantChecker;
import challenge.meli.config.AppConfig;
import challenge.meli.config.MongoConfig;
import challenge.meli.controller.MutantController;
import challenge.meli.model.Organism;
import challenge.meli.model.OrganismType;
import challenge.meli.model.Statistics;
import challenge.meli.repository.OrganismRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { AppConfig.class, MongoConfig.class })
public class MutantTest {

	@Autowired
	private MutantController mutantController;

	@Autowired
	private OrganismRepository organismRepository;

	@Test
	public void testWhenIsMutantInMatrixRows() throws Exception {
		String[] mutantDna = { "ATGCGA", "CAGTGC", "TTTTAC", "AGACGG", "GGGGCA", "TCACTG" };
		MutantChecker mutantChecker = new MutantChecker();
		assertEquals(mutantChecker.isMutant(mutantDna), true);
	}

	@Test
	public void testWhenIsMutantInMatrixColumns() throws Exception {
		String[] mutantDna = { "ATGCGA", "CTGTGC", "TTGAAC", "ATGCGG", "GGGCCA", "TCACTG" };
		MutantChecker mutantChecker = new MutantChecker();
		assertEquals(mutantChecker.isMutant(mutantDna), true);
	}

	@Test
	public void testWhenIsMutantInMatrixOblique() throws Exception {
		String[] mutantDna = { "ATGCGA", "CTGTAC", "TTGAAC", "ATACGG", "GAGCCA", "TCACTG" };
		MutantChecker mutantChecker = new MutantChecker();
		assertEquals(mutantChecker.isMutant(mutantDna), true);
	}

	@Test(expected = Exception.class)
	public void testInvalidBaseMatrix() throws Exception {
		String[] mutantDna = { "ATGCGA", "CTGXAC", "TTGAAC", "ATACGG", "GAGCCA", "TCACTG" };
		MutantChecker mutantChecker = new MutantChecker();
		mutantChecker.isMutant(mutantDna);
	}

	@Test(expected = Exception.class)
	public void testInvalidMatrixAditionalRow() throws Exception {
		String[] mutantDna = { "ATGCGA", "CTGXAC", "TTGAAC", "ATACGG", "GAGCCA", "TCACTG", "AAAAAA" };
		MutantChecker mutantChecker = new MutantChecker();
		mutantChecker.isMutant(mutantDna);
	}
	
	@Test(expected = Exception.class)
	public void testInvalidMatrixAditionalColumn() throws Exception {
		String[] mutantDna = { "ATGCGA", "CTGXAC", "TTGAAAC", "ATACGG", "GAGCCA", "TCACTG", "AAAAAA" };
		MutantChecker mutantChecker = new MutantChecker();
		mutantChecker.isMutant(mutantDna);
	}

	@Test
	public void testInsertMutantDatabase() {
		organismRepository.deleteAll();

		String[] dna = { "ATGCGA", "CAGTGC", "TTTTAC", "AGACGG", "GGGGCA", "TCACTG" };

		Organism organism = new Organism(dna);

		ResponseEntity<Organism> responseEntity = mutantController.checkMutantDna(organism);

		Optional<Organism> mutant = organismRepository.findById(Arrays.toString(dna));

		assertEquals(mutant.get().getId(), organism.getId());
		assertEquals(mutant.get().getOrganismType(), OrganismType.MUTANT);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

	}

	@Test
	public void testInsertHumanDatabase() {
		organismRepository.deleteAll();

		String[] dna = { "ATGCGA", "CAGTGC", "TTTGAC", "AGACGG", "GGGGCA", "TCACTG" };

		Organism organism = new Organism(dna);

		ResponseEntity<Organism> responseEntity = mutantController.checkMutantDna(organism);

		Optional<Organism> human = organismRepository.findById(Arrays.toString(dna));

		assertEquals(human.get().getId(), organism.getId());
		assertEquals(human.get().getOrganismType(), OrganismType.HUMAN);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.FORBIDDEN);

	}
	
	@Test
	public void testStats() {
		organismRepository.deleteAll();

		String[] dnaHuman1 = { "ATGCGA", "CAGTGC", "TTTGAC", "AGACGG", "GGGGCA", "TCACTG" };
		String[] dnaHuman2 = { "ATGCGA", "CAGTGC", "TGTGAC", "AGACGG", "GGGGCA", "TCACTG" };
		String[] dnaHuman3 = { "ATGCGA", "CAGTGC", "TGTGAC", "AGACGG", "GGGCCA", "TCACTG" };
		String[] dnaMutant = { "ATGCGA", "CAGTGC", "TTTGAC", "AGACGG", "GGGGCA", "TTTTCG" };

		Organism humanOrganism1 = new Organism(dnaHuman1);
		Organism humanOrganism2 = new Organism(dnaHuman2);
		Organism humanOrganism3 = new Organism(dnaHuman3);
		Organism mutantOrganism = new Organism(dnaMutant);

		mutantController.checkMutantDna(humanOrganism1);
		mutantController.checkMutantDna(humanOrganism2);
		mutantController.checkMutantDna(humanOrganism3);
		mutantController.checkMutantDna(mutantOrganism);

		ResponseEntity<Statistics> responseEntity = mutantController.generateStatics();

		assertEquals(responseEntity.getBody().getCount_human_dna(), new Long (3));
		assertEquals(responseEntity.getBody().getCount_mutant_dna(), new Long(1));
		assertEquals(responseEntity.getBody().getRatio(), new Double(1/3));
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

	}

}
