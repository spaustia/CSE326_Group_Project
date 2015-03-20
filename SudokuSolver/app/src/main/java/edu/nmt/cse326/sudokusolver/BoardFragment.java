package edu.nmt.cse326.sudokusolver;

import java.util.ArrayList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

/*

This fragment will contain the 9x9 board representing the puzzle

*/
public class BoardFragment extends Fragment {

    //private List<Spinner> mSpinners;
    private Spinner[] mSpinners;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSpinners = new Spinner[81];
        Puzzle.getInstance().spinners = mSpinners;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup parent,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board, parent, false);

        GridView gridview = (GridView) view.findViewById(R.id.gridview);

        GridViewAdapter adapter = new GridViewAdapter(getActivity(), mSpinners);
        gridview.setAdapter(adapter);


        return view;
    }

}