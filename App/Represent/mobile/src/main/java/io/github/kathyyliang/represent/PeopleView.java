package io.github.kathyyliang.represent;

import android.content.Context;
import android.content.Intent;
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

public class PeopleView extends AppCompatActivity {
    public final static String IMAGE = "io.github.kathyyliang.represent.IMAGE";
    public final static String NAME = "io.github.kathyyliang.represent.NAME";
    public final static String PARTY = "io.github.kathyyliang.represent.PARTY";
    public final static String TYPE = "io.github.kathyyliang.represent.TYPE";
    public final static String NAMES = "io.github.kathyyliang.represent.NAME";
    public final static String PARTIES = "io.github.kathyyliang.represent.PARTY";
    public final static String TYPES = "io.github.kathyyliang.represent.TYPE";
    public final static String IMAGE_NAMES = "io.github.kathyyliang.represent.IMAGE_NAMES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_view);

        Intent intent = getIntent();
        String title = intent.getStringExtra(MainActivity.TITLE);
        setTitle(title);

        android.widget.ListView listView = (android.widget.ListView) findViewById(R.id.listView);
        //currently hardcoded
        String[] names = {"Barbara Boxer", "Dianne Feinstein", "Barbara Lee"};
        String[] parties = {"Democrat", "Democrat", "Democrat"};
        String[] types = {"Senator", "Senator", "Representative"};
        String[] tweets = {"In the 70+ year history of the @UN, there has never been a female Secretary-General. Isn’t it about time?", "Spoke on the Senate floor urging Republicans to consider president’s nominee to the Supreme Court. Watch: #SCOTUS", "The #ACA has provided health insurance for 20 million. That means lives saved, bankruptcies averted & families kept whole. #ThanksObama"};
        String[] emails = {""};
        String[] websites = {""};
        int[] images = {R.drawable.barbara_boxer_120, R.drawable.dianne_feinstein_120, R.drawable.barbara_lee_120};
        String[] image_names = {"barbara_boxer_120", "dianne_feinstein_120", "barbara_lee_120"};

        RepresentAdapterSimple adapter = new RepresentAdapterSimple(this, names, parties, types, tweets, emails, websites, images);
        listView.setAdapter(adapter);

        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
        sendIntent.putExtra(TYPES, types);
        sendIntent.putExtra(NAMES, names);
        sendIntent.putExtra(PARTIES, parties);
        sendIntent.putExtra(IMAGE_NAMES, image_names);
        startService(sendIntent);
    }

    public class RepresentAdapterSimple extends BaseAdapter {

        private Context context;
        public String[] names;
        public String[] parties;
        public String[] types;
        public String[] tweets;
        public String[] emails;
        public String[] websites;
        public int[] images;

        public RepresentAdapterSimple(Context context, String[] names, String[] parties,
                                      String[] types, String[] tweets, String[] emails,
                                      String[] websites, int[] images) {
            this.context = context;
            this.names = names;
            this.parties = parties;
            this.types = types;
            this.tweets = tweets;
            this.emails = emails;
            this.websites = websites;
            this.images = images;
        }

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return names[position];
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

            imageView.setImageResource(images[position]);
            imageView.setTag(images[position]);
            nameView.setText(names[position]);
            partyView.setText(parties[position]);
            typeView.setText(types[position]);
            tweetView.setText(tweets[position]);

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
                    intent.putExtra(IMAGE, (int) imageView.getTag());
                    intent.putExtra(NAME, nameView.getText());
                    intent.putExtra(PARTY, partyView.getText());
                    intent.putExtra(TYPE, typeView.getText());
                    startActivity(intent);
                }
            });

            return representative;
        }

    }

    /*public class RepresentAdapter extends BaseAdapter {

        public ArrayList<Representative> list;

        public RepresentAdapter(Context context, ArrayList<Representative> list) {

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            return view;
        }

    }*/

}
