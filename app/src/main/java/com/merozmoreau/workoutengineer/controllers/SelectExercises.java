package com.merozmoreau.workoutengineer.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.merozmoreau.workoutengineer.R;
import com.merozmoreau.workoutengineer.data.ExerciseListAdapter;
import com.merozmoreau.workoutengineer.data.RequestGenerator;
import com.merozmoreau.workoutengineer.data.RequestGeneratorCallback;
import com.merozmoreau.workoutengineer.models.Exercise;
import com.merozmoreau.workoutengineer.utils.OnExerciseItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectExercises extends AppCompatActivity implements OnExerciseItemClickListener {

    private final int NUM_EXERCISES_IN_LIST = 20;

    private RecyclerView exerciseRecyclerView;
    private Button button;

    private ExerciseListAdapter adapter;
    private RequestGenerator requestGenerator;
    private Context context;

    private List<Exercise> selectedExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exercises);

        initialize();

        requestGenerator.fetchExercises(NUM_EXERCISES_IN_LIST, new RequestGeneratorCallback() {
            @Override
            public void fetchExercisesCallback(List<Exercise> exercises) {
                adapter = new ExerciseListAdapter(context, exercises, SelectExercises.this);

                exerciseRecyclerView.setAdapter(adapter);
                exerciseRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
        });
    }

    @Override
    public void onItemClick(Exercise exercise) {
        // Make sure that we cannot add 2 times the same exercise.
        if (!selectedExercises.contains(exercise))
            selectedExercises.add(exercise);
        else
            selectedExercises.remove(exercise);
    }

    private void initialize() {
        exerciseRecyclerView = findViewById(R.id.exercises_recyclerView);
        button = findViewById(R.id.button_exercises_selected);

        context = this;
        requestGenerator = new RequestGenerator(this);
        selectedExercises = new ArrayList<>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectExercises.this, CreateWorkoutActivity.class);

                ArrayList<String> tempList = new ArrayList<>();
                for (Exercise exercise : selectedExercises)
                    tempList.add(new Gson().toJson(exercise));

                intent.putStringArrayListExtra("exercises", tempList);

                startActivity(intent);
                finish();
            }
        });
    }
}