package io.github.kathyyliang.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailedView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_view);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String title = bundle.getString(PeopleView.NAME);
        setTitle(title);

        int image = bundle.getInt(PeopleView.IMAGE);
        String party = bundle.getString(PeopleView.PARTY);
        String type = bundle.getString(PeopleView.TYPE);

        // hardcoded for now
        if (type.equals("Senator")) {
            TextView locationView = (TextView) findViewById(R.id.detailedLocation);
            locationView.setText("California");
        }

        ImageView imageView = (ImageView) findViewById(R.id.detailedImageView);
        imageView.setImageResource(image);
        TextView partyView = (TextView) findViewById(R.id.detailedParty);
        partyView.setText(party);
        TextView typeView = (TextView) findViewById(R.id.detailedType);
        typeView.setText(type);

        ListView listViewCommittees = (ListView) findViewById(R.id.list_committees);
        ListView listViewBills = (ListView) findViewById(R.id.list_bills);
        // hardcoded sample committees and bills
        String[] sample_committees = {"Appropriations Committee", "Budget Committee", "Cats Committee", "Dogs Committee"};
        String[] sample_bills = {"10/08/2015 - Improving Access to Mental Health Act",
                "06/10/2015 - Pathways Out of Poverty Act of 2015",
                "05/15/2015 - Better Lives for Cats and Dogs Act"};
        ArrayAdapter committees = new ArrayAdapter(this, android.R.layout.simple_list_item_1, sample_committees);
        ArrayAdapter bills = new ArrayAdapter(this, android.R.layout.simple_list_item_1, sample_bills);
        listViewCommittees.setAdapter(committees);
        listViewBills.setAdapter(bills);
    }
}
