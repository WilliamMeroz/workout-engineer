package com.merozmoreau.workoutengineer.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.merozmoreau.workoutengineer.R;
import com.merozmoreau.workoutengineer.data.WorkoutDa;
import com.merozmoreau.workoutengineer.data.WorkoutDaCallback;
import com.merozmoreau.workoutengineer.models.Workout;
import com.merozmoreau.workoutengineer.utils.OptionsMenuGeneral;

import java.util.List;


public class MainActivity extends OptionsMenuGeneral {

    Button createWorkoutButton;
    Button editWorkoutButton;
    Button startWorkoutButton;

    View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            Intent intent;
            if (id == R.id.button_create_workout) {
                intent = new Intent(MainActivity.this, SelectExercises.class);
                startActivity(intent);
                finish();
            } else if (id == R.id.button_edit_workout) {
                //intent = new Intent(MainActivity.this, ...);
                finish();
            } else {
                //intent = new Intent(MainActivity.this, ...);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        createWorkoutButton = findViewById(R.id.button_create_workout);
        editWorkoutButton = findViewById(R.id.button_edit_workout);
        startWorkoutButton = findViewById(R.id.button_start_workout);

        createWorkoutButton.setOnClickListener(buttonOnClickListener);
        editWorkoutButton.setOnClickListener(buttonOnClickListener);
        startWorkoutButton.setOnClickListener(buttonOnClickListener);
    }
}