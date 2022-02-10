package co.com.mutants.domain.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

import co.com.mutants.domain.service.CalculateRatioDelegate;

@Component
public class CalculateRatioDelegateImpl implements CalculateRatioDelegate {

    @Override
    public double calculate(long numberOfMutants, long numberOfHumans) {
        if (numberOfHumans == 0) {
            return numberOfMutants;
        }
        return BigDecimal.valueOf(numberOfMutants)
                .divide(BigDecimal.valueOf(numberOfHumans), 2, RoundingMode.HALF_UP).doubleValue();
    }

}
