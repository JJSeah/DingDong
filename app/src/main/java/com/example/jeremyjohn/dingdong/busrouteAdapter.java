package com.example.jeremyjohn.dingdong;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jeremyjohn.dingdong.models.RouteModel;
import com.example.jeremyjohn.dingdong.models.ServiceNoModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class busrouteAdapter extends RecyclerView.Adapter<busrouteViewHolder> {
    private List<ServiceNoModel> busList ;
    private Context context;
    int i = BusRoute.p;
    int o = 0;
    private static final int NOTIFICATION_CHANNEL_ID = 1000;
    private NotificationHandler notificationHandler;


    public busrouteAdapter(Context c, ArrayList<ServiceNoModel> l) {
        context = c;
        busList = l;
    }
    @Override
    public busrouteViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        notificationHandler = new NotificationHandler(context);
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.busroute_layout, parent, false);
        return new busrouteViewHolder(item);
    }


    @Override
    public void onBindViewHolder(busrouteViewHolder holder, final int position) {
        int stopno = bstops_radiobtn.getNumPanelsSelected(context);
        ServiceNoModel s = busList.get(position);
        List<TextView> bs = new ArrayList<>();
        holder.stopname.setText(s.getDescription());
        holder.txtLat.setText(s.getLatitude());
        holder.txtLong.setText(s.getLongitude());

        if(position == i){
            holder.imgStop.setImageResource(R.drawable.ding);
        }
        if (s.getPass().equals("Y")){
            holder.imgStop.setImageResource(R.drawable.dong);
            i++;
        }

        //reaching x destination
        int stopNo = bstops_radiobtn.getNumPanelsSelected(context); //getting from settings
        Log.d(TAG, "alerttiming "+stopNo);
        if (i == busList.size() - stopNo && BusRoute.e==0){
            BusRoute.e++;
            postNotificationToUserDevice(NOTIFICATION_CHANNEL_ID,
                    "Arriving at Destination");
            View content = ((Activity) context)
                    .getLayoutInflater()
                    .inflate(R.layout.alert_arrive  //layout file
                            , null);
            AlertDialog.Builder build = new AlertDialog.Builder(context);
            build.setTitle("Ding Dong");
            build.setView(content);
            //positive button
            build.setPositiveButton("OK!",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                             //remove item from list
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });

            //to show the button

            build.show();
        }
        if (i == busList.size() && BusRoute.e==1){
            BusRoute.e++;
            postNotificationToUserDevice(NOTIFICATION_CHANNEL_ID,
                    "Alight now!");
            View content = ((Activity) context)
                    .getLayoutInflater()
                    .inflate(R.layout.alert_alight  //layout file
                            , null);
            AlertDialog.Builder build = new AlertDialog.Builder(context);
            build.setTitle("Ding Dong");
            TextView tv = content.findViewById((R.id.txtalight));
            tv.setText(busList.get(i-1).getDescription());
            build.setView(content);
            //positive button
            build.setPositiveButton("ALIGHT !",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //remove item from list
                            notifyDataSetChanged();
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                            dialog.dismiss();
                        }
                    });

            //to show the button
            build.show();
        }
        //bus arriving at the bus stop
        int arrivalalert = arrivalalert_radiobtn.getNumPanelsSelected(context); //getting from settings
        if (BusRoute.i==arrivalalert && o==0){
            o++;
            BusRoute.i++;
            postNotificationToUserDevice(NOTIFICATION_CHANNEL_ID,
                    "Your bus is arriving soon!");
            View content = ((Activity) context)
                    .getLayoutInflater()
                    .inflate(R.layout.alert_alight  //layout file
                            , null);
            AlertDialog.Builder build = new AlertDialog.Builder(context);
            build.setTitle("Ding Dong");
            TextView tv = content.findViewById((R.id.txtalight));
            tv.setText("Your bus is arriving soon!");
            build.setView(content);
            //positive button
            build.setPositiveButton("Board !",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //remove item from list
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });

            //to show the button
            build.show();
        }
    }

    @Override
    public int getItemCount() {
        if(busList == null) {
            return 0;
        } else {
            return busList.size();
        }

    }

    private void postNotificationToUserDevice (int notificationID, String titleText){

        Notification.Builder notificationBuilder = null;

        switch (notificationID){
            case NOTIFICATION_CHANNEL_ID :
                notificationBuilder = notificationHandler.createAndReturnWatchMovieNotification
                        ("Ding Dong",titleText);
                break;
        }

        if (notificationBuilder != null){
            notificationHandler.notifyTheUser(NOTIFICATION_CHANNEL_ID,
                    notificationBuilder);
        }
    }

}
