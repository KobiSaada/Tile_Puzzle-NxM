/**
 * this class is the main class which needs to actually run and solve the game.
 */
import java.io.*;


 class Ex1 {
	public static Output outs;
	public static Input input;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String path = "input.txt";
		TilePuzzle_AI game = new TilePuzzle_AI(path);
		//Input input =game.LoadGame();
	//	Output outs=game.solve(input);


			input =game.LoadGame();
			outs=game.solve(input);

		if (input.isWithopen()) {
			System.out.println("\nfile name: "+path);
	 	    System.out.println("Algorithm: "+game.getAlgo()+ "\nfirst state: \n"+game.getFirstState()+"\n***** result: *****");

			printResult();
			System.out.println("*****THE STATE GOAL***** :\n" + outs.get_Goal());
			System.out.println("the result has been saved successfully in the FIle!\ngood-bye.");
		}

		saveResult("output.txt");







	}
	public static void saveResult(String file) {
		try {
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
		//	writer.write("no path\nNum: " + outs.get_numOfNodesCreated());

			String output1 =  outs.get_path() + "\n";
			output1 += "Num: " +  outs.get_numOfNodesCreated() + "\n";
			output1 += "Cost: " +  outs.get_cost() + "\n";
			if ( input.isWithTime())
				output1 +=  outs.getTimeInSecconds() + " seconds\n";
			//output1+=outs.get_Goal();
			writer.write(output1);


			writer.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * prints the result to the screen
	 */

	public static void printResult () {

		String output1 = outs.get_path() + "\n";
		output1 += "Num: " + outs.get_numOfNodesCreated() + "\n";
		output1 += "Cost: " + outs.get_cost() + "\n";
		if (input.isWithTime())
			output1+= outs.getTimeInSecconds()+ " seconds\n";
		System.out.println(outs);
	}




}
