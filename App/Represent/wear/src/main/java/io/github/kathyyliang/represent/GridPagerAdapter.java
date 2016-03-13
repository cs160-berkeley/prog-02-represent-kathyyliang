package io.github.kathyyliang.represent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.wearable.view.FragmentGridPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * http://developer.android.com/samples/GridViewPager/src/com.example.android.wearable.gridviewpager/CustomFragment.html
 */
public class GridPagerAdapter extends FragmentGridPagerAdapter {

    private final Context context;
    private List<Row> mRows;
    private List<Drawable> mBackgrounds;

    public GridPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;

        String message = MainActivity.EXTRAS.getString(WatchListenerService.MESSAGE);
        String[] info = message.split(";");
        ArrayList<Bundle> bundleList = new ArrayList<>();
        for (int i = 0; i < info.length; i += 4) {
            Bundle temp = new Bundle();
            temp.putString("IMAGE_URL", info[i]);
            temp.putString("TYPE", info[i+1]);
            temp.putString("NAME", info[i+2]);
            temp.putString("PARTY", info[i+3]);
            bundleList.add(temp);
        }

        mBackgrounds = new ArrayList<>();

        Row peopleRow = new Row();
        for (int j = 0; j < bundleList.size(); j++) {
            CustomFragment temp = new CustomFragment();
            Bundle bundle = bundleList.get(j);
            temp.setArguments(bundle);
            temp.setCardMarginBottom(context.getResources().getDimensionPixelSize(R.dimen.card_margin_bottom));
            //String imageName = bundle.getString("IMAGE_URL");
            //Resources resources = context.getResources();
            //int image = resources.getIdentifier(imageName, "drawable", "io.github.kathyyliang.represent");
            //mBackgrounds.add(resources.getDrawable(image, null));
            peopleRow.add(temp);
        }

        mRows = new ArrayList<>();
        mRows.add(peopleRow);
    }

    private class Row {
        final List<Fragment> columns = new ArrayList<Fragment>();

        public Row(Fragment... fragments) {
            for (Fragment f : fragments) {
                add(f);
            }
        }

        public void add(Fragment f) {
            columns.add(f);
        }

        Fragment getColumn(int i) {
            return columns.get(i);
        }

        public int getColumnCount() {
            return columns.size();
        }
    }

    @Override
    public int getColumnCount(int rowNum) {
        return mRows.get(rowNum).getColumnCount();
    }

    @Override
    public int getRowCount() {
        return mRows.size();
    }

    @Override
    public Fragment getFragment(int row, int col) {
        Row adapterRow = mRows.get(row);
        return adapterRow.getColumn(col);
    }

    /*@Override
    public Drawable getBackgroundForPage(final int row, final int column) {
        return mBackgrounds.get(column);
    }*/
}
