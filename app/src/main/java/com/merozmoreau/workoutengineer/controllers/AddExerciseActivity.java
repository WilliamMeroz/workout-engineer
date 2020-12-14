package com.merozmoreau.workoutengineer.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.merozmoreau.workoutengineer.R;
import com.merozmoreau.workoutengineer.data.ExerciseDa;
import com.merozmoreau.workoutengineer.models.Exercise;
import com.merozmoreau.workoutengineer.utils.OptionsMenuGeneral;

public class AddExerciseActivity extends OptionsMenuGeneral {

    private EditText nameEt;
    private EditText descEt;
    private Button createButton;
    private Button cancelButton;

    private ExerciseDa exerciseDa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        initialize();
    }

    private void initialize() {
        exerciseDa = new ExerciseDa();

        nameEt = findViewById(R.id.new_exercise_name_et);
        descEt = findViewById(R.id.new_exercise_desc_et);
        createButton = findViewById(R.id.create_exercise_button);
        cancelButton = findViewById(R.id.create_exercise_cancel);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Verify the user entered a name
                if (nameEt.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "You have to enter an exercise name!", Toast.LENGTH_LONG).show();
                    return;
                }

                // Create the exercise, set its parameters and add it into the DB
                Exercise ex = new Exercise();
                ex.setName(nameEt.getText().toString());
                ex.setDescription(descEt.getText().toString());
                exerciseDa.addExercise(ex);

                // Go back to the SelectExercises Activity
                startActivity(new Intent(AddExerciseActivity.this, SelectExercises.class));
                finish();
            }
        });

        // If the user cancels, we just go back to the previous activity
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddExerciseActivity.this, SelectExercises.class));
                finish();
            }
        });
    }
}