
package com.finance.finance_planner.service;

import com.finance.finance_planner.dto.GoalRequestDTO;
import com.finance.finance_planner.dto.GoalResponseDTO;
import com.finance.finance_planner.entity.GoalEntity;
import com.finance.finance_planner.entity.UserEntity;
import com.finance.finance_planner.exception.BadRequestException;
import com.finance.finance_planner.exception.ResourceNotFoundException;
import com.finance.finance_planner.repository.GoalRepository;
import com.finance.finance_planner.repository.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoalService {

    private final UserRepository userRepository;
    private final GoalRepository goalRepository;

    public GoalService(
            UserRepository userRepository,
            GoalRepository goalRepository) {

        this.userRepository = userRepository;
        this.goalRepository = goalRepository;
    }

    private UserEntity getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    // Add Goal
    public GoalResponseDTO addGoal(GoalRequestDTO request) {

        UserEntity user = getCurrentUser();

        validateGoalRequest(request);

        GoalEntity goal = new GoalEntity();

        goal.setCurrentAmount(request.getCurrentAmount());
        goal.setTargetAmount(request.getTargetAmount());
        goal.setDeadline(request.getDeadline());
        goal.setProductName(request.getProductName());
        goal.setUser(user);

        GoalEntity savedGoal = goalRepository.save(goal);

        return convertToResponseDTO(savedGoal);
    }

    // Get All Goals
    public List<GoalResponseDTO> getAllGoals() {

        UserEntity user = getCurrentUser();

        List<GoalEntity> goals =
                goalRepository.findByUserId(user.getId());

        List<GoalResponseDTO> response = new ArrayList<>();

        for (GoalEntity goal : goals) {
            response.add(convertToResponseDTO(goal));
        }

        return response;
    }

    // Get Goal By ID
    public GoalResponseDTO getGoalById(Long id) {

        UserEntity user = getCurrentUser();

        GoalEntity goal = goalRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Goal not found"));

        return convertToResponseDTO(goal);
    }

    // Update Goal
    public GoalResponseDTO updateGoal(
            Long id,
            GoalRequestDTO request) {

        UserEntity user = getCurrentUser();

        GoalEntity goal = goalRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Goal not found"));

        validateGoalRequest(request);

        goal.setProductName(request.getProductName());
        goal.setTargetAmount(request.getTargetAmount());
        goal.setCurrentAmount(request.getCurrentAmount());
        goal.setDeadline(request.getDeadline());

        GoalEntity updatedGoal = goalRepository.save(goal);

        return convertToResponseDTO(updatedGoal);
    }

    // Delete Goal
    public void deleteGoal(Long id) {

        UserEntity user = getCurrentUser();

        GoalEntity goal = goalRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Goal not found"));

        goalRepository.delete(goal);
    }

    // Validate Goal Request
    private void validateGoalRequest(GoalRequestDTO request) {

        if (request.getCurrentAmount()
                .compareTo(request.getTargetAmount()) > 0) {

            throw new BadRequestException(
                    "Current savings cannot exceed target amount");
        }

        if (!request.getDeadline().isAfter(LocalDate.now())) {

            throw new BadRequestException(
                    "Deadline must be a future date");
        }
    }

    // Calculate Goal Status
    private String calculateGoalStatus(GoalEntity goal) {

        LocalDate today = LocalDate.now();

        // Goal completed
        if (goal.getCurrentAmount()
                .compareTo(goal.getTargetAmount()) >= 0) {

            return "COMPLETED";
        }

        // Goal overdue
        if (goal.getDeadline().isBefore(today)) {

            return "OVERDUE";
        }

        // Calculate total duration
        LocalDate createdAt = goal.getCreatedAt();

        long totalDays = ChronoUnit.DAYS.between(
                createdAt,
                goal.getDeadline()
        );

        // Calculate elapsed duration
        long elapsedDays = ChronoUnit.DAYS.between(
                createdAt,
                today
        );

        if (totalDays <= 0 || elapsedDays <= 0) {
            return "ON_TRACK";
        }

        // Calculate expected savings
        BigDecimal expectedAmount = goal.getTargetAmount()
                .multiply(BigDecimal.valueOf(elapsedDays))
                .divide(
                        BigDecimal.valueOf(totalDays),
                        2,
                        RoundingMode.CEILING
                );

        if (goal.getCurrentAmount()
                .compareTo(expectedAmount) >= 0) {

            return "ON_TRACK";
        }

        return "BEHIND";
    }

    // Convert Entity to Response DTO
    private GoalResponseDTO convertToResponseDTO(GoalEntity goal) {

        BigDecimal remainingAmount =
                goal.getTargetAmount().subtract(goal.getCurrentAmount());

        // Calculate progress percentage
        BigDecimal progressPercentage =
                goal.getCurrentAmount()
                        .multiply(BigDecimal.valueOf(100))
                        .divide(
                                goal.getTargetAmount(),
                                2,
                                RoundingMode.HALF_UP
                        );

        // Calculate remaining months
        LocalDate today = LocalDate.now();
        LocalDate deadline = goal.getDeadline();

        long monthsRemaining =
                ChronoUnit.MONTHS.between(today, deadline);

        // Round partial months up
        if (today.plusMonths(monthsRemaining).isBefore(deadline)) {
            monthsRemaining++;
        }

        // Calculate required monthly savings
        BigDecimal requiredMonthlySavings = BigDecimal.ZERO;

        if (remainingAmount.compareTo(BigDecimal.ZERO) > 0
                && monthsRemaining > 0) {

            requiredMonthlySavings = remainingAmount.divide(
                    BigDecimal.valueOf(monthsRemaining),
                    2,
                    RoundingMode.CEILING
            );
        }

        // Calculate goal status
        String status = calculateGoalStatus(goal);

        return new GoalResponseDTO(
                goal.getId(),
                goal.getProductName(),
                goal.getTargetAmount(),
                goal.getCurrentAmount(),
                remainingAmount,
                progressPercentage,
                requiredMonthlySavings,
                goal.getDeadline(),
                status
        );
    }
}
