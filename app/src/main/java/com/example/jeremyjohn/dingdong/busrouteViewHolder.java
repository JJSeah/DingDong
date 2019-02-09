package com.example.jeremyjohn.dingdong;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class busrouteViewHolder extends RecyclerView.ViewHolder{
    public TextView stopname,txtLat,txtLong;
    public ImageView imgStop;
    public View view;
    public View parentLayout;

    public busrouteViewHolder(View v) {
        super(v);
        view = v;
        stopname = view.findViewById(R.id.stopname);
        imgStop = view.findViewById(R.id.imgStop);
        parentLayout = view.findViewById(R.id.busstoplist);
        txtLat = view.findViewById(R.id.txtLat);
        txtLong = view.findViewById(R.id.txtLong);
    }

}
