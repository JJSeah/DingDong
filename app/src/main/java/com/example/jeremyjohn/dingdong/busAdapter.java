package com.example.jeremyjohn.dingdong;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jeremyjohn.dingdong.models.BusNoModel;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class busAdapter extends RecyclerView.Adapter<busViewHolder> {
    private List<BusNoModel> busList;
    private Context context;

    public busAdapter(Context c, List<BusNoModel> l) {
        context = c;
        busList = l;
    }
    @Override
    public busViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bus_layout, parent, false);
        return new busViewHolder(item);
    }

    @Override
    public void onBindViewHolder(busViewHolder holder, final int position) {
        BusNoModel s = busList.get(position);
        List<BusNoModel.NextBus> n = s.getBusList();
        List<ProgressBar> p = new ArrayList<>();
        List<TextView> ib = new ArrayList<>();
        p.add(holder.crowd);
        p.add(holder.crowd2);
        p.add(holder.crowd3);
        ib.add(holder.incomingBus);
        ib.add(holder.incomingBus2);
        ib.add(holder.incomingBus3);
        holder.busNo.setText(s.getServiceNo());

        for(int i=0; i<3; i++)
        {

            if (n.get(i).getLoad().equals("SEA")) {
                p.get(i).setProgress(0);
            } else if (n.get(i).getLoad().equals("SDA")) {
                p.get(i).setProgress(50);
            } else {
                p.get(i).setProgress(0);
            }

            if (n.get(i).getEstimatedArrival() < 1) {
                ib.get(i).setText("Arr");
            } else {
                ib.get(i).setText(Long.toString(n.get(i).getEstimatedArrival()));
            }
        }
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BusRoute.class);
                intent.putExtra("busno", busList.get(position).getServiceNo());
                intent.putExtra("bustop", AvaliableBus.bustop);
                intent.putExtra("desno", AvaliableBus.desno);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(busList == null) {
            return 0;
        } else {
            return busList.size();
        }

    }

}
