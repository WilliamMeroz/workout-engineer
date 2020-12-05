package com.merozmoreau.workoutengineer.models;

import java.io.Serializable;

// Exercise model used to encapsulate information about the exercises the user has chosen to do in their workouts.
public class Exercise implements Serializable {
    public enum MuscleType {
        DELTOID,
        BICEPS,
        GLUTEUS,
        PECTORALS,
        QUADRICEPS,
        TRAPEZIUS,
        TRICEPS
    }

    private int id;
    private String name;
    private MuscleType type;
    private int setNumber;
    private int repNumber;
    private float weightUsed;
    private String description;

    public Exercise() {
    }

    public Exercise(int id, String name, MuscleType type, int setNumber, int repNumber, float weightUsed, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.setNumber = setNumber;
        this.repNumber = repNumber;
        this.weightUsed = weightUsed;
        this.description = description;
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

    public MuscleType getType() {
        return type;
    }

    public void setType(MuscleType type) {
        this.type = type;
    }

    public int getSetNumber() {
        return setNumber;
    }

    public int getRepNumber() {
        return repNumber;
    }

    public void setRepNumber(int repNumber) {
        this.repNumber = repNumber;
    }

    public void setSetNumber(int setNumber) {
        this.setNumber = setNumber;
    }

    public float getWeightUsed() {
        return weightUsed;
    }

    public void setWeightUsed(float weightUsed) {
        this.weightUsed = weightUsed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", setNumber=" + setNumber +
                ", repNumber=" + repNumber +
                ", weightUsed=" + weightUsed +
                ", description='" + description + '\'' +
                '}';
    }
}
