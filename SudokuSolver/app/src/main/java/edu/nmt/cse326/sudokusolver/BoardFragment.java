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


import com.google.android.gms.plus.PlusOneButton;

/*

This fragment will contain the 9x9 board representing the puzzle


*/
public class BoardFragment extends Fragment {

    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup parent,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board, parent, false);

        GridView gridview = (GridView) view.findViewById(R.id.frag_grid);

        String[] test = {"1", "2", "3", "4", "5"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), R.layout.spinner_cell, test);

        gridview.setAdapter(adapter);


        return view;
    }



}
