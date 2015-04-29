
package edu.nmt.cse326.sudokusolver;

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

public class GridViewAdapter extends BaseAdapter
{

	private Context mContext;
	private Spinner[] mSpinners;
	private ArrayAdapter <CharSequence> adapter;
	private AdapterView.OnItemSelectedListener mListener;

    /**
     * Constructor for the adapter.
     *
     * @param c The Current Context
     * @param listener The listen that should be used for spinner selection
     */
	public GridViewAdapter(Context c, AdapterView.OnItemSelectedListener listener)
	{
		mContext = c;
		mSpinners = new Spinner[81];
		mListener = listener;
		createSpinAdapter();
	}
    /**
     * Returns the total number of view objects
     *
     */
	public int getCount()
	{
		return 81;
	}

    /**
     * Return the object at position
     *
     * @param position return the item at the given position
     */
	public Object getItem(int position)
	{
		return mSpinners[position];
	}

    /**
     *  Get the id of the object at position
     *
     * @param position return the id at the given position
     */
	public long getItemId(int position)
	{
		return 0;
	}

    /**
     *  Populate and return a spinner view
     *
     * @param position return a view for the given position
     * @param convertView view to be converted
     * @param parent the ViewGroup parent child to be inserted too
     */	public View getView(int position, View convertView, ViewGroup parent)
	{
		final Spinner mSpinner;

		if (convertView == null)
		{ // if it's not recycled, initialize some attributes
			// mSpinner = new Spinner(mContext);
			final LayoutInflater inflater = LayoutInflater.from(mContext);
			mSpinner = (Spinner)inflater.inflate(R.layout.spinner, parent, false);
			setSpinnerForPosition(mSpinner, position);

			mSpinner.setTag(position);

			mSpinner.setAdapter(adapter);

			mSpinners[position] = mSpinner;

			mSpinner.setOnItemSelectedListener(mListener);

		}
		else
		{
			mSpinner = (Spinner)convertView;
			setSpinnerForPosition(mSpinner, position);
			mSpinner.setTag(position);

			mSpinner.setAdapter(adapter);

			mSpinners[position] = mSpinner;

			mSpinner.setOnItemSelectedListener(mListener);
		}

		mSpinner.getLayoutParams().height = (parent.getRootView().findViewById(R.id.gridview).getWidth() / 9);

		mSpinner.setSelection(Puzzle.getInstance().getCell(position));

		return mSpinner;
	}

    /**
     *  Set the spinner boards for clean game board
     *
     * @param position position of the spinner
     * @param spinner the spinner we are setting the border for
     */
    private void setSpinnerForPosition(Spinner spinner, int position)
	{
		if (position % 3 == 0)
		{
			if ((position / 9) % 3 == 0)
				spinner.setBackgroundResource(R.drawable.spinner_background1);
			else if ((position / 9) % 3 == 1)
				spinner.setBackgroundResource(R.drawable.spinner_background4);
			else
				spinner.setBackgroundResource(R.drawable.spinner_background7);
		}
		else if (position % 3 == 1)
		{
			if ((position / 9) % 3 == 0)
				spinner.setBackgroundResource(R.drawable.spinner_background2);
			else if ((position / 9) % 3 == 1)
				spinner.setBackgroundResource(R.drawable.spinner_background5);
			else
				spinner.setBackgroundResource(R.drawable.spinner_background8);
		}
		else
		{
			if ((position / 9) % 3 == 0)
				spinner.setBackgroundResource(R.drawable.spinner_background3);
			else if ((position / 9) % 3 == 1)
				spinner.setBackgroundResource(R.drawable.spinner_background6);
			else
				spinner.setBackgroundResource(R.drawable.spinner_background9);
		}

	}


    /**
     *  Create the adapter for the spinners
     *
     */
    private void createSpinAdapter()
	{
		adapter = ArrayAdapter.createFromResource(mContext, R.array.spinner_array, R.layout.textview);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}

}
