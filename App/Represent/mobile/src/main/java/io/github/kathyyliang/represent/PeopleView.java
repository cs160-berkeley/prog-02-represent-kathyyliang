package io.github.kathyyliang.represent;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class PeopleView extends AppCompatActivity {

    public final static String IMAGE = "io.github.kathyyliang.represent.IMAGE";
    public final static String NAME = "io.github.kathyyliang.represent.NAME";
    public final static String PARTY = "io.github.kathyyliang.represent.PARTY";
    public final static String TYPE = "io.github.kathyyliang.represent.TYPE";
    public final static String BIOGUIDE_ID = "io.github.kathyyliang.represent.BIOGUIDE_ID";

    public final static String NAMES = "io.github.kathyyliang.represent.NAMES";
    public final static String PARTIES = "io.github.kathyyliang.represent.PARTIES";
    public final static String TYPES = "io.github.kathyyliang.represent.TYPES";
    public final static String TWITTER_IDS = "io.github.kathyyliang.represent.TWITTER_IDS";
    public final static String EMAILS = "io.github.kathyyliang.represent.EMAILS";
    public final static String WEBSITES = "io.github.kathyyliang.represent.WEBSITES";
    public final static String BIOGUIDE_IDS = "io.github.kathyyliang.represent.BIOGUIDE_IDS";
    public final static String IMAGE_URLS = "io.github.kathyyliang.represent.IMAGE_URLS";
    public final static String TWEETS = "io.github.kathyyliang.represent.TWEETS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_view);

        Intent intent = getIntent();
        String title = intent.getStringExtra(MainActivity.TITLE);
        setTitle(title);

        try {
            ArrayList<ArrayList<String>> bigList = new MyAsyncTask(this).execute(title).get();

            android.widget.ListView listView = (android.widget.ListView) findViewById(R.id.listView);

            RepresentAdapterSimple adapter = new RepresentAdapterSimple(this, bigList.get(0), bigList.get(1),
                    bigList.get(2), bigList.get(3), bigList.get(4), bigList.get(5), bigList.get(6), bigList.get(7));
            listView.setAdapter(adapter);

            Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
            sendIntent.putExtra(NAMES, bigList.get(0).toArray(new String[0]));
            sendIntent.putExtra(PARTIES, bigList.get(1).toArray(new String[0]));
            sendIntent.putExtra(TYPES, bigList.get(2).toArray(new String[0]));
            sendIntent.putExtra(IMAGE_URLS, bigList.get(7).toArray(new String[0]));
            startService(sendIntent);
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }
    }

    public class RepresentAdapterSimple extends BaseAdapter {

        private Context context;
        public ArrayList<String> names2;
        public ArrayList<String> parties2;
        public ArrayList<String> types2;
        public ArrayList<String> tweets2;
        public ArrayList<String> emails2;
        public ArrayList<String> websites2;
        public ArrayList<String> bioguide_ids2;
        public ArrayList<String> image_urls2;

        public RepresentAdapterSimple(Context context, ArrayList<String> names, ArrayList<String> parties,
                                      ArrayList<String> types, ArrayList<String> tweets, ArrayList<String> emails,
                                      ArrayList<String> websites, ArrayList<String> bioguide_ids, ArrayList<String> image_urls) {
            this.context = context;
            this.names2 = names;
            this.parties2 = parties;
            this.types2 = types;
            this.tweets2 = tweets;
            this.emails2 = emails;
            this.websites2 = websites;
            this.bioguide_ids2 = bioguide_ids;
            this.image_urls2 = image_urls;
        }

        @Override
        public int getCount() {
            return names2.size();
        }

        @Override
        public Object getItem(int position) {
            return names2.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View representative = inflater.inflate(R.layout.representative, parent, false);

            ImageView imageView = (ImageView) representative.findViewById(R.id.imageView);
            TextView nameView = (TextView) representative.findViewById(R.id.name);
            TextView partyView = (TextView) representative.findViewById(R.id.party);
            TextView typeView = (TextView) representative.findViewById(R.id.type);
            TextView tweetView = (TextView) representative.findViewById(R.id.tweet);
            TextView emailView = (TextView) representative.findViewById(R.id.email);
            TextView websiteView = (TextView) representative.findViewById(R.id.website);

            //imageView.setImageResource(images2[position]);
            Picasso.with(context).load(image_urls2.get(position)).into(imageView);
            imageView.setTag(image_urls2.get(position));
            nameView.setText(names2.get(position));
            nameView.setTag(bioguide_ids2.get(position));
            partyView.setText(parties2.get(position));
            typeView.setText(types2.get(position));
            String twit = tweets2.get(position);
            if (twit.equals("null")) {
                twit = "";
            }
            tweetView.setText(twit);
            emailView.setText(emails2.get(position));
            websiteView.setText(websites2.get(position));

            Button moreInfo = (Button) representative.findViewById(R.id.more_info);
            moreInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailedView.class);
                    View parentRow = (View) v.getParent();
                    ImageView imageView = (ImageView) parentRow.findViewById(R.id.imageView);
                    TextView nameView = (TextView) parentRow.findViewById(R.id.name);
                    TextView partyView = (TextView) parentRow.findViewById(R.id.party);
                    TextView typeView = (TextView) parentRow.findViewById(R.id.type);
                    intent.putExtra(IMAGE, (String) imageView.getTag());
                    intent.putExtra(NAME, nameView.getText());
                    intent.putExtra(BIOGUIDE_ID, (String) nameView.getTag());
                    intent.putExtra(PARTY, partyView.getText());
                    intent.putExtra(TYPE, typeView.getText());
                    startActivity(intent);
                }
            });

            return representative;
        }

    }



}
