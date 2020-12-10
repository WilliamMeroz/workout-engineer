package com.merozmoreau.workoutengineer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.merozmoreau.workoutengineer.R;

public class ThemeApplier {

    Context context;
    boolean darkThemeApplied;

    private int lightBackgroundColor;
    private int darkBackgroundColor;
    private int lightThemeGeneralText;
    private int darkThemeGeneralText;
    private int darkThemeAppBar;
    private int lightThemeAppBar;

    public ThemeApplier(Context context) {
        this.context = context;
        darkThemeApplied = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("theme", false);

        lightBackgroundColor = context.getResources().getColor(R.color.colorLightBackgroundColor);
        darkBackgroundColor = context.getResources().getColor(R.color.colorDarkBackgroundColor);
        lightThemeGeneralText = context.getResources().getColor(R.color.colorTextLightTheme);
        darkThemeGeneralText = context.getResources().getColor(R.color.colorTextDarkTheme);
        darkThemeAppBar = context.getResources().getColor(R.color.colorDarkAppbar);
        lightThemeAppBar = context.getResources().getColor(R.color.colorLightAppbar);
    }

    public int getBackgroundColor() {
        if (darkThemeApplied)
            return darkBackgroundColor;
        else
            return lightBackgroundColor;
    }

    public int getGeneralTextColor() {
        if (darkThemeApplied)
            return darkThemeGeneralText;
        else
            return lightThemeGeneralText;
    }

    public int getAppBarColor() {
        if (darkThemeApplied)
            return darkThemeAppBar;
        else
            return lightThemeAppBar;
    }
}
