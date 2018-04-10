package challenge.meli.test;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import challenge.meli.controller.dto.DnaDTO;
import challenge.meli.repository.DnaRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class XmenApplicationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private MockMvc mockMvc;
	

	@Autowired
	private DnaRepository dnaRepository;

	@Test
	public void mutantShouldReturnOkSendingMutantDNA() throws Exception {
		String[] mutantDna = { "ATGCGA", "CAGTGC", "TTTTAC", "AGACGG", "GGGGCA", "TCACTG" };

		DnaDTO dnaDTO = new DnaDTO();
		dnaDTO.setDna(mutantDna);

		Gson gson = new Gson();
		String json = gson.toJson(dnaDTO);

		this.mockMvc.perform(post("/mutant").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk());

	}

	@Test
	public void mutantShouldReturnForbidennSendingHumanDNA() throws Exception {
		String[] humanDna = { "ATGCGA", "CAGTGC", "TTTAAC", "AGACGG", "GGGGCA", "TCACTG" };

		DnaDTO dnaDTO = new DnaDTO();
		dnaDTO.setDna(humanDna);

		Gson gson = new Gson();
		String json = gson.toJson(dnaDTO);

		this.mockMvc.perform(post("/mutant").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().is4xxClientError());

	}

	@Test
	public void statsShouldReturnValidStatistics() throws Exception{
		
		dnaRepository.deleteAll();
		
		String[] humanDna1 = { "ATGCGA", "CAGTGC", "TTTAAC", "AGACGG", "GGGGCA", "TCACTG" };
		String[] humanDna2 = { "GTGCGA", "CAGTCC", "TTCAAC", "AGACGG", "GGGGCA", "TCACTG" };
		String[] mutantDna = { "ATGCGA", "CAGTGC", "TTTTAC", "AGACGG", "GGGGCA", "TCACTG" };
		
		DnaDTO humanDna1DTO = new DnaDTO();
		humanDna1DTO.setDna(humanDna1);
		
		DnaDTO humanDna2DTO = new DnaDTO();
		humanDna2DTO.setDna(humanDna2);
		
		DnaDTO mutantDnaDTO = new DnaDTO();
		mutantDnaDTO.setDna(mutantDna);
		
		Gson gson = new Gson();
		String jsonHumanDna1 = gson.toJson(humanDna1DTO);
		String jsonHumanDna2 = gson.toJson(humanDna2DTO);
		String jsonMutantDna = gson.toJson(mutantDnaDTO);
		
		this.mockMvc.perform(post("/mutant").contentType(MediaType.APPLICATION_JSON).content(jsonHumanDna1));
		this.mockMvc.perform(post("/mutant").contentType(MediaType.APPLICATION_JSON).content(jsonHumanDna2));
		this.mockMvc.perform(post("/mutant").contentType(MediaType.APPLICATION_JSON).content(jsonMutantDna));
		
		
		this.mockMvc.perform(get("/stats"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.count_mutant_dna", is(1)))
			.andExpect(jsonPath("$.count_human_dna", is(2)))
			.andExpect(jsonPath("$.ratio", is(0.5)));

		
	}
	
	@Test
	public void mutantShouldReturnInternalServerErrorSendingInvalidDNA() throws Exception {
		String[] humanDna = { "ATGCGA", "CAGTGC", "TTTAAC", "AGXCGG", "GGGGCA", "TCACTG" };

		DnaDTO dnaDTO = new DnaDTO();
		dnaDTO.setDna(humanDna);

		Gson gson = new Gson();
		String json = gson.toJson(dnaDTO);

		this.mockMvc.perform(post("/mutant").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().is5xxServerError());

	}

}
