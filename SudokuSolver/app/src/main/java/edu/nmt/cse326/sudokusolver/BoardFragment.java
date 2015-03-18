package edu.nmt.cse326.sudokusolver;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;


import com.google.android.gms.plus.PlusOneButton;

import java.util.ArrayList;
import java.util.List;

/*

This fragment will contain the 9x9 board representing the puzzle

*/
public class BoardFragment extends Fragment {

    private List<Spinner> mSpinners;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSpinners = new ArrayList<Spinner>();

        for(int i = 0; i < 81; i++){
            mSpinners.add(i, new Spinner(getActivity()));
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup parent,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board, parent, false);

        GridView gridview = (GridView) view.findViewById(R.id.frag_grid);

        GridViewAdapter adapter = new GridViewAdapter(getActivity());

        gridview.setAdapter(adapter);


        return view;
    }



}
