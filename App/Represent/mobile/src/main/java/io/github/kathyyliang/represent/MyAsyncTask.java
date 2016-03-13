package io.github.kathyyliang.represent;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kathyyliang on 3/12/16.
 */
public class MyAsyncTask extends AsyncTask<String, Void, ArrayList<ArrayList<String>>> {

    public Context context;
    //static ArrayList<String> tweets;

    public MyAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<ArrayList<String>> doInBackground(String... params) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            String apikey = bundle.getString("congress.api.sunlightfoundation.apikey");
            String baseURL = "https://congress.api.sunlightfoundation.com";
            String zip = params[0];
            URL url = new URL(baseURL + "/legislators/locate?zip=" + zip + "&apikey=" + apikey);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
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

                ArrayList<String> names = new ArrayList<>();
                ArrayList<String> parties = new ArrayList<>();
                ArrayList<String> types = new ArrayList<>();
                //tweets = new ArrayList<>();
                ArrayList<String> twitter_ids = new ArrayList<>();
                ArrayList<String> emails = new ArrayList<>();
                ArrayList<String> websites = new ArrayList<>();
                ArrayList<String> bioguide_ids = new ArrayList<>();
                ArrayList<String> image_urls = new ArrayList<>();

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject temp = jArray.getJSONObject(i);

                    String name = temp.getString("first_name") + " " + temp.getString("last_name");
                    names.add(name);

                    String party = temp.getString("party");
                    if (party.equalsIgnoreCase("D")) {
                        party = "Democrat";
                    } else if (party.equalsIgnoreCase("R")) {
                        party = "Republican";
                    }
                    parties.add(party);

                    String type = temp.getString("title");
                    if (type.equalsIgnoreCase("Rep")) {
                        type = "Representative";
                    } else if (type.equalsIgnoreCase("Sen")) {
                        type = "Senator";
                    }
                    types.add(type);

                    final String twitter_id = temp.getString("twitter_id");
                    twitter_ids.add(twitter_id);

                    /*if (twitter_id != null) {
                        TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
                            @Override
                            public void success(Result<AppSession> result) {
                                AppSession session = result.data;
                                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient(session);
                                twitterApiClient.getStatusesService().userTimeline(null, twitter_id, 1, null, null, null, null, null, null, new Callback<List<Tweet>>() {
                                    @Override
                                    public void success(Result<List<Tweet>> result) {
                                        for (Tweet tweet : result.data) {
                                            String tweetText = tweet.text;
                                            Log.i("MyTag", tweetText);
                                            tweets.add(tweetText);
                                        }
                                    }

                                    @Override
                                    public void failure(TwitterException e) {
                                        Log.i("MyTag", "errorororor");
                                    }
                                });
                            }

                            @Override
                            public void failure(TwitterException e) {
                                Log.i("MyTag", "hellooo");
                            }
                        });
                    } else {
                        tweets.add("No twitter.");
                    }*/

                    String email = temp.getString("oc_email");
                    emails.add(email);

                    String website = temp.getString("website");
                    websites.add(website);

                    String bioguide_id = temp.getString("bioguide_id");
                    bioguide_ids.add(bioguide_id);

                    String image_url = "https://theunitedstates.io/images/congress/225x275/" + bioguide_id + ".jpg";
                    image_urls.add(image_url);
                }
                ArrayList<ArrayList<String>> bigList = new ArrayList<>();
                bigList.add(names);
                bigList.add(parties);
                bigList.add(types);
                //bigList.add(tweets);
                bigList.add(twitter_ids);
                bigList.add(emails);
                bigList.add(websites);
                bigList.add(bioguide_ids);
                bigList.add(image_urls);
                return bigList;
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

}