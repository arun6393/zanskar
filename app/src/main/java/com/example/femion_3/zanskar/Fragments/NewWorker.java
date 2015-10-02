package com.example.femion_3.zanskar.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.femion_3.zanskar.AdapterDecorate.RoundImage;
import com.example.femion_3.zanskar.Main.NavigationDrawer;
import com.example.femion_3.zanskar.Network.GetData;
import com.example.femion_3.zanskar.QrScan.CameraTestActivity;
import com.example.femion_3.zanskar.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.soundcloud.android.crop.Crop;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;

public class NewWorker extends Fragment implements View.OnClickListener {

    EditText Wname, Waddress, Wage, Wadharcardno, WlastName;
    Spinner Wtype;
    RadioGroup Wsex;
    Button submit;
    RadioButton male, female;
    ImageView workerImage;
    ProgressDialog progressDialog;


    Context c;
    String type[] = {"Select Worker Type", "Maid", "Driver"};
    String headerCode,errorMessage;
    Uri outputUri=null;
    String name, addresss, set, Uid, gender, lname,
            wname, waddresss,wgender, wlname,wage,wadharno,wtype;
    String U_id,date,path,S_id;
    private Uri inputUri;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    String x1="null";
    private String p;
    private RoundImage roundedImage;


    public NewWorker(Context c) {
        this.c = c;
    }

