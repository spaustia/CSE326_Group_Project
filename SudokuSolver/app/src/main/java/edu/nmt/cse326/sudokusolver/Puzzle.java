package edu.nmt.cse326.sudokusolver;

import android.util.Log;

/**
 * Created by steve on 3/18/15.
 */
public class Puzzle {

    private int[][] cells;
    public static Puzzle sPuzzle;

    public Puzzle(){
        cells = new int[9][9];

        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                cells[i][j] = 0;
            }

        }

        sPuzzle = this;

    }

    public void setCell(int pos, int val){
        cells[pos/9][pos % 9] = val;
    }

    public String toString(){
        String str = "";

        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                str += "cell[" + String.valueOf(i) + "][" + String.valueOf(j) + "] = ";
                str += String.valueOf(cells[i][j]);
                str += "\n";
            }

        }

        return str;
    }






}