package com.fitness.fitnesss.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fitness.fitnesss.entity.WorkoutLogger;
import com.fitness.fitnesss.repo.LoggerRepo;

@Service
public class WorkoutService {
    private final LoggerRepo loggerRepo;
    public WorkoutService(LoggerRepo loggerRepo){
        this.loggerRepo=loggerRepo;
    }
    public WorkoutLogger saveWorkout(WorkoutLogger workout){
        if(workout.getCaloriesBurned() == 0) {
            workout.setCaloriesBurned(workout.getDurationInMinutes()*7.0);
        }
        return loggerRepo.save(workout);
    }
    public List<WorkoutLogger> getAllWorkouts() {
        return loggerRepo.findAll();
    }
    public void deleteWorkout(Long id){
        loggerRepo.deleteById(id);
    }
}
