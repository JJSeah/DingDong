package com.example.jeremyjohn.dingdong;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;
import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class fetchBus extends AsyncTask<Void, Void, Void> {
    StringBuffer data = new StringBuffer();
    String uri = "http://datamall2.mytransport.sg/";
    String path = "ltaodataservice/BusArrivalv2?";
    String stop = AvaliableBus.desno;
    int stopno = BusRoute.alertno;
    static List<BusNoModel> BusList = new ArrayList<>();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    Date now = new Date();
    long time = 10;
    static int x = 0;
    private Context context;
    //SharedPreferences prefs = context.getSharedPreferences("AppAlert", MODE_PRIVATE);
    //int alerttime =prefs.getInt("arrival_alert", 1);

    @Override
    protected Void doInBackground(Void... voids) {

        if (BusList != null) ;
        {
            BusList.clear();
        }
        try {

            while(time <= stopno) {
                URL url = new URL(uri + path + "BusStopCode=" + stop);
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
                    Date nextBusTimingDate = format.parse(nextBusTimeString);
                    long nextBusTiming = nextBusTimingDate.getTime() - now.getTime();
                    nextBusTiming = nextBusTiming / 60000;
                    time = nextBusTiming;

                    String nextBus2TimeString = nextBus2.getString("EstimatedArrival");
                    Date nextBus2TimingDate = format.parse(nextBus2TimeString);
                    long nextBus2Timing = nextBus2TimingDate.getTime() - now.getTime();
                    nextBus2Timing = nextBus2Timing / 60000;

                    String nextBus3TimeString = nextBus3.getString("EstimatedArrival");
                    Date nextBus3TimingDate = format.parse(nextBus3TimeString);
                    long nextBus3Timing = nextBus3TimingDate.getTime() - now.getTime();
                    nextBus3Timing = nextBus3Timing / 60000;

                    BusNoModel.NextBus incomingBus = new BusNoModel.NextBus(nextBus.getString("DestinationCode"), nextBusTiming, nextBus.getString("Latitude"), nextBus.getString("Longitude"), nextBus.getString("VisitNumber"), nextBus.getString("Load"), nextBus.getString("Feature"), nextBus.getString("Type"), nextBus.getString("OriginCode"));
                    BusNoModel.NextBus incomingBus2 = new BusNoModel.NextBus(nextBus2.getString("DestinationCode"), nextBus2Timing, nextBus2.getString("Latitude"), nextBus2.getString("Longitude"), nextBus2.getString("VisitNumber"), nextBus2.getString("Load"), nextBus2.getString("Feature"), nextBus2.getString("Type"), nextBus2.getString("OriginCode"));
                    BusNoModel.NextBus incomingBus3 = new BusNoModel.NextBus(nextBus3.getString("DestinationCode"), nextBus3Timing, nextBus3.getString("Latitude"), nextBus3.getString("Longitude"), nextBus3.getString("VisitNumber"), nextBus3.getString("Load"), nextBus3.getString("Feature"), nextBus3.getString("Type"), nextBus3.getString("OriginCode"));
                    busNoModel.getBusList().add(incomingBus);
                    busNoModel.getBusList().add(incomingBus2);
                    busNoModel.getBusList().add(incomingBus3);

                    BusList.add(busNoModel);


                }
            }
        } catch (ParseException | JSONException | IOException e) {
            e.printStackTrace(); }

        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(time <= stopno){
            x = 1;
        }

        BusRoute.bAdapter.notifyDataSetChanged();
    }

}
