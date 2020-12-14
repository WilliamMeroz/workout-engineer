package com.merozmoreau.workoutengineer.controllers;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.merozmoreau.workoutengineer.R;
import com.merozmoreau.workoutengineer.data.ViewStateAdapter;
import com.merozmoreau.workoutengineer.models.Exercise;
import com.merozmoreau.workoutengineer.utils.OnExerciseItemClickListener;
import com.merozmoreau.workoutengineer.utils.OptionsMenuGeneral;
import com.merozmoreau.workoutengineer.utils.ThemeApplier;


import java.util.ArrayList;
import java.util.List;

// Used by the user to select exercises to add in a workout.
public class SelectExercises extends OptionsMenuGeneral implements OnExerciseItemClickListener {

    private List<Exercise> selectedExercises;

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private Toolbar toolbar;
    private ThemeApplier themeApplier;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exercises);

        initialize();

        setSupportActionBar(toolbar);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.all)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.arms)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.core)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.legs)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.back)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.custom_exercises)));

        FragmentManager fragmentManager = getSupportFragmentManager();
        ViewStateAdapter viewStateAdapter = new ViewStateAdapter(fragmentManager, getLifecycle());
        viewStateAdapter.setNumberOfTabs(tabLayout.getTabCount());
        viewPager2.setAdapter(viewStateAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Ensures the user has selected exercises
                if (selectedExercises.size() == 0) {
                    Toast.makeText(getApplicationContext(), getString(R.string.no_exercises_error), Toast.LENGTH_LONG).show();
                    return;
                }

                // Send the selected exercises to the CreateWorkoutActivity.
                Intent intent = new Intent(SelectExercises.this, CreateWorkoutActivity.class);
                Gson gson = new Gson();

                ArrayList<String> tempList = new ArrayList<>();

                for (Exercise exercise : selectedExercises)
                    tempList.add(gson.toJson(exercise));

                intent.putStringArrayListExtra("exercises", tempList);

                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onItemClick(Exercise exercise) {
        // Make sure that we cannot add 2 times the same exercise.
        for (Exercise ex: selectedExercises) {
            if (ex.getName().equals(exercise.getName()))
                return;

            if (ex.getId() == exercise.getId())
                selectedExercises.remove(exercise);
        }
            selectedExercises.add(exercise);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.add_exercise_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.add_exercise_item) {
            Intent intent = new Intent(SelectExercises.this, AddExerciseActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    private void initialize() {
        themeApplier = new ThemeApplier(this);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        floatingActionButton = findViewById(R.id.fab_create_workout);

        selectedExercises = new ArrayList<>();

        findViewById(R.id.select_exercises_background).setBackgroundColor(themeApplier.getBackgroundColor());
        toolbar.setBackgroundColor(themeApplier.getAppBarColor());
        toolbar.setTitleTextColor(themeApplier.getGeneralTextColor());
        tabLayout.setTabTextColors(themeApplier.getGeneralTextColor(), themeApplier.getGeneralTextColor());
    }
}