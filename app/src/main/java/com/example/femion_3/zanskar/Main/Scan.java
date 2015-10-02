package com.example.femion_3.zanskar.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.femion_3.zanskar.QrScan.CameraTestActivity;
import com.example.femion_3.zanskar.R;

/**
 * Created by Femion-3 on 10/07/2015.
 */
public class Scan extends AppCompatActivity implements View.OnClickListener {
    Button scan,skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan);
        scan= (Button) findViewById(R.id.adharcard);
        skip= (Button) findViewById(R.id.skip);
        scan.setOnClickListener(this);
        skip.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.adharcard:

                Intent i=new Intent(this, CameraTestActivity.class);
                startActivity(i);
                finish();
                break;


            case R.id.skip:
//                Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show();
//                FragmentManager fragmentManager1 = getFragmentManager();
//                FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
//                NewWorker newWorker1 = new NewWorker(this);
//                fragmentTransaction1.add(R.id.scan, newWorker1);
//                //this.getSupportActionBar().setTitle("Edit Record");
//
//                fragmentTransaction1.commit();
                Intent x=new Intent(this,NavigationDrawer.class);
                x.putExtra("new", 10);
                startActivity(x);

                break;
        }

    }

    @Override
    public void onBackPressed() {




        super.onBackPressed();
/*

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        NewWorker newWorker= new NewWorker(this);
        fragmentTransaction.replace(R.id.scan,newWorker);
        //getSupportActionBar().setTitle("Add Worker");
        fragmentTransaction.commit();
*/


        Intent v=new Intent(this,NavigationDrawer.class);
        startActivity(v);
    }
}
