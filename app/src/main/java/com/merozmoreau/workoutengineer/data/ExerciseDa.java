package com.merozmoreau.workoutengineer.data;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.merozmoreau.workoutengineer.models.Exercise;

public class ExerciseDa {
    private final String COLLECTION_NAME = "exercises";

    private FirebaseFirestore db;
    private Context context;

    public ExerciseDa(Context context) {
        db = FirebaseFirestore.getInstance();
        this.context = context;
    }

    public void getExercises() {
        //RequestQueueSingleton.getInstance(context).addToRequestQueue();
    }

    public void getExercise() {

    }

    public void getExercisesByType(Exercise.MuscleType type) {

    }

    public void addExercise(Exercise exercise) {
        db.collection(COLLECTION_NAME).document(String.valueOf(exercise.getId())).set(exercise).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("test1", "add success");
            }
        });
    }

    public void deleteExercise() {

    }

    public void updateExercise() {

    }
}
