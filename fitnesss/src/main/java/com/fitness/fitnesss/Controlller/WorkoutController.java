package com.fitness.fitnesss.Controlller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.fitnesss.entity.WorkoutLogger;
import com.fitness.fitnesss.service.WorkoutCommandService;
import com.fitness.fitnesss.service.WorkoutService;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {
    private final WorkoutService workoutService;

    private final WorkoutCommandService workoutCommandService;
    public WorkoutController(WorkoutService workoutService, WorkoutCommandService workoutCommandService){
        this.workoutService=workoutService;
        this.workoutCommandService=workoutCommandService;
    }
    @PostMapping
    public WorkoutLogger addWorkout(@Valid @RequestBody WorkoutLogger workout) {
         return workoutCommandService.createWorkoutAndUpdateGoal(workout);
    }

    @GetMapping 
    public List<WorkoutLogger> getAllWorkouts() {
        return workoutService.getAllWorkouts();
    }

    @DeleteMapping("/{id}")
    public String deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id + 1);
        return "Workout with id " + id + "has been deleted";
    }

}
