package com.finance.finance_planner.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseResponseDTO {

    private Long id;
    private String category;
    private BigDecimal amount;
    private String description;
    private LocalDate date;

    public ExpenseResponseDTO(
            Long id,
            String category,
            BigDecimal amount,
            String description,
            LocalDate date) {

        this.id = id;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }
}