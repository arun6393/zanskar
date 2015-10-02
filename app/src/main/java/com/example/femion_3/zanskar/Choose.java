package com.example.femion_3.zanskar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Femion-3 on 06/07/2015.
 */
public class Choose extends Activity implements AdapterView.OnItemClickListener {


    ListView l;
    String s[] = {"add a new worker", "edit person details", "edit servant details", "submit records to authorities", "share app via whatsapp", "logout"};
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose);
        setsharedpreference();
        l = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, s);
        l.setAdapter(a);

        SharedPreferences shared = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean channel = (shared.getBoolean("hasLoggedIn", false));
        Log.i("shared", channel + "");
        l.setOnItemClickListener(this);

    }

    private void setsharedpreference() {

        SharedPreferences settings = getSharedPreferences(Choose.PREFS_NAME, 0); // 0 - for private mode
        SharedPreferences.Editor editor = settings.edit();

//Set "hasLoggedIn" to true
        editor.putBoolean("hasLoggedIn", true);

// Commit the edits!
        editor.commit();
    }

    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 5:
                SharedPreferences settings = getSharedPreferences(Choose.PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();

//Set "hasLoggedIn" to true
                editor.putBoolean("hasLoggedIn", false);

// Commit the edits!
                editor.commit();
                Intent i1 = new Intent(this, MainActivity.class);
                startActivity(i1);
                break;
        }
    }
}
