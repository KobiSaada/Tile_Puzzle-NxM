import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;


public class DFID_Search extends Algorithm {

	public DFID_Search(State startPuzzle, boolean withTime, boolean withOpen) {
		super(startPuzzle, withTime, withOpen);
	}

	private class Result {

		protected boolean cutoff;
		protected Tile node;

		Result(boolean cutoff, Tile node) {
			this.cutoff = cutoff;
			this.node = node;
		}
	}


	@Override
	public Output solve() {
		HashMap<Tile, Boolean> visited = new HashMap<>();
		if(_withTime)
			_startTimeInNannoSecconds = System.nanoTime();
		if(_startPuzzleNode.get_data().isGoal()) {
			_path = "";
			if(_withTime)
				_endTimeInNannoSecconds = System.nanoTime();
			return generateOutput();
		}
		for (int i = 1; i < Integer.MAX_VALUE; i++) {
			Pair<Tile, Boolean> currentAns = DFS(_startPuzzleNode, i, visited);
			_numOfNodesCreated++;

			if(currentAns.getLeft() != null) { //if found goal
				_cost = currentAns.getLeft().get_nodeCost();
				getPathFromGoal(currentAns.getLeft());
				break;
			}
			else if(!currentAns.getRight()) {//if no path
				break;
			}
			_startPuzzleNode = new Tile(_startPuzzleNode.get_data());
			visited = new HashMap<>();
		}
		if(_withTime)
			_endTimeInNannoSecconds = System.nanoTime();
		return generateOutput();
	}

	private Pair<Tile, Boolean> DFS(Tile node, int depth, HashMap<Tile, Boolean> visited) {
		visited.put(node, true);
		if (withOpen)
			System.out.println("***************** Open List: **********************\n"+visited+"\n");
		if(depth == 0) {
			visited.remove(node);
			_numOfNodesCreated++;
			if(node.get_data().isGoal())
				return new Pair<Tile, Boolean>(node, true);
			else {
				return new Pair<Tile, Boolean>(null, true);
			}
		}

		boolean anyRemaining = false;
		Tile currentChild = node.generateChild();
		while(currentChild != null) {
			if(visited.containsKey(currentChild)) {
				currentChild = node.generateChild();
				continue;
			}
			Pair<Tile, Boolean> rtn = DFS(currentChild, depth-1, visited);
			if(rtn.getLeft() != null) {
				rtn.setRight(true);
				return rtn;
			}
			if(rtn.getRight())
				anyRemaining = true;
			currentChild = node.generateChild();
		}
		visited.remove(node);
		return new Pair<Tile, Boolean>(null, anyRemaining);
	}

}














