package io.github.kathyyliang.represent;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "BiYmYnGapve3pwg1gB6zvS08o";
    private static final String TWITTER_SECRET = "Yc40hCa7T5TPH7TUcVUJ4pZ7x6ZpVEbTtYvs1rekMCYbFlEE6s";

    public static Bundle EXTRAS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        EXTRAS = intent.getExtras();

        if (EXTRAS != null) {
            //String catName = extras.getString("CAT_NAME");
            //mFeedBtn.setText("Feed " + catName);
            //String message = extras.getString(WatchListenerService.MESSAGE);
            /*if (message != null) {
                String[] info = message.split(";");
                Resources resources = getResources();
                int image = resources.getIdentifier(info[0], "drawable", getPackageName());
                ImageView imageView = (ImageView) findViewById(R.id.watch_image);
                imageView.setImageResource(image);
                TextView nameView = (TextView) findViewById(R.id.watch_name);
                TextView partyView = (TextView) findViewById(R.id.watch_party);
                TextView typeView = (TextView) findViewById(R.id.watch_type);
                typeView.setText(info[1]);
                nameView.setText(info[2]);
                partyView.setText(info[3]);
                TextView titleView = (TextView) findViewById(R.id.watch_title);
                titleView.setText("");
            }*/
            final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
            pager.setAdapter(new GridPagerAdapter(this, getFragmentManager()));
            TextView titleView = (TextView) findViewById(R.id.watch_title);
            titleView.setText("");
            DotsPageIndicator dotsPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
            dotsPageIndicator.setPager(pager);
            dotsPageIndicator.setDotColorSelected(R.color.colorAccent);
            dotsPageIndicator.setDotColor(R.color.colorPrimary);
            Log.d("MyTag", "end");
        }

        /*mFeedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                startService(sendIntent);
            }
        });*/
    }

}