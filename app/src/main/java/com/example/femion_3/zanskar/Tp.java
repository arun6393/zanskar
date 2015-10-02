package com.example.femion_3.zanskar;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.femion_3.zanskar.Fragments.NewWorker;

import com.example.femion_3.zanskar.Fragments.*;

/**
 * Created by Femion-3 on 07/07/2015.
 */
public class Tp extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tp);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NewWorker fragment = new NewWorker(this);
        fragmentTransaction.add(R.id.ll1, fragment);
        fragmentTransaction.commit();

    }
}
