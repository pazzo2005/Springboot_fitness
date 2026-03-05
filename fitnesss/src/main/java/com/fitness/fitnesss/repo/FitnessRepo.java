package com.fitness.fitnesss.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fitness.fitnesss.entity.FitnessGoal;

import jakarta.persistence.LockModeType;

@Repository
public interface FitnessRepo extends JpaRepository<FitnessGoal,Long>{

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT g FROM FitnessGoal g ORDER BY g.id ASC")
    List<FitnessGoal> findFirstForUpdate(Pageable pageable);
}
