package com.finance.finance_planner.controller;

import com.finance.finance_planner.dto.ExpenseRequestDTO;
import com.finance.finance_planner.dto.ExpenseResponseDTO;
import com.finance.finance_planner.entity.ExpenseEntity;
import com.finance.finance_planner.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/expense")
public class ExpenseController {
    private final ExpenseService expenseService;
    public ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> addExpense(
            @Valid @RequestBody ExpenseRequestDTO request) {

        ExpenseResponseDTO response = expenseService.addExpense(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> getAllExpenses() {

        List<ExpenseResponseDTO> expenses =
                expenseService.getAllExpenses();

        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> getExpenseById(
            @PathVariable Long id) {

        ExpenseResponseDTO expense =
                expenseService.getExpenseById(id);

        return ResponseEntity.ok(expense);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequestDTO request) {

        ExpenseResponseDTO updatedExpense =
                expenseService.updateExpense(id, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(updatedExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {

        expenseService.deleteExpense(id);

        return ResponseEntity.noContent().build();
    }
}
