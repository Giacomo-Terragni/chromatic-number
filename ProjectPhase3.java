import java.io.*; 
import java.util.*; 

public class ProjectPhase3 {
	public static void main(String[] args) throws IOException{

		//creating a scanner to read the file name
		//Scanner scanner = new Scanner(System.in);
		//System.out.println("name of the file:");
		String originalFile = args[0];
		System.out.println(" ");

		//adjacency matrix
		ReadGraph3 four = new ReadGraph3(originalFile);
		four.generateMatrix(); //creating the adjacency matrix for the given graph

		//start structures stopwatch 
		Timer tim1 = new Timer();
		tim1.ExecutionTimer();

		//structures
		// if a structure is detected the stopwatch will stop and the chromatic number will be printed 
		if (four.findStructures() == true){  
			//stop structures timer
			tim1.end();
			//print timer result
			System.out.println("Time for the chromatic number: " + tim1.duration()+ " ms");
		}
		//no structures
		// if no structures are dectected then the program will continue and find lower and upper bound
		else{
			//graph transformation
			//transforming the layout of the given graph to satisfy the input requirements of the lower bound algorithm 
			TransformGraph two = new TransformGraph(originalFile);
			// na e of the new text file
			String newFile = two.transform();

			//start lower bound stopwatch
			Timer tim2 = new Timer();
			tim2.ExecutionTimer();

			//lower bound algorithm 
			FindLower three = new FindLower();
			//lower bound value 
			int lowerBound = three.run(newFile, args);

			//stop lower bound stopwatch
			tim2.end();

			//start upper bound timer
			Timer tim3 = new Timer();
			tim3.ExecutionTimer();

			//upper bound algorithm
			int upperBound = four.upperBound();

			//stop upper bound timer
			tim3.end();

			if (lowerBound == upperBound){
				//print chromatic number
				System.out.println("CHROMATIC NUMBER = " + lowerBound);

				//print the timer result for the chromatic number 
				if (tim2.duration() > tim3.duration()){
					System.out.println("Time for the chromatic number: " + tim2.duration() + " ms");
				}
				else{
					System.out.println("Time for the chromatic number: " + tim3.duration() + " ms");
				}
			}
			else{
				//print lower and upper bound results
				System.out.println("NEW BEST LOWER BOUND = " + lowerBound);
				System.out.println("NEW BEST UPPER BOUND = " + upperBound);

				//print timer results
				System.out.println("Time for the lower bound: " + tim2.duration() + " ms");
				if (tim3.duration() == 0.0){
					System.out.println("Time for the upper bound: " + (tim3.duration() + 2) + " ms");
				}
				else{
					System.out.println("Time for the upper bound: " + tim3.duration() + " ms");
				} 
			}    
	    }
    }
}
