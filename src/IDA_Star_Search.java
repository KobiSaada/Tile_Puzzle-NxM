import java.util.*;

public class IDA_Star_Search extends HeuristicAlgorithm {

	public IDA_Star_Search(State startPuzzle, boolean withTime,Boolean withopen) {
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
		Stack<Tile> L = new Stack<>();
		Hashtable<Tile, Tile> H = new Hashtable<>();
		Hashtable<Tile, Boolean> outMarker = new Hashtable<>();
		int t = h(_startPuzzleNode.get_data());
		int minF;

		outerloop:
		while(t != Integer.MAX_VALUE) {
			minF = Integer.MAX_VALUE;
			L.add(_startPuzzleNode);
			H.put(_startPuzzleNode, _startPuzzleNode);
			outMarker.put(_startPuzzleNode, false);
			while(!L.isEmpty()) {
				if(withOpen)
					System.out.println("***************** Open List: **********************\n"+outMarker);
				Tile n = L.pop();
				if(outMarker.get(n))
					H.remove(n);
				else {
					outMarker.put(n, true);
					_numOfNodesCreated++;
					L.add(n);
					Tile currentChild = n.generateChild();
					while(currentChild != null) {
						if(!outMarker.containsKey(currentChild))
							outMarker.put(currentChild, false);
						if(f(currentChild) > t) {
							minF = Math.min(minF, f(currentChild));
							currentChild = n.generateChild();
							continue;
						}
						if(H.containsKey(currentChild)) {
							if(outMarker.get(currentChild)) {
								currentChild = n.generateChild();
								continue;
							}
							else {
								if(f(H.get(currentChild)) > f(currentChild)) {
									L.remove(currentChild);
									H.remove(currentChild);
								}
								else {
									currentChild = n.generateChild();
									continue;
								}
							}
						}
						if(currentChild.get_data().isGoal()) {
							getPathFromGoal(currentChild);
							_cost = currentChild.get_nodeCost();
							break outerloop;
						}

						L.add(currentChild);
						H.put(currentChild, currentChild);
						currentChild = n.generateChild();
					} //child generator loop
				}
			}//end while L != empty
			_startPuzzleNode = new Tile(_startPuzzleNode.get_data());
			outMarker.clear();
			t = minF;
		}//end while t != infinity

		if(_withTime)
			_endTimeInNannoSecconds = System.nanoTime();
		return generateOutput();
	}

	private int f(Tile n) {
		return n.get_nodeCost() + h(n.get_data());
	}

}
