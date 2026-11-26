
package com.finance.finance_planner.service;

import com.finance.finance_planner.dto.ScenarioRequestDTO;
import com.finance.finance_planner.dto.ScenarioResponseDTO;
import com.finance.finance_planner.entity.ExpenseEntity;
import com.finance.finance_planner.entity.IncomeEntity;
import com.finance.finance_planner.entity.UserEntity;
import com.finance.finance_planner.exception.BadRequestException;
import com.finance.finance_planner.exception.ResourceNotFoundException;
import com.finance.finance_planner.repository.ExpenseRepository;
import com.finance.finance_planner.repository.IncomeRepository;
import com.finance.finance_planner.repository.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

import java.util.List;

@Service
public class ScenarioService {

    private final UserRepository userRepository;
    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    public ScenarioService(
            UserRepository userRepository,
            IncomeRepository incomeRepository,
            ExpenseRepository expenseRepository) {

        this.userRepository = userRepository;
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
    }

    public ScenarioResponseDTO simulateScenario(ScenarioRequestDTO request) {

        UserEntity user = getCurrentUser();

        validateScenario(request);

        // Get current month's date range
        YearMonth currentMonth = YearMonth.now();

        LocalDate startDate = currentMonth.atDay(1);
        LocalDate endDate = currentMonth.atEndOfMonth();

        // Fetch current month's income and expenses
        List<IncomeEntity> incomes =
                incomeRepository.findByUserIdAndDateBetween(
                        user.getId(), startDate, endDate);

        List<ExpenseEntity> expenses =
                expenseRepository.findByUserIdAndDateBetween(
                        user.getId(), startDate, endDate);

        // Calculate total income
        BigDecimal monthlyIncome = incomes.stream()
                .map(IncomeEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate total expenses
        BigDecimal monthlyExpenses = expenses.stream()
                .map(ExpenseEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate monthly surplus
        BigDecimal monthlySurplus =
                monthlyIncome.subtract(monthlyExpenses);

        // Calculate remaining amount
        BigDecimal remainingAmount =
                request.getTargetAmount()
                        .subtract(request.getCurrentAmount());

        // Calculate months remaining
        LocalDate today = LocalDate.now();

        long monthsRemaining = ChronoUnit.MONTHS.between(
                today.withDayOfMonth(1),
                request.getDeadline().withDayOfMonth(1));

        if (request.getDeadline().getDayOfMonth() > today.getDayOfMonth()) {
            monthsRemaining++;
        }

        monthsRemaining = Math.max(1, monthsRemaining);

        // Calculate required monthly savings
        BigDecimal requiredMonthlySavings =
                remainingAmount.divide(
                        BigDecimal.valueOf(monthsRemaining),
                        2,
                        RoundingMode.CEILING);

        // Check affordability
        boolean affordable =
                monthlySurplus.compareTo(requiredMonthlySavings) >= 0;

        // Calculate monthly shortfall
        BigDecimal monthlyShortfall = affordable
                ? BigDecimal.ZERO
                : requiredMonthlySavings.subtract(monthlySurplus);

        // Prevent negative shortfall when surplus is negative
        monthlyShortfall = monthlyShortfall.max(BigDecimal.ZERO);

        String message = affordable
                ? "Your goal appears affordable based on this month's surplus."
                : "Your current monthly surplus is insufficient. Consider reducing expenses, increasing income, or extending the deadline.";

        return new ScenarioResponseDTO(
                request.getTargetAmount(),
                request.getCurrentAmount(),
                remainingAmount,
                request.getDeadline(),
                monthsRemaining,
                requiredMonthlySavings,
                monthlyIncome,
                monthlyExpenses,
                monthlySurplus,
                monthlyShortfall,
                affordable,
                message
        );
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

    private void validateScenario(ScenarioRequestDTO request) {

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
}
