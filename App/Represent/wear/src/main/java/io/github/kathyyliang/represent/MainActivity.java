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

public class MainActivity extends Activity {

    public static Bundle EXTRAS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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