package co.com.mutants.domain.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.mutants.app.api.StatsApi;
import co.com.mutants.domain.repository.HumanRepository;
import co.com.mutants.domain.repository.MutantRepository;
import co.com.mutants.domain.service.CalculateRatioDelegate;
import co.com.mutants.domain.service.StatsService;

@Service
public class StatsServiceImpl implements StatsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatsServiceImpl.class);

    private final MutantRepository mutantRepository;
    private final HumanRepository humanRepository;
    private final CalculateRatioDelegate calculateRatioDelegate;

    @Autowired
    public StatsServiceImpl(MutantRepository mutantRepository,
                            HumanRepository humanRepository,
                            CalculateRatioDelegate calculateRatioDelegate) {
        this.mutantRepository = mutantRepository;
        this.humanRepository = humanRepository;
        this.calculateRatioDelegate = calculateRatioDelegate;
    }

    @Override
    public StatsApi getCheckStats() {
        long numberOfMutants = mutantRepository.count();
        long numberOfHumans = humanRepository.count();
        double ratio = calculateRatioDelegate.calculate(numberOfMutants, numberOfHumans);

        LOGGER.info("The number of mutants is {}, the number of humans is {} and the ratio is {}", numberOfMutants,
                numberOfHumans, ratio);

        return StatsApi.newBuilder()
                .countMutantDna(numberOfMutants)
                .countHumanDna(numberOfHumans)
                .ratio(ratio)
                .build();
    }

}
