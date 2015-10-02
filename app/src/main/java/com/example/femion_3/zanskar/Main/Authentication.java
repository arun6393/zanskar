package com.example.femion_3.zanskar.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.femion_3.zanskar.R;

/**
 * Created by Femion-3 on 06/07/2015.
 */
public class Authentication extends Activity implements View.OnClickListener {

    Spinner city;
    EditText uno;
    Button verify;
    String ucity[]={"Select Your City","Mumbai","Pune"};

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
        ArrayAdapter<String> t = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ucity);
        city.setAdapter(t);

    }

    @Override
    public void onClick(View v) {

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(uno.getText().toString(), null, "UR VERIFICATION CODE IS", null, null);
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.putExtra("sms_body", "default content");
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivity(sendIntent);

    }
}
