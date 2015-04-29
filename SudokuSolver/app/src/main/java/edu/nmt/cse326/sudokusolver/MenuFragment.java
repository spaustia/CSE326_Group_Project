
package edu.nmt.cse326.sudokusolver;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Button;

import java.util.List;

/*
 * This fragment will contain the 9x9 board representing the puzzle
 */
public class MenuFragment extends Fragment
{

	private List <Spinner> mSpinners;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_menu, parent, false);

		Button clearButton = (Button)view.findViewById(R.id.clear_button);
		clearButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Puzzle.getInstance().clear();
			}
		});

		Button solveButton = (Button)view.findViewById(R.id.solve_button);
		solveButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Puzzle.getInstance().solve();
			}
		});

		Button saveButton = (Button)view.findViewById(R.id.save_button);
		saveButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
                SaveDialogFragment fragment = new SaveDialogFragment();
                fragment.show(getActivity().getFragmentManager(), "Save");
			}
		});

		Button loadButton = (Button)view.findViewById(R.id.load_button);
		loadButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
                LoadDialogFragment fragment = new LoadDialogFragment();
                fragment.show(getActivity().getFragmentManager(), "Load");
			}
		});

		return view;
	}

}
