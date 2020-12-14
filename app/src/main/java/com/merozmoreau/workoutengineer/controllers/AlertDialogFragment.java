package com.merozmoreau.workoutengineer.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.merozmoreau.workoutengineer.R;

// Simple DialogFragment class used to display alerts.
public class AlertDialogFragment extends DialogFragment {
    private Context context;
    private Button button;

    private String dialogTitle;
    private String dialogText;
    private String dialogButtonText;

    public AlertDialogFragment(Context context, String dialogTitle, String dialogText, String dialogButtonText) {
        this.context = context;
        this.dialogTitle = dialogTitle;
        this.dialogText = dialogText;
        this.dialogButtonText = dialogButtonText;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_alert_dialog, container, false);
        button = rootView.findViewById(R.id.alert_dialog_button);
        button.setText(dialogButtonText);

        // Trough the use of these TextViews, we can customize the alert to whatever we want.
        TextView title = rootView.findViewById(R.id.alert_dialog_title);
        title.setText(dialogTitle);

        TextView text = rootView.findViewById(R.id.alert_dialog_text);
        text.setText(dialogText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                ((Activity) context).finish();
            }
        });

        return rootView;
    }
}