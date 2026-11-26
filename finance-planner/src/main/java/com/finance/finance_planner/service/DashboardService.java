
package com.finance.finance_planner.service;

import com.finance.finance_planner.dto.DashboardResponseDTO;
import com.finance.finance_planner.dto.MonthlySummaryResponseDTO;
import com.finance.finance_planner.entity.UserEntity;
import com.finance.finance_planner.exception.ResourceNotFoundException;
import com.finance.finance_planner.repository.ExpenseRepository;
import com.finance.finance_planner.repository.GoalRepository;
import com.finance.finance_planner.repository.IncomeRepository;
import com.finance.finance_planner.repository.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;

@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final GoalRepository goalRepository;

    public DashboardService(
            UserRepository userRepository,
            IncomeRepository incomeRepository,
            ExpenseRepository expenseRepository,
            GoalRepository goalRepository) {

        this.userRepository = userRepository;
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
        this.goalRepository = goalRepository;
    }

    // Get Current User
    private UserEntity getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    // Get Overall Dashboard Data
    public DashboardResponseDTO getDashboardData() {

        UserEntity user = getCurrentUser();

        BigDecimal totalIncome = incomeRepository
                .findByUserId(user.getId())
                .stream()
                .map(income -> income.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpenses = expenseRepository
                .findByUserId(user.getId())
                .stream()
                .map(expense -> expense.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalGoalSavings = goalRepository
                .findByUserId(user.getId())
                .stream()
                .map(goal -> goal.getCurrentAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal availableBalance =
                totalIncome.subtract(totalExpenses);

        return new DashboardResponseDTO(
                totalIncome,
                totalExpenses,
                availableBalance,
                totalGoalSavings
        );
    }

    // Get Monthly Financial Summary
    public MonthlySummaryResponseDTO getMonthlySummary(YearMonth month) {

        UserEntity user = getCurrentUser();

        // Get selected month's date range
        LocalDate startDate = month.atDay(1);
        LocalDate endDate = month.atEndOfMonth();

        // Calculate monthly income
        BigDecimal monthlyIncome = incomeRepository
                .findByUserIdAndDateBetween(
                        user.getId(),
                        startDate,
                        endDate
                )
                .stream()
                .map(income -> income.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate monthly expenses
        BigDecimal monthlyExpenses = expenseRepository
                .findByUserIdAndDateBetween(
                        user.getId(),
                        startDate,
                        endDate
                )
                .stream()
                .map(expense -> expense.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate monthly savings
        BigDecimal monthlySavings =
                monthlyIncome.subtract(monthlyExpenses);

        // Calculate savings rate
        BigDecimal savingsRate = BigDecimal.ZERO;

        if (monthlyIncome.compareTo(BigDecimal.ZERO) > 0) {

            savingsRate = monthlySavings
                    .multiply(BigDecimal.valueOf(100))
                    .divide(
                            monthlyIncome,
                            2,
                            RoundingMode.HALF_UP
                    );
        }

        return new MonthlySummaryResponseDTO(
                month,
                monthlyIncome,
                monthlyExpenses,
                monthlySavings,
                savingsRate
        );
    }
}
