import java.util.*;

public class BFS_Search extends Algorithm {

	public BFS_Search(State startPuzzle, boolean withTime, Boolean withopen) {
		super(startPuzzle, withTime,withopen);
	}

	@Override
	public Output solve() {
		boolean breakFlag = false;
		if(_withTime)
			_startTimeInNannoSecconds = System.nanoTime();
		if(_startPuzzleNode.get_data().isGoal()) {
			_path = "";
			if(_withTime)
				_endTimeInNannoSecconds = System.nanoTime();
			return generateOutput();
		}
		_numOfNodesCreated=1;
		Queue<Tile> q = new LinkedList<>();
		Hashtable<State, Boolean> openList = new Hashtable<>();
		Hashtable<State, Boolean> closedList = new Hashtable<>();

		q.add(_startPuzzleNode);
		openList.put(_startPuzzleNode.get_data(), true);

		while(!q.isEmpty()) {
			if(withOpen)
				System.out.println("***************** Open List: **********************\n"+openList+"\n");
			Tile n = q.poll();
			openList.remove(n.get_data());
			_numOfNodesCreated++;
			closedList.put(n.get_data(), true);
			Tile currentChild = n.generateChild();
			_numOfNodesCreated++;
			while(currentChild != null) {
				if(!closedList.containsKey(currentChild.get_data()) && !openList.containsKey(currentChild.get_data())) {
					if(currentChild.get_data().isGoal()) {
						getPathFromGoal(currentChild);
						_cost = currentChild.get_nodeCost();
						_Goal=currentChild.get_data().toString();
						breakFlag = true;
						break;
					}
					q.add(currentChild);
					openList.put(currentChild.get_data(), true);
				}
				currentChild = n.generateChild();
			}
			if(breakFlag)
				break;
		}
		if(_withTime)
			_endTimeInNannoSecconds = System.nanoTime();

		return generateOutput();
	}

}
