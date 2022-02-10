package co.com.mutants.app.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.mutants.app.api.DnaSequenceApi;
import co.com.mutants.domain.exception.MutantException;
import co.com.mutants.domain.service.MutantsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", maxAge = 86400)
@RestController
@Validated
@RequestMapping("/mutant")
@Tag(name = "Mutants Controller")
public class MutantsController {

    private final MutantsService mutantsService;

    @Autowired
    public MutantsController(MutantsService mutantsService) {
        this.mutantsService = mutantsService;
    }

    @Operation(description = "Analyze candidate by DNA", operationId = "mutants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidate is a mutant."),
            @ApiResponse(responseCode = "403", description = "Candidate is not a mutant.")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> analyzeCandidateByDNA(@RequestBody @Valid DnaSequenceApi dnaSequenceApi)
            throws MutantException {
        mutantsService.analyzeCandidateByDNA(dnaSequenceApi);
        return ResponseEntity.ok().build();
    }

}
