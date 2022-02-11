package co.com.mutants.app.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.mutants.app.api.StatsApi;
import co.com.mutants.domain.service.StatsService;

@RunWith(MockitoJUnitRunner.class)
public class StatsControllerTest {

    @Mock
    private StatsService statsService;

    @InjectMocks
    private StatsController subject;

    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(subject)
                .build();
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
    }

    @Test
    public void shouldPerformGetCheckStatsMethod() throws Exception {
        StatsApi statsApi = StatsApi.newBuilder()
                .countHumanDna(1L)
                .countHumanDna(1L)
                .ratio(1D)
                .build();
        when(statsService.getCheckStats()).thenReturn(statsApi);

        MvcResult result = mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(result.getResponse().getContentAsString());
        assertEquals(statsApi, mapper.readValue(result.getResponse().getContentAsString(), StatsApi.class));
        verify(statsService).getCheckStats();
    }

}