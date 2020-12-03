package com.merozmoreau.workoutengineer.data;

import com.merozmoreau.workoutengineer.models.Exercise;

import java.util.List;

public interface ExerciseDaCallback {
    void getExercisesCallback(List<Exercise> exercises);
    void getExerciseCallback(Exercise exercise);
}