    int RESULT_LOAD_IMAGE = 41;
    int x = 1;
    CameraTestActivity cameraTestActivity = (CameraTestActivity) getActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_new_worker1, container, false);
        setTheLayout(v);

        getTheDetailsViaScan();

        return v;


    }

    private void getTheDetailsViaScan() {

        name = settings.getString("name1", "null");
        addresss = settings.getString("address", "null");
        Uid = settings.getString("uid", "null");
        set = settings.getString("set", "null");
        gender = settings.getString("sex", "null");
        lname = settings.getString("lname", "null");
        U_id=settings.getString("U_id", "null");

        if (!set.equals("null")) {
            Wname.setText(name);
            Log.i("name is", name);
            Wadharcardno.setText(Uid);
            Waddress.setText(addresss);
            WlastName.setText(lname);

            if (gender.contains("M")) {
                male.setChecked(true);
                wgender="Male";
                female.setChecked(false);
            } else {
                male.setChecked(false);
                wgender="Female";
                female.setChecked(true);
            }
            setvisibility();
        }
    }

    private void setTheLayout(View v) {

        Wname = (EditText) v.findViewById(R.id.Wname);
        Waddress = (EditText) v.findViewById(R.id.Waddress);
        Wage = (EditText) v.findViewById(R.id.Wage);
        Wadharcardno = (EditText) v.findViewById(R.id.EditTextAdharcardno);
        WlastName = (EditText) v.findViewById(R.id.Wlastname);
        Wtype = (Spinner) v.findViewById(R.id.Wtype);
        Wsex = (RadioGroup) v.findViewById(R.id.Wgender);
        submit = (Button) v.findViewById(R.id.Wsubmit);
        male = (RadioButton) v.findViewById(R.id.Wmale);
        female = (RadioButton) v.findViewById(R.id.Wfemale);
        workerImage = (ImageView) v.findViewById(R.id.WorkerImage);
        ArrayAdapter<String> t = new ArrayAdapter<String>(c, R.layout.workertext,R.id.list_content, type);
        Wtype.setAdapter(t);
        submit.setOnClickListener(this);
        workerImage.setOnClickListener(this);
        settings = getActivity().getSharedPreferences(NavigationDrawer.PREFS_NAME, 0);
        editor = settings.edit();
    }

    private void setvisibility() {
        Wname.setEnabled(false);
        Waddress.setEnabled(false);
        Wadharcardno.setEnabled(false);
        Wsex.setEnabled(false);
        male.setEnabled(false);
        female.setEnabled(false);
        WlastName.setEnabled(false);
        x = 2;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.Wsubmit:

                if (x != 2) {
                    switch (Wsex.getCheckedRadioButtonId()) {
                        case R.id.Wmale:
                            wgender = "male";
                            Log.i("it is", "male");
                            break;
                        case R.id.Wfemale:
                            wgender = "female";

                            Log.i("it is", "female");
                            break;
                    }
                }

                if (checkIfNotEmpty()) {
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Sending Details To The Server");
                    progressDialog.show();
                    sendworkerdatatoserver();
                } else {
                    Toast.makeText(getActivity(), "enter allt he details", Toast.LENGTH_SHORT).show();
                }
                break;


            case R.id.WorkerImage:

                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i, RESULT_LOAD_IMAGE);

                break;

        }
    }


    private boolean checkIfNotEmpty() {
        String x=Wtype.getSelectedItem().toString();
        if (!Wname.getText().toString().equals("") &&
                !Waddress.getText().toString().equals("") &&
                !Wadharcardno.getText().toString().equals("") &&
                !Wage.getText().toString().equals("") &&
                !x.equals("Select Worker Type") &&
                !WlastName.getText().toString().equals("") &&
                workerImage.getDrawable()!= null) {
            Log.d("image is","set");
            Log.d("worker image is",""+workerImage.getDrawable());
            return true;
        } else {
            Log.d("image is not ","set");
            return false;
        }
    }

    private void sendworkerdatatoserver() {

        getalltheedittextvalue();
            sendData();
    }

    private void getalltheedittextvalue() {

        wname = Wname.getText().toString();
        waddresss = Waddress.getText().toString();
        wlname = WlastName.getText().toString();
        wage = Wage.getText().toString();
        wadharno = Wadharcardno.getText().toString();
        wtype = Wtype.getSelectedItem().toString();

        Log.d("gender is", wgender);

    }

    private void sendData() {

        setdate();

        RequestParams params = new RequestParams();

        params.put("S_name", wname);
        params.put("S_lastname", wlname);
        params.put("S_address", waddresss);
        params.put("S_age", wage);
        params.put("S_gender", wgender);
        params.put("S_type", wtype);
        params.put("S_adharno", wadharno);
        params.put("S_created", date);
        params.put("U_id", U_id);
        try {
            Log.d("uri is",""+this.outputUri);
            if(this.outputUri!=null)
            params.put("S_image", new File(this.outputUri.getPath()));
            else{
                params.put("S_image","null");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Log.d("data sent to add worker is", params.toString());
        GetData.post("addWorker", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("data returned from url is", "" + response);
                try {
                    headerCode = response.getString("headerCode");
                    errorMessage = response.getString("errorMessage");
                    S_id = response.getString("S_id");

                    if (headerCode.equals("0") && errorMessage.equals("") && !S_id.equals("0")) {
                        progressDialog.cancel();
                        Log.d("hello", "hi");
                        Toast.makeText(getActivity(), "added successfuly", Toast.LENGTH_SHORT).show();
                        clearEditTetValues();
                    } else {
                        if (headerCode.equals("1") && errorMessage.equals("Worker already exists")) {
                            SharedPreferences settings = getActivity().getSharedPreferences(NavigationDrawer.PREFS_NAME, 0);
                            String u_id = settings.getString("U_id", "");
                            String W_id = response.getString("S_id");
                            Log.d("u_id and s_id is", "" + u_id + "" + W_id);
                            RequestParams p = new RequestParams();
                            p.put("S_id", W_id);
                            p.put("U_id", u_id);
                            p.put("R_created", date);
                            p.put("R_status", 1);
                            addrelationship(p);
                            progressDialog.cancel();

                        }
                    }


                } catch (Exception e) {
                    System.out.println(e.toString());
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("error", responseString);
            }
        });
    }


    private void addrelationship(RequestParams p) {

        GetData.post("workerAndUser", p, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("relationship table data", response.toString());


                try {
                    headerCode = response.getString("headerCode");
                    errorMessage = response.getString("errorMessage");

                    if (headerCode.equals("0") && errorMessage.equals("")) {
                        Toast.makeText(getActivity(), "Worker added", Toast.LENGTH_SHORT).show();
                        clearEditTetValues();
                    } else {
                        if (headerCode.equals("1") && errorMessage.equals("The Worker Is already working there")) {
                            Toast.makeText(getActivity(), "worker is already working there", Toast.LENGTH_SHORT).show();
                            clearEditTetValues();
                        } else {
                            Toast.makeText(getActivity(), "Some error occured", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    System.out.println(e.toString());
                }
            }
        });
    }

    private void clearEditTetValues() {

        Wname.setText("");
        Waddress.setText("");
        Wage.setText("");
        Wadharcardno.setText("");
        WlastName.setText("");
        Wtype.setSelection(0);
        Wsex.clearCheck();
        workerImage.setImageResource(R.drawable.user);
    }

    private void setdate() {

        Calendar calander = Calendar.getInstance();
        int cDay = calander.get(Calendar.DAY_OF_MONTH);
        int cMonth = calander.get(Calendar.MONTH) + 1;
        int cYear = calander.get(Calendar.YEAR);

        int cHour = calander.get(Calendar.HOUR);
        int cMinute = calander.get(Calendar.MINUTE);
        int cSecond = calander.get(Calendar.SECOND);

        date=cYear+"-"+cMonth+"-"+cDay+" "+cHour+":"+cMinute+":" + cSecond;
        Log.d("current date and time is", "day" + cDay + "month" + cMonth + "year" + cYear +
                "hour" + cHour + "minute" + cMinute + "seconds" + cSecond);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.println("RequestCOde:" + requestCode + "::ResultCode::" + resultCode);
        if (requestCode == Crop.REQUEST_CROP) {
            try {
                Log.i("the output uri is", "" + outputUri);
                Log.i("the input uri is", "" + inputUri);
                editor.putString("U_image", outputUri.toString());


                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;//returning null for below statement
                Bitmap bm = BitmapFactory.decodeFile(path,options);
                roundedImage = new RoundImage(bm);
                workerImage.setImageDrawable(roundedImage);
            } catch (Exception e) {
                Log.i("error", e.toString());
            }

        } else {
            if (requestCode == RESULT_LOAD_IMAGE && null != data) {
                Log.i("entered", "entered");

                inputUri = data.getData();
Log.d("input uri is",inputUri.toString());
                getpath(inputUri);

                Log.d("image path is", path);
                p=path.toLowerCase();
                addFormat(p);

                this.outputUri=Uri.fromFile(new File(this.getActivity().getCacheDir(), "cropped"+"."+x1));
                Crop.of(inputUri, this.outputUri).asSquare().start(getActivity(), this);
                Log.i("entered", "error" + requestCode + "   " + resultCode);


            } else {
                Log.i("error", "error" + requestCode + "   " + resultCode);
            }

        }
    }

    private void addFormat(String p) {

        if(p.endsWith(".png"))
            x1="PNG";

        if(p.endsWith(".gif"))
            x1="GIF";
        if(p.endsWith(".jpg"))
            x1="JPG";

        if(p.endsWith(".jpeg"))
            x1="JPEG";

        if(!p.endsWith(".png") && !p.endsWith(".gif") && !p.endsWith(".jpg") && !p.endsWith(".jpeg"))
        {
            x1="PNG";
        }
    }

    private void getpath(Uri inputUri) {


        Cursor cursor = getActivity().getContentResolver().query(inputUri, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            path = inputUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            path = cursor.getString(idx);
            cursor.close();


        }

       // Log.d("image path is",path);
        Toast.makeText(getActivity(),"image path is"+path, Toast.LENGTH_SHORT).show();

    }


}







