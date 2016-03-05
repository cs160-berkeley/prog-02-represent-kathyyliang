package io.github.kathyyliang.represent;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

public class WatchListenerService extends WearableListenerService {

    // In PhoneToWatchService, we passed in a path, either "/FRED" or "/LEXY"
    // These paths serve to differentiate different phone-to-watch messages
    /*private static final String FRED_FEED = "/Fred";
    private static final String LEXY_FEED = "/Lexy";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("MyTag", "in WatchListenerService, got: " + messageEvent.getPath());
        //use the 'path' field in sendmessage to differentiate use cases
        //(here, fred vs lexy)

        if( messageEvent.getPath().equalsIgnoreCase( FRED_FEED ) ) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, MainActivity.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("CAT_NAME", "Fred");
            Log.d("T", "about to start watch MainActivity with CAT_NAME: Fred");
            startActivity(intent);
        } else if (messageEvent.getPath().equalsIgnoreCase( LEXY_FEED )) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, MainActivity.class );
            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("CAT_NAME", "Lexy");
            Log.d("T", "about to start watch MainActivity with CAT_NAME: Lexy");
            startActivity(intent);
        } else {
            super.onMessageReceived( messageEvent );
        }

    }*/

    private static final String REPRESENT = "/represent";
    public final static String MESSAGE = "io.github.kathyyliang.represent.MESSAGE";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (messageEvent.getPath().equalsIgnoreCase(REPRESENT)) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Log.d("MyTag", value);
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(MESSAGE, value);
            startActivity(intent);
        } else {
            super.onMessageReceived(messageEvent);
        }

    }
}