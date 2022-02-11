package co.com.mutants.domain.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.mutants.app.api.DnaSequenceApi;
import co.com.mutants.domain.exception.MutantException;
import co.com.mutants.domain.model.Human;
import co.com.mutants.domain.model.Mutant;
import co.com.mutants.domain.repository.HumanRepository;
import co.com.mutants.domain.repository.MutantRepository;
import co.com.mutants.domain.service.MutantAnalyzerFactory;
import co.com.mutants.domain.service.MutantsService;

@Service
public class MutantsServiceImpl implements MutantsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MutantsServiceImpl.class);

    private final MutantAnalyzerFactory mutantAnalyzerFactory;
    private final MutantRepository mutantRepository;
    private final HumanRepository humanRepository;

    @Autowired
    public MutantsServiceImpl(MutantAnalyzerFactory mutantAnalyzerFactory,
                              MutantRepository mutantRepository,
                              HumanRepository humanRepository) {
        this.mutantAnalyzerFactory = mutantAnalyzerFactory;
        this.mutantRepository = mutantRepository;
        this.humanRepository = humanRepository;
    }

    @Override
    public void analyzeCandidateByDNA(DnaSequenceApi dnaSequenceApi) throws MutantException {
        if (candidateExistsAndIsMutant(dnaSequenceApi)) {
            LOGGER.info("Candidate is a mutant and is already added");
            return;
        }
        if (candidateExistsAndIsHuman(dnaSequenceApi)) {
            LOGGER.info("Candidate is a human and is already added");
            throwMutantException();
        }
        boolean isMutant = mutantAnalyzerFactory.create(dnaSequenceApi.getDna()).isMutant();
        if (isMutant) {
            Mutant mutant = new Mutant(dnaSequenceApi.getDna());
            mutantRepository.insert(mutant);
            LOGGER.info("Mutant {} was added", mutant.getDNA());
        } else {
            Human human = new Human(dnaSequenceApi.getDna());
            humanRepository.insert(human);
            LOGGER.info("Human {} was added", human.getDNA());
            throwMutantException();
        }
    }

    private boolean candidateExistsAndIsMutant(DnaSequenceApi dnaSequenceApi) {
        Mutant mutant = new Mutant(dnaSequenceApi.getDna());
        return mutantRepository.existsById(mutant.getDNA());
    }

    private boolean candidateExistsAndIsHuman(DnaSequenceApi dnaSequenceApi) {
        Human human = new Human(dnaSequenceApi.getDna());
        return humanRepository.existsById(human.getDNA());
    }

    private void throwMutantException() throws MutantException {
        throw new MutantException("Candidate is not a mutant", "CANDIDATE_IS_NOT_A_MUTANT");
    }

}
