
package com.finance.finance_planner.service;

import com.finance.finance_planner.dto.IncomeRequestDTO;
import com.finance.finance_planner.dto.IncomeResponseDTO;
import com.finance.finance_planner.entity.IncomeEntity;
import com.finance.finance_planner.entity.UserEntity;
import com.finance.finance_planner.exception.ResourceNotFoundException;
import com.finance.finance_planner.repository.IncomeRepository;
import com.finance.finance_planner.repository.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final UserRepository userRepository;

    public IncomeService(
            IncomeRepository incomeRepository,
            UserRepository userRepository) {
        this.incomeRepository = incomeRepository;
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

    // Add Income
    public IncomeResponseDTO addIncome(IncomeRequestDTO request) {

        UserEntity user = getCurrentUser();

        IncomeEntity income = new IncomeEntity();

        income.setSource(request.getSource());
        income.setAmount(request.getAmount());
        income.setDescription(request.getDescription());
        income.setDate(request.getDate());
        income.setUser(user);

        IncomeEntity savedIncome = incomeRepository.save(income);

        return convertToResponseDTO(savedIncome);
    }

    // Get All Income Records
    public List<IncomeResponseDTO> getAllIncomes() {

        UserEntity user = getCurrentUser();

        List<IncomeEntity> incomes =
                incomeRepository.findByUserId(user.getId());

        List<IncomeResponseDTO> response = new ArrayList<>();

        for (IncomeEntity income : incomes) {
            response.add(convertToResponseDTO(income));
        }

        return response;
    }

    // Get Income By ID
    public IncomeResponseDTO getIncomeById(Long id) {

        UserEntity user = getCurrentUser();

        IncomeEntity income = incomeRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Income not found"));

        return convertToResponseDTO(income);
    }

    // Update Income
    public IncomeResponseDTO updateIncome(
            Long id,
            IncomeRequestDTO request) {

        UserEntity user = getCurrentUser();

        IncomeEntity income = incomeRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Income not found"));

        income.setSource(request.getSource());
        income.setAmount(request.getAmount());
        income.setDescription(request.getDescription());
        income.setDate(request.getDate());

        IncomeEntity updatedIncome = incomeRepository.save(income);

        return convertToResponseDTO(updatedIncome);
    }

    // Delete Income
    public void deleteIncome(Long id) {

        UserEntity user = getCurrentUser();

        IncomeEntity income = incomeRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Income not found"));

        incomeRepository.delete(income);
    }

    // Convert Entity to Response DTO
    private IncomeResponseDTO convertToResponseDTO(IncomeEntity income) {

        return new IncomeResponseDTO(
                income.getId(),
                income.getSource(),
                income.getAmount(),
                income.getDescription(),
                income.getDate()
        );
    }
}
