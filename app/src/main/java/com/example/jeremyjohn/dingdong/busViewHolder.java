package com.example.jeremyjohn.dingdong;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class busViewHolder extends RecyclerView.ViewHolder{
    public TextView busNo, incomingBus, incomingBus2, incomingBus3;
    public ProgressBar crowd, crowd2, crowd3;
    public View view;
    public View parentLayout;

    public busViewHolder (View v) {
        super(v);
        view = v;
        busNo = view.findViewById(R.id.busNo);
        incomingBus = view.findViewById(R.id.incomingBus);
        incomingBus2 = view.findViewById(R.id.incomingBus2);
        incomingBus3 = view.findViewById(R.id.incomingBus3);
        crowd = view.findViewById(R.id.crowd);
        crowd2 = view.findViewById(R.id.crowd2);
        crowd3 = view.findViewById(R.id.crowd3);
        parentLayout = view.findViewById(R.id.buslayout);
    }

}
