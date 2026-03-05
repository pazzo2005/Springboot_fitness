package com.fitness.fitnesss.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.fitnesss.entity.FitnessGoal;
import com.fitness.fitnesss.repo.FitnessRepo;

@Service
public class GoalService {
     private static final Logger log = LoggerFactory.getLogger(GoalService.class);
     private final FitnessRepo fitnessrepo;
     private final Object createGoalLock = new Object();

     public GoalService(FitnessRepo fitnessrepo) {
        this.fitnessrepo=fitnessrepo;
     }

     @Transactional
     public void updateProgress(Double calories) {
        if (calories == null || calories <= 0.0) {
            return;
        }

        FitnessGoal goal = getOrCreateGoalForUpdate();
        double newProgress  = goal.getCurrentProgress() + calories;
        goal.setCurrentProgress(newProgress);
        if (newProgress >= goal.getTargetCalories()) {
            log.info("Congratulations! Fitness goal achieved. progress={}, target={}", newProgress, goal.getTargetCalories());
        }
        fitnessrepo.save(goal);
     }

     private FitnessGoal getOrCreateGoalForUpdate() {
        List<FitnessGoal> goals = fitnessrepo.findFirstForUpdate(PageRequest.of(0, 1));
        if (!goals.isEmpty()) {
            return goals.get(0);
        }

        synchronized (createGoalLock) {
            return fitnessrepo.findAll(PageRequest.of(0, 1))
                    .stream()
                    .findFirst()
                    .orElseGet(() -> {
                        FitnessGoal newGoal = new FitnessGoal();
                        newGoal.setTargetCalories(2000.0);
                        newGoal.setCurrentProgress(0.0);
                        return fitnessrepo.saveAndFlush(newGoal);
                    });
        }
     }
}
