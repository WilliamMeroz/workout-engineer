package com.merozmoreau.workoutengineer.controllers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.google.gson.Gson;
import com.merozmoreau.workoutengineer.R;
import com.merozmoreau.workoutengineer.models.Exercise;
import com.merozmoreau.workoutengineer.utils.CompleteExerciseCallback;
import com.merozmoreau.workoutengineer.utils.TableGenerator;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditableTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditableTableFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<String> serializedExercises;
    private ArrayList<Exercise> exercises;
    public TableGenerator tableGenerator;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditableTableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditableTableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditableTableFragment newInstance(String param1, String param2) {
        EditableTableFragment fragment = new EditableTableFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            serializedExercises = getArguments().getStringArrayList("exercises");
            exercises = new ArrayList<>();

            for (String exercise : serializedExercises)
                exercises.add(new Gson().fromJson(exercise, Exercise.class));

            tableGenerator = new TableGenerator(getActivity(), exercises, true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ScrollView scrollView = tableGenerator.generateRootView();
        TableLayout tableLayout = tableGenerator.generateTableLayout();
        tableLayout.addView(tableGenerator.generateTableHeader());

        for (TableRow row : tableGenerator.generateTableBody())
            tableLayout.addView(row);

        scrollView.addView(tableLayout);
        // Inflate the layout for this fragment
        return scrollView;
    }

}