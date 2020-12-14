package com.merozmoreau.workoutengineer.utils;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.merozmoreau.workoutengineer.R;

// Class used to return the different colors needs to change the theme of the app depending on the boolean value in the SharedPreferences.
public class ThemeApplier {

    Context context;
    boolean darkThemeApplied;

    private int lightBackgroundColor;
    private int darkBackgroundColor;
    private int lightThemeGeneralText;
    private int darkThemeGeneralText;
    private int darkThemeAppBar;
    private int lightThemeAppBar;
    private int lightSelectedColor;
    private int darkSelectedColor;

    public ThemeApplier(Context context) {
        this.context = context;
        darkThemeApplied = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("theme", false);

        lightBackgroundColor = context.getResources().getColor(R.color.colorLightBackgroundColor);
        darkBackgroundColor = context.getResources().getColor(R.color.colorDarkBackgroundColor);
        lightThemeGeneralText = context.getResources().getColor(R.color.colorTextLightTheme);
        darkThemeGeneralText = context.getResources().getColor(R.color.colorTextDarkTheme);
        darkThemeAppBar = context.getResources().getColor(R.color.colorDarkAppbar);
        lightThemeAppBar = context.getResources().getColor(R.color.colorLightAppbar);
        lightSelectedColor = context.getResources().getColor(R.color.lightListItemSelected);
        darkSelectedColor = context.getResources().getColor(R.color.darkListItemSelected);
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

    public String getAppbarTitleColor(String title) {
        if (darkThemeApplied)
            return String.format("<font color='#FFFFFF'>%s</font>", title);
        else
            return String.format("<font color='#000000'>%s</font>", title);
    }

    public int getSelectColor() {
        if (darkThemeApplied)
            return darkSelectedColor;
        else
            return lightSelectedColor;
    }
}
