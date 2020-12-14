package com.merozmoreau.workoutengineer.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.merozmoreau.workoutengineer.models.Exercise;

import java.util.ArrayList;
import java.util.List;

// Exercise Data access class with CRUD operations.
public class ExerciseDa {
    private final String COLLECTION_NAME = "exercises";

    private FirebaseFirestore db;

    public ExerciseDa() {
        db = FirebaseFirestore.getInstance();
    }

    public void getExercises(final ExerciseDaCallback callback) {
        final List<Exercise> list = new ArrayList<>();
        db.collection(COLLECTION_NAME).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                    for (QueryDocumentSnapshot doc : task.getResult())
                        list.add(doc.toObject(Exercise.class));

                    callback.getExercisesCallback(list);
            }
        });
    }

    public void getExercise(int id, final ExerciseDaCallback callback) {
        db.collection(COLLECTION_NAME).document(String.valueOf(id)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                    callback.getExerciseCallback(task.getResult().toObject(Exercise.class));
            }
        });
    }

    public void getExercisesByType(Exercise.MuscleType type, final ExerciseDaCallback callback) {
        final List<Exercise> list = new ArrayList<>();
        db.collection(COLLECTION_NAME).whereEqualTo("type", type.toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                    for (QueryDocumentSnapshot doc : task.getResult())
                        list.add(doc.toObject(Exercise.class));

                    callback.getExercisesCallback(list);
            }
        });
    }

    public void addExercise(Exercise exercise) {
        db.collection(COLLECTION_NAME).document(exercise.getName()).set(exercise).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("test1", "add success");
            }
        });
    }

    public void deleteExercise(int id) {
        db.collection(COLLECTION_NAME).document(String.valueOf(id)).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("test1", "delete");
            }
        });
    }

    public void updateExercise(String name, Exercise exercise) {
        db.collection(COLLECTION_NAME).document(name).set(exercise).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("test1", "update success");
            }
        });
    }
}
