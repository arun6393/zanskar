package com.example.femion_3.zanskar.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by bunty on 7/27/2015.
 */
public class AdapterWorker extends RecyclerView.Adapter<AdapterWorker.ViewHolder1> {

    @Override
    public ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }


    @Override
    public void onBindViewHolder(ViewHolder1 holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    class ViewHolder1 extends RecyclerView.ViewHolder{

        public ViewHolder1(View itemView) {
            super(itemView);
        }
    }
}

