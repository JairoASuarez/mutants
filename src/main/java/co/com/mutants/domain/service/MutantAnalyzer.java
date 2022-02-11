package co.com.mutants.domain.service;

import co.com.mutants.domain.exception.MutantException;

public interface MutantAnalyzer {

    boolean isMutant() throws MutantException;

}
