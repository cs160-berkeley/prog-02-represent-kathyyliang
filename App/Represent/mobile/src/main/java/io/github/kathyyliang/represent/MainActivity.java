package io.github.kathyyliang.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public final static String TITLE = "io.github.kathyyliang.represent.TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button zipCodeButton = (Button) findViewById(R.id.zip_code_button);
        Button locationButton = (Button) findViewById(R.id.location_button);

        zipCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PeopleView.class);
                //EditText zipCode = (EditText) findViewById(R.id.zip_code);
                //String title = zipCode.getText().toString();
                // hardcoded zip code for now
                String title = "94720";
                intent.putExtra(TITLE, title);
                startActivity(intent);

                //feedCat();
            }
        });

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PeopleView.class);
                // hardcoded location for now
                intent.putExtra(TITLE, "Berkeley, CA");
                startActivity(intent);

                //feedCat();
            }
        });


    }

    /*public void useZipCode(View view) {
        Intent intent = new Intent(this, PeopleView.class);
        EditText zipCode = (EditText) findViewById(R.id.zip_code);
        String title = zipCode.getText().toString();
        // hardcoded zip code for now
        title = "94720";
        intent.putExtra(TITLE, title);
        startActivity(intent);
    }

    public void useLocation(View view) {
        Intent intent = new Intent(this, PeopleView.class);
        // hardcoded location for now
        intent.putExtra(TITLE, "Berkeley, CA");
        startActivity(intent);
    }

    public void feedCat() {
        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
        sendIntent.putExtra("CAT_NAME", "Lexy");
        Log.d("MyTag", "startService sendIntent");
        startService(sendIntent);
    }*/

}
