package com.merozmoreau.workoutengineer.controllers;

import android.os.Bundle;
import android.util.Log;

import com.merozmoreau.workoutengineer.R;
import com.merozmoreau.workoutengineer.data.RequestGenerator;
import com.merozmoreau.workoutengineer.data.RequestGeneratorCallback;
import com.merozmoreau.workoutengineer.models.Exercise;
import com.merozmoreau.workoutengineer.utils.OptionsMenuGeneral;

import java.util.List;

public class MainActivity extends OptionsMenuGeneral {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestGenerator r = new RequestGenerator(this);
        r.fetchExercises(20, new RequestGeneratorCallback() {
            @Override
            public void fetchExercisesCallback(List<Exercise> exercises) {
                for (Exercise e : exercises)
                    Log.d("test1", e.toString());

                Log.d("test1", "size: " + exercises.size());
            }
        });
    }

    private void initialize() {

    }
}