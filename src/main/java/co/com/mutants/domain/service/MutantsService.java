package co.com.mutants.domain.service;

import co.com.mutants.app.api.DnaSequenceApi;
import co.com.mutants.domain.exception.MutantException;

public interface MutantsService {

    void analyzeCandidateByDNA(DnaSequenceApi dnaSequenceApi) throws MutantException;

}
