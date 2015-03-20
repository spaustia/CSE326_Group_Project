package edu.nmt.cse326.sudokusolver;

import java.util.ArrayList;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.view.LayoutInflater;


/**
 * Created by steve on 3/18/15.
 */

public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private Spinner[] mSpinners;
    private ArrayAdapter<CharSequence> adapter;
    private AdapterView.OnItemSelectedListener mListener;

    public GridViewAdapter(Context c,Spinner[] spinnerArray, AdapterView.OnItemSelectedListener listener) {
        mContext = c;
        mSpinners = spinnerArray;
        mListener = listener;
        createSpinAdapter();
    }

    public int getCount() {
        return 81;
    }

    public Object getItem(int position) {
        return mSpinners[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        final Spinner mSpinner;

        if (convertView == null) {  // if it's not recycled, initialize some attributes
            //mSpinner = new Spinner(mContext);
            final LayoutInflater inflater = LayoutInflater.from(mContext);
            mSpinner = (Spinner)inflater.inflate(R.layout.spinner, parent, false);
//            mSpinner.setMinimumHeight(mSpinner.getWidth());

            mSpinner.setTag(position);

            mSpinner.setAdapter(adapter);

            mSpinners[position] = mSpinner;

            mSpinner.setOnItemSelectedListener(mListener);

        } else {
            mSpinner = (Spinner) convertView;
        }

       mSpinner.getLayoutParams().height = (parent.getRootView().findViewById(R.id.gridview).getWidth()/9);

        mSpinner.setSelection(Puzzle.getInstance().getCell(position));

        return mSpinner;
    }

    // Setup and return an ArrayAdapter for a spinner
    private void createSpinAdapter(){

        adapter = ArrayAdapter.createFromResource(mContext,
                R.array.spinner_array, R.layout.textview);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

}