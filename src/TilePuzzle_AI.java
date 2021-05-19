import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * this class deals with the input/output of the game including loading
 * the first state, running the game and returning the output of the game
 * @author oriel
 *
 */
public class TilePuzzle_AI {

	private String algo = "";
	private boolean WithTime = true, WithOpen = true;
	private State firstState;
	private Algorithm search_algo;
	private Output output;
	int CountForSpace=0;


	public String _path="";
	public BufferedReader _br;

	public TilePuzzle_AI(String path) {
		_path = new String(path);
		try {
			_br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			System.out.println("could not create InputParser Object \n");
			e.printStackTrace();
		}
	}

	public TilePuzzle_AI() {
	}

	public Input LoadGame() throws IOException {
		//File fileName = new File(file);
		// _br = new BufferedReader(new FileReader(fileName));
		String[][] puzzle ;

		String string;
		algo = _br.readLine(); // row 1 = algo
		string = _br.readLine(); // row 2 = time
		if (string!=null&&string.contains("no"))
			WithTime = false;
		string = _br.readLine(); // row 3 = open
		if (string!=null&&string.contains("no"))
			WithOpen = false;
		string = _br.readLine(); // row 4 = size
		String[] size = string.replace(" ", "_").split("x"); //NxM
		int n = Integer.parseInt(size[0]);
		int m = Integer.parseInt(size[1]);
		puzzle = new String[n][m];

		//iterate over puzzle rows in txt file
		for (int i = 0; i < n; i++) {
			string = _br.readLine();
			puzzle[i] = string.replace(" ", "_").split(",");

		}

		firstState = new State(puzzle);


		//System.out.println(firstState.toString());


		return new Input(algo,WithTime, firstState,WithOpen);
	}

	public Output solve(Input _input) {
		Algorithm algo = null;

		switch (_input.get_algorithm()) {
			case "BFS":
				algo = new BFS_Search(_input.get_startPuzzle(), _input.isWithTime(), _input.isWithopen());
				break;
			case "DFID":
				algo = new DFID_Search(_input.get_startPuzzle(), _input.isWithTime(), _input.isWithopen());
				break;
			case "A*":
				algo = new A_Star_Search(_input.get_startPuzzle(), _input.isWithTime(), _input.isWithopen());
				break;
			case "IDA*":
				algo = new IDA_Star_Search(_input.get_startPuzzle(), _input.isWithTime(), _input.isWithopen());
				break;
			case "DFBnB":
				algo = new DFBnB_Search(_input.get_startPuzzle(), _input.isWithTime(), _input.isWithopen());
				break;
			default:
				break;
		}

		Output out = algo.solve();
		return out;
	}



		/**
		 * returns the first state of the game
		 * @return
		 */

		public State getFirstState () {
			return firstState;
		}
		/**
		 * returns the algorithm that was chosen to solve this game
		 * @return
		 */

		public String getAlgo () {
			return algo;
		}
	}

