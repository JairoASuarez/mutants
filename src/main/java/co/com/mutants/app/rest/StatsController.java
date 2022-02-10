package co.com.mutants.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.mutants.app.api.StatsApi;
import co.com.mutants.domain.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", maxAge = 86400)
@RestController
@RequestMapping(path = "/stats")
@Tag(name = "Stats Controller")
public class StatsController {

    @Autowired
    private final StatsService statsService;

    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @Operation(description = "Analyze candidate by DNA", operationId = "mutants")
    @ApiResponse(responseCode = "200", description = "Stats")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatsApi> getCheckStats() {
        return ResponseEntity.ok(statsService.getCheckStats());
    }

}