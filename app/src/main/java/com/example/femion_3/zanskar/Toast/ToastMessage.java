package com.example.femion_3.zanskar.Toast;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.femion_3.zanskar.Main.NavigationDrawer;

/**
 * Created by Femion-3 on 14/07/2015.
 */
public class ToastMessage {
    public static void message(Context c,String s)
    {
        Toast.makeText(c,s,Toast.LENGTH_SHORT).show();
    }

    public static void log(String tagname,String tag)
    {
        Log.i(tagname,tag);
    }

    public static void logout(Context c)
    {
        SharedPreferences settings = c.getSharedPreferences(NavigationDrawer.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

//Set "hasLoggedIn" to true
        editor.putBoolean("hasLoggedIn", false);

// Commit the edits!
        editor.commit();
    }
}
