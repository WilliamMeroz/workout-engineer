package com.merozmoreau.workoutengineer.controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
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
import com.merozmoreau.workoutengineer.utils.ThemeApplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Activity used to edit a workout
public class EditWorkoutActivity extends OptionsMenuGeneral {

    private Gson gson;
    private Workout workout;
    private WorkoutDa db;
    private EditableTableFragment editableTableFragment;
    private HashMap<Exercise, List<EditText>> exercisesAndViews;
    private ThemeApplier themeApplier;

    private FragmentManager fragmentManager;
    private AlertDialogFragmentTwoButtons alertDialogFragmentTwoButtons;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);

        initialize();

        // We add the fragment with the editable table.
        fragmentManager.beginTransaction().add(R.id.table_placeholder_edit, editableTableFragment).commit();

        // Once the user is done, we create a new workout object
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

                // We upadte the database with the new workout object
                newWorkout.setName(workout.getName());
                newWorkout.setId(workout.getId());
                db.updateWorkout(newWorkout.getId(), newWorkout);

                startActivity(new Intent(EditWorkoutActivity.this, MainActivity.class));
            }
        });
    }


    private void initialize() {
        button = findViewById(R.id.finish_edit_workout);
        themeApplier = new ThemeApplier(this);
        fragmentManager = getSupportFragmentManager();
        gson = new Gson();
        editableTableFragment = new EditableTableFragment();
        db = new WorkoutDa();

        workout = gson.fromJson(getIntent().getStringExtra("workout"), Workout.class);

        findViewById(R.id.edit_workout_background).setBackgroundColor(themeApplier.getBackgroundColor());
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(themeApplier.getAppBarColor()));
        getSupportActionBar().setTitle(Html.fromHtml(themeApplier.getAppbarTitleColor(getString(R.string.edit_workout_title))));

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
        // If the user clicks on the trashcan icon
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