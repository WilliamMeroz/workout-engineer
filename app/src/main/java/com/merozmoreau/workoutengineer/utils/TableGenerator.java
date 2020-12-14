package com.merozmoreau.workoutengineer.utils;

import android.app.ActionBar;
import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.merozmoreau.workoutengineer.R;
import com.merozmoreau.workoutengineer.models.Exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Class used to generate the different tables seen in the app. It can generate a table with both editable fields or non-editable fields.
public class TableGenerator {
    private final Context context;
    private final List<Exercise> exercises;
    private final String[] headerColumns = new String[] { "Exercise", "Sets", "Reps", "Weight" };
    private final boolean editableFields;
    private ThemeApplier themeApplier;

    private HashMap<Exercise, List<EditText>> exerciseAndViews;

    public TableGenerator(Context context, List<Exercise> exercises, boolean editableFields) {
        this.context = context;
        this.exercises = exercises;
        this.editableFields = editableFields;

        themeApplier = new ThemeApplier(context);
        exerciseAndViews = new HashMap<>();
    }

    public ScrollView generateRootView() {
        ScrollView scrollView = new ScrollView(context);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return scrollView;
    }

    public TableLayout generateTableLayout() {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tableLayout.setStretchAllColumns(true);
        return tableLayout;
    }

    public TableRow generateTableHeader() {
        TableRow header = new TableRow(context);
        header.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        for (String column : headerColumns) {
            TextView t = new TextView(context);
            t.setLayoutParams(new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            t.setText(column);
            t.setTextColor(context.getResources().getColor(R.color.basicTextColor));
            t.setTextSize(22);
            t.setPadding(5, 5, 5, 5);
            t.setBackground(context.getResources().getDrawable(R.drawable.cell_shape_headers));

            header.addView(t);
        }
        return header;
    }

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

    public List<TableRow> generateTableBody() {
        List<TableRow> rows = new ArrayList<>();

        for (Exercise exercise : exercises) {
            TableRow row = new TableRow(context);
            TextView exerciseName = new TextView(context);
            exerciseName.setLayoutParams(new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            // Useful with making sure that the lengths of the names are not too big to fit on the screen.
            if (exercise.getName().length() >= 19)
                exerciseName.setText(exercise.getName().substring(0, 19));
            else
                exerciseName.setText(exercise.getName());

            exerciseName.setTextColor(themeApplier.getGeneralTextColor());
            exerciseName.setTextSize(22);
            exerciseName.setPadding(5, 5, 5, 5);
            exerciseName.setBackground(context.getResources().getDrawable(R.drawable.cell_shape_general));

            if (!editableFields) {
                TextView setsTv = new TextView(context);
                setsTv.setLayoutParams(new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                setsTv.setTextColor(context.getResources().getColor(R.color.basicTextColor));
                setsTv.setTextSize(22);
                setsTv.setTextColor(themeApplier.getGeneralTextColor());
                setsTv.setText(String.valueOf(exercise.getSetNumber()));
                setsTv.setPadding(5, 5, 5, 5);
                setsTv.setBackground(context.getResources().getDrawable(R.drawable.cell_shape_general));

                TextView repsTv = new TextView(context);
                repsTv.setLayoutParams(new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                repsTv.setTextColor(context.getResources().getColor(R.color.basicTextColor));
                repsTv.setTextSize(22);
                repsTv.setTextColor(themeApplier.getGeneralTextColor());
                repsTv.setText(String.valueOf(exercise.getRepNumber()));
                repsTv.setPadding(5, 5, 5, 5);
                repsTv.setBackground(context.getResources().getDrawable(R.drawable.cell_shape_general));

                // TODO: Make weight used float
                TextView weightTv = new TextView(context);
                weightTv.setLayoutParams(new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                weightTv.setTextColor(context.getResources().getColor(R.color.basicTextColor));
                weightTv.setTextSize(22);
                weightTv.setTextColor(themeApplier.getGeneralTextColor());
                weightTv.setText(String.valueOf(exercise.getWeightUsed()));
                weightTv.setPadding(5, 5, 5, 5);
                weightTv.setBackground(context.getResources().getDrawable(R.drawable.cell_shape_general));

                row.addView(exerciseName);
                row.addView(setsTv);
                row.addView(repsTv);
                row.addView(weightTv);
            } else {
                // Generate EditText fields.
                EditText setsEt = new EditText(context);
                setsEt.setLayoutParams(new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                setsEt.setTextColor(context.getResources().getColor(R.color.basicTextColor));
                setsEt.setTextSize(22);
                setsEt.setTextColor(themeApplier.getGeneralTextColor());
                setsEt.setText(String.valueOf(exercise.getSetNumber()));
                setsEt.setSelectAllOnFocus(true);
                setsEt.setFilters(new InputFilter[] { new InputFilter.LengthFilter(2), numericFilter });
                setsEt.setPadding(5, 5, 5, 5);
                setsEt.setBackground(context.getResources().getDrawable(R.drawable.cell_shape_general));

                EditText repsEt = new EditText(context);
                repsEt.setLayoutParams(new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                repsEt.setTextColor(context.getResources().getColor(R.color.basicTextColor));
                repsEt.setTextSize(22);
                repsEt.setTextColor(themeApplier.getGeneralTextColor());
                repsEt.setText(String.valueOf(exercise.getRepNumber()));
                repsEt.setSelectAllOnFocus(true);
                repsEt.setFilters(new InputFilter[] { new InputFilter.LengthFilter(2), numericFilter });
                repsEt.setPadding(5, 5, 5, 5);
                repsEt.setBackground(context.getResources().getDrawable(R.drawable.cell_shape_general));

                // TODO: Make weight used float
                EditText weightEt = new EditText(context);
                weightEt.setLayoutParams(new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                weightEt.setTextColor(context.getResources().getColor(R.color.basicTextColor));
                weightEt.setFilters(new InputFilter[] { new InputFilter.LengthFilter(2), numericFilter });
                weightEt.setTextSize(22);
                weightEt.setTextColor(themeApplier.getGeneralTextColor());
                weightEt.setText(String.valueOf(exercise.getWeightUsed()));
                weightEt.setSelectAllOnFocus(true);
                weightEt.setPadding(5, 5, 5, 5);
                weightEt.setBackground(context.getResources().getDrawable(R.drawable.cell_shape_general));

                row.addView(exerciseName);
                row.addView(setsEt);
                row.addView(repsEt);
                row.addView(weightEt);

                List<EditText> tempList = new ArrayList<>();
                tempList.add(setsEt);
                tempList.add(repsEt);
                tempList.add(weightEt);
                exerciseAndViews.put(exercise, tempList);
            }
            rows.add(row);
        }

        return rows;
    }

    public HashMap<Exercise, List<EditText>> getExercisesAndViews() {
        return exerciseAndViews;
    }
}
