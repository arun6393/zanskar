package com.example.femion_3.zanskar.Main;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.femion_3.zanskar.Adapter.AdapterData;
import com.example.femion_3.zanskar.AdapterDecorate.DividerItemDecoration;
import com.example.femion_3.zanskar.Fragments.EditPersonalDetails;
import com.example.femion_3.zanskar.Fragments.EditWorkerDetails;
import com.example.femion_3.zanskar.Fragments.NewWorker;
import com.example.femion_3.zanskar.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;

/**
 * Created by Femion-3 on 06/07/2015.
 */


public class NavigationDrawer extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {


    String TITLES[] = {"User Profile",
            "Add Record",
            "Edit Record",
            "Submit Record to Authorities",
            "Share App",
            "Logout"};

    int ICONS[] = {R.drawable.user,
            R.drawable.edit,
            R.drawable.edit,
            R.drawable.edit,
            R.drawable.share,
            R.drawable.logout};

    public static final String PREFS_NAME = "MyPrefsFile";
    private Toolbar toolbar;                              // Declaring the Toolbar Object
    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;
    int pos,scaneddata1, noscan,x1;
    boolean haslognedinviafb;
    TextView v;
    private GoogleApiClient mGoogleApiClient;
    String U_name,U_image;
    SharedPreferences shared;
    private SharedPreferences.Editor editor;

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigationdrawer);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();

        setsharedpreference();
        setProfileName();


        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        v = (TextView) findViewById(R.id.text);


        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);  // Assigning the RecyclerView Object to the xml View
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new AdapterData(this, TITLES, ICONS,U_image);

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


                    displaydata(recyclerView.getChildPosition(child));


                    return true;

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {


            }
        });


        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);  // Drawer object Assigned to the view


        if (savedInstanceState == null || savedInstanceState != null) {
            setTheFragments();
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

    private void setTheFragments() {
        Intent intent = getIntent();
        pos = intent.getIntExtra("position", -1);
        x1=intent.getIntExtra("new", -1);
        noscan=intent.getIntExtra("noscan", -1);
        scaneddata1=intent.getIntExtra("scaneddata", -1);


        if (pos >= 0) {
            displayeditworkerdetails(pos);
            Log.i("postion is", "" + pos);
        } else {
            if (x1 == 10 || scaneddata1 == 11 || noscan == 100) {
                if (x1 == 10) {
                   shared = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("set", "null");
                    editor.commit();
                }
                displayeditworkerdetails(1);
            } else {
                // on first time to display view for first navigation item based on the number
                displaydata(0); // 2 is your fragment's number for "CollectionFragment"
            }
        }
    }

    private void setProfileName() {

        shared = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        U_name=shared.getString("U_name","");
        U_image=shared.getString("image","");
        Log.d("the image is",U_image);
        if(!U_name.equals("")) {
            TITLES[0] = U_name;
        }
    }


    public static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void displayeditworkerdetails(int pos1) {
        FragmentManager fragmentManager1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
        NewWorker newWorker1 = new NewWorker(getApplicationContext());
        fragmentTransaction1.replace(R.id.frag, newWorker1);
        if (pos >= 0) {
            getSupportActionBar().setTitle("Edit Record");
        } else {
            getSupportActionBar().setTitle("Add Record");
        }
        // getSupportActionBar().set
        pos = -1;
        fragmentTransaction1.commit();
    }

    private void displaydata(int childPosition) {



        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (childPosition) {


            case 0:

                EditPersonalDetails editPersonalDetails = new EditPersonalDetails();
                fragmentTransaction.replace(R.id.frag, editPersonalDetails);
                if(!U_name.equals("")) {
                    getSupportActionBar().setTitle(U_name);
                }
                fragmentTransaction.commit();


                break;


            case 1:

                Intent intent = new Intent(this, Scan.class);
                startActivity(intent);
                break;




            case 2:
                EditWorkerDetails editWorkerDetails = new EditWorkerDetails(getApplicationContext());
                fragmentTransaction.replace(R.id.frag, editWorkerDetails);
                getSupportActionBar().setTitle("Select Worker");
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
                if (mGoogleApiClient.isConnected()) {
                    Toast.makeText(this, "gmail se logned in", Toast.LENGTH_SHORT).show();
                    Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                    mGoogleApiClient.disconnect();
                } else {
                    if (chechfblogin()) {
                        LoginManager.getInstance().logOut();
                        //setsharedpreference();
                    }
                }


               shared = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                editor =shared.edit();

                editor.putBoolean("hasLoggedIn", false);




                editor.commit();

                editor.putString("U_id", "");
                editor.putString("U_name","");
                editor.putString("U_email","");
                editor.putString("U_police","");
                editor.putString("U_address", "");
                editor.putString("U_number", "");
                editor.putString("U_landmark", "");
                editor.putString("U_verified", "");
                editor.putString("U_image","");
                editor.putString("U_gender","");
                editor.putString("Iset","");

                Intent i1 = new Intent(this, MainActivity.class);
                this.startActivity(i1);
                break;
        }


    }

    private boolean chechfblogin() {
        shared = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        haslognedinviafb = (shared.getBoolean("fblogin", false));
        if (haslognedinviafb) {
            return true;
        } else {
            return false;
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

        shared = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

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
    public boolean onOptionsItemSelected(MenuItem item) {


        if (mDrawerToggle.onOptionsItemSelected(item)) {
            hideSoftKeyboard(this);
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

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


}


