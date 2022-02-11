package co.com.mutants.domain.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CalculateRatioDelegateImplTest {

    private CalculateRatioDelegateImpl subject;

    @Before
    public void setUp() {
        subject = new CalculateRatioDelegateImpl();
    }

    @Test
    public void shouldCalculateTheRatioWhenNumberOfHumansIsZero() {
        assertEquals(1D, subject.calculate(1L, 0L), 0);
    }

    @Test
    public void shouldCalculateTheRatioWhenNumberOfMutantsIsGreater() {
        assertEquals(2.5D, subject.calculate(100L, 40L), 0);
    }

    @Test
    public void shouldCalculateTheRatioWhenNumberOfHumansIsGreater() {
        assertEquals(0.4D, subject.calculate(40L, 100L), 0);
    }

}