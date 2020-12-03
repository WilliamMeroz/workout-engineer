package com.merozmoreau.workoutengineer.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.merozmoreau.workoutengineer.models.Workout;

import java.util.ArrayList;
import java.util.List;

// Workout Data access class with CRUD operations.
public class WorkoutDa {
    private final String COLLECTION_NAME = "workouts";

    private FirebaseFirestore db;

    public WorkoutDa() {
        db = FirebaseFirestore.getInstance();
    }

    public void addWorkout(Workout workout) {
        db.collection(COLLECTION_NAME)
                .document(String.valueOf(workout.getId())).set(workout)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("test1", "added success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("test1", "failed");
                        e.printStackTrace();
                    }
                });
    }

    public void getWorkouts(final WorkoutDaCallback callback) {
        final List<Workout> list = new ArrayList<>();

        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                            for (QueryDocumentSnapshot document : task.getResult())
                                list.add(document.toObject(Workout.class));

                            callback.getWorkoutsCallback(list);
                    }
                });
    }

    public void getWorkout(final int id, final WorkoutDaCallback callback) {
        db.collection(COLLECTION_NAME).document(String.valueOf(id)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    callback.getWorkoutCallback(doc.toObject(Workout.class));
                }
            }
        });
    }

    public void deleteWorkout(int id) {
        db.collection(COLLECTION_NAME).document(String.valueOf(id)).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("test1", "delete success");
            }
        });
    }

    public void updateWorkout(int id, Workout workout) {
        db.collection(COLLECTION_NAME).document(String.valueOf(id)).set(workout).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("test1", "update success");
            }
        });
    }
}
