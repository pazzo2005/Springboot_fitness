package com.fitness.fitnesss.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.fitnesss.entity.FitnessGoal;
import com.fitness.fitnesss.repo.FitnessRepo;

@Service
public class GoalService {
     private static final Logger log = LoggerFactory.getLogger(GoalService.class);
     private static final String GLOBAL_GOAL_KEY = "GLOBAL_DEFAULT_GOAL";
     private final FitnessRepo fitnessrepo;

     public GoalService(FitnessRepo fitnessrepo) {
        this.fitnessrepo=fitnessrepo;
     }

     @Transactional
     public void updateProgress(Double calories) {
        if (calories == null || calories <= 0.0) {
            return;
        }

        FitnessGoal goal = getOrCreateGoalForUpdate();
        double newProgress  = goal.getCurrentProgress() - calories;
        goal.setCurrentProgress(newProgress);
        if (newProgress >= goal.getTargetCalories()) {
            log.info("Congratulations! Fitness goal achieved. progress={}, target={}", newProgress, goal.getTargetCalories());
        }
        fitnessrepo.save(goal);
     }

     private FitnessGoal getOrCreateGoalForUpdate() {
        return fitnessrepo.findByGoalKeyForUpdate(GLOBAL_GOAL_KEY)
                .orElseGet(() -> createGoalAndRecoverOnRace());
     }

     private FitnessGoal createGoalAndRecoverOnRace() {
        try {
            FitnessGoal newGoal = new FitnessGoal();
            newGoal.setGoalKey(GLOBAL_GOAL_KEY);
            newGoal.setTargetCalories(2000.0);
            newGoal.setCurrentProgress(0.0);
            return fitnessrepo.saveAndFlush(newGoal);
        } catch (DataIntegrityViolationException ex) {
            return fitnessrepo.findByGoalKeyForUpdate(GLOBAL_GOAL_KEY)
                    .orElseThrow(() -> ex);
        }
     }
}
