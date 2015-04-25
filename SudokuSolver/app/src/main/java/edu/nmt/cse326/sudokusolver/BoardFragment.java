
package edu.nmt.cse326.sudokusolver;

import java.util.ArrayList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

/*
 * This fragment will contain the 9x9 board representing the puzzle
 */
public class BoardFragment extends Fragment
{

	private Spinner[] mSpinners;
	private AdapterView.OnItemSelectedListener spinnerListener;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mSpinners = new Spinner[81];

		spinnerListener = new AdapterView.OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView <?> parentView, View selectedItemView, int position, long id)
			{
				Spinner spinner = (Spinner)parentView;
				String val = String.valueOf(spinner.getSelectedItem());

				int i_val = 0;
				try
				{
					i_val = Integer.parseInt(val);
				}
				catch (Exception e)
				{

				}
				Puzzle.getInstance().setCell((int)spinner.getTag(), i_val);
			}

			@Override
			public void onNothingSelected(AdapterView <?> parentView)
			{
				// your code here
			}

		};

		Puzzle.getInstance().spinners = mSpinners;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_board, parent, false);

		GridView gridview = (GridView)view.findViewById(R.id.gridview);

		GridViewAdapter adapter = new GridViewAdapter(getActivity(), mSpinners, spinnerListener);
		gridview.setAdapter(adapter);

		return view;
	}

}
