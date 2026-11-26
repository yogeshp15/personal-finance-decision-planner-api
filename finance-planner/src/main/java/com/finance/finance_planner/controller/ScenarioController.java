
package com.finance.finance_planner.controller;

import com.finance.finance_planner.dto.ScenarioRequestDTO;
import com.finance.finance_planner.dto.ScenarioResponseDTO;
import com.finance.finance_planner.service.ScenarioService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scenario")
public class ScenarioController {

    private final ScenarioService scenarioService;

    public ScenarioController(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    @PostMapping("/simulate")
    public ResponseEntity<ScenarioResponseDTO> simulateScenario(
            @Valid @RequestBody ScenarioRequestDTO request) {

        ScenarioResponseDTO response =
                scenarioService.simulateScenario(request);

        return ResponseEntity.ok(response);
    }
}
