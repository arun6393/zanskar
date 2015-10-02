package com.example.femion_3.zanskar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Femion-3 on 06/07/2015.
 */
public class Register extends Activity implements View.OnClickListener {

    EditText name, surname, email_id, password;
    Button submit;
    String uname, usurname, uid, upass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        setvariables();
        getvalues();
        submit.setOnClickListener(this);

    }

    private void getvalues() {
        uname = name.getText().toString();
        usurname = surname.getText().toString();
        uid = email_id.getText().toString();
        upass = password.getText().toString();
    }

    private void setvariables() {
        name = (EditText) findViewById(R.id.name);
        surname = (EditText) findViewById(R.id.surname);
        email_id = (EditText) findViewById(R.id.Uid);
        password = (EditText) findViewById(R.id.pass);
        submit = (Button) findViewById(R.id.submit);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, NavigationDrawer.class);
        startActivity(i);
    }
}















