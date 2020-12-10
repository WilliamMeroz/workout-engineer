package com.merozmoreau.workoutengineer.controllers;

import com.merozmoreau.workoutengineer.R;
import com.merozmoreau.workoutengineer.utils.GeneralCallback;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AlertDialogFragmentTwoButtons extends DialogFragment {

    private Context context;
    private Button yesButton;
    private Button noButton;
    private TextView title;
    private TextView text;

    private String dialogTitle;
    private String dialogText;

    private GeneralCallback yesAction;

    public AlertDialogFragmentTwoButtons(Context context, String dialogTitle, String dialogText, GeneralCallback yesAction) {
        this.context = context;
        this.dialogTitle = dialogTitle;
        this.dialogText = dialogText;
        this.yesAction = yesAction;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.framgent_alert_dialog_two_buttons, container, false);

        title = rootView.findViewById(R.id.alert_dialog_two_buttons_title);
        text = rootView.findViewById(R.id.alert_dialog_two_buttons_text);
        yesButton = rootView.findViewById(R.id.alert_dialog_button_yes);
        noButton = rootView.findViewById(R.id.alert_dialog_button_no);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yesAction.performAction();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, getString(R.string.action_not_performed), Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        title.setText(dialogTitle);
        text.setText(dialogText);

        return rootView;
    }
}