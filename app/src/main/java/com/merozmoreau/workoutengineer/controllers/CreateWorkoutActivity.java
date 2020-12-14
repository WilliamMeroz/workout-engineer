package com.merozmoreau.workoutengineer.controllers;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.merozmoreau.workoutengineer.R;
import com.merozmoreau.workoutengineer.data.WorkoutDa;
import com.merozmoreau.workoutengineer.models.Exercise;
import com.merozmoreau.workoutengineer.models.Workout;
import com.merozmoreau.workoutengineer.utils.OptionsMenuGeneral;
import com.merozmoreau.workoutengineer.utils.ThemeApplier;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.List;

// Activity that creates the workout
public class CreateWorkoutActivity extends OptionsMenuGeneral {
    private WorkoutDa db;
    private ThemeApplier themeApplier;

    private HashMap<Exercise, List<EditText>> exercisesAndViews;
    private EditableTableFragment fragment;
    private Context context;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        initialize();

        // We display the Fragment that shows the table with the exercises in them.
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

                // We ask the user to enter a name for the workout
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
        themeApplier = new ThemeApplier(this);
        context = this;
        button = findViewById(R.id.finish_create_workout);

        findViewById(R.id.create_workout_background).setBackgroundColor(themeApplier.getBackgroundColor());
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(themeApplier.getAppBarColor()));
        getSupportActionBar().setTitle(Html.fromHtml(themeApplier.getAppbarTitleColor(getString(R.string.create_workout_title))));

        // Pass this bundle to the Fragment that needs to display the table.
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("exercises", getIntent().getStringArrayListExtra("exercises"));

        fragment = new EditableTableFragment();
        fragment.setArguments(bundle);
    }
}