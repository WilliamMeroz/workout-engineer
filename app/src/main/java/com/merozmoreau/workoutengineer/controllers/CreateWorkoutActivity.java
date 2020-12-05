package com.merozmoreau.workoutengineer.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.merozmoreau.workoutengineer.R;
import com.merozmoreau.workoutengineer.data.WorkoutDa;
import com.merozmoreau.workoutengineer.models.Exercise;
import com.merozmoreau.workoutengineer.models.Workout;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.grpc.StatusException;

public class CreateWorkoutActivity extends AppCompatActivity {

    List<Exercise> exercises;
    HashMap<Exercise, List<EditText>> exercisesAndViews;
    WorkoutDa db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_create_workout);

        db = new WorkoutDa();

        // Create our ScrollView.
        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // Create our TableLayout.
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tableLayout.setStretchAllColumns(true);

        TableRow header = new TableRow(this);
        header.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        header.addView(generateHeaderCell((String) getResources().getText(R.string.table_header_exercise)));
        header.addView(generateHeaderCell((String) getResources().getText(R.string.table_header_sets)));
        header.addView(generateHeaderCell((String) getResources().getText(R.string.table_headers_rep)));
        header.addView(generateHeaderCell((String) getResources().getText(R.string.table_headers_weight)));

        tableLayout.addView(header);

        exercises = (List<Exercise>) getIntent().getSerializableExtra("exercises");

        for (TableRow row : generateTableRows(exercises))
            tableLayout.addView(row);

        Button button = new Button(this);
        button.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
        button.setText(getResources().getText(R.string.button_create_workout));

        // When user finishes entering data, create new workout.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Workout workout = new Workout();
                for (Exercise exercise : exercisesAndViews.keySet()) {
                    EditText setsEt = exercisesAndViews.get(exercise).get(0);
                    EditText repsEt = exercisesAndViews.get(exercise).get(1);
                    EditText weightEt = exercisesAndViews.get(exercise).get(2);

                    exercise.setSetNumber(Integer.parseInt(setsEt.getText().toString()));
                    exercise.setRepNumber(Integer.parseInt(repsEt.getText().toString()));
                    exercise.setWeightUsed(Float.parseFloat(weightEt.getText().toString()));

                    workout.addExerciseToWorkout(exercise);
                }

                // TODO: Ask for name from user
                FragmentManager fragmentManager = getSupportFragmentManager();
                InputDialogFragment dialogFragment = new InputDialogFragment(new InputDialogCallback() {
                    @Override
                    public void performAction(String data) {
                        workout.setName(data);
                        db.addWorkout(workout);
                    }
                });
                dialogFragment.show(fragmentManager, "Workout Name Dialog");
            }
        });

        tableLayout.addView(button);
        scrollView.addView(tableLayout);

        // Display our root ScrollView.
        setContentView(scrollView);
    }

    private List<TableRow> generateTableRows(List<Exercise> exercises) {
        List<TableRow> rows = new ArrayList<>();
        exercisesAndViews = new HashMap<>();

        InputFilter numericFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int start, int end) {

                try {
                    Integer.parseInt(charSequence.toString());
                    return null;
                } catch (NumberFormatException e) {
                    return "";
                }
            }
        };

        for (Exercise exercise : exercises) {
            TextView exerciseName = new TextView(this);
            exerciseName.setLayoutParams(new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            // Useful with making sure that the lengths of the names are not too big to fit on the screen.
            if (exercise.getName().length() >= 20)
                exerciseName.setText(exercise.getName().substring(0, 20));
            else
                exerciseName.setText(exercise.getName());

            exerciseName.setTextColor(getResources().getColor(R.color.basicTextColor));
            exerciseName.setTextSize(22);
            exerciseName.setPadding(5, 5, 5, 5);
            exerciseName.setBackground(getResources().getDrawable(R.drawable.cell_shape_general));

            EditText setsEt = new EditText(this);
            setsEt.setLayoutParams(new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            setsEt.setTextColor(getResources().getColor(R.color.basicTextColor));
            setsEt.setTextSize(22);
            setsEt.setText("0");
            setsEt.setSelectAllOnFocus(true);
            setsEt.setFilters(new InputFilter[] { new InputFilter.LengthFilter(2), numericFilter });
            setsEt.setPadding(5, 5, 5, 5);
            setsEt.setBackground(getResources().getDrawable(R.drawable.cell_shape_general));

            EditText repsEt = new EditText(this);
            repsEt.setLayoutParams(new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            repsEt.setTextColor(getResources().getColor(R.color.basicTextColor));
            repsEt.setTextSize(22);
            repsEt.setText("0");
            repsEt.setSelectAllOnFocus(true);
            repsEt.setFilters(new InputFilter[] { new InputFilter.LengthFilter(2), numericFilter });
            repsEt.setPadding(5, 5, 5, 5);
            repsEt.setBackground(getResources().getDrawable(R.drawable.cell_shape_general));

            // TODO: Make weight used float
            EditText weightEt = new EditText(this);
            weightEt.setLayoutParams(new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            weightEt.setTextColor(getResources().getColor(R.color.basicTextColor));
            weightEt.setFilters(new InputFilter[] { new InputFilter.LengthFilter(2), numericFilter });
            weightEt.setTextSize(22);
            weightEt.setText("0");
            weightEt.setSelectAllOnFocus(true);
            weightEt.setPadding(5, 5, 5, 5);
            weightEt.setBackground(getResources().getDrawable(R.drawable.cell_shape_general));

            TableRow row = new TableRow(this);
            row.addView(exerciseName);
            row.addView(setsEt);
            row.addView(repsEt);
            row.addView(weightEt);

            List<EditText> tempList = new ArrayList<>();
            tempList.add(setsEt);
            tempList.add(repsEt);
            tempList.add(weightEt);

            exercisesAndViews.put(exercise, tempList);

            rows.add(row);
        }

        return rows;
    }

    private TextView generateHeaderCell(String message) {
        TextView t = new TextView(this);
        t.setLayoutParams(new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        t.setText(message);
        t.setTextColor(getResources().getColor(R.color.basicTextColor));
        t.setTextSize(22);
        t.setPadding(5, 5, 5, 5);
        t.setBackground(getResources().getDrawable(R.drawable.cell_shape_headers));

        return t;
    }
}