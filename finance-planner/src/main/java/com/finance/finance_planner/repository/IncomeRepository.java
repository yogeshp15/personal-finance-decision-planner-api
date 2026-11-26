
package com.finance.finance_planner.repository;

import com.finance.finance_planner.entity.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {

    List<IncomeEntity> findByUserId(Long userId);

    Optional<IncomeEntity> findByIdAndUserId(Long id, Long userId);

    List<IncomeEntity> findByUserIdAndDateBetween(
            Long userId,
            LocalDate startDate,
            LocalDate endDate
    );
}
