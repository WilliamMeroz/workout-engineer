package com.merozmoreau.workoutengineer.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.merozmoreau.workoutengineer.R;
import com.merozmoreau.workoutengineer.controllers.EditWorkoutActivity;
import com.merozmoreau.workoutengineer.controllers.PerformWorkoutActivity;
import com.merozmoreau.workoutengineer.models.Workout;

import java.util.List;

public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.ViewHolder> {
    // We need the list of workouts fetches from Firebase and the context.
    private final Context context;
    private final List<Workout> workouts;
    private final Gson gson;
    private final int ACTION_MODE;

    public WorkoutsAdapter(Context context, List<Workout> workouts, int action) {
        this.context = context;
        this.workouts = workouts;
        gson = new Gson();
        ACTION_MODE = action;
    }

    @NonNull
    @Override
    public WorkoutsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutsAdapter.ViewHolder holder, final int position) {
        final Workout workout = workouts.get(position);
        holder.workoutName.setText(workout.getName());
        holder.rootParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When the user selects a workout, we start a new activity and finish the current one. We also pass the selected workout object in the intent.
                Intent intent = null;
                if (ACTION_MODE == 1)
                    intent = new Intent(context, PerformWorkoutActivity.class);
                else if (ACTION_MODE == 2)
                    intent = new Intent(context, EditWorkoutActivity.class);

                String serializedWorkout = gson.toJson(workout);
                intent.putExtra("workout", serializedWorkout);

                context.startActivity(intent);
                // We have to cast our context into an Activity in order to finish the activity.
                Activity activity = (Activity)context;
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView rootParent;
        private final TextView workoutName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rootParent = itemView.findViewById(R.id.parent_layout);
            workoutName = itemView.findViewById(R.id.item_text_view);
        }
    }
}
