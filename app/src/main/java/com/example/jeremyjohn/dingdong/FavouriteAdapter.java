package com.example.jeremyjohn.dingdong;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jeremyjohn.dingdong.models.Favourites;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;


public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavViewHolder>{

    private Context context;
    private List<Favourites> list;
    Favourite fav;

    public FavouriteAdapter(Context c, List<Favourites> l) {
        context = c;
        list = l;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.favourites_layout, parent, false);
        return new FavViewHolder(view);
    }

    public void onBindViewHolder(FavViewHolder holder, final int position) {
        Favourites sa = list.get(position);
        holder.currentname.setText(sa.getCurname());
        holder.desname.setText(sa.getDesname());
        Log.d(TAG,"HELPTQ4 " + sa.getCurname());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AvaliableBus.class);
                intent.putExtra("Curno", list.get(position).getCurno());
                intent.putExtra("Desno", list.get(position).getDesno());
                intent.putExtra("Currentname", list.get(position).getCurname());
                intent.putExtra("Destinationame", list.get(position).getDesname());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        if(list == null) {
            return 0;
        } else {
            return list.size();
        }
    }
    public class FavViewHolder extends RecyclerView.ViewHolder {

        private TextView currentname,desname;
        public View parentLayout;

        public FavViewHolder(View itemView) {
            super(itemView);

            currentname = itemView.findViewById(R.id.txtcur);
            desname = itemView.findViewById(R.id.txtdes);
            parentLayout = itemView.findViewById(R.id.favlist);
        }

    }

}
