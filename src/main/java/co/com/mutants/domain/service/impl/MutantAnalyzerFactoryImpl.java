package co.com.mutants.domain.service.impl;

import org.springframework.stereotype.Component;

import co.com.mutants.domain.service.MutantAnalyzer;
import co.com.mutants.domain.service.MutantAnalyzerFactory;

@Component
public class MutantAnalyzerFactoryImpl implements MutantAnalyzerFactory {

    @Override
    public MutantAnalyzer create(String[] dna) {
        return new MutantAnalyzerImpl(dna);
    }

}
