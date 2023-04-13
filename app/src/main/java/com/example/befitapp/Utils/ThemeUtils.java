package com.example.befitapp.Utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.view.View;

import com.example.befitapp.R;

public class ThemeUtils {

    private static final String PREF_THEME = "theme";
    private static final int THEME_LIGHT = 0;


    public static void applyTheme(Context context) {
        int theme = getTheme(context);
        if (theme == THEME_LIGHT) {
            context.setTheme(R.style.AppTheme_Light);
        } else {
            context.setTheme(R.style.AppTheme_Dark);
        }
    }


    private static int getBackgroundDrawableId(Context context) {
        int theme = getTheme(context);
        if (theme == THEME_LIGHT) {
            return R.drawable.app_gradient;
        } else {
            return R.drawable.app_gradient_dark;
        }
    }

    public static int getTheme(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(PREF_THEME, THEME_LIGHT);
    }


    public static void updateBackground(View view, Context context) {
        int backgroundDrawableId = getBackgroundDrawableId(context);
        view.setBackgroundResource(backgroundDrawableId);
    }

}