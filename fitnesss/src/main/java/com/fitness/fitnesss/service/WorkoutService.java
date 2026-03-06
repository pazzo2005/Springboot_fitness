package com.fitness.fitnesss.service;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.fitnesss.entity.WorkoutLogger;
import com.fitness.fitnesss.repo.LoggerRepo;

@Service
public class WorkoutService {
    private final LoggerRepo loggerRepo;
    public WorkoutService(LoggerRepo loggerRepo){
        this.loggerRepo=loggerRepo;
    }

    @Transactional
    public WorkoutLogger saveWorkout(WorkoutLogger workout){
        if (workout == null) {
            throw new IllegalArgumentException("Workout payload cannot be null");
        }

        Integer duration = workout.getDurationInMinutes();
        if (duration == null || duration <= 0) {
            throw new IllegalArgumentException("Duration must be a positive number of minutes");
        }

        Double calories = workout.getCaloriesBurned();
        if (calories == null || calories <= 0.0) {
            workout.setCaloriesBurned(duration * 7.0);
        }

        return loggerRepo.save(workout);
    }

    @Transactional(readOnly = true)
    public List<WorkoutLogger> getAllWorkouts() {
        return loggerRepo.findAll();
    }

    @Transactional
    public void deleteWorkout(Long id){
        try {
            loggerRepo.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new IllegalArgumentException("Workout not found for id: " + id, ex);
        }
    }
}
