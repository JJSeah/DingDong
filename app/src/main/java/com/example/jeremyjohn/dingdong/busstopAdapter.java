package com.example.jeremyjohn.dingdong;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jeremyjohn.dingdong.models.ServiceNoModel;

import java.util.ArrayList;
import java.util.List;

public class busstopAdapter extends RecyclerView.Adapter<busstopViewHolder> {
    private List<ServiceNoModel> busList;
    private Context context;

    public busstopAdapter(Context c, List<ServiceNoModel> l) {
        context = c;
        busList = l;
    }
    @Override
    public busstopViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.busstop_layout, parent, false);
        return new busstopViewHolder(item);
    }

    @Override
    public void onBindViewHolder(busstopViewHolder holder, final int position) {
        ServiceNoModel s = busList.get(position);
        List<TextView> bs = new ArrayList<>();
        holder.stopname.setText(s.getDescription());
        holder.stopno.setText(s.getBusStopCode());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Currentstop.class);
                intent.putExtra("stopnumber", busList.get(position).getBusStopCode());
                intent.putExtra("stopname", busList.get(position).getDescription());
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
    public void filterList(ArrayList<ServiceNoModel> filteredList) {
        busList = filteredList;
        notifyDataSetChanged();
    }

}
