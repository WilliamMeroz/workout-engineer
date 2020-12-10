package com.merozmoreau.workoutengineer.controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.merozmoreau.workoutengineer.R;
import com.merozmoreau.workoutengineer.data.WorkoutDa;
import com.merozmoreau.workoutengineer.models.Exercise;
import com.merozmoreau.workoutengineer.models.Workout;
import com.merozmoreau.workoutengineer.utils.GeneralCallback;
import com.merozmoreau.workoutengineer.utils.OptionsMenuGeneral;
import com.merozmoreau.workoutengineer.utils.TableGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditWorkoutActivity extends OptionsMenuGeneral {

    private Gson gson;
    private Workout workout;
    private WorkoutDa db;
    private EditableTableFragment editableTableFragment;
    private HashMap<Exercise, List<EditText>> exercisesAndViews;

    private FragmentManager fragmentManager;
    private AlertDialogFragmentTwoButtons alertDialogFragmentTwoButtons;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);

        initialize();

        fragmentManager.beginTransaction().add(R.id.table_placeholder_edit, editableTableFragment).commit();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercisesAndViews = editableTableFragment.tableGenerator.getExercisesAndViews();
                final Workout newWorkout = new Workout();
                for (Exercise exercise : exercisesAndViews.keySet()) {
                    EditText setsEt = exercisesAndViews.get(exercise).get(0);
                    EditText repsEt = exercisesAndViews.get(exercise).get(1);
                    EditText weightEt = exercisesAndViews.get(exercise).get(2);

                    exercise.setSetNumber(Integer.parseInt(setsEt.getText().toString()));
                    exercise.setRepNumber(Integer.parseInt(repsEt.getText().toString()));
                    exercise.setWeightUsed(Integer.parseInt(weightEt.getText().toString()));

                    newWorkout.addExerciseToWorkout(exercise);
                }

                newWorkout.setName(workout.getName());
                newWorkout.setId(workout.getId());
                db.updateWorkout(newWorkout.getId(), newWorkout);

                startActivity(new Intent(EditWorkoutActivity.this, MainActivity.class));
            }
        });
    }


    private void initialize() {
        button = findViewById(R.id.finish_edit_workout);

        fragmentManager = getSupportFragmentManager();
        gson = new Gson();
        editableTableFragment = new EditableTableFragment();
        db = new WorkoutDa();

        workout = gson.fromJson(getIntent().getStringExtra("workout"), Workout.class);

        ArrayList<String> tempList = new ArrayList<>();

        for (Exercise exercise : workout.getExercisesList())
            tempList.add(gson.toJson(exercise));

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("exercises", tempList);

        editableTableFragment.setArguments(bundle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.delete_workout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_workout_item) {
            GeneralCallback yesCallback = new GeneralCallback() {
                @Override
                public void performAction() {
                    db.deleteWorkout(workout.getId());
                    startActivity(new Intent(EditWorkoutActivity.this, MainActivity.class));
                    finish();
                }
            };

            alertDialogFragmentTwoButtons = new AlertDialogFragmentTwoButtons(this,
                    getString(R.string.delete_workout_menu_item),
                    getString(R.string.delete_workout_text),
                    yesCallback);

            alertDialogFragmentTwoButtons.setCancelable(false);
            alertDialogFragmentTwoButtons.show(fragmentManager, "deleteWorkoutDialog");
        }

        return true;
    }
}