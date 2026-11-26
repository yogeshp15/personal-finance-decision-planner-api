
package com.finance.finance_planner.repository;

import com.finance.finance_planner.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    List<ExpenseEntity> findByUserId(Long userId);

    Optional<ExpenseEntity> findByIdAndUserId(Long id, Long userId);

    List<ExpenseEntity> findByUserIdAndDateBetween(
            Long userId,
            LocalDate startDate,
            LocalDate endDate
    );
}
