package com.example.femion_3.zanskar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Femion-3 on 06/07/2015.
 */
public class Authentication extends Activity implements View.OnClickListener {

    Spinner city;
    EditText uno;
    Button verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication);
        setvariables();
        verify.setOnClickListener(this);

    }

    private void setvariables() {
        city = (Spinner) findViewById(R.id.Ucity);
        uno = (EditText) findViewById(R.id.Unumber);
        verify = (Button) findViewById(R.id.Uverify);
    }

    @Override
    public void onClick(View v) {

    }
}
