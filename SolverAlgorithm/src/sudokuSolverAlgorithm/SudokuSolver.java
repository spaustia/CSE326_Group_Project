
package sudokuSolverAlgorithm;

public class SudokuSolver
{
	private int[][] input; // Stores the input
	private int[][] solution; // Stores the solution
	private boolean[][][] canbe; // Stores the values that can be here.

	/**
	 * Creates a new instance of the solver.
	 * 
	 * @param input 9x9 integer array consisting of the input. Unknown values should be filled out to 0.
	 */
	public SudokuSolver(int[][] input)
	{
		this.input = input;
	}

	/**
	 * Solves the sudoku puzzle given the input specified in the constructor. Returns NULL and prints out problem on error.
	 * 
	 * @return 9x9 integer array with solution, NULL on error.
	 */
	public int[][] solve()
	{
		// First, check the size of the input.
		if (input.length != 9)
		{
			System.out.println("There should be 9 rows, but we were given " + input.length);
			return null;
		}
		for (int i = 0; i < 9; i++)
		{
			int[] a = input[i];
			if (a.length != 9)
			{
				System.out.println("On row " + i + "there should be 9 columns, but we were given " + a.length);
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

		// Make sure the puzzle is still solvable
		if (isImpossible() == true)
		{
			System.out.println("Puzzle is unsolvable!");
			return null;
		}

		// Check to see if we solved it already.
		if (isSolved())
			return solution;

		// Not solved, time for solution spanning tree.

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

		// Should be one of the entries in the can be array
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
		boolean clean;
		int numPossibilities = 0;
		int num = 0, i = 0;
		int xc = 0, yc = 0, x = 0, y = 0;
		do
		{
			clean = true; // Used to see if we need to go back through and check again.

			// First, check for squares that can be only one number.
			for (x = 0; x < 9; x++)
			{
				for (y = 0; y < 9; y++)
				{
					// Check to see if we need to look at this spot.
					if (solution[x][y] == 0)
					{
						// Check for just one possibility
						numPossibilities = 0;
						num = 0;
						for (i = 0; i < 9; i++)
						{
							if (canbe[x][y][i] == true)
							{
								numPossibilities++;
								num = i + 1;
							}
						}
						if (numPossibilities == 1)
						{
							// Only one possibility
							mark(x, y, num);
							clean = false;
						}
					}
				}
			}
			// Next, check for numbers that can only be in one place.
			for (i = 0; i < 9; i++)
			{
				// Check for every row
				for (x = 0; x < 9; x++)
				{
					numPossibilities = 0;
					for (y = 0; y < 9; y++)
					{
						if (canbe[x][y][i] == true)
						{
							numPossibilities++;
							num = y;
						}
					}
					if (numPossibilities == 1)
					{
						// Only one spot for this number on the row.
						mark(x, num, i + 1);
						clean = false;
					}
				}

				// Check for every column
				for (y = 0; y < 9; y++)
				{
					numPossibilities = 0;
					for (x = 0; x < 9; x++)
					{
						if (canbe[x][y][i])
						{
							numPossibilities++;
							num = x;
						}
					}
					if (numPossibilities == 1)
					{
						// Only one spot for this number on the column.
						mark(num, y, i + 1);
						clean = false;
					}
				}
				// Check for every box
				for (int xoffset = 0; xoffset < 9; xoffset += 3)
				{
					for (int yoffset = 0; yoffset < 9; yoffset += 3)
					{
						numPossibilities = 0;
						xc = 0;
						yc = 0;
						for (x = 0; x < 3; x++)
						{
							for (y = 0; y < 3; y++)
							{
								if (canbe[x + xoffset][y + yoffset][i] == true)
								{
									numPossibilities++;
									xc = x + xoffset;
									yc = y + yoffset;
								}
							}
						}
						if (numPossibilities == 1)
						{
							// Only one spot for this number in the box.
							mark(xc, yc, i + 1);
							clean = false;
						}
					}
				}
			}
			// Finally, check for puzzle linearity
			clean = (clean & checkForLinearity());

		}
		while (clean != true);
	}

	/**
	 * This implements an advanced solution algorithm, marking certain boxes impossible due to puzzle constraints on linearity.
	 * 
	 * @return true if nothing was marked, false if progress was made
	 */
	private boolean checkForLinearity()
	{
		boolean clean = true;
		boolean[] a = new boolean[3];
		int x = 0, y = 0, i = 0, num = 0, numFound = 0;

		for (num = 0; num < 9; num++)
		{
			// First, check for all possibilities for the row in the same box.
			for (x = 0; x < 9; x++)
			{
				// Per Row

				// Clear the array
				for (i = 0; i < 3; i++)
					a[i] = false;
				numFound = 0;

				for (y = 0; y < 9; y++)
				{
					if (canbe[x][y][num] == true)
					{
						// Mark the box
						a[y / 3] = true;
					}
				}
				// Check if all the row possibilities are in the same box.
				int byoffset = 0;
				for (i = 0; i < 3; i++)
				{
					if (a[i] == true)
					{
						numFound++;
						byoffset = i;
					}
				}
				if (numFound == 1)
				{
					// All row possibilities are in the same box.
					// Mark all other squares in the box as not possible.
					int bxoffset = x / 3;
					for (int bx = 0; bx == 3; bx++)
					{
						for (int by = 0; by == 3; by++)
						{
							if (canbe[bx + (bxoffset * 3)][by + (byoffset * 3)][num] == true)
							{
								clean = false;
								canbe[bx + (bxoffset * 3)][by + (byoffset * 3)][num] = false;
							}
						}
					}
				}
			}

			// Next, check for all possibilities for the column in the same box.
			for (y = 0; y < 9; y++)
			{
				// Per column
				// Clear the array
				for (i = 0; i < 3; i++)
					a[i] = false;
				numFound = 0;

				for (x = 0; x < 9; x++)
				{
					if (canbe[x][y][num] == true)
					{
						// Mark the box
						a[x / 3] = true;
					}
				}
				// Check if all the row possibilities are in the same box.
				int bxoffset = 0;
				for (i = 0; i < 3; i++)
				{
					if (a[i] == true)
					{
						numFound++;
						bxoffset = i;
					}
				}
				if (numFound == 1)
				{
					// All row possibilities are in the same box.
					// Mark all other squares in the box as not possible.
					int byoffset = y / 3;
					for (int by = 0; by == 3; by++)
					{
						for (int bx = 0; bx == 3; bx++)
						{
							if (canbe[bx + (bxoffset * 3)][by + (byoffset * 3)][num] == true)
							{
								clean = false;
								canbe[bx + (bxoffset * 3)][by + (byoffset * 3)][num] = false;
							}
						}
					}
				}

			}
			// Next, check for all possibilities for the same box in the same row.

			// Next, check for all possibilities for the same box in the same column.
		}

		return clean;
	}

	/**
	 * Checks the solution for impossibility to solve.
	 * 
	 * @return true if puzzle is impossible, false otherwise.
	 */
	private boolean isImpossible()
	{
		int x, y, i, numPossibilities;
		for (x = 0; x < 9; x++)
		{
			for (y = 0; y < 9; y++)
			{
				// Check to see if we need to look at this spot.
				if (solution[x][y] == 0)
				{
					// We do, so make sure every square has at least one possibility
					numPossibilities = 0;
					for (i = 0; i < 9; i++)
					{
						if (canbe[x][y][i] == true)
						{
							numPossibilities++;
						}
					}
					if (numPossibilities == 0)
					{
						// No possibilities for this square
						System.out.println("Can't solve, because " + x + "," + y + " has no possibilties!");
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Checks to see if the puzzle is solved
	 * 
	 * @return true if puzzle is complete, false otherwise.
	 */
	private boolean isSolved()
	{
		for (int x = 0; x < 9; x++)
		{
			for (int y = 0; y < 9; y++)
			{
				// Check to see if we need to fill in this spot
				if (solution[x][y] == 0)
				{
					// We do, so puzzle isn't solved.
					return false;
				}
			}
		}
		// Everything is filled in.
		return true;
	}
}
