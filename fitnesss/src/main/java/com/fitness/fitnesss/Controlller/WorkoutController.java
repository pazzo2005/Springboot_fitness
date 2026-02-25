package com.fitness.fitnesss.Controlller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.fitnesss.entity.WorkoutLogger;
import com.fitness.fitnesss.service.WorkoutService;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {
    private final WorkoutService workoutService;
    public WorkoutController(WorkoutService workoutService){
        this.workoutService=workoutService;
    }
    @PostMapping
    public WorkoutLogger addWorkout(@RequestBody WorkoutLogger workoutLogger) {
        return workoutService.saveWorkout(workoutLogger);
    }

    @GetMapping 
    public List<WorkoutLogger> getAllWorkouts() {
        return workoutService.getAllWorkouts();
    }

    @DeleteMapping("/{id}")
    public String deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return "Workout with id " + id + "has been deleted";
    }
}
