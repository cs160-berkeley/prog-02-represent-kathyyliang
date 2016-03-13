package io.github.kathyyliang.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DetailedView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_view);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String title = bundle.getString(PeopleView.NAME);
        setTitle(title);

        String image_url = bundle.getString(PeopleView.IMAGE);
        String party = bundle.getString(PeopleView.PARTY);
        String type = bundle.getString(PeopleView.TYPE);
        String bioguide_id = bundle.getString(PeopleView.BIOGUIDE_ID);

        // hardcoded for now
        /*if (type.equals("Senator")) {
            TextView locationView = (TextView) findViewById(R.id.detailedLocation);
            locationView.setText("California");
        }*/

        try {
            ArrayList<ArrayList<String>> bigList = new MyAsyncTask2(this).execute(bioguide_id).get();
            TextView locationView = (TextView) findViewById(R.id.detailedLocation);
            locationView.setText(bigList.get(0).get(0));
            TextView termEndsView = (TextView) findViewById(R.id.term_ends);
            String termEnds = "Term ends " + bigList.get(0).get(1);
            termEndsView.setText(termEnds);
            ListView listViewCommittees = (ListView) findViewById(R.id.list_committees);
            ListView listViewBills = (ListView) findViewById(R.id.list_bills);
            ArrayList<String> committees_list = bigList.get(1);
            ArrayList<String> bills_list = bigList.get(2);
            ArrayAdapter committees = new ArrayAdapter(this, android.R.layout.simple_list_item_1, committees_list);
            ArrayAdapter bills = new ArrayAdapter(this, android.R.layout.simple_list_item_1, bills_list);
            listViewCommittees.setAdapter(committees);
            listViewBills.setAdapter(bills);
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }

        ImageView imageView = (ImageView) findViewById(R.id.detailedImageView);
        Picasso.with(getBaseContext()).load(image_url).into(imageView);
        TextView partyView = (TextView) findViewById(R.id.detailedParty);
        partyView.setText(party);
        TextView typeView = (TextView) findViewById(R.id.detailedType);
        typeView.setText(type);

        // hardcoded sample committees and bills
        //String[] sample_committees = {"Appropriations Committee", "Budget Committee", "Cats Committee", "Dogs Committee"};
        /*String[] sample_bills = {"10/08/2015 - Improving Access to Mental Health Act",
                "06/10/2015 - Pathways Out of Poverty Act of 2015",
                "05/15/2015 - Better Lives for Cats and Dogs Act"};*/

    }
}
