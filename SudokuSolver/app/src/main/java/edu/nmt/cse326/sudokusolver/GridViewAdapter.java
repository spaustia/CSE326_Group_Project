package edu.nmt.cse326.sudokusolver;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

/**
 * Created by steve on 3/18/15.
*/
public class GridViewAdapter extends BaseAdapter {
    private Context mContext;

    public GridViewAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return 81;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        Spinner mSpinner;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            mSpinner = new Spinner(mContext);
//
//            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);
        } else {
            mSpinner = (Spinner) convertView;
        }

        return mSpinner;
    }
}
