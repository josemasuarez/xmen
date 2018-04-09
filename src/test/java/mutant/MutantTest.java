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
import challenge.meli.controller.dto.DnaDTO;
import challenge.meli.controller.dto.StatisticsDTO;
import challenge.meli.model.DNA;
import challenge.meli.model.DNAType;
import challenge.meli.repository.DnaRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { AppConfig.class, MongoConfig.class })
public class MutantTest {

	@Autowired
	private MutantController mutantController;

	@Autowired
	private DnaRepository dnaRepository;

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
		dnaRepository.deleteAll();

		String[] dna = { "ATGCGA", "CAGTGC", "TTTTAC", "AGACGG", "GGGGCA", "TCACTG" };

		DnaDTO dnaDTO = new DnaDTO(dna);

		ResponseEntity<DnaDTO> responseEntity = mutantController.checkMutantType(dnaDTO);

		Optional<DNA> mutant = dnaRepository.findById(Arrays.toString(dna));

		assertEquals(mutant.get().getId(), Arrays.toString(dnaDTO.getDna()));
		assertEquals(mutant.get().getDnaType(), DNAType.MUTANT);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

	}

	@Test
	public void testInsertHumanDatabase() {
		dnaRepository.deleteAll();

		String[] dna = { "ATGCGA", "CAGTGC", "TTTGAC", "AGACGG", "GGGGCA", "TCACTG" };

		DnaDTO humanDNA = new DnaDTO(dna);

		ResponseEntity<DnaDTO> responseEntity = mutantController.checkMutantType(humanDNA);

		Optional<DNA> human = dnaRepository.findById(Arrays.toString(dna));

		assertEquals(human.get().getId(), Arrays.toString(humanDNA.getDna()));
		assertEquals(human.get().getDnaType(), DNAType.HUMAN);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	public void testStatistics() {
		dnaRepository.deleteAll();

		String[] dnaHuman1 = { "ATGCGA", "CAGTGC", "TTTGAC", "AGACGG", "GGGGCA", "TCACTG" };
		String[] dnaHuman2 = { "ATGCGA", "CAGTGC", "TGTGAC", "AGACGG", "GGGGCA", "TCACTG" };
		String[] dnaHuman3 = { "ATGCGA", "CAGTGC", "TGTGAC", "AGACGG", "GGGCCA", "TCACTG" };
		String[] dnaMutant = { "ATGCGA", "CAGTGC", "TTTGAC", "AGACGG", "GGGGCA", "TTTTCG" };

		DnaDTO human1 = new DnaDTO(dnaHuman1);
		DnaDTO human2 = new DnaDTO(dnaHuman2);
		DnaDTO human3 = new DnaDTO(dnaHuman3);
		DnaDTO mutant = new DnaDTO(dnaMutant);

		mutantController.checkMutantType(human1);
		mutantController.checkMutantType(human2);
		mutantController.checkMutantType(human3);
		mutantController.checkMutantType(mutant);

		ResponseEntity<StatisticsDTO> responseEntity = mutantController.generateStatics();

		assertEquals(responseEntity.getBody().getHumanDnaCount(), new Long (3));
		assertEquals(responseEntity.getBody().getMutantDnaCount(), new Long(1));
		assertEquals(responseEntity.getBody().getRatio(), new Double(1/3));
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

	}

}
