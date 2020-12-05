package com.merozmoreau.workoutengineer.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.merozmoreau.workoutengineer.models.Exercise;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Class used to make HTTP requests to the Wger API. This API returns lists of exercises.
public class RequestGenerator {
    // Specify that we want to only get exercises in english, since the API is in german by default.
    private final String EXERCISES_URL = "https://wger.de/api/v2/exercise?format=json&language=2&limit=";
    private final Context context;
    private final Gson gson;

    public RequestGenerator(Context context) {
        this.context = context;
        gson = new Gson();
    }

    public void fetchExercises(final int numExercises, final RequestGeneratorCallback callback) {
        final List<Exercise> list = new ArrayList<>();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                EXERCISES_URL + numExercises,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("results");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);
                                JSONArray muscleTypeArray = jsonObject.getJSONArray("muscles");

                                Exercise exercise = gson.fromJson(String.valueOf(jsonObject), Exercise.class);

                                // Comparing muscle IDs with types.
                                if (muscleTypeArray.length() > 0) {
                                    int type = muscleTypeArray.getInt(0);
                                    exercise.setType(getMuscleType(type));
                                }
                                list.add(exercise);
                            }
                            if (callback != null) {
                                callback.fetchExercisesCallback(list);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("test1", error.getMessage());
                    }
                }
        );

        RequestQueueSingleton.getInstance(context).addToRequestQueue(request);
    }

    public void fetchExercisesByType(int numExercises, final int type, final RequestGeneratorCallback callback) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                EXERCISES_URL + numExercises + "&muscles=" + type,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            List<Exercise> list = new ArrayList<>();
                            JSONArray array = response.getJSONArray("results");

                            for (int i = 0; i < array.length(); i++) {
                                Exercise exercise = gson.fromJson(String.valueOf(array.getJSONObject(i)), Exercise.class);
                                exercise.setType(getMuscleType(type));
                                list.add(exercise);
                            }


                            callback.fetchExercisesCallback(list);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("test1", error.getMessage());
                    }
                }
        );
        RequestQueueSingleton.getInstance(context).addToRequestQueue(request);
    }

    // Since the API uses numbers to distinguish amongst muscle types, we have to associate them to our Enum values.
    private Exercise.MuscleType getMuscleType(int type) {
        Exercise.MuscleType muscleType = null;
        switch (type) {
            case 1:
            case 11:
                muscleType = Exercise.MuscleType.BICEPS;
                break;
            case 2:
                muscleType = Exercise.MuscleType.DELTOID;
                break;
            case 8:
                muscleType = Exercise.MuscleType.GLUTEUS;
                break;
            case 10:
                muscleType = Exercise.MuscleType.QUADRICEPS;
                break;
            case 4:
                muscleType = Exercise.MuscleType.PECTORALS;
                break;
            case 9:
                muscleType = Exercise.MuscleType.TRAPEZIUS;
                break;
            case 5:
                muscleType = Exercise.MuscleType.TRICEPS;
                break;
        }
        return muscleType;
    }
}
