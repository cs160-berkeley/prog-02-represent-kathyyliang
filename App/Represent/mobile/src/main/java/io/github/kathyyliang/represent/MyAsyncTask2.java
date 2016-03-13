package io.github.kathyyliang.represent;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kathyyliang on 3/12/16.
 */
public class MyAsyncTask2 extends AsyncTask<String, Void, ArrayList<ArrayList<String>>> {

    public Context context;

    public MyAsyncTask2(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<ArrayList<String>> doInBackground(String... params) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            String apikey = bundle.getString("congress.api.sunlightfoundation.apikey");
            String baseURL = "https://congress.api.sunlightfoundation.com";
            String bioguide_id = params[0];
            URL legislator = new URL(baseURL + "/legislators?bioguide_id=" + bioguide_id + "&apikey=" + apikey);
            HttpURLConnection urlConnection = (HttpURLConnection) legislator.openConnection();
            ArrayList<String> info = new ArrayList<>();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                String json = stringBuilder.toString();
                JSONObject jObject = new JSONObject(json);
                JSONArray jArray = jObject.getJSONArray("results");
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject temp = jArray.getJSONObject(i);

                    String state = temp.getString("state_name");
                    String district = temp.getString("district");
                    if (district.equals("null")) {
                        district = "";
                    } else {
                        district = " District " + district;
                    }
                    String location = state + district;
                    String term_end = temp.getString("term_end");
                    Log.i("MyTag", location);
                    Log.i("MyTag", term_end);
                    info.add(location);
                    info.add(term_end);
                }
            } finally {
                urlConnection.disconnect();
            }
            URL committees = new URL(baseURL + "/committees?member_ids=" + bioguide_id + "&apikey=" + apikey);
            urlConnection = (HttpURLConnection) committees.openConnection();
            ArrayList<String> committees_list = new ArrayList<>();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                String json = stringBuilder.toString();
                JSONObject jObject = new JSONObject(json);
                JSONArray jArray = jObject.getJSONArray("results");
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject temp = jArray.getJSONObject(i);
                    String name = temp.getString("name");
                    committees_list.add(name);
                }
                Log.i("MyTag", "Committees: " + committees_list.size());
            } finally {
                urlConnection.disconnect();
            }
            URL bills = new URL(baseURL + "/bills?sponsor_id=" + bioguide_id + "&apikey=" + apikey);
            urlConnection = (HttpURLConnection) bills.openConnection();
            ArrayList<String> bills_list = new ArrayList<>();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                String json = stringBuilder.toString();
                JSONObject jObject = new JSONObject(json);
                JSONArray jArray = jObject.getJSONArray("results");
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject temp = jArray.getJSONObject(i);
                    String name = temp.getString("short_title");
                    if (name.equals("null")) {
                        name = temp.getString("official_title");
                    }
                    bills_list.add(name);
                    if (i > 8) {
                        i = jArray.length();
                    }
                }
                Log.i("MyTag", "Bills: " + bills_list.size());
            } finally {
                urlConnection.disconnect();
            }
            ArrayList<ArrayList<String>> bigList = new ArrayList<>();
            bigList.add(info);
            bigList.add(committees_list);
            bigList.add(bills_list);
            return bigList;
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }
}
