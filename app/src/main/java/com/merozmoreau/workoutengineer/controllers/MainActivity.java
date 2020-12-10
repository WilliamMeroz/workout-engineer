package com.merozmoreau.workoutengineer.controllers;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.merozmoreau.workoutengineer.R;
import com.merozmoreau.workoutengineer.data.WorkoutDa;
import com.merozmoreau.workoutengineer.data.WorkoutDaCallback;
import com.merozmoreau.workoutengineer.models.Workout;
import com.merozmoreau.workoutengineer.utils.OptionsMenuGeneral;
import com.merozmoreau.workoutengineer.utils.ThemeApplier;

import java.util.List;


public class MainActivity extends OptionsMenuGeneral {

    Button createWorkoutButton;
    Button editWorkoutButton;
    Button startWorkoutButton;

    private ThemeApplier themeApplier;

    private final int SEND_START_WORKOUT = 1;
    private final int SEND_EDIT_WORKOUT = 2;

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
                intent = new Intent(MainActivity.this, SelectWorkoutActivity.class);
                intent.putExtra("action_code", SEND_EDIT_WORKOUT);
                startActivity(intent);
                finish();
            } else if (id == R.id.button_start_workout){
                intent = new Intent(MainActivity.this, SelectWorkoutActivity.class);
                intent.putExtra("action_code", SEND_START_WORKOUT);
                startActivity(intent);
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
        themeApplier = new ThemeApplier(this);

        // Set theme colors
        TextView textView = findViewById(R.id.main_activity_title);
        textView.setTextColor(themeApplier.getGeneralTextColor());
        findViewById(R.id.main_activity_background).setBackgroundColor(themeApplier.getBackgroundColor());
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(themeApplier.getAppBarColor()));
        getSupportActionBar().setTitle(Html.fromHtml(String.format("<font=\"\"")));

        createWorkoutButton = findViewById(R.id.button_create_workout);
        editWorkoutButton = findViewById(R.id.button_edit_workout);
        startWorkoutButton = findViewById(R.id.button_start_workout);

        createWorkoutButton.setOnClickListener(buttonOnClickListener);
        editWorkoutButton.setOnClickListener(buttonOnClickListener);
        startWorkoutButton.setOnClickListener(buttonOnClickListener);
    }
}