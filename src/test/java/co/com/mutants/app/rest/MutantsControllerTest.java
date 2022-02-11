package co.com.mutants.app.rest;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.mutants.app.api.DnaSequenceApi;
import co.com.mutants.domain.service.MutantsService;

@RunWith(MockitoJUnitRunner.class)
public class MutantsControllerTest {

    @Mock
    private MutantsService mutantsService;

    @InjectMocks
    private MutantsController subject;

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
    public void shouldPerformAnalyzeCandidateByDNAMethod() throws Exception {
        String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
        DnaSequenceApi dnaSequenceApi = DnaSequenceApi.newBuilder()
                .dna(dna)
                .build();

        MvcResult result = mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dnaSequenceApi)))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().isEmpty());
        verify(mutantsService).analyzeCandidateByDNA(dnaSequenceApi);
    }

}