package com.example.femion_3.zanskar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {


    Button login, register;
    EditText uname, upass;
    String name, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setvariables();
        getvariables();
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences settings = getSharedPreferences(Choose.PREFS_NAME, 0);
//Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);

        if (hasLoggedIn) {
            Intent i = new Intent(this, NavigationDrawer.class);
            startActivity(i);
            MainActivity.this.finish();
        }
    }

    private void getvariables() {
        name = uname.getText().toString();
        pass = upass.getText().toString();
    }

    private void setvariables() {
        login = (Button) findViewById(R.id.Ulogin);
        register = (Button) findViewById(R.id.Ureg);
        uname = (EditText) findViewById(R.id.Uname);
        upass = (EditText) findViewById(R.id.Upass);
    }


    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Ulogin:
                Bundle b = new Bundle();

                Intent i1 = new Intent(this, NavigationDrawer.class);
                b.putString("uname", name);
                b.putString("upass", pass);
                i1.putExtras(b);
                startActivity(i1);


                break;


            case R.id.Ureg:


                Intent i = new Intent(this, Register.class);

                startActivity(i);

                break;
        }
    }
}
