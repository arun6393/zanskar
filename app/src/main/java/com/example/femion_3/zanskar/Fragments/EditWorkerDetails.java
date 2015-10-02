package com.example.femion_3.zanskar.Fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.femion_3.zanskar.Main.NavigationDrawer;
import com.example.femion_3.zanskar.Main.Scan;
import com.example.femion_3.zanskar.Network.GetData;
import com.example.femion_3.zanskar.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.melnykov.fab.FloatingActionButton;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditWorkerDetails extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {


    AlertDialog levelDialog;
    ListView editworkerdetails;
    Context c;
    String workername[] = {"A", "B", "C"};
    //SharedPreferences settings;
    //SharedPreferences.Editor editor;

    public EditWorkerDetails(Context c) {
        // Required empty public constructor
        this.c = c;

   //      editor = settings.edit();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_worker_details1, container, false);
        editworkerdetails = (ListView) v.findViewById(R.id.editworker);
        ArrayAdapter<String> name = new ArrayAdapter<String>(c, R.layout.workertext,R.id.list_content, workername);
        editworkerdetails.setAdapter(name);
        editworkerdetails.setOnItemClickListener(this);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.attachToListView(editworkerdetails);
        fab.setOnClickListener(this);

        getworkerdetails();

        return v;
    }

    private void getworkerdetails() {
       SharedPreferences settings = getActivity().getSharedPreferences(NavigationDrawer.PREFS_NAME, 0);
        RequestParams p=new RequestParams();
String id=settings.getString("U_id","");
        if(!id.equals(""))
        {
p.put("U_id",id);
        GetData.post("workerDetails", p, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
Toast.makeText(getActivity(),""+response,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
Log.d("error is",errorResponse.toString());
            }
        });

        }else{
            Log.d("some error","occured");
        }
    }
        @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




        Intent i = new Intent(getActivity(), NavigationDrawer.class);
        i.putExtra("position", position);
        getActivity().startActivity(i);


    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(),"clicked",Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getActivity(), Scan.class);
        startActivity(i);



/*// Strings to Show In Dialog with Radio Buttons
        final CharSequence[] items = {" Easy "," Medium "," Hard "," Very Hard "};

        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select The Difficulty Level");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {


                switch(item)
                {
                    case 0:
                        // Your code when first option seletced
                        break;
                    case 1:
                        // Your code when 2nd  option seletced

                        break;
                    case 2:
                        // Your code when 3rd option seletced
                        break;
                    case 3:
                        // Your code when 4th  option seletced
                        break;

                }
                levelDialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();*/



    }
}
