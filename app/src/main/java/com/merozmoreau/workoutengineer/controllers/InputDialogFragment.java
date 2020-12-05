package com.merozmoreau.workoutengineer.controllers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.merozmoreau.workoutengineer.R;
import com.merozmoreau.workoutengineer.models.Workout;

public class InputDialogFragment extends DialogFragment {
    private EditText editText;
    private Button button;

    private InputDialogCallback callback;

    public InputDialogFragment(InputDialogCallback callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_input_dialog, container, false);
        editText = rootView.findViewById(R.id.workout_name_et);
        button = rootView.findViewById(R.id.input_dialog_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText.getText().toString().isEmpty()) {
                   callback.performAction(editText.getText().toString());
                   getDialog().dismiss();
                }
            }
        });

        getDialog().setTitle("Dialog lol");
        return rootView;
    }


}
