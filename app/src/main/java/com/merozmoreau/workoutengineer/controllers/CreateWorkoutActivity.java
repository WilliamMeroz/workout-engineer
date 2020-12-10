package com.merozmoreau.workoutengineer.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.merozmoreau.workoutengineer.R;
import com.merozmoreau.workoutengineer.data.WorkoutDa;
import com.merozmoreau.workoutengineer.models.Exercise;
import com.merozmoreau.workoutengineer.models.Workout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.List;

public class CreateWorkoutActivity extends AppCompatActivity {
    WorkoutDa db;

    HashMap<Exercise, List<EditText>> exercisesAndViews;
    EditableTableFragment fragment;
    Context context;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        initialize();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.editable_table_placeholder, fragment);
        fragmentTransaction.commit();

        // When user finishes entering data, create new workout.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercisesAndViews = fragment.tableGenerator.getExercisesAndViews();
                final Workout workout = new Workout();
                for (Exercise exercise : exercisesAndViews.keySet()) {
                    EditText setsEt = exercisesAndViews.get(exercise).get(0);
                    EditText repsEt = exercisesAndViews.get(exercise).get(1);
                    EditText weightEt = exercisesAndViews.get(exercise).get(2);

                    exercise.setSetNumber(Integer.parseInt(setsEt.getText().toString()));
                    exercise.setRepNumber(Integer.parseInt(repsEt.getText().toString()));
                    exercise.setWeightUsed(Integer.parseInt(weightEt.getText().toString()));

                    workout.addExerciseToWorkout(exercise);
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                InputDialogFragment dialogFragment = new InputDialogFragment(context, new InputDialogCallback() {
                    @Override
                    public void performAction(String data) {
                        workout.setName(data);
                        db.addWorkout(workout);
                    }
                });
                dialogFragment.show(fragmentManager, "Workout Name Dialog");
            }
        });
    }

    private void initialize() {
        db = new WorkoutDa();
        context = this;
        button = findViewById(R.id.finish_create_workout);

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("exercises", getIntent().getStringArrayListExtra("exercises"));

        fragment = new EditableTableFragment();
        fragment.setArguments(bundle);
    }
}