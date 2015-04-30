
package edu.nmt.cse326.sudokusolver;

import android.graphics.Color;
import android.util.Log;
import android.widget.Spinner;
import android.content.Context;
import android.widget.TextView;

import java.io.*;

/**
 * Created by steve on 3/18/15.
 */
public class Puzzle
{

	private int[][] cells;
	private static Puzzle instance = null;
    public BoardFragment board;

    /**
     * Gets the puzzle instance.
     *
     * @return A reference to the puzzle
     */
	public static Puzzle getInstance()
	{
		if (instance == null)
		{
			instance = new Puzzle();
		}
		return instance;
	}

    /**
     * Initializes the int array to 0.
     */
	protected Puzzle()
	{
		cells = new int[9][9];

		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				cells[i][j] = 0;
			}

		}
	}

    /**
     * Sets the value of a cell.
     *
     * @param pos The position of the cell to set.
     * @param val The new value of the cell.
     */
	public void setCell(int pos, int val)
	{

		if (val < 0 || val > 9)
		{
			Log.d("Puzzle", "Attempt to set puzzle cell to out of range value: " + val);
			val = 0;
		}
		cells[pos / 9][pos % 9] = val;
	}

    /**
     * Sets the value in the puzzle and updates the board.
     *
     * @param pos The position of the cell to set.
     * @param val The new value of the cell.
     */
	public void setCellAndSpinner(int pos, int val)
	{

		if (val < 0 || val > 9)
		{
			Log.d("Puzzle", "Attempt to set puzzle cell to out of range value: " + val);
			val = 0;
    }
		cells[pos / 9][pos % 9] = val;

        /* I don't know why this happens, but sometimes the spinner with the appropriate tag isn't found */
        if ((Spinner)board.getView().findViewWithTag(pos) != null) {
            ((Spinner)board.getView().findViewWithTag(pos)).setSelection(val);

        }

    }

    /**
     * Get the value of a cell in the puzzle.
     *
     * @param pos The position of the cell. Between 0 and 80.
     * @return The value at the position.
     */
	public int getCell(int pos)
	{
		return cells[pos / 9][pos % 9];
	}

    /**
     * Sets all cells in the puzzle to 0.
     */
	public void clear()
	{
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				if (cells[i][j] != 0)
				{
					cells[i][j] = 0;
                    ((Spinner)board.getView().findViewWithTag(i*9+j)).setSelection(0);
				}
			}
		}
	}

    /**
     * Solves the current puzzle.
     * Fills the puzzle array with the solution and updates the board.
     */
	public void solve()
	{

		Solver s = new Solver(cells);
		int [][] solution = s.solve();
        if (solution == null)
            return;

//        Update the spinners
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                setCellAndSpinner(i * 9 + j, solution[i][j]);
            }
        }
	}

    /**
     * Saves the content of the puzzle to a file.
     *
     * @param filename The file to store the puzzle in.
     */
	public void saveFile(String filename)
	{
		OutputStream out;
        if (board == null)
            return;
        Context context = board.getActivity().getBaseContext();

		try
		{
			File file = new File(context.getFilesDir(), filename);
			out = new BufferedOutputStream(new FileOutputStream(file));
			for (int i = 0; i < 81; i++)
			{
				out.write(getCell(i));
			}
			out.close();

		}
		catch (FileNotFoundException e)
		{ }
		catch (IOException e)
		{ }
	}

    /**
     * Fill the puzzle with the contents of a file.
     *
     * @param filename The name of the file to load.
     */
	public void loadFile(String filename)
	{
		InputStream in;
        if (board == null)
            return;
        Context context = board.getActivity().getBaseContext();

		try
		{
			File file = new File(context.getFilesDir(), filename);
			in = new BufferedInputStream(new FileInputStream(file));
			for (int i = 0; i < 81; i++)
			{
				setCellAndSpinner(i, in.read());
			}
			in.close();

		}
		catch (FileNotFoundException e)
		{ }
		catch (IOException e)
		{ }
	}

    /**
     * Gets the contents of the puzzle as a string.
     *
     * @return The puzzle string.
     */
	public String toString()
	{
		String str = "";

		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				str += "cell[" + String.valueOf(i) + "][" + String.valueOf(j) + "] = ";
				str += String.valueOf(cells[i][j]);
				str += "\n";
			}

		}

		return str;
	}
}
