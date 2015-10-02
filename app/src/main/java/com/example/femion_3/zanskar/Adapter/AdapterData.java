package com.example.femion_3.zanskar.Adapter;

/**
 * Created by Femion-3 on 06/07/2015.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.femion_3.zanskar.AdapterDecorate.RoundImage;
import com.example.femion_3.zanskar.Main.NavigationDrawer;
import com.example.femion_3.zanskar.R;

import java.io.InputStream;
import java.net.URL;

public class AdapterData extends RecyclerView.Adapter<AdapterData.ViewHolderBusNo> {

    private RoundImage roundedImage;
    Context context;
    private SharedPreferences shared;
    String U_image;
    Bitmap resized;

    Boolean fblogin;


    private LayoutInflater layoutInflater;

    String s[];
    private int mIcons[];


    public AdapterData(Context context, String[] TITLES, int Icons[], String u_image) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.s = TITLES;
        this.mIcons=Icons;
        U_image = u_image;
        shared = context.getSharedPreferences(NavigationDrawer.PREFS_NAME, 0);
        fblogin=shared.getBoolean("fblogin",false);
/*
        if(!U_image.equals("null"))
            Log.d("image is","not null");
        new LoadImage().execute(U_image);
*/

    }

    @Override
    public ViewHolderBusNo onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_row, parent, false);
        ViewHolderBusNo viewHolder = new ViewHolderBusNo(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolderBusNo holder, final int position) {

        holder.textView.setText(s[position]);
        if(position==0) {

            if(fblogin || !U_image.equals("null")) {
                Bitmap myBitmap = BitmapFactory.decodeFile(U_image);

//            ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);

                //myImage.setImageBitmap(myBitmap);
/*
                resized = Bitmap.createScaledBitmap(myBitmap, 50, 50, true);
                roundedImage = new RoundImage(resized);*/
                holder.iconimage.setImageBitmap(myBitmap);

            }


        } else {

            holder.iconimage.setImageResource(mIcons[position]);
        }
        Log.i("onbind", "position is" + position);


    }

    @Override
    public int getItemCount() {
        return s.length;
    }


    class ViewHolderBusNo extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView iconimage;


        public ViewHolderBusNo(View itemView) {

            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.rowText);
            iconimage= (ImageView) itemView.findViewById(R.id.rowIcon);
        }



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

            if (image != null) {
               resized= Bitmap.createScaledBitmap(image, 50, 50, true);
                roundedImage = new RoundImage(resized);
//                uimage.setImageDrawable(roundedImage);


            } else {


                Toast.makeText(context,"Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }
}




//        1) http://getbootstrap.com/components/
//        2) https://www.iconfinder.com/
//        3) http://marcoceppi.github.io/bootstrap-glyphicons/
//
//        icons