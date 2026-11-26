
package com.finance.finance_planner.dto;

import java.math.BigDecimal;
import java.time.YearMonth;

public class MonthlySummaryResponseDTO {

    private YearMonth month;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpenses;
    private BigDecimal monthlySavings;
    private BigDecimal savingsRate;

    public MonthlySummaryResponseDTO(
            YearMonth month,
            BigDecimal monthlyIncome,
            BigDecimal monthlyExpenses,
            BigDecimal monthlySavings,
            BigDecimal savingsRate) {

        this.month = month;
        this.monthlyIncome = monthlyIncome;
        this.monthlyExpenses = monthlyExpenses;
        this.monthlySavings = monthlySavings;
        this.savingsRate = savingsRate;
    }

    public YearMonth getMonth() {
        return month;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public BigDecimal getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public BigDecimal getMonthlySavings() {
        return monthlySavings;
    }

    public BigDecimal getSavingsRate() {
        return savingsRate;
    }
}
