package co.com.mutants.domain.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.mutants.domain.exception.MutantException;
import co.com.mutants.domain.service.MutantAnalyzer;

public class MutantAnalyzerImpl implements MutantAnalyzer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MutantAnalyzerImpl.class);
    private static final List<String> sequences = Arrays.asList("AAAA", "TTTT", "CCCC", "GGGG");

    private final String[] dna;
    private final int dnaLength;
    private final AtomicInteger numberOfSequences;

    public MutantAnalyzerImpl(String[] dna) {
        super();
        this.dna = dna;
        this.dnaLength = dna.length;
        this.numberOfSequences = new AtomicInteger(0);
    }

    @Override
    public boolean isMutant() throws MutantException {
        validateDNA(dna);
        return checkSequences(dna);
    }

    private void validateDNA(String[] dna) throws MutantException {
        if (dnaLength == 0) {
            LOGGER.error("The DNA cannot be empty");
            throw new MutantException("DNA is empty", "INVALID_DNA");
        }

        for (String value : dna) {
            if (value.length() != dnaLength) {
                LOGGER.error("The DNA is not NxN");
                throw new MutantException("DNA has an invalid size", "INVALID_DNA_SIZE");
            }
        }
    }

    private boolean checkSequences(String[] dna) {
        checkHorizontalAndVerticalSequences(dna);
        checkMainDiagonalSequences(dna);
        checkAntiDiagonalSequences(dna);
        return numberOfSequences.get() > 1;
    }

    private void checkHorizontalAndVerticalSequences(String[] dna) {
        for (int i = 0; i < dnaLength && numberOfSequences.get() <= 1; i++) {
            checkSequence(dna[i]);
            StringBuilder column = new StringBuilder();
            for (String s : dna) {
                column.append(s.charAt(i));
            }
            checkSequence(column.toString());
        }
    }

    private void checkMainDiagonalSequences(String[] dna) {
        for (int i = 0; i < dnaLength && numberOfSequences.get() <= 1; i++) {
            int x = 0;
            StringBuilder diagonal = new StringBuilder();
            for (int j = i; j >= 0; j--) {
                diagonal.append(dna[j].charAt(x));
                x++;
            }
            checkSequence(diagonal.toString());
        }

        for (int i = 0; i < dnaLength && numberOfSequences.get() <= 1; i++) {
            int x = dnaLength - 1;
            int y = dnaLength - i;
            StringBuilder diagonal = new StringBuilder();
            for (int j = 0; j < i; j++) {
                diagonal.append(dna[x].charAt(y));
                x--;
                y++;
            }
            checkSequence(diagonal.toString());
        }
    }

    private void checkAntiDiagonalSequences(String[] dna) {
        StringBuilder firstDiagonal = new StringBuilder();
        for (int i = 0; i < dnaLength && numberOfSequences.get() <= 1; i++) {
            int x = dnaLength - i;
            StringBuilder upperDiagonal = new StringBuilder();
            StringBuilder lowerDiagonal = new StringBuilder();
            for (int j = 0; j < i; j++) {
                upperDiagonal.append(dna[j].charAt(x));
                lowerDiagonal.append(dna[x].charAt(j));
                x++;
            }
            firstDiagonal.append(dna[i].charAt(i));
            checkSequence(upperDiagonal.toString());
            checkSequence(lowerDiagonal.toString());
        }
        checkSequence(firstDiagonal.toString());
    }

    private void checkSequence(String sequence) {
        sequences.forEach(x -> numberOfSequences.getAndAdd(StringUtils.countMatches(sequence, x)));
    }

}
