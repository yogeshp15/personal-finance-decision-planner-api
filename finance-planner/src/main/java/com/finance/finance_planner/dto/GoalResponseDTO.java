
package com.finance.finance_planner.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GoalResponseDTO {

    private Long id;
    private String productName;
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private BigDecimal remainingAmount;
    private BigDecimal progressPercentage;
    private BigDecimal requiredMonthlySavings;
    private LocalDate deadline;
    private String status;

    public GoalResponseDTO(
            Long id,
            String productName,
            BigDecimal targetAmount,
            BigDecimal currentAmount,
            BigDecimal remainingAmount,
            BigDecimal progressPercentage,
            BigDecimal requiredMonthlySavings,
            LocalDate deadline,
            String status) {

        this.id = id;
        this.productName = productName;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.remainingAmount = remainingAmount;
        this.progressPercentage = progressPercentage;
        this.requiredMonthlySavings = requiredMonthlySavings;
        this.deadline = deadline;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
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

    public BigDecimal getProgressPercentage() {
        return progressPercentage;
    }

    public BigDecimal getRequiredMonthlySavings() {
        return requiredMonthlySavings;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public String getStatus() {
        return status;
    }
}
