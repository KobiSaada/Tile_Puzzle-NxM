/**
 * This class is an abstract class which repressents all the algorithms which use 
 * a heuristic method
 *
 */
public abstract class HeuristicAlgorithm extends Algorithm {

	public HeuristicAlgorithm(State startPuzzle, boolean withTime,boolean withopen) {
		super(startPuzzle, withTime,withopen);
		// TODO Auto-generated constructor stub
	}

	@Override
	public abstract Output solve();

	/**
	 * This is the heuristic algorithm
	 * @return h(givenPuzzle)
	 */
	protected int h(State puzzle) {
		String[][] puzzleMatrix = puzzle.get_puzzle();
		int ans = 0;
		for (int i = 0; i < puzzleMatrix.length; i++) {
			for (int j = 0; j < puzzleMatrix[0].length; j++) {
				if(!puzzleMatrix[i][j].equals(State.EMPTY_CELL))
					ans += manhattanDistnace(Integer.parseInt(puzzleMatrix[i][j]), i, j, puzzleMatrix.length, puzzleMatrix[0].length);
			}
		}

		return ans;
	}

	private int manhattanDistnace(int num, int i, int j, int numOfRows, int numOfColumns) {
		int correct_i = num % numOfColumns == 0 ? num / numOfColumns-1 : num / numOfColumns;
		int correct_j = num % numOfRows == 0 ? num / numOfRows-1 : num / numOfRows;
		return Math.abs(i - correct_i) + Math.abs(j - correct_j);
	}

}