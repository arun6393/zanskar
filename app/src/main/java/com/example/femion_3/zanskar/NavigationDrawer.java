package com.example.femion_3.zanskar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.femion_3.zanskar.Fragments.*;
import com.example.femion_3.zanskar.Fragments.NewWorker;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * Created by Femion-3 on 06/07/2015.
 */


public class NavigationDrawer extends ActionBarActivity {


    String TITLES[] = {"ADD A NEW WORKER",
            "VIEW/EDIT PERSONAL DETAILS",
            "EDIT SERVANT DETAILS",
            "SUBMIT RECORDS TO AUTHORITIES",
            "SHARE APP VIA WHATSAPP",
            "LOGOUT"};

    public static final String PREFS_NAME = "MyPrefsFile";
    private Toolbar toolbar;                              // Declaring the Toolbar Object

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout
    TextView v;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigationdrawer);

        setsharedpreference();
        SharedPreferences shared = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean channel = (shared.getBoolean("hasLoggedIn", false));
        Log.i("shared", channel + "");


        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        v = (TextView) findViewById(R.id.text);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);  // Assigning the RecyclerView Object to the xml View
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new AdapterData(this, TITLES);

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView


        final GestureDetector mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });


        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());


                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    Drawer.closeDrawers();
                    //Toast.makeText(getApplication(), "The Item Clicked is: " + recyclerView.getChildPosition(child), Toast.LENGTH_SHORT).show();
                    displaydata(recyclerView.getChildPosition(child));


                    return true;

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                Toast.makeText(getApplication(), "hello", Toast.LENGTH_LONG).show();

            }
        });


        // mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);  // Drawer object Assigned to the view

        if (savedInstanceState == null) {
            // on first time to display view for first navigation item based on the number
            displaydata(1); // 2 is your fragment's number for "CollectionFragment"
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu();

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();

            }


        }; // Drawer Toggle Object Made


        setupDrawer();


    }

    private void displaydata(int childPosition) {


        Toast.makeText(this, "position is" + childPosition, Toast.LENGTH_SHORT).show();
        //  v.setText("position" + childPosition + "id clicked");
//        if (childPosition == 5) {
//            SharedPreferences settings = getSharedPreferences(Choose.PREFS_NAME, 0);
//            SharedPreferences.Editor editor = settings.edit();
//
////Set "hasLoggedIn" to true
//            editor.putBoolean("hasLoggedIn", false);
//
//// Commit the edits!
//            editor.commit();
//            Intent i1 = new Intent(this, MainActivity.class);
//            startActivity(i1);
//        }
//        else
//        {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (childPosition) {

            case 0:
                NewWorker newWorker = new NewWorker(getApplicationContext());
                fragmentTransaction.replace(R.id.frag, newWorker);
                getSupportActionBar().setTitle(TITLES[childPosition]);
                fragmentTransaction.commit();
                break;


            case 1:

                EditPersonalDetails editPersonalDetails = new EditPersonalDetails();
                fragmentTransaction.replace(R.id.frag, editPersonalDetails);
                getSupportActionBar().setTitle(TITLES[childPosition]);
                fragmentTransaction.commit();


                break;


            case 2:
                EditWorkerDetails editWorkerDetails = new EditWorkerDetails(getApplicationContext());
                fragmentTransaction.replace(R.id.frag, editWorkerDetails);
                getSupportActionBar().setTitle(TITLES[childPosition]);
                fragmentTransaction.commit();


                break;


            case 3:


                break;


            case 4:
                Intent waIntent = new Intent(Intent.ACTION_SEND);
                waIntent.setType("text/plain");
                String text = "Download this app";
                waIntent.setPackage("com.whatsapp");
                if (waIntent != null) {
                    waIntent.putExtra(Intent.EXTRA_TEXT, text);//
                    startActivity(Intent.createChooser(waIntent, "Share with"));
                } else {
                    Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                }


                break;


            case 5:

                SharedPreferences settings = getApplication().getSharedPreferences(Choose.PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();

//Set "hasLoggedIn" to true
                editor.putBoolean("hasLoggedIn", false);

// Commit the edits!
                editor.commit();
                Intent i1 = new Intent(this, MainActivity.class);
                this.startActivity(i1);
                break;
        }


    }


    private void setupDrawer() {
        mDrawerToggle.setDrawerIndicatorEnabled(true);

        Drawer.setDrawerListener(mDrawerToggle);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
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
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        int count = getFragmentManager().getBackStackEntryCount();
//        if (count == 0) {
        //super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
//        } else {
//Toast.makeText(getApplication(),"stack is there",Toast.LENGTH_SHORT).show();
//
//        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mDrawerToggle.syncState();
    }
}


