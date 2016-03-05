package io.github.kathyyliang.represent;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomFragment extends CardFragment {

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View watchRepresentative = inflater.inflate(R.layout.custom_fragment, container, false);

        Bundle bundle = getArguments();

        String imageName = bundle.getString("IMAGE_NAME");
        Resources resources = getResources();
        int image = resources.getIdentifier(imageName, "drawable", "io.github.kathyyliang.represent");
        //LinearLayout linearLayout = (LinearLayout) watchRepresentative.findViewById(R.id.custom_fragment);
        //linearLayout.setBackgroundResource(image);
        ImageView imageView = (ImageView) watchRepresentative.findViewById(R.id.watch_image);
        imageView.setImageResource(image);

        String name = bundle.getString("NAME");
        String type = bundle.getString("TYPE");
        String party = bundle.getString("PARTY");
        TextView nameView = (TextView) watchRepresentative.findViewById(R.id.watch_name);
        TextView typeView = (TextView) watchRepresentative.findViewById(R.id.watch_type);
        TextView partyView = (TextView) watchRepresentative.findViewById(R.id.watch_party);
        nameView.setText(name);
        typeView.setText(type);
        partyView.setText(party);

        return watchRepresentative;
    }
}
