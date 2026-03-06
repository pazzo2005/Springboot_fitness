package com.fitness.fitnesss.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.fitnesss.entity.WorkoutLogger;

@Service
public class WorkoutCommandService {

    private final WorkoutService workoutService;
    private final GoalService goalService;

    public WorkoutCommandService(WorkoutService workoutService, GoalService goalService) {
        this.workoutService = workoutService;
        this.goalService = goalService;
    }

    @Transactional
    public WorkoutLogger createWorkoutAndUpdateGoal(WorkoutLogger workout) {
        WorkoutLogger savedWorkout = workoutService.saveWorkout(workout);
        goalService.updateProgress(savedWorkout.getCaloriesBurned());
        return savedWorkout;
    }
}

