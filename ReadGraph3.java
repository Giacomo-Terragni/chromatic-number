import java.io.*;
import java.util.*;

class ColEdge{
	int u;
	int v;
}
		
public class ReadGraph3{
		
	public final static boolean DEBUG = true;
	public final static String COMMENT = "//";
	public static int[][] vertices;
	public static int n;
	public static String inputfile;
	public static int count = 0; //set a constant which can be altered (not final) to count the amount of colours needed.
								//want it to be seen in any method.

	public ReadGraph3 (String inputfile){
		this.inputfile = inputfile;
	} 
	public static void generateMatrix (){
			
		boolean seen[] = null;
		
		//! n is the number of vertices in the graph
		n = -1;
		
		//! m is the number of edges in the graph
		int m = -1;
		
		//! e will contain the edges of the graph
		ColEdge e[] = null;
			
		try{ 
	    	FileReader fr = new FileReader(inputfile);
	        BufferedReader br = new BufferedReader(fr);

	        String record = new String();
					
			//! THe first few lines of the file are allowed to be comments, staring with a // symbol.
			//! These comments are only allowed at the top of the file.
			
			//! -----------------------------------------
	        while ((record = br.readLine()) != null){
				if( record.startsWith("//") ) continue;
				break; // Saw a line that did not start with a comment -- time to start reading the data in!
			}
	
			if (record.startsWith("VERTICES = ")){
				n = Integer.parseInt( record.substring(11) );					
				//if(DEBUG) System.out.println(COMMENT + " Number of vertices = "+n);
			}

			seen = new boolean[n+1];		
			record = br.readLine();
			if (record.startsWith("EDGES = ")){
				m = Integer.parseInt( record.substring(8) );					
				//if(DEBUG) System.out.println(COMMENT + " Expected number of edges = "+m);
			}

			e = new ColEdge[m];	
										
			for (int d=0; d<m; d++){
				//if(DEBUG) System.out.println(COMMENT + " Reading edge "+(d+1));
				record = br.readLine();
				String data[] = record.split(" ");
				if (data.length != 2){
					//System.out.println("Error! Malformed edge line: "+record);
					System.exit(0);
				}
				e[d] = new ColEdge();
				e[d].u = Integer.parseInt(data[0]);
				e[d].v = Integer.parseInt(data[1]);

				seen[ e[d].u ] = true;
				seen[ e[d].v ] = true;
				
				//if(DEBUG) System.out.println(COMMENT + " Edge: "+ e[d].u +" "+e[d].v);
			}
									
			String surplus = br.readLine();
			if (surplus != null){
				//if( surplus.length() >= 2 ) if(DEBUG) System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '"+surplus+"'");						
			}			
		}
		catch (IOException ex){ 
	        // catch possible io errors from readLine()
		    System.out.println("Error! Problem reading file "+inputfile);
			System.exit(0);
		}

		for (int x=1; x<=n; x++ ){
			if (seen[x] == false ){
				//if(DEBUG) System.out.println(COMMENT + " Warning: vertex "+x+" didn't appear in any edge : it will be considered a disconnected vertex on its own.");
			}
		}

		vertices = new int[n][n]; 

		for (ColEdge edge: e){ 	//For every value in 
			//System.out.println(edge.u + " " + edge.v);
			vertices[edge.u-1][edge.v-1] = 1;			//establish symmetry by just flipping edge.v with edge.u
			vertices[edge.v-1][edge.u-1] = 1;			//because if 1 is related to 3, 3 is also related to 1.
		}
	}

	//method to find structures in the graph
	public boolean findStructures(){

		Bipartite one = new Bipartite(n);

		//test if the given graph is bipartite
		if (one.isBipartite(vertices, 0)){
			System.out.println("Structure found: Bipartite");
			System.out.println("CHROMATIC NUMBER = 2");
			return true;
		}
		else if (one.checkCycleGraph(vertices)) {
			System.out.println("Structure found: Circular");
			if (n%2==0) {
				System.out.println("CHROMATIC NUMBER = 2");
				return true;
			}
			else if (n%2==1) {
				System.out.println("CHROMATIC NUMBER = 3");
				return true;
			}
		}
		else{
			System.out.println("No structures found");
			
		} 	
		return false; 
	}

	public static int upperBound(){
		int[] colours = new int[n]; //list which will contain the colour values (e.g. 1 or 4) for each given vertex colours[vertex].

		assigningColours(vertices, colours, n, 0); //Calling the backtracking (recursive) method to print the colours and where they are assigned.

		return count;  //returning the upper bound which we end up with.	
	}
	//For a chosen vertex 'vertex', we check whether it is related to any given value of i (where i is from 0 to V (vertices.length)).
	//We check whether a relation exists by checking whether at a given point (e.g. vertices[5][0]) the is a 1 ('1' meaning vertex and i are related).
	public static boolean isColourAllowed(int[][] vertices, int[] colours, int vertex, int colour) {
		for (int i = 0; i<vertices.length; i++) {            			        	 
			if ((colours[i] == colour) && (vertices[vertex][i] == 1)) {
				return false;
			}
		}
		return true;
	}
	//In this method we are checking whether
	//commented lines which were used for debugging (and which can be used in the future).
	public static boolean assigningColours(int[][] vertices, int[] colours, int colour, int vertex) {
		//System.out.println(vertex + " " + vertices.length);
		if (vertex == vertices.length) {		//The base case.
			//System.out.print("Colours assigned to individual vertices: ");	//When we reach the final vertex, we enter the if loop, signalling the end of the
			//System.out.println();		
			//System.out.println(colour);			//actual colour assignment process and printing the numbers which have been assigned.
			for (int i = 0; i<colour; i++) {
				//System.out.print(colours[i] + " "); //Used to print the colour which was assigned to each given vertex.
				if (colours[i] > count) {			
					count = colours[i];		//checks whether count is reasonable or not (if the counted amount of colours is less than the value
				}							//that has been assigned to a colour (e.g. count is 3 but a vertex has colour 4, doesnt make sense))
			}
			//System.out.println(); 	//to make a new line after printing the colour value of all the vertices.
			return true;
		}
		// colours start with 1 		//We're starting with 1 just because it makes more sense to the uninitiated.
		for (int i = 1; i<=colour; i++) {
			//System.out.println("colour: " + i);
			if (isColourAllowed(vertices, colours, vertex, i)) {	//Referring to method isColourAllowed to check whether it 
			//	System.out.println("allowed");
				colours[vertex] = i;
				if (assigningColours(vertices,colours,colour,vertex+1)) { //testing the next vertex by using recursion (see vertex+1).
					return true;
				}
				colours[vertex] = 1; //If the vertex isn't assigned to a relationship we can give it the first colour (disconnected vertex).
			}
		}
		return false; 
	}
}
