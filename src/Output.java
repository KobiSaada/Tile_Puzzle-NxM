import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This is an object which represeents the output of an algorithm
 */
public class Output {

	private String _path;
	private int _numOfNodesCreated;
	private int _cost;
	private long _timeInNanoseconds;
	private String _Goal;
	
	public Output(String _path, int _numOfNodesCreated, int _cost, long _timeInNanoseconds,String g) {
		this._path = _path;
		this._numOfNodesCreated = _numOfNodesCreated;
		this._cost = _cost;
		this._timeInNanoseconds = _timeInNanoseconds;
		this._Goal=g;
	}
	public Output(String _path, int _numOfNodesCreated, int _cost, long _timeInNanoseconds) {
		this._path = _path;
		this._numOfNodesCreated = _numOfNodesCreated;
		this._cost = _cost;
		this._timeInNanoseconds = _timeInNanoseconds;

	}


	public Output(String _path, int _numOfNodesCreated, int _cost,String g) {
		this._path = _path;
		this._numOfNodesCreated = _numOfNodesCreated;
		this._cost = _cost;
		this._timeInNanoseconds = -1;
		this._Goal=g;
	}
	public Output(String _path, int _numOfNodesCreated, int _cost ){
		this._path = _path;
		this._numOfNodesCreated = _numOfNodesCreated;
		this._cost = _cost;
		this._timeInNanoseconds = -1;

	}



	public String getTimeInSecconds() {
		double rtn = (double) _timeInNanoseconds/1_000_000_000;
		rtn = roundToThree(rtn);
		return String.valueOf(rtn);
	}
	
	private double roundToThree(double num) {
		BigDecimal bd = new BigDecimal(Double.toString(num));
		bd = bd.setScale(3, RoundingMode.HALF_UP);
		num = bd.doubleValue();
		return bd.doubleValue();
	}

	@Override
	public String toString() {
		String rtn = "";
		rtn += _path + "\n";
		rtn += "Num: " + _numOfNodesCreated + "\n";
		rtn += "Cost: " + _cost + "\n";
		rtn += _timeInNanoseconds == -1 ? "" : getTimeInSecconds() + " seconds";
		
		return rtn;
		
	}

	public String get_path() {
		return _path;
	}

	public int get_numOfNodesCreated() {
		return _numOfNodesCreated;
	}

	public int get_cost() {
		return _cost;
	}
	public String get_Goal(){return this._Goal;}
	
	
	
	
	
	
	
}
