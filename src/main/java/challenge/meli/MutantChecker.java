package challenge.meli;

import java.util.ArrayList;
import java.util.List;

public class MutantChecker {

	private static final int MATRIX_COLUMNS = 6;
	private static final int MATRIX_ROWS = 6;
	private static final String NITROGENEOUS_BASES = "ACGT";
	private static final String MUTANT_SEQUENCE_A = "AAAA";
	private static final String MUTANT_SEQUENCE_T = "TTTT";
	private static final String MUTANT_SEQUENCE_G = "GGGG";
	private static final String MUTANT_SEQUENCE_C = "CCCC";

	public boolean isMutant(String[] dnaRows) throws Exception {

		// String[] dnaRows = { "ATGCGA", "CAGTGC", "TTATTT", "AGACGG",
		// "GCGTCA", "TCACTG" };
		// String[] dnaRows = { "AAAAGA", "CCCCGC", "TTATTT", "AGACGG",
		// "GCGTCA", "TCACTG" };

		if (dnaRows.length != MATRIX_ROWS)
			throw new Exception("Invalid matrix");

		String[] dnaColumns = buildColumnSequence(dnaRows);
		String[] dnaOblique = buildObliqueSequence(dnaRows);

		return buildDnaCombinations(dnaRows, dnaColumns, dnaOblique).stream()
				.filter(sequence -> sequence.contains(MUTANT_SEQUENCE_A) || sequence.contains(MUTANT_SEQUENCE_T) || sequence.contains(MUTANT_SEQUENCE_G)
						|| sequence.contains(MUTANT_SEQUENCE_C))
				.count() > 1;
	}

	private List<String> buildDnaCombinations(String[] dnaRows, String[] dnaColumns, String[] dnaOblique) {
		List<String> dnaCombinationList = new ArrayList<>();
		fillCombinationsListFromArray(dnaRows, dnaCombinationList);
		fillCombinationsListFromArray(dnaColumns, dnaCombinationList);
		fillCombinationsListFromArray(dnaOblique, dnaCombinationList);

		return dnaCombinationList;
	}

	private void fillCombinationsListFromArray(String[] dnaArray, List<String> dnaCombinationsList) {
		for (int i = 0; i < dnaArray.length; i++) {
			dnaCombinationsList.add(dnaArray[i]);
		}
	}

	private String[] buildColumnSequence(String[] dna) throws Exception {

		String[] dnaColumns = { "", "", "", "", "", "" };

		for (int c = 0; c < MATRIX_COLUMNS; c++) {
			for (int i = 0; i < dna.length; i++) {

				if (dna[i].length() != MATRIX_COLUMNS)
					throw new Exception("Invalid matrix");

				String base = String.valueOf(dna[i].charAt(c));

				if (!NITROGENEOUS_BASES.contains(base)) {
					throw new Exception("Invalid base");
				}
				dnaColumns[c] = dnaColumns[c].concat(base);
			}
		}

		return dnaColumns;
	}

	private String[] buildObliqueSequence(String[] dna) throws Exception {

		String[] obliqueSequence = { "", "" };
		int columnIndex = -1;

		for (int r = 0; r < MATRIX_ROWS; r++) {
			String base = String.valueOf(dna[r].charAt(r));

			obliqueSequence[0] = obliqueSequence[0].concat(base);
		}

		for (int r = MATRIX_ROWS - 1; r <= MATRIX_ROWS - 1 && r > -1; r--) {
			columnIndex = columnIndex + 1;
			String base = String.valueOf(dna[r].charAt(columnIndex));
			if (!NITROGENEOUS_BASES.contains(base)) {
				throw new Exception("Invalid base");
			}
			obliqueSequence[1] = obliqueSequence[1].concat(base);
		}

		return obliqueSequence;
	}

}
