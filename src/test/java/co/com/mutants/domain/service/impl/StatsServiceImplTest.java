package co.com.mutants.domain.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import co.com.mutants.app.api.StatsApi;
import co.com.mutants.domain.repository.HumanRepository;
import co.com.mutants.domain.repository.MutantRepository;
import co.com.mutants.domain.service.CalculateRatioDelegate;

@RunWith(MockitoJUnitRunner.class)
public class StatsServiceImplTest {

    @Mock
    private MutantRepository mutantRepository;
    @Mock
    private HumanRepository humanRepository;
    @Mock
    private CalculateRatioDelegate calculateRatioDelegate;

    @InjectMocks
    private StatsServiceImpl subject;

    @Test
    public void shouldReturnTheStatsApi() {
        when(mutantRepository.count()).thenReturn(1L);
        when(humanRepository.count()).thenReturn(1L);
        when(calculateRatioDelegate.calculate(anyLong(), anyLong())).thenReturn(1D);

        StatsApi expectedStats = StatsApi.newBuilder()
                .countMutantDna(1L)
                .countHumanDna(1L)
                .ratio(1D)
                .build();
        assertEquals(expectedStats, subject.getCheckStats());
        verify(mutantRepository).count();
        verify(humanRepository).count();
        verify(calculateRatioDelegate).calculate(1L, 1L);
    }

}