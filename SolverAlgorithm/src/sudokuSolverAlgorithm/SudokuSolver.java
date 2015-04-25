
package sudokuSolverAlgorithm;

public class SudokuSolver
{
	private int[][] input; // Stores the input
	private int[][] solution; // Stores the solution
	private boolean[][][] canbe; // Stores the values that can be here.
	private int[][] testInput;

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

		while (isSolved() == false)
		{
			// Next, check if there's any values that can be only one thing.
			checkForOnePossibility();

			// Make sure the puzzle is still solvable
			if (isImpossible())
			{
				System.out.println("Puzzle is unsolvable!");
				return null;
			}
			// Check to see if we solved it already.
			if (isSolved())
				return solution;

			// Not solved, time for advanced algorithm.
			System.out.println("Trying advanced linearity algorithm.");
			if (checkForLinearity() == false)
				checkForOnePossibility();

			if (isSolved())
				return solution;

			// Still not solved. Time to make a guess (AKA spanning tree)
			System.out.println("Still not solved. Making a guess.");
			int guessX = -1, guessY = -1, guessI = -1;
			testInput = new int[9][9];
			for (int x = 0; x < 9; x++)
			{
				for (int y = 0; y < 9; y++)
				{
					testInput[x][y] = solution[x][y];
					if (guessI == -1)
					{
						for (int i = 0; i < 9; i++)
						{
							if (canbe[x][y][i] == true)
							{
								testInput[x][y] = i + 1;
								guessX = x;
								guessY = y;
								guessI = i;
								System.out.println("Guessing that " + x + "," + y + " is " + (i + 1) + ".");
								break;
							}
						}
					}
				}
			}
			SudokuSolver n = new SudokuSolver(testInput);
			testInput = n.solve();
			if (testInput == null)
			{
				// Wrong guess
				System.out.println("Bad guess.");
				canbe[guessX][guessY][guessI] = false;
			}
			else
				return testInput;

		}
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
		int x = 0, y = 0, num = 0, numFound = 0;
		int bxoffset = 0, byoffset = 0;

		for (num = 0; num < 9; num++)
		{
			// First, check for all possibilities for the row in the same box.
			for (x = 0; x < 9; x++)
			{
				// Per Row

				// Clear the array
				for (int i = 0; i < 3; i++)
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
				byoffset = 0;
				for (int i = 0; i < 3; i++)
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
					bxoffset = x / 3;
					for (int i = 0; i < 3; i++)
					{
						for (int j = 0; j < 3; j++)
						{
							if (bxoffset * 3 + i != x)
							{
								if (canbe[bxoffset * 3 + i][byoffset * 3 + j][num] == true)
								{
									clean = false;
									canbe[bxoffset * 3 + i][byoffset * 3 + j][num] = false;
								}
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
				for (int i = 0; i < 3; i++)
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
				// Check if all the column possibilities are in the same box.
				bxoffset = 0;
				for (int i = 0; i < 3; i++)
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
					byoffset = y / 3;
					for (int i = 0; i < 3; i++)
					{
						for (int j = 0; j < 3; j++)
						{
							if (byoffset * 3 + j != y)
							{
								if (canbe[bxoffset * 3 + i][byoffset * 3 + j][num] == true)
								{
									clean = false;
									canbe[bxoffset * 3 + i][byoffset * 3 + j][num] = false;
								}
							}
						}
					}
				}

			}
			// Next, check for all possibilities for the same box in the same row.

			/*
			for (bxoffset = 0; bxoffset < 3; bxoffset++)
			{
				for (byoffset = 0; byoffset < 3; byoffset++)
				{
					// For each box
					for (i = 0; i < 3; i++)
					{
						a[i] = false;
					}
					numFound = 0;

					for (x = 0; x < 3; x++)
					{
						for (y = 0; y < 3; y++)
						{
							if (canbe[x + (bxoffset * 3)][y + (byoffset * 3)][num] == true)
							{
								// Mark the row
								a[x] = true;
							}
						}
					}

					// Determine if all on the same row.
					for (i = 0; i < 3; i++)
					{
						if (a[i] == true)
						{
							numFound++;
							bx = i;
						}
					}
					if (numFound == 1)
					{
						// All on the same row.
						for (i = 0; i < 9; i++)
						{
							if (canbe[bx + (bxoffset * 3)][i][num] == true)
							{
								clean = false;
								// TODO
							}
						}
					}

				}
			}

			// Next, check for all possibilities for the same box in the same column.
			// TODO
			*/
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
		// Check to make sure every row, column, and box has one of each number.
		for (i = 0; i < 9; i++)
		{
			// First, every row
			for (x = 0; x < 9; x++)
			{
				boolean found = false;
				for (y = 0; y < 9; y++)
				{
					if (solution[x][y] == i + 1)
					{
						// Solution has the number.
						found = true;
						break;
					}
					else
					{
						if (canbe[x][y][i] == true)
						{
							found = true;
							break;
						}
					}
				}
				if (found == false)
				{
					System.out.println("Cannot find " + i + " in row " + x + ".");
					return true;
				}
			}
			// Next, every column
			for (y = 0; y < 9; y++)
			{
				boolean found = false;
				for (x = 0; x < 9; x++)
				{
					if (solution[x][y] == i + 1)
					{
						found = true;
						break;
					}
					else
					{
						if (canbe[x][y][i] == true)
						{
							found = true;
							break;
						}
					}
				}
				if (found == false)
				{
					System.out.println("Cannot find " + i + " in column " + y + ".");
					return true;
				}
			}
			// Next, every box
			for (int xm = 0; xm < 3; xm++)
			{
				for (int ym = 0; ym < 3; ym++)
				{
					boolean found = false;
					for (x = 0; x < 3; x++)
					{
						for (y = 0; y < 3; y++)
						{
							if (solution[x + xm * 3][y + ym * 3] == i + 1)
							{
								found = true;
								break;
							}
							else
							{
								if (canbe[x + xm * 3][y + ym * 3][i] == true)
								{
									found = true;
									break;
								}
							}
						}
						if (found == true)
						{
							break;
						}
					}
					if (found == false)
					{
						System.out.println("Cannot find " + i + " in box " + xm + "," + ym + ".");
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
