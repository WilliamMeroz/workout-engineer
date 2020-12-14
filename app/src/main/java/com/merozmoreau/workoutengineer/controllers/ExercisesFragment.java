package com.merozmoreau.workoutengineer.controllers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.merozmoreau.workoutengineer.R;
import com.merozmoreau.workoutengineer.data.ExerciseDa;
import com.merozmoreau.workoutengineer.data.ExerciseDaCallback;
import com.merozmoreau.workoutengineer.data.ExerciseListAdapter;
import com.merozmoreau.workoutengineer.data.RequestGenerator;
import com.merozmoreau.workoutengineer.data.RequestGeneratorCallback;
import com.merozmoreau.workoutengineer.data.ViewStateAdapter;
import com.merozmoreau.workoutengineer.models.Exercise;
import com.merozmoreau.workoutengineer.utils.OnExerciseItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExercisesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

// Fragment used to display the lists of exercises in the RecyclerViews in the SelectExercises activity
public class ExercisesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private ExerciseListAdapter adapter;

    private RequestGenerator requestGenerator;
    private ExerciseDa exerciseDa;
    private final int NUM_EXERCISES = 20;

    // TODO: Rename and change types of parameters
    private int muscleParam = 0;

    public ExercisesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ExercisesFragment newInstance(int type) {
        ExercisesFragment fragment = new ExercisesFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            muscleParam = getArguments().getInt("type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_exercises, container, false);
        recyclerView = rootView.findViewById(R.id.exercises_recyclerView);

        requestGenerator = new RequestGenerator(recyclerView.getContext());
        exerciseDa = new ExerciseDa();

        // The muscleParam attribute is used by the adapter to know which kind of exercises to fetch from either the database or the API. Used for the tabs in the TabLayout
        if (muscleParam == 0) {
            requestGenerator.fetchExercises(NUM_EXERCISES, new RequestGeneratorCallback() {
                @Override
                public void fetchExercisesCallback(List<Exercise> exercises) {
                    adapter = new ExerciseListAdapter(rootView.getContext(), exercises, (SelectExercises) getActivity());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
                }
            });
        } else if (muscleParam == -1) {
            // If -1, that means we want to fetch the exercises the user created himself
            exerciseDa.getExercises(new ExerciseDaCallback() {
                @Override
                public void getExercisesCallback(List<Exercise> exercises) {
                    adapter = new ExerciseListAdapter(rootView.getContext(), exercises, (SelectExercises) getActivity());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
                }

                @Override
                public void getExerciseCallback(Exercise exercise) { }
            });
        } else {
                requestGenerator.fetchExercisesByType(NUM_EXERCISES, muscleParam, new RequestGeneratorCallback() {
                    @Override
                    public void fetchExercisesCallback(List<Exercise> exercises) {
                        adapter = new ExerciseListAdapter(rootView.getContext(), exercises, (SelectExercises) getActivity());
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
                    }
                });
            }

        return rootView;
    }
}