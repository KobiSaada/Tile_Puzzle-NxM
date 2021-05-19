import java.util.*;

public class DFBnB_Search extends HeuristicAlgorithm {

	public DFBnB_Search(State startPuzzle, boolean withTime,boolean withOpen) {
		super(startPuzzle, withTime,withOpen);
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
		Stack<Tile> L = new Stack<>();
		Hashtable<Tile, Tile> H = new Hashtable<>();
		Hashtable<Tile, Boolean> outMarker = new Hashtable<>();
		ArrayList<Tile> N = new ArrayList<>();
		Tile currentChild;

		L.add(_startPuzzleNode);
		H.put(_startPuzzleNode, _startPuzzleNode);
		int t = Integer.MAX_VALUE;
		outMarker.put(_startPuzzleNode, false);

		while(!L.isEmpty()) {
			Tile n = L.pop();
			if (withOpen)
				System.out.println("***************** Open List: **********************\n"+H+"\n");
			if(outMarker.get(n))
				H.remove(n);//TODO: never gets here
			else {
				outMarker.put(n, true);
				_numOfNodesCreated++;
				L.push(n);
				N = createN(n, outMarker);
				N.sort(new puzzleNodeComperator()); //sort N by f
				Iterator<Tile> it = N.iterator();
				while(it.hasNext()) {
					currentChild = it.next();
					if(f(currentChild) >= t) {
						N.subList(N.indexOf(currentChild), N.size()).clear();//remove currentChild and after it from N
						break;
					}
					else if(H.containsKey(currentChild)) {
						if(outMarker.get(currentChild))
							it.remove();//TODO: never gets here
						else {
							if(f(H.get(currentChild)) <= f(currentChild))
								it.remove();
							else {
								L.remove(currentChild);//TODO: never gets here
								H.remove(currentChild);
							}

						}
					}
					else if(currentChild.get_data().isGoal()) {
						t = f(currentChild);
						getPathFromGoal(currentChild);
						_cost = currentChild.get_nodeCost();
						N.subList(N.indexOf(currentChild), N.size()).clear();//remove currentChild and after
						break;
					}

				}

				//insert N in a reverse order to L and H
				Collections.reverse(N);
				L.addAll(N);
				for (Tile puzzleNode : N) H.put(puzzleNode, puzzleNode); //insert into H

			}//end Else
		}//end while

		if(_withTime)
			_endTimeInNannoSecconds = System.nanoTime();
		return generateOutput();
	}

	private int f(Tile n) {
		return n.get_nodeCost() + h(n.get_data());
	}

	private ArrayList<Tile> createN(Tile parent, Hashtable<Tile, Boolean> outMarker) {
		Tile currentChild = parent.generateChild();
		ArrayList<Tile> rtn = new ArrayList<>();
		while(currentChild != null) {
			if(!outMarker.containsKey(currentChild))
				outMarker.put(currentChild, false);
			rtn.add(currentChild);
			currentChild = parent.generateChild();
		}

		return rtn;
	}

	//this comperator compares 2 puzzle nodes by their f value
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