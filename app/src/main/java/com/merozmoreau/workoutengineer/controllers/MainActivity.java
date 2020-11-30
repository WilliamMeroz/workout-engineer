package com.merozmoreau.workoutengineer.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.merozmoreau.workoutengineer.R;
import com.merozmoreau.workoutengineer.utils.OptionsMenuGeneral;

public class MainActivity extends OptionsMenuGeneral {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}