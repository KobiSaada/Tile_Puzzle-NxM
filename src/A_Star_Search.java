import java.util.*;

public class A_Star_Search extends HeuristicAlgorithm {
	public A_Star_Search(State startPuzzle, boolean withTime,boolean withopen) {
		super(startPuzzle, withTime,withopen);
	}

	@Override
	public Output solve() {
		if(_withTime)
			_startTimeInNannoSecconds = System.nanoTime();
		if(_startPuzzleNode.get_data().isGoal()) {
			_path = "";
			if(_withTime)
				_endTimeInNannoSecconds = System.nanoTime();
			return generateOutput();
		}

		Comparator<Tile> comparator = new A_Star_Search.puzzleNodeComperator();
		PriorityQueue<Tile> L = new PriorityQueue<>(comparator);
		Hashtable<Tile, Boolean> openList = new Hashtable<>();
		Hashtable<Tile, Boolean> closedList = new Hashtable<>();
		L.add(_startPuzzleNode);
		openList.put(_startPuzzleNode, true);

		while(!L.isEmpty()) {
			if(withOpen)
				System.out.println("***************** Open List: **********************\n"+openList+"\n");
			Tile n = L.poll();
		    openList.remove(n);
			_numOfNodesCreated++;

			if(n.get_data().isGoal()) {
				getPathFromGoal(n);
				_cost = n.get_nodeCost();
				_Goal=n.get_data().toString();
				//System.out.println(n.get_data().toString());
				break;

			}
		    closedList.put(n, true);
			Tile currentChild = n.generateChild();
			while(currentChild != null) {
				if(!closedList.containsKey(currentChild) && !openList.containsKey(currentChild)) {
					L.add(currentChild);
					openList.put(currentChild, true);
				}
				else if(openList.containsKey(currentChild)) {
					Tile tempNode = findInQueue(L, currentChild);
					if(tempNode.get_nodeCost() > currentChild.get_nodeCost()) {
						L.remove(tempNode);
						L.add(currentChild);
					}
				}
				//if (currentChild.get_parent()==null)


				currentChild = n.generateChild();
				//currentChild=new Tile(currentChild.get_data()).generateChild();
			}

		}

		if(_withTime)
			_endTimeInNannoSecconds = System.nanoTime();

		return generateOutput();
	}

	//finds a tile in priorityQueue. return null if not found
	private Tile findInQueue(PriorityQueue<Tile> L, Tile n) {
		for (Iterator iterator = L.iterator(); iterator.hasNext();) {
			Tile puzzleNode = (Tile) iterator.next();
			if(puzzleNode.equals(n)) {
				return puzzleNode;
			}
		}
		return null;
	}



	//this comperator compares 2 tile nodes by their f value
	public class puzzleNodeComperator implements Comparator<Tile> {
		@Override
		public int compare(Tile x, Tile y) {

			int xTotal = x.get_nodeCost() + h(x.get_data());
			int yTotal = y.get_nodeCost() + h(y.get_data());

			if (xTotal < yTotal) {
				return -1;
			}
			if (xTotal > yTotal) {
				return 1;
			}
			if(xTotal == yTotal) {
				if(x.get_operatorCreatorRank() < y.get_operatorCreatorRank())
					return -1;
				if(x.get_operatorCreatorRank() > y.get_operatorCreatorRank())
					return 1;

			}
			return 0;
		}
	}

}
