
package sudokuSolverAlgorithm;

public class SudokuSolver
{
	private int[][] input; // Stores the input
	private int[][] solution; // Stores the solution
	private boolean[][][] canbe; // Stores the values that can't be here.

	/**
	 * Creates a new instance of the solver.
	 * 
	 * @param input 9x9 integer array consisting of the input. Unknown values should be 0.
	 */
	public SudokuSolver(int[][] input)
	{
		this.input = input;

	}

	/**
	 * Solves the sudoku puzzle given the input specified in the constructor. Returns NULL on error.
	 * 
	 * @return 9x9 integer array with solution, NULL on error.
	 */
	public int[][] solve()
	{
		// First, check the size of the input.
		if (input.length != 9)
		{
			System.out.println("Outer array is the wrong size! Should be 9, given " + input.length);
			return null;
		}
		for (int i = 0; i < 9; i++)
		{
			int[] a = input[i];
			if (a.length != 9)
			{
				System.out.println("Inner array [" + i + "] is the wrong size! Should be 9, given " + a.length);
				return null;
			}
		}

		// Length checks out, initialize our working arrays.
		canbe = new boolean[9][9][9];
		solution = new int[9][9];
		for (int x = 0; x < 9; x++)
		{
			for (int y = 0; y < 9; y++)
			{
				for (int i = 0; i < 9; i++)
				{
					canbe[x][y][i] = true;
				}
				solution[x][y] = 0;
			}
		}

		// Next, fill out the solution array with what we're given, and clear the values in the can be array
		for (int x = 0; x < 9; x++)
		{
			int[] a = input[x];
			for (int y = 0; y < 9; y++)
			{
				int i = a[y];
				if (i != 0)
				{
					// Given a number here.
					mark(x, y, i);
				}
			}
		}

		// Next, check if there's any values that can be only one thing.
		checkForOnePossibility();
		return solution;
	}

	/**
	 * This marks a position on the board as part of the solution.
	 * 
	 * @param x x location to mark, 0-8
	 * @param y y location to mark, 0-8
	 * @param num number to fill in, 1-9
	 */
	private void mark(int x, int y, int num)
	{
		// First, check to make sure the number can be there.
		int i = num - 1;
		// Solution array should be 0
		if (solution[x][y] != 0)
		{
			System.out.println("ERROR: Attempted to mark " + x + "," + y + " as " + num + " but it was already marked as a " + solution[x][y]);
			return;
		}

		// Should be allowed.
		if (canbe[x][y][i] == false)
		{
			System.out.println("ERROR: Attempted to mark " + x + "," + y + " as " + num + " but it was already marked as can't be!");
			return;
		}

		// It's allowed, so add it to the solution array.
		solution[x][y] = num;

		// Also it can't be anything else.
		for (int j = 0; j < 9; j++)
		{
			canbe[x][y][j] = false;
		}

		// Next, clear all the can be values.
		// Row
		for (int j = 0; j < 9; j++)
		{
			canbe[x][j][i] = false;
		}
		// Column
		for (int j = 0; j < 9; j++)
		{
			canbe[j][y][i] = false;
		}
		// Finally, box

		// First, set the offset for our box variables.
		int joffset;
		int koffset;
		if (x < 3)
			joffset = 0;
		else if (x < 6)
			joffset = 3;
		else
			joffset = 6;
		if (y < 3)
			koffset = 0;
		else if (y < 6)
			koffset = 3;
		else
			koffset = 6;

		// Next, go fill in the box.
		for (int j = 0; j < 3; j++)
		{
			for (int k = 0; k < 3; k++)
			{
				canbe[j + joffset][k + koffset][i] = false;
			}
		}
		// All done.
	}

	/**
	 * Checks the solution for boxes that only have one possible value. Continues to run until it can't anymore.
	 */
	private void checkForOnePossibility()
	{
		boolean clean = true;
		do
		{
			for (int x = 0; x < 9; x++)
			{
				for (int y = 0; y < 9; y++)
				{
					// Check to see if we need to look at this spot.
					if (solution[x][y] == 0)
					{
						int numPossibilities = 0;
						int num = 0;
						for (int i = 0; i < 9; i++)
						{
							if (canbe[x][y][i] == true)
							{
								numPossibilities++;
								num = i + 1;
							}
						}
						if (numPossibilities == 1)
						{
							mark(x, y, num);
							clean = false;
						}
					}
				}
			}
		}
		while (clean != true);
	}

	/**
	 * Checks the solution for impossiblity to solve.
	 * 
	 * @return true if puzzle is impossible, false otherwise.
	 */
	private boolean isImpossible()
	{

		for (int x = 0; x < 9; x++)
		{
			for (int y = 0; y < 9; y++)
			{
				// Check to see if we need to look at this spot.
				if (solution[x][y] == 0)
				{
					int numPossibilities = 0;
					for (int i = 0; i < 9; i++)
					{
						if (canbe[x][y][i] == true)
						{
							numPossibilities++;
						}
					}
					if (numPossibilities == 0)
					{
						System.out.println("Can't solve, because " + x + "," + y + " has no possibilties!");
						return true;
					}
				}
			}
		}
		return false;
	}
}
