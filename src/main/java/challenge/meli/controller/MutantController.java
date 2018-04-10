package challenge.meli.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import challenge.meli.controller.dto.DnaDTO;
import challenge.meli.controller.dto.StatisticsDTO;
import challenge.meli.service.DNAService;

@RestController
public class MutantController {

	@Autowired
	private DNAService dnaService;

	/**
	 * Returns a Http code 200 if the DNA requested is mutant and returns a Http
	 * code 403 if the DNA requested is human
	 * <p>
	 *
	 * @param Json
	 *            with a String array which has a 6x6 matrix
	 * @return Http Status
	 */
	@PostMapping(value = "/mutant")
	public ResponseEntity<DnaDTO> checkMutantType(@RequestBody DnaDTO aDNA) {

		try {
			if (!dnaService.isMutant(aDNA.getDna())){
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}else{
				return new ResponseEntity<>(HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<DnaDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Returns a Json with Human and Mutant statistics which has how many
	 * mutants and human there are in the database and also a ratio.
	 * <p>
	 *
	 * @return Json Statistics
	 */

	@RequestMapping(value = "/stats")
	public ResponseEntity<StatisticsDTO> generateStatics() {

		return new ResponseEntity<StatisticsDTO>(dnaService.generateStatistics(), HttpStatus.OK);

	}

}
