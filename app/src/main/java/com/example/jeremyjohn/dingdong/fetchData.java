package com.example.jeremyjohn.dingdong;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.jeremyjohn.dingdong.models.BusNoModel;
import com.example.jeremyjohn.dingdong.models.RouteModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class fetchData extends AsyncTask<Void, Void, Void> {
    StringBuffer data = new StringBuffer();
    String uri = "http://datamall2.mytransport.sg/";
    String path = "ltaodataservice/BusArrivalv2?";
    int skip = 4000;
    static List<RouteModel> RouteList = new ArrayList<>();
    String currentstop = AvaliableBus.bustop;
    String destop = AvaliableBus.desno;
    //public TextView t = AvaliableBus.t;
    String stop = "";
    static List<BusNoModel> CurrentList = new ArrayList<>();
    static List<BusNoModel> DesList = new ArrayList<>();
    static List<BusNoModel> CommonList = new ArrayList<>();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    Date now = new Date();
    DBHelper mDatabaseHelper;
    Cursor c = null;
    private Context mContext;

    public fetchData (Context context){
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (CommonList != null) ;
        {
            CommonList.clear();
        }

        if (DesList != null) ;
        {
            DesList.clear();
        }
        try {
            URL url = new URL(uri + path + "BusStopCode=" + destop);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("AccountKey", "uwOl921LRvSBMqYyO59TTQ==");
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data.append(line);
            }

            JSONObject obj_JSONObject = new JSONObject(data.toString());
            JSONArray obj_JSONArray = obj_JSONObject.getJSONArray("Services");


            //Parsing data from JSON and inputting into the list to be used
            for (int i = 0; i < obj_JSONArray.length(); i++) {
                JSONObject serviceObject = obj_JSONArray.getJSONObject(i);
                BusNoModel busNoModel = new BusNoModel();
                busNoModel.setServiceNo(serviceObject.getString("ServiceNo"));
                busNoModel.setOperator(serviceObject.getString("Operator"));

                JSONObject nextBus = serviceObject.getJSONObject("NextBus");
                JSONObject nextBus2 = serviceObject.getJSONObject("NextBus2");
                JSONObject nextBus3 = serviceObject.getJSONObject("NextBus3");

                String nextBusTimeString = nextBus.getString("EstimatedArrival");
                String newstr = nextBusTimeString.substring(0, 22) + nextBusTimeString.substring(22 + 1);
                Log.d(TAG, "newstr "+newstr);
                Date nextBusTimingDate = format.parse(newstr);
                long nextBusTiming = nextBusTimingDate.getTime() - now.getTime();
                nextBusTiming = nextBusTiming / 60000;

                String nextBus2TimeString = nextBus2.getString("EstimatedArrival");
                String newstr2 = nextBus2TimeString.substring(0, 22) + nextBus2TimeString.substring(22 + 1);
                Log.d(TAG, "newstr "+newstr2);
                Date nextBus2TimingDate = format.parse(newstr2);
                long nextBus2Timing = nextBus2TimingDate.getTime() - now.getTime();
                nextBus2Timing = nextBus2Timing / 60000;

                String nextBus3TimeString = nextBus3.getString("EstimatedArrival");
                String newstr3 = nextBus3TimeString.substring(0, 22) + nextBus3TimeString.substring(22 + 1);
                Log.d(TAG, "newstr "+newstr3);
                Date nextBus3TimingDate = format.parse(newstr3);
                long nextBus3Timing = nextBus3TimingDate.getTime() - now.getTime();
                nextBus3Timing = nextBus3Timing / 60000;

                BusNoModel.NextBus incomingBus = new BusNoModel.NextBus(nextBus.getString("DestinationCode"), nextBusTiming, nextBus.getString("Latitude"), nextBus.getString("Longitude"), nextBus.getString("VisitNumber"), nextBus.getString("Load"), nextBus.getString("Feature"), nextBus.getString("Type"), nextBus.getString("OriginCode"));
                BusNoModel.NextBus incomingBus2 = new BusNoModel.NextBus(nextBus2.getString("DestinationCode"), nextBus2Timing, nextBus2.getString("Latitude"), nextBus2.getString("Longitude"), nextBus2.getString("VisitNumber"), nextBus2.getString("Load"), nextBus2.getString("Feature"), nextBus2.getString("Type"), nextBus2.getString("OriginCode"));
                BusNoModel.NextBus incomingBus3 = new BusNoModel.NextBus(nextBus3.getString("DestinationCode"), nextBus3Timing, nextBus3.getString("Latitude"), nextBus3.getString("Longitude"), nextBus3.getString("VisitNumber"), nextBus3.getString("Load"), nextBus3.getString("Feature"), nextBus3.getString("Type"), nextBus3.getString("OriginCode"));
                busNoModel.getBusList().add(incomingBus);
                busNoModel.getBusList().add(incomingBus2);
                busNoModel.getBusList().add(incomingBus3);
                Log.d(TAG,"hell "+ DesList.size());

                DesList.add(busNoModel);


            }
        } catch (ParseException | JSONException | IOException e) {
            e.printStackTrace(); }


        if (CurrentList != null) ;
        {
            CurrentList.clear();
        }
        try {
            URL url1 = new URL(uri + path + "BusStopCode=" + currentstop);
            HttpURLConnection httpURLConnection1 = (HttpURLConnection) url1.openConnection();
            httpURLConnection1.setRequestMethod("GET");
            httpURLConnection1.setRequestProperty("AccountKey", "uwOl921LRvSBMqYyO59TTQ==");
            InputStream inputStream1 = httpURLConnection1.getInputStream();
            BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1));
            String line = "";
            StringBuffer data1 = new StringBuffer();
            while (line != null) {
                line = bufferedReader1.readLine();
                data = data1.append(line);
            }

            JSONObject obj_JSONObject1 = new JSONObject(data1.toString());
            JSONArray obj_JSONArray1 = obj_JSONObject1.getJSONArray("Services");

            //Parsing data from JSON and inputting into the list to be used
            for (int i = 0; i < obj_JSONArray1.length(); i++) {
                Log.d(TAG,"pleasework "+ i + obj_JSONArray1.length());
                JSONObject serviceObject = obj_JSONArray1.getJSONObject(i);
                BusNoModel busNModel = new BusNoModel();
                busNModel.setServiceNo(serviceObject.getString("ServiceNo"));
                busNModel.setOperator(serviceObject.getString("Operator"));

                JSONObject nextBus = serviceObject.getJSONObject("NextBus");
                JSONObject nextBus2 = serviceObject.getJSONObject("NextBus2");
                JSONObject nextBus3 = serviceObject.getJSONObject("NextBus3");

                String nextBusTimeString = nextBus.getString("EstimatedArrival");
                String newstr = nextBusTimeString.substring(0, 22) + nextBusTimeString.substring(22 + 1);
                Log.d(TAG, "newstr "+newstr);
                Date nextBusTimingDate = format.parse(newstr);
                long nextBusTiming = nextBusTimingDate.getTime() - now.getTime();
                nextBusTiming = nextBusTiming / 60000;

                String nextBus2TimeString = nextBus2.getString("EstimatedArrival");
                String newstr2 = nextBus2TimeString.substring(0, 22) + nextBus2TimeString.substring(22 + 1);
                Log.d(TAG, "newstr "+newstr2);
                Date nextBus2TimingDate = format.parse(newstr2);
                long nextBus2Timing = nextBus2TimingDate.getTime() - now.getTime();
                nextBus2Timing = nextBus2Timing / 60000;

                String nextBus3TimeString = nextBus3.getString("EstimatedArrival");
                String newstr3 = nextBus3TimeString.substring(0, 22) + nextBus3TimeString.substring(22 + 1);
                Log.d(TAG, "newstr "+newstr3);
                Date nextBus3TimingDate = format.parse(newstr3);
                long nextBus3Timing = nextBus3TimingDate.getTime() - now.getTime();
                nextBus3Timing = nextBus3Timing / 60000;

                BusNoModel.NextBus incomingBus = new BusNoModel.NextBus(nextBus.getString("DestinationCode"), nextBusTiming, nextBus.getString("Latitude"), nextBus.getString("Longitude"), nextBus.getString("VisitNumber"), nextBus.getString("Load"), nextBus.getString("Feature"), nextBus.getString("Type"), nextBus.getString("OriginCode"));
                BusNoModel.NextBus incomingBus2 = new BusNoModel.NextBus(nextBus2.getString("DestinationCode"), nextBus2Timing, nextBus2.getString("Latitude"), nextBus2.getString("Longitude"), nextBus2.getString("VisitNumber"), nextBus2.getString("Load"), nextBus2.getString("Feature"), nextBus2.getString("Type"), nextBus2.getString("OriginCode"));
                BusNoModel.NextBus incomingBus3 = new BusNoModel.NextBus(nextBus3.getString("DestinationCode"), nextBus3Timing, nextBus3.getString("Latitude"), nextBus3.getString("Longitude"), nextBus3.getString("VisitNumber"), nextBus3.getString("Load"), nextBus3.getString("Feature"), nextBus3.getString("Type"), nextBus3.getString("OriginCode"));
                busNModel.getBusList().add(incomingBus);
                busNModel.getBusList().add(incomingBus2);
                busNModel.getBusList().add(incomingBus3);

                CurrentList.add(busNModel);

            }

        } catch (ParseException | JSONException | IOException e) {
            e.printStackTrace(); }
        if (RouteList != null) ;
        {
            RouteList.clear();
        }
        /*while(skip<4000){
            skip += 500;
        }
        //74 18500 , 19000
        while (skip <= 4500) {

            String path = "ltaodataservice/BusRoutes?$skip="+(String.valueOf(skip));
            try {
                URL url = new URL(uri + path);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("AccountKey", "uwOl921LRvSBMqYyO59TTQ==");
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer data = new StringBuffer();
                String line2 = "";
                while (line2 != null) {
                    line2 = bufferedReader.readLine();
                    data = data.append(line2);
                }
                JSONObject obj_JSONObject = new JSONObject(data.toString());
                JSONArray obj_JSONArray = obj_JSONObject.getJSONArray("value");

                //Parsing data from JSON and inputting into the list to be used
                for (int i = 0; i < obj_JSONArray.length(); i++) {
                    JSONObject serviceObject = obj_JSONArray.getJSONObject(i);
                    RouteModel routeModel = new RouteModel();
                    routeModel.setServiceNo(serviceObject.getString("ServiceNo"));
                    routeModel.setDirection(serviceObject.getString("Direction"));
                    routeModel.setStopSequence(serviceObject.getString("StopSequence"));
                    routeModel.setBusStopCode(serviceObject.getString("BusStopCode"));
                    RouteList.add(routeModel);
                    Log.d(TAG, path);
                }
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            skip += 500;

        }*/

        mDatabaseHelper =  new DBHelper(mContext, null, null, 1);
        try {
            mDatabaseHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
        c = mDatabaseHelper.query("bus", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
            } while (c.moveToNext());
        }
        Cursor data = mDatabaseHelper.getBusRoute();
        while(data.moveToNext()){
            //get the value from the database
            //then add it to the ArrayList
            RouteList.add(new RouteModel(data.getInt(0),data.getString(1),data.getString(2),data.getString(3),data.getString(4)));
        }
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        String currentdirection = "1";
        String currentseq = "1";
        String desdirection = "1";
        String desseq = "1";
        //Log.d(TAG,"pleasework "+ DesList.size());
        for(BusNoModel z:CurrentList) {
            Log.d(TAG,"z"+ z.getServiceNo());
            for (BusNoModel y : DesList) {
                if (z.getServiceNo().equals(y.getServiceNo())) {

                    for (RouteModel x:RouteList){
                        Log.d(TAG,"test"+x.getServiceNo());
                        if (z.getServiceNo().equals(x.getServiceNo())){ //filter to the specific bus

                            if(destop.equals(x.getBusStopCode())){ //filter to the specify des bus stop
                                desdirection = x.getDirection();
                                desseq = x.getStopSequence();
                            }
                            if(currentstop.equals(x.getBusStopCode())){ //filter to the specify current bus stop
                                Log.d(TAG,"test2");
                                currentdirection = x.getDirection(); // get current direction
                                currentseq = x.getStopSequence(); //get current seq

                            }
                        }
                    }
                    Log.d(TAG,"currentdirection "+currentdirection);
                    Log.d(TAG,"currentseq "+currentseq);
                    Log.d(TAG,"desdirection "+desdirection);
                    Log.d(TAG,"desseq "+desseq);
                    if(currentdirection.equals(desdirection)){//check direction
                        if (Integer.valueOf(desseq)>(Integer.valueOf(currentseq))){
                            CommonList.add(z);
                            Log.d(TAG,"PLEASE "+z.getServiceNo());
                        }
                        else{
                        }

                    }
                }
            }
        }
        AvaliableBus.busAdapter.notifyDataSetChanged();
    }

}
