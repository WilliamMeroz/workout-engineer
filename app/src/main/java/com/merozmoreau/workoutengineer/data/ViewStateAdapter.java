package com.merozmoreau.workoutengineer.data;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.merozmoreau.workoutengineer.controllers.ExercisesFragment;
import com.merozmoreau.workoutengineer.models.Exercise;

// Simple FragmentStateAdapter used by the TabLayout on the SelectExercises activity.
public class ViewStateAdapter extends FragmentStateAdapter {

    private int numberOfTabs;

    public ViewStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                // All exercises
                return new ExercisesFragment();
            case 1:
                // Arms only
                return ExercisesFragment.newInstance(1);
            case 2:
                // Core
                return ExercisesFragment.newInstance(6);
            case 3:
                // Legs
                return ExercisesFragment.newInstance(10);
            case 4:
                // Back
                return ExercisesFragment.newInstance(12);
            case 5:
                // Custom exercises
                return ExercisesFragment.newInstance(-1);
            default:
                return new ExercisesFragment();
        }
    }

    @Override
    public int getItemCount() {
        return numberOfTabs;
    }

    public void setNumberOfTabs(int numberOfTabs) {
        this.numberOfTabs = numberOfTabs;
    }
}
