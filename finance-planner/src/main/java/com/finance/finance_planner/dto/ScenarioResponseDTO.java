
package com.finance.finance_planner.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ScenarioResponseDTO {

    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private BigDecimal remainingAmount;

    private LocalDate deadline;
    private long monthsRemaining;

    private BigDecimal requiredMonthlySavings;

    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpenses;
    private BigDecimal monthlySurplus;

    private BigDecimal monthlyShortfall;
    private boolean affordable;

    private String message;

    public ScenarioResponseDTO(
            BigDecimal targetAmount,
            BigDecimal currentAmount,
            BigDecimal remainingAmount,
            LocalDate deadline,
            long monthsRemaining,
            BigDecimal requiredMonthlySavings,
            BigDecimal monthlyIncome,
            BigDecimal monthlyExpenses,
            BigDecimal monthlySurplus,
            BigDecimal monthlyShortfall,
            boolean affordable,
            String message) {

        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.remainingAmount = remainingAmount;
        this.deadline = deadline;
        this.monthsRemaining = monthsRemaining;
        this.requiredMonthlySavings = requiredMonthlySavings;
        this.monthlyIncome = monthlyIncome;
        this.monthlyExpenses = monthlyExpenses;
        this.monthlySurplus = monthlySurplus;
        this.monthlyShortfall = monthlyShortfall;
        this.affordable = affordable;
        this.message = message;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public long getMonthsRemaining() {
        return monthsRemaining;
    }

    public BigDecimal getRequiredMonthlySavings() {
        return requiredMonthlySavings;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public BigDecimal getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public BigDecimal getMonthlySurplus() {
        return monthlySurplus;
    }

    public BigDecimal getMonthlyShortfall() {
        return monthlyShortfall;
    }

    public boolean isAffordable() {
        return affordable;
    }

    public String getMessage() {
        return message;
    }
}
