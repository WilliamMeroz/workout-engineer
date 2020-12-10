package com.merozmoreau.workoutengineer.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.merozmoreau.workoutengineer.R;
import com.merozmoreau.workoutengineer.data.WorkoutDa;
import com.merozmoreau.workoutengineer.data.WorkoutDaCallback;
import com.merozmoreau.workoutengineer.data.WorkoutsAdapter;
import com.merozmoreau.workoutengineer.models.Workout;
import java.util.List;

public class SelectWorkoutActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WorkoutsAdapter adapter;
    private WorkoutDa db;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_workout);

        initialize();

        // Fetch Workouts collection to get the workouts the user has created.
        db.getWorkouts(new WorkoutDaCallback() {
            @Override
            public void getWorkoutsCallback(List<Workout> workouts) {

                if (workouts.isEmpty()) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    AlertDialogFragment alertDialogFragment = new AlertDialogFragment(context, getString(R.string.title_no_workouts_created),
                            getString(R.string.no_workouts_dialog_error_message),
                            getString(R.string.go_back_button));

                    alertDialogFragment.show(fragmentManager, "noWorkoutsFound");
                    alertDialogFragment.setCancelable(false);
                } else {
                    adapter = new WorkoutsAdapter(context, workouts, getIntent().getIntExtra("action_code", 10));
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(adapter);
                }
            }

            // Unused, we are trying to get all the workouts contained in the collection.
            @Override
            public void getWorkoutCallback(Workout workout) { }
        });
    }

    private void initialize() {
        db = new WorkoutDa();
        recyclerView = findViewById(R.id.workout_recyclerView);
        context = this;
    }
}