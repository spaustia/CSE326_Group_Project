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

/**
 * Created by steve on 3/18/15.
 */

public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private Spinner[] mSpinners;
    private ArrayAdapter<CharSequence> adapter;

    public GridViewAdapter(Context c,Spinner[] spinnerArray) {
        mContext = c;
        mSpinners = spinnerArray;
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
            mSpinner = new Spinner(mContext);
            mSpinner.setTag(position);
            //mSpinner.setSelection(Puzzle.getInstance().getCell(position));

            mSpinner.setAdapter(adapter);

            mSpinners[position] = mSpinner;

            //Log.d("Puzzle", "pos["+position+"] = "+Puzzle.getInstance().getCell(position));

            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

//                public int check = 0;
//
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    String val = String.valueOf(mSpinner.getSelectedItem());
//                    if (check <= 1) {
//                        Log.d("Puzzle", "Initial:Spinner["+mSpinner.getTag() + "] "+Puzzle.getInstance().getCell((int)mSpinner.getTag())+ "-> "+Integer.parseInt(val));
//                        return;
//                    }
                    if(Integer.parseInt(val) > -1) {


                        Log.d("Puzzle", "Spinner["+mSpinner.getTag() + "] "+Puzzle.getInstance().getCell((int)mSpinner.getTag())+ "-> "+Integer.parseInt(val));
                        Puzzle.getInstance().setCell( (int) mSpinner.getTag(), Integer.parseInt(val));

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });

        } else {
            mSpinner = (Spinner) convertView;
            //mSpinner.setSelection(Puzzle.getInstance().getCell(position));
        }


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