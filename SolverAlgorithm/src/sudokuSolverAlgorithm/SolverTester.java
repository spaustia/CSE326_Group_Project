
package sudokuSolverAlgorithm;

import java.util.Scanner;

public class SolverTester
{

	public static void main(String[] args)
	{
		System.out.println("Sudoku Solver Algorithm Tester");

		System.out.println("Enter puzzle. Use 0 for unknown, 1-9 for values:");

		int[][] input = new int[9][9];
		Scanner keyboard = new Scanner(System.in);
		for (int x = 0; x < 9; x++)
		{
			String in = keyboard.nextLine();
			while (in.length() != 9)
			{
				System.out.println("Wrong number of numbers given, please try again.");
				in = keyboard.nextLine();
			}
			for (int y = 0; y < 9; y++)
			{
				int a = Character.getNumericValue(in.charAt(y));
				input[x][y] = a;
			}
		}

		// Instantiate the solver, and solve the puzzle.
		SudokuSolver s = new SudokuSolver(input);
		int[][] solution = s.solve();

		System.out.println("Solution:");
		for (int x = 0; x < 9; x++)
		{
			for (int y = 0; y < 9; y++)
			{
				System.out.print(solution[x][y]);
			}
			System.out.println();
		}

	}

}
