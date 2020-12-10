package com.merozmoreau.workoutengineer.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.merozmoreau.workoutengineer.R;
import com.merozmoreau.workoutengineer.models.Exercise;
import com.merozmoreau.workoutengineer.models.Workout;
import com.merozmoreau.workoutengineer.utils.CompleteExerciseCallback;
import com.merozmoreau.workoutengineer.utils.TableGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PerformWorkoutActivity extends AppCompatActivity implements CompleteExerciseCallback {

    private Workout workout;
    private List<Exercise> exercises;
    private Gson gson;
    private ProgressBar progressBar;
    private NonEditableTableFragment fragment;
    private AlertDialogFragment alertDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perform_workout);

        initialize();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.table_placeholder, fragment);
        fragmentTransaction.commit();

    }

    private void initialize() {
        gson = new Gson();
        Intent intent = getIntent();
        workout = gson.fromJson(intent.getStringExtra("workout"), Workout.class);
        exercises = workout.getExercisesList();

        ArrayList<String> serializedExercises = new ArrayList<>();

        for (Exercise exercise : exercises)
            serializedExercises.add(gson.toJson(exercise));

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("exercises", serializedExercises);
        bundle.putBoolean("isEditable", false);

        fragment = new NonEditableTableFragment();
        fragment.setArguments(bundle);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(exercises.size());

         alertDialogFragment = new AlertDialogFragment(this, getString(R.string.congratulation_title),
                getString(R.string.congratulation_text),
                getString(R.string.finish_workout_button));
         alertDialogFragment.setCancelable(false);
    }

    @Override
    public void completeExercise(int increment) {
        progressBar.incrementProgressBy(increment);

        if (progressBar.getProgress() == exercises.size()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            alertDialogFragment.show(fragmentManager, "workoutComplete");
        }
    }
}