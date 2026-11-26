package com.finance.finance_planner.controller;

import com.finance.finance_planner.dto.GoalRequestDTO;
import com.finance.finance_planner.dto.GoalResponseDTO;
import com.finance.finance_planner.service.GoalService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    // Add Goal
    @PostMapping
    public ResponseEntity<GoalResponseDTO> addGoal(
            @Valid @RequestBody GoalRequestDTO request) {

        GoalResponseDTO response = goalService.addGoal(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get All Goals
    @GetMapping
    public ResponseEntity<List<GoalResponseDTO>> getAllGoals() {

        List<GoalResponseDTO> response = goalService.getAllGoals();

        return ResponseEntity.ok(response);
    }

    // Get Goal By ID
    @GetMapping("/{id}")
    public ResponseEntity<GoalResponseDTO> getGoalById(
            @PathVariable Long id) {

        GoalResponseDTO response = goalService.getGoalById(id);

        return ResponseEntity.ok(response);
    }

    // Update Goal
    @PutMapping("/{id}")
    public ResponseEntity<GoalResponseDTO> updateGoal(
            @PathVariable Long id,
            @Valid @RequestBody GoalRequestDTO request) {

        GoalResponseDTO response = goalService.updateGoal(id, request);

        return ResponseEntity.ok(response);
    }

    // Delete Goal
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(
            @PathVariable Long id) {

        goalService.deleteGoal(id);

        return ResponseEntity.noContent().build();
    }
}