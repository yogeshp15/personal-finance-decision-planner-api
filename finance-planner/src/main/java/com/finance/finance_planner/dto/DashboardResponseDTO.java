package com.finance.finance_planner.dto;

import java.math.BigDecimal;

public class DashboardResponseDTO {

    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private BigDecimal availableBalance;
    private BigDecimal totalGoalSavings;

    public DashboardResponseDTO(
            BigDecimal totalIncome,
            BigDecimal totalExpenses,
            BigDecimal availableBalance,
            BigDecimal totalGoalSavings) {

        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.availableBalance = availableBalance;
        this.totalGoalSavings = totalGoalSavings;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public BigDecimal getTotalGoalSavings() {
        return totalGoalSavings;
    }
}