package com.merozmoreau.workoutengineer.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.merozmoreau.workoutengineer.R;
import com.merozmoreau.workoutengineer.data.ExerciseDa;
import com.merozmoreau.workoutengineer.data.ExerciseDaCallback;
import com.merozmoreau.workoutengineer.data.ExerciseListAdapter;
import com.merozmoreau.workoutengineer.data.RequestGenerator;
import com.merozmoreau.workoutengineer.data.RequestGeneratorCallback;
import com.merozmoreau.workoutengineer.models.Exercise;

import java.util.ArrayList;
import java.util.List;

public class SelectExercises extends AppCompatActivity {

    private final int NUM_EXERCISES_IN_LIST = 20;

    private RecyclerView exerciseRecyclerView;
    private ExerciseListAdapter adapter;
    RequestGenerator requestGenerator;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exercises);
        context = this;

        requestGenerator = new RequestGenerator(this);

        exerciseRecyclerView = findViewById(R.id.exercises_recyclerView);
        requestGenerator.fetchExercises(20, new RequestGeneratorCallback() {
            @Override
            public void fetchExercisesCallback(List<Exercise> exercises) {
                adapter = new ExerciseListAdapter(context, exercises);

                exerciseRecyclerView.setAdapter(adapter);
                exerciseRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
        });


    }
}