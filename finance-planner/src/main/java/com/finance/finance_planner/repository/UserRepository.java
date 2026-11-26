package com.finance.finance_planner.repository;

import com.finance.finance_planner.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String mail);
    boolean existsByEmail(String mail);
}
