/**
 * this class represents a state in the colored tile puzzle game
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class State {
	public static final String EMPTY_CELL = "_";

	private enum Move {
		LEFT,
		UP,
		RIGHT,
		DOWN
	}

	private int _numOfRows;
	private int _numOfColumns;
	private  int[][] GOAL=new int[_numOfRows][_numOfColumns];
	private String[][] _puzzle;
	private Pair<Integer,Integer> IndexFor1Space;
	private Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> _indexesOfEmptySpaces;
	public int CountSpace;
	private boolean _needToHashash;
	private int _puzzleMatrixHash;

	public State(String[][] puzzle) {
		_puzzle = copyMatrix(puzzle);
		_numOfRows = _puzzle.length;
		_numOfColumns = _puzzle[0].length;
		CountS(puzzle);
		updateEmptySpacesIndexes();
		_needToHashash = true;
		_puzzleMatrixHash = 0;
		this.GOAL=new int[puzzle.length][puzzle[0].length];

	}

	public boolean isonplace(int i,int j){
		setGoal();
		if(_puzzle[i][j].equals(EMPTY_CELL))
			return false;
		int ko=Integer.parseInt(_puzzle[i][j]);
				if(ko==GOAL[i][j])
		return true;
				return false;
			}



	public void CountS(String[][] p){
		for(int i=0;i<p.length;i++){
			for (int j=0;j<p[i].length;j++){
				if(p[i][j].equals(EMPTY_CELL))
					CountSpace++;
			}
		}
		this.CountSpace=CountSpace;

	}

	public State(State other) {
		_puzzle = copyMatrix(other._puzzle);
		_numOfRows = other._numOfRows;
		_numOfColumns = other._numOfColumns;
		this.CountSpace=other.CountSpace;
		if(CountSpace==2) {
			Pair left = new Pair(other._indexesOfEmptySpaces.getLeft().getLeft(), other._indexesOfEmptySpaces.getLeft().getRight());
			Pair right = new Pair(other._indexesOfEmptySpaces.getRight().getLeft(), other._indexesOfEmptySpaces.getRight().getRight());
			_indexesOfEmptySpaces = new Pair(left, right);
		}
		if (CountSpace==1) {
			IndexFor1Space=new Pair(other.IndexFor1Space.getLeft(), other.IndexFor1Space.getRight());
		}
		_needToHashash = false;
		_puzzleMatrixHash = other._puzzleMatrixHash;
	}
	private void setGoal(){
		this.GOAL=new int[_numOfRows][_numOfColumns];
		int index = 1;
		for (int i = 0; i < _numOfRows; i++) {
			for (int j = 0; j < _numOfColumns; j++) {
				if(index<=_numOfRows*_numOfColumns-1)
				GOAL[i][j] = index++;

			}
		}
	}

	/**
	 * This function checks if *this* is a goal(sorted)
	 * @return
	 */
	public boolean isGoal() {
		int counter = 1;
		int lastNum =CountSpace==2? _numOfRows * _numOfColumns - 2:_numOfRows * _numOfColumns - 1;

		if (CountSpace==2) {

			if (_indexesOfEmptySpaces.getLeft().getLeft() != _numOfRows - 1 ||
					_indexesOfEmptySpaces.getRight().getLeft() != _numOfRows - 1 ||
					_indexesOfEmptySpaces.getRight().getRight() != _numOfColumns - 2 ||
					_indexesOfEmptySpaces.getLeft().getRight() != _numOfColumns - 1)
				return false;
		}
		for (int i = 0; i < _numOfRows; i++) {
			for (int j = 0; j < _numOfColumns; j++) {
				if((_puzzle[i][j].matches("^[0-9]+$") && Integer.parseInt(_puzzle[i][j]) != counter) || _puzzle[i][j].equals(State.EMPTY_CELL))
					return false;
				if(counter == lastNum)
					return true;
				counter++;
			}
		}
		return false;
	}

	/**
	 * This function checks if *this* can do a given operation
	 * @param operation
	 * @return true if can false otherwise
	 */
	public boolean canOperate(byte operation) {


			switch (operation) {


			case Operator.TWO_LEFT:
				//make sure two empty spaces are on same column
				if(_indexesOfEmptySpaces.getRight().getRight() != _indexesOfEmptySpaces.getLeft().getRight())
					return false;
				//make sure two empty spaces aren't on the right column
				if(_indexesOfEmptySpaces.getRight().getRight() == _numOfColumns-1)
					return false;
				//make sure two empty spaces are close to each other
				if(Math.abs(_indexesOfEmptySpaces.getRight().getLeft() - _indexesOfEmptySpaces.getLeft().getLeft()) != 1)
					return false;
				return true;

			case Operator.TWO_UP:
				//make sure two empty spaces are on same row
				if(_indexesOfEmptySpaces.getRight().getLeft() != _indexesOfEmptySpaces.getLeft().getLeft())
					return false;
				//make sure two empty spaces aren't on the bottom row
				if(_indexesOfEmptySpaces.getRight().getLeft() == _numOfRows-1)
					return false;
				//make sure two empty spaces are close to each other
				if(Math.abs(_indexesOfEmptySpaces.getRight().getRight() - _indexesOfEmptySpaces.getLeft().getRight()) != 1)
					return false;
				return true;

			case Operator.TWO_RIGHT:
				//make sure two empty spaces are on same column
				if(_indexesOfEmptySpaces.getRight().getRight() != _indexesOfEmptySpaces.getLeft().getRight())
					return false;
				//make sure two empty spaces aren't on the left colomn
				if(_indexesOfEmptySpaces.getRight().getRight() == 0)
					return false;
				//make sure two empty spaces are close to each other
				if(Math.abs(_indexesOfEmptySpaces.getRight().getLeft() - _indexesOfEmptySpaces.getLeft().getLeft()) != 1)
					return false;
				return true;

			case Operator.TWO_DOWN:
				//make sure two empty spaces are on same row
				if(_indexesOfEmptySpaces.getRight().getLeft() != _indexesOfEmptySpaces.getLeft().getLeft())
					return false;
				//make sure two empty spaces aren't on the top row
				if(_indexesOfEmptySpaces.getRight().getLeft() == 0)
					return false;
				//make sure two empty spaces are close to each other
				if(Math.abs(_indexesOfEmptySpaces.getRight().getRight() - _indexesOfEmptySpaces.getLeft().getRight()) != 1)
					return false;
				return true;

			case Operator.SINGLE_LEFT_FIRST:
				return canMove(_indexesOfEmptySpaces.getRight(), Move.LEFT);

			case Operator.SINGLE_UP_FIRST:
				return canMove(_indexesOfEmptySpaces.getRight(), Move.UP);

			case Operator.SINGLE_RIGHT_FIRST:
				return canMove(_indexesOfEmptySpaces.getRight(), Move.RIGHT);

			case Operator.SINGLE_DOWN_FIRST:
				return canMove(_indexesOfEmptySpaces.getRight(), Move.DOWN);

			case Operator.SINGLE_LEFT_SECCOND:
				return canMove(_indexesOfEmptySpaces.getLeft(), Move.LEFT);

			case Operator.SINGLE_UP_SECCOND:
				return canMove(_indexesOfEmptySpaces.getLeft(), Move.UP);

			case Operator.SINGLE_RIGHT_SECCOND:
				return canMove(_indexesOfEmptySpaces.getLeft(), Move.RIGHT);

			case Operator.SINGLE_DOWN_SECCOND:
				return canMove(_indexesOfEmptySpaces.getLeft(), Move.DOWN);

			default:
				break;
		}

		return false;
	}



	public boolean canOperateFor1(byte operation) {

		switch (operation) {

			case Operator.SINGLE_LEFT_FIRST:
				return canMoveFor1(IndexFor1Space, Move.LEFT);

			case Operator.SINGLE_UP_FIRST:
				return canMoveFor1(IndexFor1Space, Move.UP);

			case Operator.SINGLE_RIGHT_FIRST:
				return canMoveFor1(IndexFor1Space, Move.RIGHT);

			case Operator.SINGLE_DOWN_FIRST:
				return canMoveFor1(IndexFor1Space, Move.DOWN);

			default:
				break;
		}

		return false;
	}


	private void swap(int i1, int j1, int i2, int j2) throws IndexOutOfBoundsException {
		String temp = _puzzle[i1][j1];
		_puzzle[i1][j1] = _puzzle[i2][j2];
		_puzzle[i2][j2] = temp;
		_needToHashash = true;
	}

	/**
	 * This function does an operation(TWO_LEFT, TWO_RIGHT etc.) on *this* puzzle
	 * This function does not check if the operation is possible or not. There is a separate function for it
	 * @param operation
	 * @return
	 */
	public String operate(byte operation) throws IndexOutOfBoundsException {

		String output = "";
		int i_emptyRight = _indexesOfEmptySpaces.getRight().getLeft();
		int j_emptyRight = _indexesOfEmptySpaces.getRight().getRight();
		int i_emptyLeft = _indexesOfEmptySpaces.getLeft().getLeft();
		int j_emptyLeft = _indexesOfEmptySpaces.getLeft().getRight();

		switch (operation) {
			case Operator.TWO_LEFT:
				swap(i_emptyRight, j_emptyRight, i_emptyRight, j_emptyRight+1);
				swap(i_emptyLeft, j_emptyLeft, i_emptyLeft, j_emptyLeft+1);
				output += _puzzle[i_emptyRight][j_emptyRight] + "&" + _puzzle[i_emptyLeft][j_emptyLeft] + "L";
				_indexesOfEmptySpaces.getRight().setRight(j_emptyRight+1);
				_indexesOfEmptySpaces.getLeft().setRight(j_emptyLeft+1);
				break;

			case Operator.TWO_UP:
				swap(i_emptyRight, j_emptyRight, i_emptyRight+1, j_emptyRight);
				swap(i_emptyLeft, j_emptyLeft, i_emptyLeft+1, j_emptyLeft);
				output += _puzzle[i_emptyRight][j_emptyRight] + "&" + _puzzle[i_emptyLeft][j_emptyLeft] + "U";
				_indexesOfEmptySpaces.getRight().setLeft(i_emptyRight+1);
				_indexesOfEmptySpaces.getLeft().setLeft(i_emptyLeft+1);
				break;

			case Operator.TWO_RIGHT:
				swap(i_emptyRight, j_emptyRight, i_emptyRight, j_emptyRight-1);
				swap(i_emptyLeft, j_emptyLeft, i_emptyLeft, j_emptyLeft-1);
				output += _puzzle[i_emptyRight][j_emptyRight] + "&" + _puzzle[i_emptyLeft][j_emptyLeft] + "R";
				_indexesOfEmptySpaces.getRight().setRight(j_emptyRight-1);
				_indexesOfEmptySpaces.getLeft().setRight(j_emptyLeft-1);
				break;
			case Operator.TWO_DOWN:
				swap(i_emptyRight, j_emptyRight, i_emptyRight-1, j_emptyRight);
				swap(i_emptyLeft, j_emptyLeft, i_emptyLeft-1, j_emptyLeft);
				output += _puzzle[i_emptyRight][j_emptyRight] + "&" + _puzzle[i_emptyLeft][j_emptyLeft] + "D";
				_indexesOfEmptySpaces.getRight().setLeft(i_emptyRight-1);
				_indexesOfEmptySpaces.getLeft().setLeft(i_emptyLeft-1);
				break;
			case Operator.SINGLE_LEFT_FIRST:
				swap(i_emptyRight, j_emptyRight, i_emptyRight, j_emptyRight+1);
				output += _puzzle[i_emptyRight][j_emptyRight] + "L";
				_indexesOfEmptySpaces.getRight().setRight(j_emptyRight+1);
				break;
			case Operator.SINGLE_UP_FIRST:
				swap(i_emptyRight, j_emptyRight, i_emptyRight+1, j_emptyRight);
				output += _puzzle[i_emptyRight][j_emptyRight] + "U";
				_indexesOfEmptySpaces.getRight().setLeft(i_emptyRight+1);
				break;
			case Operator.SINGLE_RIGHT_FIRST:
				swap(i_emptyRight, j_emptyRight, i_emptyRight, j_emptyRight-1);
				output += _puzzle[i_emptyRight][j_emptyRight] + "R";
				_indexesOfEmptySpaces.getRight().setRight(j_emptyRight-1);
				break;
			case Operator.SINGLE_DOWN_FIRST:
				swap(i_emptyRight, j_emptyRight, i_emptyRight-1, j_emptyRight);
				output += _puzzle[i_emptyRight][j_emptyRight] + "D";
				_indexesOfEmptySpaces.getRight().setLeft(i_emptyRight-1);
				break;
			case Operator.SINGLE_LEFT_SECCOND:
				swap(i_emptyLeft, j_emptyLeft, i_emptyLeft, j_emptyLeft+1);
				output += _puzzle[i_emptyLeft][j_emptyLeft] + "L";
				_indexesOfEmptySpaces.getLeft().setRight(j_emptyLeft+1);
				break;
			case Operator.SINGLE_UP_SECCOND:
				swap(i_emptyLeft, j_emptyLeft, i_emptyLeft+1, j_emptyLeft);
				output += _puzzle[i_emptyLeft][j_emptyLeft] + "U";
				_indexesOfEmptySpaces.getLeft().setLeft(i_emptyLeft+1);
				break;
			case Operator.SINGLE_RIGHT_SECCOND:
				swap(i_emptyLeft, j_emptyLeft, i_emptyLeft, j_emptyLeft-1);
				output += _puzzle[i_emptyLeft][j_emptyLeft] + "R";
				_indexesOfEmptySpaces.getLeft().setRight(j_emptyLeft-1);
				break;
			case Operator.SINGLE_DOWN_SECCOND:
				swap(i_emptyLeft, j_emptyLeft, i_emptyLeft-1, j_emptyLeft);
				output += _puzzle[i_emptyLeft][j_emptyLeft] + "D";
				_indexesOfEmptySpaces.getLeft().setLeft(i_emptyLeft-1);
				break;

			default:
				break;
		}

		sortEmptySpaces();
		_needToHashash = true;
		return output;
	}


	public String operateFor1(byte operation) throws IndexOutOfBoundsException {
updateEmptySpacesIndexes();
		String output = "";
		int i_emptyRight = IndexFor1Space.getRight();
		int j_emptyLeft =  IndexFor1Space.getLeft();


		switch (operation) {

			case Operator.SINGLE_LEFT_FIRST:
				swap(i_emptyRight, j_emptyLeft, i_emptyRight, j_emptyLeft+1);
				output += _puzzle[i_emptyRight][j_emptyLeft] + "L";
				IndexFor1Space.setRight(j_emptyLeft+1);
				break;
			case Operator.SINGLE_UP_FIRST:
				swap(i_emptyRight, j_emptyLeft, i_emptyRight+1, j_emptyLeft);
				output += _puzzle[i_emptyRight][j_emptyLeft] + "U";
				IndexFor1Space.setLeft(i_emptyRight+1);
				break;
			case Operator.SINGLE_RIGHT_FIRST:
				swap(i_emptyRight, j_emptyLeft, i_emptyRight, j_emptyLeft-1);
				output += _puzzle[i_emptyRight][j_emptyLeft] + "R";
				IndexFor1Space.setRight(j_emptyLeft-1);
				break;
			case Operator.SINGLE_DOWN_FIRST:
				swap(i_emptyRight, j_emptyLeft, i_emptyRight-1, j_emptyLeft);
				output += _puzzle[i_emptyRight][j_emptyLeft] + "D";
				IndexFor1Space.setRight(i_emptyRight-1);
				break;

			default:
				break;
		}

		sortEmptySpaces();
		_needToHashash = true;
		return output;
	}





	//A function to update the current empty spaces in the puzzle
	private void updateEmptySpacesIndexes() {
		if(CountSpace==2) {
			if (_indexesOfEmptySpaces == null)
				_indexesOfEmptySpaces = new Pair();
			for (int i = 0; i < _numOfRows; i++) {
				for (int j = 0; j < _numOfColumns; j++) {
					if (_puzzle[i][j].equals(State.EMPTY_CELL)) {
						if (_indexesOfEmptySpaces.getRight() == null)
							_indexesOfEmptySpaces.setRight(new Pair<Integer, Integer>(i, j));
						else
							_indexesOfEmptySpaces.setLeft(new Pair<Integer, Integer>(i, j));
					}
				}
			}
		}
		if (CountSpace==1){
			if (IndexFor1Space== null)
				IndexFor1Space= new Pair();

			for (int i = 0; i < _numOfRows; i++) {
				for (int j = 0; j < _numOfColumns; j++) {
					if (_puzzle[i][j].equals(State.EMPTY_CELL)) {
							IndexFor1Space.setRight(i);
							IndexFor1Space.setLeft(j);
					}
					}
			}

					}


		}


	//This function checks if you can move(LEFT, UP, RIGHT or DOWN) to an emptySpace
	private boolean canMove(Pair<Integer, Integer> emptySpace, Move move) {
		updateEmptySpacesIndexes();
		switch (move) {
			case LEFT:
				//check that empty space isent on the most right column
				if(emptySpace.getRight()== _numOfColumns-1 || _puzzle[emptySpace.getLeft()][emptySpace.getRight()+1].equals(EMPTY_CELL))
					return false;
				return true;

			case UP:
				//check that empty space isent on the most bottom row
				if(emptySpace.getLeft() == _numOfRows-1 || _puzzle[emptySpace.getLeft()+1][emptySpace.getRight()].equals(EMPTY_CELL))
					return false;
				return true;

			case RIGHT:
				//check that empty space isent on the most left column
				if(emptySpace.getRight() == 0 || _puzzle[emptySpace.getLeft()][emptySpace.getRight()-1].equals(EMPTY_CELL))
					return false;
				return true;

			case DOWN:
				//check that empty space isent on the most top row
				if(emptySpace.getLeft() == 0 || _puzzle[emptySpace.getLeft()-1][emptySpace.getRight()].equals(EMPTY_CELL))
					return false;
				return true;

			default:
				break;
		}

		return false;
	}

	private boolean canMoveFor1(Pair<Integer, Integer> emptySpace, Move move) {
updateEmptySpacesIndexes();
		switch (move) {
			case LEFT:
				//check that empty space isent on the most right column

				if(emptySpace.getLeft() == _numOfColumns-1 )
					return false;
				return true;

			case UP:
				//check that empty space isent on the most bottom row
				if(emptySpace.getRight() == _numOfRows-1)
					return false;
				return true;

			case RIGHT:
				//check that empty space isent on the most left column
				if(emptySpace.getLeft()==0 )
					return false;
				return true;

			case DOWN:
				//check that empty space isent on the most top row
				if(emptySpace.getRight() == 0 )
				return false;
			return true;

			default:
				break;
		}

		return false;
	}




	//A function to swap right and left empty spaces
	private void swapEmptyPlaces() {
		Pair<Integer, Integer> temp = _indexesOfEmptySpaces.getRight();
		_indexesOfEmptySpaces.setRight(_indexesOfEmptySpaces.getLeft());
		_indexesOfEmptySpaces.setLeft(temp);
	}

	//A function to sort the empty places in the pair such that the first is the most top left
	private void sortEmptySpaces() {
		if (this.CountSpace==2) {
			if (_indexesOfEmptySpaces.getRight().getLeft() > _indexesOfEmptySpaces.getLeft().getLeft())
				swapEmptyPlaces();
			else if (_indexesOfEmptySpaces.getRight().getLeft() == _indexesOfEmptySpaces.getLeft().getLeft())
				if (_indexesOfEmptySpaces.getRight().getRight() > _indexesOfEmptySpaces.getLeft().getRight())
					swapEmptyPlaces();
		}
	}

	public String[][] get_puzzle() {
		return _puzzle;
	}

	private String[][] copyMatrix(String[][] mat) {
		String[][] rtn = new String[mat.length][mat[0].length];
		for (int i = 0; i < rtn.length; i++) {
			for (int j = 0; j < rtn[0].length; j++) {
				rtn[i][j] = mat[i][j];
			}
		}
		return rtn;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_indexesOfEmptySpaces == null) ? 0 : _indexesOfEmptySpaces.hashCode());
		result = prime * result + _numOfColumns;
		result = prime * result + _numOfRows;
		result = prime * result + create2DArrayHashCode();
		return result;
	}


	private int create2DArrayHashCode() {
		if (_needToHashash) {
			int hash = 0;
			for (int i = 0; i < _numOfRows; i++)
				for (int j = 0; j < _numOfColumns; j++)
					hash += _puzzle[i][j].hashCode() * (i + _numOfColumns * j);

			_puzzleMatrixHash = hash;
			_needToHashash = false;
		}

		return _puzzleMatrixHash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (this.CountSpace==2&&_indexesOfEmptySpaces == null) {
			if (other._indexesOfEmptySpaces != null)
				return false;
		} else if ( _numOfColumns != other._numOfColumns)
			return false;
		if (_numOfRows != other._numOfRows)
			return false;
		if (this.CountSpace==2&&!_indexesOfEmptySpaces.equals(other._indexesOfEmptySpaces))
			return false;
		if (!Arrays.deepEquals(_puzzle, other._puzzle))
			return false;
		return true;
	}



}
