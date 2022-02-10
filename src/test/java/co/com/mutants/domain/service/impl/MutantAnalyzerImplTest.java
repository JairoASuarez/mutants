package co.com.mutants.domain.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import co.com.mutants.domain.exception.MutantException;
import co.com.mutants.domain.service.MutantAnalyzer;

@RunWith(MockitoJUnitRunner.class)
public class MutantAnalyzerImplTest {

    private MutantAnalyzerFactoryImpl factory;

    @Before
    public void setUp() {
        factory = new MutantAnalyzerFactoryImpl();
    }

    @Test
    public void shouldFindAMutantWithHorizontalAndVerticalSequences() throws MutantException {
        String[] dna = new String[] { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
        MutantAnalyzer subject = factory.create(dna);
        assertTrue(subject.isMutant());
    }

    @Test
    public void shouldFindAMutantWithMainDiagonalSequences() throws MutantException {
        String[] dna = new String[] { "ATGCAA", "CAGTGC", "TCATGT", "AGCAGG", "TCCCTA", "TCACTG" };
        MutantAnalyzer subject = factory.create(dna);
        assertTrue(subject.isMutant());
    }

    @Test
    public void shouldFindAMutantWithAntiDiagonalSequences() throws MutantException {
        String[] dna = new String[] { "ATGCAAG", "CAGATGA", "TTATGGA", "AACGAGC", "TCCCTAG", "TCACTGA", "ATGCATG" };
        MutantAnalyzer subject = factory.create(dna);
        assertTrue(subject.isMutant());
    }

    @Test
    public void shouldFindAHumanWithShortSequence() throws MutantException {
        String[] dna = new String[] { "AA", "AA" };
        MutantAnalyzer subject = factory.create(dna);
        assertFalse(subject.isMutant());
    }

    @Test
    public void shouldFindAHuman() throws MutantException {
        String[] dna = new String[] { "ATTCA", "CAGTG", "TCACG", "AGAAG", "TCGCT" };
        MutantAnalyzer subject = factory.create(dna);
        assertFalse(subject.isMutant());
    }

    @Test(expected = MutantException.class)
    public void shouldThrowAnExceptionIfDNAisEmpty() throws MutantException {
        String[] dna = new String[0];
        try {
            MutantAnalyzer subject = factory.create(dna);
            subject.isMutant();
        } catch (MutantException ex) {
            assertEquals("INVALID_DNA", ex.getErrorCode());
            assertEquals("DNA is empty", ex.getMessage());
            throw ex;
        }
        fail();
    }

    @Test(expected = MutantException.class)
    public void shouldThrowAnExceptionIfDNAisNotASquare() throws MutantException {
        String[] dna = new String[] { "ATTCA", "CAGTG", "TCACG"};
        try {
            MutantAnalyzer subject = factory.create(dna);
            subject.isMutant();
        } catch (MutantException ex) {
            assertEquals("INVALID_DNA_SIZE", ex.getErrorCode());
            assertEquals("DNA has an invalid size", ex.getMessage());
            throw ex;
        }
        fail();
    }

}