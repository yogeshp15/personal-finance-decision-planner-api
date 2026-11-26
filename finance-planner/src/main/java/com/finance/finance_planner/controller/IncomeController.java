package com.finance.finance_planner.controller;

import com.finance.finance_planner.dto.IncomeRequestDTO;
import com.finance.finance_planner.dto.IncomeResponseDTO;
import com.finance.finance_planner.service.IncomeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/income")
public class IncomeController {
    private final IncomeService incomeService;
    public IncomeController(IncomeService incomeService){
        this.incomeService = incomeService;
    }

    @PostMapping
    public ResponseEntity<IncomeResponseDTO> addIncome(@Valid @RequestBody IncomeRequestDTO request){
        IncomeResponseDTO saveIncome = incomeService.addIncome(request);
        return new ResponseEntity<>(saveIncome, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<IncomeResponseDTO>> getAllIncomes(){
        List<IncomeResponseDTO> responses = incomeService.getAllIncomes();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncomeResponseDTO> getIncomeById(@PathVariable Long id){
        IncomeResponseDTO income = incomeService.getIncomeById(id);
        return ResponseEntity.ok(income);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncomeResponseDTO> updateIncome(@PathVariable Long id,@Valid @RequestBody IncomeRequestDTO request){
        IncomeResponseDTO income = incomeService.updateIncome(id,request);
        return ResponseEntity.ok(income);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable  Long id){
        incomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }
}
