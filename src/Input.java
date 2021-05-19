/**
 * This class represents an input from the input.txt
 *
 */
public class Input {
	
	private String _algorithm;
	private boolean withTime;
	private boolean withopen;
	private State _startPuzzle;

	
	public Input(String _algorithm, boolean withTime, State _startPuzzle,Boolean withopen) {
		this._algorithm = _algorithm;
		this.withTime = withTime;
		this._startPuzzle = new State(_startPuzzle);
		this.withopen=withopen;

	}

	public String get_algorithm() {
		return _algorithm;
	}

	public boolean isWithTime() {
		return withTime;
	}
	public boolean isWithopen(){return withopen;}

	public State get_startPuzzle() {
		return new State(_startPuzzle);
	}


	
	
	
	
	
}
