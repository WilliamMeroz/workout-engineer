package com.merozmoreau.workoutengineer.data;

import com.merozmoreau.workoutengineer.models.Exercise;

import java.util.List;

public interface RequestGeneratorCallback {
    void fetchExercisesCallback(List<Exercise> exercises);
}
