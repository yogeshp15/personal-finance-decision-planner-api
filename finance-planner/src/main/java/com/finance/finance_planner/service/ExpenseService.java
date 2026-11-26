
package com.finance.finance_planner.service;

import com.finance.finance_planner.dto.ExpenseRequestDTO;
import com.finance.finance_planner.dto.ExpenseResponseDTO;
import com.finance.finance_planner.entity.ExpenseEntity;
import com.finance.finance_planner.entity.UserEntity;
import com.finance.finance_planner.exception.ResourceNotFoundException;
import com.finance.finance_planner.repository.ExpenseRepository;
import com.finance.finance_planner.repository.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(
            ExpenseRepository expenseRepository,
            UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    // Get currently authenticated user
    private UserEntity getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    // Add Expense
    public ExpenseResponseDTO addExpense(ExpenseRequestDTO request) {

        UserEntity user = getCurrentUser();

        ExpenseEntity expense = new ExpenseEntity();

        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate());
        expense.setDescription(request.getDescription());
        expense.setUser(user);

        ExpenseEntity savedExpense = expenseRepository.save(expense);

        return convertToResponseDTO(savedExpense);
    }

    // Get All Expenses
    public List<ExpenseResponseDTO> getAllExpenses() {

        UserEntity user = getCurrentUser();

        List<ExpenseEntity> expenses =
                expenseRepository.findByUserId(user.getId());

        List<ExpenseResponseDTO> response = new ArrayList<>();

        for (ExpenseEntity expense : expenses) {
            response.add(convertToResponseDTO(expense));
        }

        return response;
    }

    // Get Expense By ID
    public ExpenseResponseDTO getExpenseById(Long id) {

        UserEntity user = getCurrentUser();

        ExpenseEntity expense = expenseRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found"));

        return convertToResponseDTO(expense);
    }

    // Update Expense
    public ExpenseResponseDTO updateExpense(
            Long id,
            ExpenseRequestDTO request) {

        UserEntity user = getCurrentUser();

        ExpenseEntity expense = expenseRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found"));

        expense.setCategory(request.getCategory());
        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setDate(request.getDate());

        ExpenseEntity updatedExpense = expenseRepository.save(expense);

        return convertToResponseDTO(updatedExpense);
    }

    // Delete Expense
    public void deleteExpense(Long id) {

        UserEntity user = getCurrentUser();

        ExpenseEntity expense = expenseRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found"));

        expenseRepository.delete(expense);
    }

    // Convert Entity to Response DTO
    private ExpenseResponseDTO convertToResponseDTO(ExpenseEntity expense) {

        return new ExpenseResponseDTO(
                expense.getId(),
                expense.getCategory(),
                expense.getAmount(),
                expense.getDescription(),
                expense.getDate()
        );
    }
}
