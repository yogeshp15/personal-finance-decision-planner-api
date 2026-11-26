package com.finance.finance_planner.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class IncomeResponseDTO {

    private Long id;
    private String source;
    private BigDecimal amount;
    private String description;
    private LocalDate date;

    public IncomeResponseDTO(Long id, String source, BigDecimal amount,
                             String description, LocalDate date) {
        this.id = id;
        this.source = source;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getSource() {
        return source;
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