package edu.nmt.cse326.sudokusolver;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;

/**
 * Created by steve on 3/18/15.
 */

public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private Puzzle mPuzzle;
    private Spinner[] mSpinners;
    private ArrayAdapter<CharSequence> adapter;

    public GridViewAdapter(Context c) {
        mContext = c;
        mSpinners = new Spinner[81];
        createSpinAdapter();
        mPuzzle = Puzzle.sPuzzle;
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
            mSpinner = new Spinner(mContext);
            mSpinner.setTag(position);

            mSpinner.setAdapter(adapter);
            mSpinners[position] = mSpinner;

            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    String val = String.valueOf(mSpinner.getSelectedItem());

                    if(Integer.parseInt(val) > -1) {


                        mPuzzle.setCell( (int) mSpinner.getTag(), Integer.parseInt(val));
                        Log.d("Puzzle", mPuzzle.toString());
                        
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });

        } else {
            mSpinner = (Spinner) convertView;
        }

        return mSpinner;
    }

    // Setup and return an ArrayAdapter for a spinner
    private void createSpinAdapter(){

        adapter = ArrayAdapter.createFromResource(mContext,
                R.array.spinner_array, R.layout.textview);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

}