package co.com.mutants.domain.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import co.com.mutants.app.api.DnaSequenceApi;
import co.com.mutants.domain.exception.MutantException;
import co.com.mutants.domain.model.Human;
import co.com.mutants.domain.model.Mutant;
import co.com.mutants.domain.repository.HumanRepository;
import co.com.mutants.domain.repository.MutantRepository;
import co.com.mutants.domain.service.MutantAnalyzerFactory;

@RunWith(MockitoJUnitRunner.class)
public class MutantsServiceImplTest {

    @Mock
    private MutantAnalyzerFactory mutantAnalyzerFactory;
    @Mock
    private MutantRepository mutantRepository;
    @Mock
    private HumanRepository humanRepository;

    @InjectMocks
    private MutantsServiceImpl subject;

    private String[] dna;
    private DnaSequenceApi dnaSequenceApi;
    private String dnaSequence;

    @Before
    public void setUp() {
        dna = new String[] { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
        dnaSequenceApi = DnaSequenceApi.newBuilder()
                .dna(dna)
                .build();
        dnaSequence = "ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG";
    }

    @Test
    public void shouldReturnVoidIfDnaSequenceExistsInMutantRepository() throws MutantException {
        when(mutantRepository.existsById(anyString())).thenReturn(true);

        subject.analyzeCandidateByDNA(dnaSequenceApi);
        verify(mutantRepository).existsById(dnaSequence);
        verifyZeroInteractions(humanRepository);
        verifyZeroInteractions(mutantAnalyzerFactory);
    }

    @Test(expected = MutantException.class)
    public void shouldReturnVoidIfDnaSequenceExistsInHumanRepository() throws MutantException {
        when(mutantRepository.existsById(anyString())).thenReturn(false);
        when(humanRepository.existsById(anyString())).thenReturn(true);

        try {
            subject.analyzeCandidateByDNA(dnaSequenceApi);
        } catch (MutantException ex) {
            assertEquals("CANDIDATE_IS_NOT_A_MUTANT", ex.getErrorCode());
            assertEquals("Candidate is not a mutant", ex.getMessage());

            InOrder inOrder = Mockito.inOrder(mutantRepository, humanRepository);
            inOrder.verify(mutantRepository).existsById(dnaSequence);
            inOrder.verify(humanRepository).existsById(dnaSequence);
            verifyZeroInteractions(mutantAnalyzerFactory);
            throw ex;
        }
        fail();
    }

    @Test
    public void shouldAnalyzeAndSaveMutant() throws MutantException {
        when(mutantRepository.existsById(anyString())).thenReturn(false);
        when(humanRepository.existsById(anyString())).thenReturn(false);

        MutantAnalyzerImpl mutantAnalyzerMock = Mockito.mock(MutantAnalyzerImpl.class);
        when(mutantAnalyzerMock.isMutant()).thenReturn(true);
        when(mutantAnalyzerFactory.create(any())).thenReturn(mutantAnalyzerMock);

        subject.analyzeCandidateByDNA(dnaSequenceApi);
        InOrder inOrder = Mockito.inOrder(mutantRepository, humanRepository, mutantAnalyzerFactory, mutantAnalyzerMock);
        inOrder.verify(mutantRepository).existsById(dnaSequence);
        inOrder.verify(humanRepository).existsById(dnaSequence);
        inOrder.verify(mutantAnalyzerFactory).create(dna);
        inOrder.verify(mutantAnalyzerMock).isMutant();
        Mutant expectedMutant = new Mutant(dna);
        inOrder.verify(mutantRepository).insert(expectedMutant);

    }

    @Test(expected = MutantException.class)
    public void shouldAnalyzeAndSaveHuman() throws MutantException {
        when(mutantRepository.existsById(anyString())).thenReturn(false);
        when(humanRepository.existsById(anyString())).thenReturn(false);

        MutantAnalyzerImpl mutantAnalyzerMock = Mockito.mock(MutantAnalyzerImpl.class);
        when(mutantAnalyzerMock.isMutant()).thenReturn(false);
        when(mutantAnalyzerFactory.create(any())).thenReturn(mutantAnalyzerMock);

        try {
            subject.analyzeCandidateByDNA(dnaSequenceApi);
        } catch (MutantException ex) {
            assertEquals("CANDIDATE_IS_NOT_A_MUTANT", ex.getErrorCode());
            assertEquals("Candidate is not a mutant", ex.getMessage());

            InOrder inOrder = Mockito.inOrder(mutantRepository, humanRepository, mutantAnalyzerFactory,
                    mutantAnalyzerMock);
            inOrder.verify(mutantRepository).existsById(dnaSequence);
            inOrder.verify(humanRepository).existsById(dnaSequence);
            inOrder.verify(mutantAnalyzerFactory).create(dna);
            inOrder.verify(mutantAnalyzerMock).isMutant();
            Human expectedHuman = new Human(dna);
            inOrder.verify(humanRepository).insert(expectedHuman);
            throw ex;
        }
        fail();
    }

}