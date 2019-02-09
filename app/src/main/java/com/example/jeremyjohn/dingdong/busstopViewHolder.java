package com.example.jeremyjohn.dingdong;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class busstopViewHolder extends RecyclerView.ViewHolder{
    public TextView stopname, stopno;
    public View view;
    public View parentLayout;

    public busstopViewHolder(View v) {
        super(v);
        view = v;
        stopname = view.findViewById(R.id.stopname);
        stopno = view.findViewById(R.id.stopno);
        parentLayout = view.findViewById(R.id.stoplist);
    }

}
