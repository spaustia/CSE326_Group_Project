
package edu.nmt.cse326.sudokusolver;

import android.util.Log;
import android.widget.Spinner;
import android.content.Context;
import java.io.*;

/**
 * Created by steve on 3/18/15.
 */
public class Puzzle
{

	private int[][] cells;
	public Spinner[] spinners;
	private static Puzzle instance = null;

	public static Puzzle getInstance()
	{
		if (instance == null)
		{
			instance = new Puzzle();
		}
		return instance;
	}

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

	public void setCell(int pos, int val)
	{

		if (val < 0 || val > 9)
		{
			Log.d("Puzzle", "Attempt to set puzzle cell to out of range value: " + val);
			val = 0;
		}
		cells[pos / 9][pos % 9] = val;
	}

	public void setCellAndSpinner(int pos, int val)
	{

		if (val < 0 || val > 9)
		{
			Log.d("Puzzle", "Attempt to set puzzle cell to out of range value: " + val);
			val = 0;
		}
		cells[pos / 9][pos % 9] = val;
		spinners[pos].setSelection(val);
	}

	public int getCell(int pos)
	{
		return cells[pos / 9][pos % 9];
	}

	public void clear()
	{
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				if (cells[i][j] != 0)
				{
					cells[i][j] = 0;
					spinners[i * 9 + j].setSelection(0);
				}
			}
		}
	}

	public void solve()
	{
		Solver s = new Solver(cells)
		int [][] solution = s.solve();
		cells = solution;
	}

	public void saveFile(String filename, Context context)
	{
		OutputStream out;

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
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void loadFile(String filename, Context context)
	{
		InputStream in;

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
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

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
