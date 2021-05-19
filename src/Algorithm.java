
public abstract class Algorithm   {

	protected Tile _startPuzzleNode;
	protected long _startTimeInNannoSecconds;
	protected long _endTimeInNannoSecconds;
	protected int _numOfNodesCreated;
	protected int _cost;
	protected String _path;
	protected boolean _withTime;
	protected boolean withOpen;
	protected String _Goal;

	public Algorithm(State startPuzzle, boolean withTime,boolean withOpen) {
		this._startPuzzleNode = new Tile(startPuzzle);
		this._startTimeInNannoSecconds = 0;
		this._endTimeInNannoSecconds = 0;
		this._numOfNodesCreated = 0;
		this._cost = 0;
		this._path = "no path";
		this._withTime = withTime;
		this.withOpen=withOpen;
		this._Goal="";
	}

	public abstract Output solve();

	//return path from goal to root
	protected void getPathFromGoal(Tile goal) {
		_path = "";
		Tile current = goal;
		while(true) {
			if(current.get_parent() == null)
				break;
			if(_path.isEmpty())
				_path = current.get_operateCreatorString();
			else
				_path = current.get_operateCreatorString() + "-" + _path;
			current = current.get_parent();
		}

	}
	
	//generate output object from *this* vars
	protected Output generateOutput() {
		Output out = null;
		long time = _endTimeInNannoSecconds - _startTimeInNannoSecconds;

		if(_withTime&&withOpen)
			//long time = _endTimeInNannoSecconds - _startTimeInNannoSecconds;
			out = new Output(_path, _numOfNodesCreated, _cost,time, _Goal);

	      if (withOpen&&!_withTime)
			out = new Output(_path, _numOfNodesCreated, _cost,_Goal);
	     if (_withTime&&!withOpen)
			out = new Output(_path, _numOfNodesCreated, _cost,time);
	     if (!withOpen&&!_withTime)
	        out = new Output(_path, _numOfNodesCreated, _cost);

		return out;
	}
}
