package com.fitness.fitnesss.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.fitnesss.entity.WorkoutLogger;



@Repository
public interface LoggerRepo extends JpaRepository<WorkoutLogger ,Long> {        
      
}
