package com.merozmoreau.workoutengineer.data;

import com.merozmoreau.workoutengineer.models.Workout;

import java.util.List;

public interface WorkoutDaCallback {
    void getWorkoutsCallback(List<Workout> workouts);
    void getWorkoutCallback(Workout workout);
}
