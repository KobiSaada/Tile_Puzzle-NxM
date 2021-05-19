import java.util.ArrayList;
import java.util.List;


public class Tile {

	private State _data;
	private List<Tile> _children;
	private Tile _parent;
	private String _operatetCreatorString;
	private int _nodeCost;
	private Operator _op;
	private int _operatorCreatorRank;

	public Tile(State _data, List<Tile> _children, Tile _parent) {
		this._data = new State(_data);
		this._children = _children;
		this._parent = _parent;
		this._operatetCreatorString = "";
		_nodeCost = 0;
		this._op = new Operator(_data);
		_operatorCreatorRank = -1;
	}

	public Tile(State _data) {
		this._data = new State(_data);
		this._children = new ArrayList<>();
		this._parent = null;
		this._operatetCreatorString = "";
		_nodeCost = 0;
		this._op = new Operator(_data);
		_operatorCreatorRank = -1;
	}

	public void set_Parent(Tile parent, String operateCreatorString, int operateCreatorCost) {
		this._parent = parent;
		this._operatetCreatorString = operateCreatorString;
		this._nodeCost = operateCreatorCost;
	}

	public void addChild(Tile child, String operateCreator, int operateCreatorCost, int operatorRank) {
		_children.add(child);
		child.set_Parent(this, operateCreator, operateCreatorCost);
		child._operatorCreatorRank = operatorRank;
	}

	public Tile generateChild() {
	State curr =new State(this._data);
		State child = _op.generateNextPuzzleState();
		while (child!=null&&child.toString().equals(curr.toString()))
		child = _op.generateNextPuzzleState();
		if ((this.get_parent() != null && this.get_parent().get_data().equals(child))) {
			child = _op.generateNextPuzzleState();
		}
		if (child == null)
			return null;
		Tile childNode = new Tile(child);
		if(!Puzzleeq(childNode)&&!_children.contains(childNode))
		addChild(childNode, _op.get_lastOperationDescription(), _nodeCost + _op.get_lastOperationCost(), _op.get_lastOperationRank());
		return childNode;
	}

	public State get_data() {
		return _data;
	}

	public List<Tile> get_children() {
		return _children;
	}

	public Tile get_parent() {
		return this._parent;
	}

	public String get_operateCreatorString() {
		return _operatetCreatorString;
	}


	public int get_nodeCost() {
		return _nodeCost;
	}

	public void set_nodeCost(int _nodeCost) {
		this._nodeCost = _nodeCost;
	}

	public void set_operateCreatorString(String _operatCreator) {
		this._operatetCreatorString = _operatCreator;
	}

	public int get_operatorCreatorRank() {
		return _operatorCreatorRank;
	}

	/**
	 * This only compares the actual puzzleState and not other vars
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_data == null) ? 0 : _data.hashCode());
		return result;
	}
	public boolean Puzzleeq(Tile n){
		for(int i=0;i<this.get_data().get_puzzle().length;i++){
			for(int j=0;j<this.get_data().get_puzzle()[i].length;j++) {
				if(this.get_data().get_puzzle()[i][j]!=n.get_data().get_puzzle()[i][j])
					return false;
			}
			}
			return true;
		}

	/**
	 * This only compares the actual puzzleState and not other vars
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (_data == null) {
			if (other._data != null)
				return false;
		} else if (!_data.equals(other._data))
			return false;
		return true;
	}



}