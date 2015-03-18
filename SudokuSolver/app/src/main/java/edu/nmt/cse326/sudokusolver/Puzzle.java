package edu.nmt.cse326.sudokusolver;

/**
 * Created by steve on 3/18/15.
 */
public class Puzzle {

    int[][] puzzle;

    public Puzzle(){

            for(int i = 0; i < 9; i++){
                for(int j = 0; j < 9; j++){
                    puzzle[i][j] = 0;
                }
            }

    }


}
