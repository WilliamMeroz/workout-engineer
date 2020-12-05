package com.merozmoreau.workoutengineer.data;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
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

import java.util.HashMap;
import java.util.List;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ViewHolder> {

    private List<Exercise> exercises;
    private HashMap<Exercise, Boolean> hasBeenPicked;
    private Context context;
    OnExerciseItemClickListener exerciseItemClickListener;

    public ExerciseListAdapter(Context context, List<Exercise> exercises, OnExerciseItemClickListener exerciseItemClickListener) {
        this.exercises = exercises;
        this.context = context;
        this.exerciseItemClickListener = exerciseItemClickListener;

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
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Exercise ex = exercises.get(position);

                exerciseItemClickListener.onItemClick(exercises.get(position));


                    if (!hasBeenPicked.get(ex)) {
                        view.setBackgroundColor(context.getResources().getColor(R.color.listItemSelected));
                        hasBeenPicked.put(ex, true);
                    } else {
                        view.setBackgroundColor(Color.WHITE);
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

            exerciseName = itemView.findViewById(R.id.exercise_textView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
