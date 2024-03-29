package com.merozmoreau.workoutengineer.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

// Workout model to encapsulate the information about each workouts the user creates.
public class Workout {
    private int id;
    private String name;
    private List<Exercise> exercisesList;

    public Workout() {
        id = new Random().nextInt();
        exercisesList = new ArrayList<>();
    }

    public Workout(int id, String name, List<Exercise> exercisesList) {
        this.id = id;
        this.name = name;
        this.exercisesList = exercisesList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Exercise> getExercisesList() {
        return exercisesList;
    }

    public void setExercisesList(List<Exercise> exercisesList) {
        this.exercisesList = exercisesList;
    }

    public void addExerciseToWorkout(Exercise exercise) {
        exercisesList.add(exercise);
    }

    public void removeExercise(int id) {
        for (Exercise e : exercisesList)
            if (e.getId() == id)
                exercisesList.remove(e);
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", exercisesList=" + exercisesList +
                '}';
    }
}
