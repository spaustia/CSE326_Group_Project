package edu.nmt.cse326.sudokusolver;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Spinner;

import java.util.List;

/*

This fragment will contain the 9x9 board representing the puzzle

*/
public class MenuFragment extends Fragment {

    private List<Spinner> mSpinners;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup parent,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, parent, false);


        return view;
    }

}