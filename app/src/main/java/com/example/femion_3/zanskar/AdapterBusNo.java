package com.example.femion_3.zanskar;

/**
 * Created by Femion-3 on 06/07/2015.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class AdapterBusNo extends RecyclerView.Adapter<AdapterBusNo.ViewHolderBusNo> {


    Context context;


    private LayoutInflater layoutInflater;

    String s[];


    public AdapterBusNo(Context context, String[] TITLES) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.s = TITLES;

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

        Log.i("onbind", "position is" + position);


    }

    @Override
    public int getItemCount() {
        return s.length;
    }


    class ViewHolderBusNo extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView;


        public ViewHolderBusNo(View itemView) {

            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.rowText);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "position is" + getPosition(), Toast.LENGTH_LONG).show();
            switch (getPosition())
            {
                case 5:SharedPreferences settings = context.getSharedPreferences(Choose.PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();

//Set "hasLoggedIn" to true
                    editor.putBoolean("hasLoggedIn", false);

// Commit the edits!
                    editor.commit();
                    Intent i1=new Intent(context,MainActivity.class);
                    context.startActivity(i1);
                    break;
            }
        }


    }
}