package com.merozmoreau.workoutengineer.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.merozmoreau.workoutengineer.R;

public class OptionsMenuGeneral extends AppCompatActivity {

    private final String TAG = "optionsMenu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.general_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_settings:
                Log.d(TAG, "onOptionsItemSelected: Settings");
                break;
            case R.id.menu_item_about:
                Log.d(TAG, "onOptionsItemSelected: About");
        }

        return true;
    }
}