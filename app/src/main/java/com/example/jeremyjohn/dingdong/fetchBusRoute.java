package com.example.jeremyjohn.dingdong;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jeremyjohn.dingdong.models.BusNoModel;
import com.example.jeremyjohn.dingdong.models.RouteModel;
import com.example.jeremyjohn.dingdong.models.ServiceNoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.view.View.ROTATION;
import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class fetchBusRoute extends AsyncTask<Void, Void, Void> {
    StringBuffer data = new StringBuffer();
    int skip = 0;
    String uri = "http://datamall2.mytransport.sg/";
    static List<RouteModel> RouteList = new ArrayList<>();
    static List<RouteModel> Filter = new ArrayList<>();
    String busno = BusRoute.busno;
    String currentstop = AvaliableBus.bustop;
    String destop = AvaliableBus.desno;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    Date now = new Date();
    @Override
    protected Void doInBackground(Void... voids) {
        while (skip <= 26000) {

            String path = "ltaodataservice/BusRoutes?$skip="+(String.valueOf(skip));
            try {
                URL url = new URL(uri + path);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("AccountKey", "uwOl921LRvSBMqYyO59TTQ==");
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer data = new StringBuffer();
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data.append(line);
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
        }
        String currentdirection = "";
            String currentseq = "";
            String desdirection = "";
            String desseq = "";
            for (RouteModel r : RouteList){
                if(r.getServiceNo().equals(busno)) {
                    if (destop.equals(r.getBusStopCode())) {
                        desdirection = r.getDirection();
                        desseq = r.getStopSequence();
                    }
                    if (currentstop.equals(r.getBusStopCode())) {
                        currentdirection = r.getDirection();
                        currentseq = r.getStopSequence();
                    }
                    if (r.getDirection().equals(currentdirection)){
                        while(Integer.valueOf(r.getStopSequence())>=Integer.valueOf(currentseq) && Integer.valueOf(r.getStopSequence())<=Integer.valueOf(desseq)){
                            Filter.add(r);
                        }
                    }
                }

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        super.onPostExecute(aVoid);

        BusRoute.bAdapter.notifyDataSetChanged();
    }
}
