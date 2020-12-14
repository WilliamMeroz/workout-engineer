package com.merozmoreau.workoutengineer.data;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.merozmoreau.workoutengineer.R;
import com.merozmoreau.workoutengineer.models.Exercise;
import com.merozmoreau.workoutengineer.utils.OnExerciseItemClickListener;
import com.merozmoreau.workoutengineer.utils.ThemeApplier;

import java.util.HashMap;
import java.util.List;

// Adapter used to feed data into the Exercises ReyclerView
public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ViewHolder> {

    private List<Exercise> exercises;
    private HashMap<Exercise, Boolean> hasBeenPicked;
    private Context context;
    private OnExerciseItemClickListener exerciseItemClickListener;
    private ThemeApplier themeApplier;

    public ExerciseListAdapter(Context context, List<Exercise> exercises, OnExerciseItemClickListener exerciseItemClickListener) {
        this.exercises = exercises;
        this.context = context;
        this.exerciseItemClickListener = exerciseItemClickListener;
        themeApplier = new ThemeApplier(context);

        // Helps to see if an exercise has already been picked.
        hasBeenPicked = new HashMap<>();
        for (Exercise ex : this.exercises)
            hasBeenPicked.put(ex, false);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.exerciseName.setText(exercises.get(position).getName());
        holder.exerciseName.setTextColor(themeApplier.getGeneralTextColor());
        holder.parentLayout.setCardBackgroundColor(themeApplier.getBackgroundColor());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Exercise ex = exercises.get(position);

                exerciseItemClickListener.onItemClick(exercises.get(position));

                    if (!hasBeenPicked.get(ex)) {
                        view.setBackgroundColor(themeApplier.getSelectColor());
                        hasBeenPicked.put(ex, true);
                    } else {
                        view.setBackgroundColor(themeApplier.getBackgroundColor());
                        hasBeenPicked.put(ex, false);
                    }
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView exerciseName;
        CardView parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            exerciseName = itemView.findViewById(R.id.item_text_view);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
