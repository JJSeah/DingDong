package com.example.jeremyjohn.dingdong;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jeremyjohn.dingdong.models.ServiceNoModel;

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

public class fetchBusStop extends AsyncTask<Void, Void, Void> {
    StringBuffer data = new StringBuffer();
    int skip = 0;
    String uri = "http://datamall2.mytransport.sg/";
    //String path = "ltaodataservice/BusStops";
    static List<ServiceNoModel> ServiceNoList = new ArrayList<>();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    Date now = new Date();
    @Override
    protected Void doInBackground(Void... voids) {
        /*while (skip <= 5000) {

            String path = "ltaodataservice/BusStops?$skip="+(String.valueOf(skip));
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
                    ServiceNoModel serviceNoModel = new ServiceNoModel();
                    serviceNoModel.setBusStopCode(serviceObject.getString("BusStopCode"));
                    serviceNoModel.setDescription(serviceObject.getString("Description"));
                    serviceNoModel.setLatitude(serviceObject.getString("Latitude"));
                    serviceNoModel.setLongitude(serviceObject.getString("Longitude"));
                    serviceNoModel.setPass("");
                    ServiceNoList.add(serviceNoModel);
                    Log.d(TAG, path);
                }
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            skip += 500;
        }*/
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {

        super.onPostExecute(aVoid);
        MainActivity.bAdapter.notifyDataSetChanged();
    }
}
