package com.example.femion_3.zanskar.Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.femion_3.zanskar.AdapterDecorate.RoundImage;
import com.example.femion_3.zanskar.Main.NavigationDrawer;
import com.example.femion_3.zanskar.R;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditPersonalDetails extends Fragment implements View.OnClickListener {


    EditText name, number, email_id, address, landmark;
    Button update, edit;
    Spinner police;
    ImageView uimage;
    RadioButton male, female;
    View progressDialog;


    boolean haslognedinviafb1;
    String U_image, U_name, U_id, U_email, U_police, U_address, U_number, U_landmark;
    Uri outputUri;
    int RESULT_LOAD_IMAGE = 41;
    static int i = 0;
    String policestation[] = {"Police Station Name", "Mumbai", "Pune", "Thane", "nerul"};
    private String U_gender;
    private Uri inputUri;
    private String path, p, x1;
    private RoundImage roundedImage;
    private SharedPreferences shared;
    SharedPreferences.Editor editor;
    ArrayAdapter<String> p1;
    private FileOutputStream fos;
    private static File file;
    String set;
    private Target loadtarget;


    public EditPersonalDetails() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_edit_personal_details, container, false);
        setTheLayout(v);

        return v;
    }

    private void setTheLayout(View v) {

        name = (EditText) v.findViewById(R.id.editname);
        number = (EditText) v.findViewById(R.id.editno);
        email_id = (EditText) v.findViewById(R.id.editemail);
        address = (EditText) v.findViewById(R.id.editaddress);
        male = (RadioButton) v.findViewById(R.id.Umale);
        female = (RadioButton) v.findViewById(R.id.Ufemale);
        landmark = (EditText) v.findViewById(R.id.editlandmark);
        progressDialog = v.findViewById(R.id.pdialog);
        update = (Button) v.findViewById(R.id.editupdate);
        edit = (Button) v.findViewById(R.id.edit);
        uimage = (ImageView) v.findViewById(R.id.UImage);
        police = (Spinner) v.findViewById(R.id.editpolicestation);
        update.setOnClickListener(this);
        edit.setOnClickListener(this);
        p1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, policestation);
        police.setAdapter(p1);

        progressDialog.setVisibility(View.VISIBLE);


    }

    @Override
    public void onStart() {
        super.onStart();
        getdata();
    }

    private void getdata() {


        shared = getActivity().getSharedPreferences(NavigationDrawer.PREFS_NAME, 0);
        editor = shared.edit();
        String uid = (shared.getString("U_id", ""));
        U_name = (shared.getString("U_name", ""));
        U_id = (shared.getString("U_id", ""));
        U_email = (shared.getString("U_email", ""));
        U_police = (shared.getString("U_police", ""));
        U_address = (shared.getString("U_address", ""));
        U_number = (shared.getString("U_number", ""));
        U_landmark = (shared.getString("U_landmark", ""));
        U_image = (shared.getString("U_image", ""));
        U_gender = (shared.getString("U_gender", ""));


        Log.d("image url is", U_image);

        Log.d("uid is", uid);
        String ss = shared.getString("Iset", "0");

/*if(ss.equals("0")) {
    new LoadImage().execute(U_image);
    uimage.setVisibility(View.VISIBLE);
    progressDialog.setVisibility(View.GONE);
}else{

    Bitmap bmp = BitmapFactory.decodeFile(file.toString());
ImageView img;
    roundedImage = new RoundImage(bmp);
    uimage.setVisibility(View.VISIBLE);
    progressDialog.setVisibility(View.GONE);
    uimage.setImageDrawable(roundedImage);

}*/


        if (!uid.equals("")) {
            if (!U_id.equals("")) {

                set();
                setthedata(U_name, U_number, U_email, U_address, U_landmark, U_police, U_gender);
            } else {
                Toast.makeText(getActivity(), "Please Enter ur details", Toast.LENGTH_SHORT).show();
                unset();
            }

        } else {
            Toast.makeText(getActivity(), "Some ERROR Occured", Toast.LENGTH_SHORT).show();
            unset();
        }
    }

    private void setthedata(String u_name, String u_number, String u_email,
                            String u_address, String u_landmark, String u_police, String U_gender) {

        name.setText(u_name);
        number.setText(u_number);
        email_id.setText(u_email);
        address.setText(u_address);
        landmark.setText(u_landmark);
        int pos = p1.getPosition(u_police);
        police.setSelection(pos);
        Log.d("gender is", U_gender);
        if (U_gender.equals("Male")) {
            Log.d("gender is", "male");
            male.setChecked(true);

            female.setChecked(false);
        } else {
            if (U_gender.endsWith("Female")) {
                Log.d("gender is", "female");
                male.setChecked(false);
                female.setChecked(true);
            } else {
                male.setChecked(false);
                female.setChecked(false);
            }
        }

        Toast.makeText(getActivity(), "value of i is" + i, Toast.LENGTH_SHORT).show();
/*if(i!=1) {


}else {*/
        String i = shared.getString("image", "null");
        if (i.equals("null")) {
            /*Bitmap bmp = BitmapFactory.decodeFile(i);
            //ImageView img;
            roundedImage = new RoundImage(bmp);*/
            uimage.setImageResource(R.drawable.user);
            progressDialog.setVisibility(View.GONE);




        } else {
            //new LoadImage().execute(U_image);
loadBitmap(U_image);
/*            Bitmap.createBitmap(Picasso.with(getActivity())
                    .load(U_image)
                    .placeholder(R.drawable.user)

                    .error(R.drawable.user));*/
                    ;







        }
    }




    public void loadBitmap(String url) {

        if (loadtarget == null)
            loadtarget = new Target() {

                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    handleLoadedBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }


            };
        Picasso.with(getActivity()).load(url).into(loadtarget);

    }
    public void handleLoadedBitmap(Bitmap b) {

        roundedImage = new RoundImage(b);
        uimage.setImageDrawable(roundedImage);
        progressDialog.setVisibility(View.GONE);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    /*--- you can select your preferred CompressFormat and quality.
     * I'm going to use JPEG and 100% quality ---*/
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
    /*--- create a new file on SD card ---*/
        file = new File(Environment.getExternalStorageDirectory()
                + File.separator + U_id + ".jpg");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    /*--- create a new FileOutputStream and write bytes to file ---*/
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(bytes.toByteArray());
            fos.close();
            Toast.makeText(getActivity(), "Image savedvto" + file, Toast.LENGTH_SHORT).show();
            editor.putString("image", file.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        editor.putString("Iset", "1");
        editor.commit();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("RequestCOde:" + requestCode + "::ResultCode::" + resultCode);
        if (requestCode == Crop.REQUEST_CROP) {
            try {
                Log.i("entered", "entered2" + outputUri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;//returning null for below statement
                Bitmap bm = BitmapFactory.decodeFile(path, options);
                roundedImage = new RoundImage(bm);

                uimage.setImageDrawable(roundedImage);
            } catch (Exception e) {
                Log.i("error", e.toString());
            }

        } else {
            if (requestCode == RESULT_LOAD_IMAGE && null != data) {
                Log.i("entered", "entered");

                inputUri = data.getData();

                getpath(inputUri);

                Log.d("image path is", path);
                p = path.toLowerCase();
                addFormat(p);

                this.outputUri = Uri.fromFile(new File(this.getActivity().getCacheDir(), "cropped" + "." + x1));
                Crop.of(inputUri, this.outputUri).asSquare().start(getActivity(), this);
                Log.i("entered", "error" + requestCode + "   " + resultCode);

            } else {
                Log.i("error", "error" + requestCode + "   " + resultCode);
            }

        }
    }

    private void addFormat(String p) {

        if (p.endsWith(".png"))
            x1 = "PNG";

        if (p.endsWith(".gif"))
            x1 = "GIF";
        if (p.endsWith(".jpg"))
            x1 = "JPG";

        if (p.endsWith(".jpeg"))
            x1 = "JPEG";

        if (!p.endsWith(".png") && !p.endsWith(".gif") && !p.endsWith(".jpg") && !p.endsWith(".jpeg")) {
            x1 = "PNG";
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
        Toast.makeText(getActivity(), "image path is" + path, Toast.LENGTH_SHORT).show();


    }

    private void setfacebookdata() {

        SharedPreferences shared = getActivity().getSharedPreferences(NavigationDrawer.PREFS_NAME, 0);
        haslognedinviafb1 = (shared.getBoolean("fblogin", false));
        if (haslognedinviafb1) {

            String id1 = shared.getString("facebookid", "");
            String name1 = shared.getString("facebookname", "");
            String email1 = shared.getString("facebookemail", "");
            name.setText(name1);
            email_id.setText(email1);


        }
    }

    private void set() {

        police.setEnabled(false);
        name.setEnabled(false);
        number.setEnabled(false);
        email_id.setEnabled(false);
        address.setEnabled(false);
        male.setEnabled(false);
        female.setEnabled(false);

        landmark.setEnabled(false);

        update.setEnabled(false);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.edit:

                unset();

                break;


            case R.id.editupdate:


                break;
        }
    }

    private void unset() {
        if (haslognedinviafb1) {
            name.setEnabled(false);

            email_id.setEnabled(false);
        } else {
            name.setEnabled(true);

            email_id.setEnabled(true);
        }
        police.setEnabled(true);

        number.setEnabled(true);

        address.setEnabled(true);
        landmark.setEnabled(true);

        update.setEnabled(true);
    }


    public class LoadImage extends AsyncTask<String, String, Bitmap> {

        @Override

        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected Bitmap doInBackground(String... args) {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {
            i = 1;
            if (image != null) {
                roundedImage = new RoundImage(image);
//                uimage.setVisibility(View.VISIBLE);
                progressDialog.setVisibility(View.GONE);
                uimage.setImageDrawable(roundedImage);


                /*--- this method will save your downloaded image to SD card ---*/

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    /*--- you can select your preferred CompressFormat and quality.
     * I'm going to use JPEG and 100% quality ---*/
                image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
    /*--- create a new file on SD card ---*/
                file = new File(Environment.getExternalStorageDirectory()
                        + File.separator + U_id + ".jpg");
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
    /*--- create a new FileOutputStream and write bytes to file ---*/
                try {
                    fos = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fos.write(bytes.toByteArray());
                    fos.close();
                    Toast.makeText(getActivity(), "Image savedvto" + file, Toast.LENGTH_SHORT).show();
                    editor.putString("image", file.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                editor.putString("Iset", "1");
                editor.commit();
            } else {


                Toast.makeText(getActivity(), "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
