package com.merozmoreau.workoutengineer.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.merozmoreau.workoutengineer.R;
import com.merozmoreau.workoutengineer.controllers.AlertDialogFragment;
import com.merozmoreau.workoutengineer.controllers.SettingsActivity;

// I know that technically, this class is an Activity but I still consider it to be a util class since it is used on other activities.
public class OptionsMenuGeneral extends AppCompatActivity {

    AlertDialogFragment alertDialogFragment;

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
        if (item.getItemId() == R.id.menu_item_about) {
            alertDialogFragment = new AlertDialogFragment(this, getString(R.string.app_name_display), getString(R.string.about_message), getString(R.string.ok_button));
            FragmentManager fragmentManager = getSupportFragmentManager();
            alertDialogFragment.show(fragmentManager, "aboutAlertDialog");
        } else if (item.getItemId() == R.id.menu_item_api) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://wger.de/en/software/api"));
            startActivity(intent);
            finish();
        }
        return true;
    }
}