package com.fitness.fitnesss.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name="fitness_goal")
public class FitnessGoal {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private double targetCalories;
   private double currentProgress;

   @Version
   private Long version;

   public FitnessGoal(){
    
   }
   public FitnessGoal(Long id,double targetCalories,double currentProgress) {
         this.id =id;
         this.targetCalories=targetCalories;
         this.currentProgress=currentProgress;
   }

    public Long getId() {
        return id;
    }

    public double getTargetCalories() {
        return targetCalories;
    }

    public void setTargetCalories(double targetCalories) {
        this.targetCalories = targetCalories;
    }
   

   public double getCurrentProgress(){
      return currentProgress;
   }
   public void setCurrentProgress(double currentProgress){
      this.currentProgress=currentProgress;
   }

   public Long getVersion() {
      return version;
   }

   
}
