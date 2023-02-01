import java.util.*; 
import java.lang.*; 
import java.io.*; 
  
public class Bipartite {

    static int A;

    // constructor of this class, A is the number of vertices of the graph
    public Bipartite (int A){
        this.A = A;
    }
     
  
    //returns true if the graph is Bipartite 
    boolean isBipartite(int B[][],int c)  
    { 

        //Create an array to store all the colors of the vertices
        //the number '-1' indicates that the vertex assigned to that index has not yet been colored,
        //the number '1' indicates that the first color has benn assigned to that vertex,
        // the number '0' indicates that the second color has benn assigned to that vertex.
        int arr[] = new int[A]; 
        for (int i=0; i<A; ++i) 
            arr[i] = -1; 
  
        // Assign first color to vertex 1 
        arr[c] = 1; 
  
        // this list is queue of vertex numbers  
        // enqueue source vertex for breadth first search
        LinkedList<Integer>d = new LinkedList<Integer>(); 
        d.add(c); 
  
        // while loop that ends when there are no more vertices in queue
        while (d.size() != 0) 
        { 
            int u = d.poll(); 
  
            // Return false if there is a self-loop  
            if (B[u][u] == 1) 
                return false;  
  
            // Find all non-colored vertices 
            for (int v=0; v<A; ++v) 
            { 
                if (B[u][v]==1 && arr[v]==-1) 
                { 
                    arr[v] = 1-arr[u]; 
                    d.add(v); 
                } 
                else if (B[u][v]==1 && arr[v]==arr[u]) {
                    return false; 
                }
            } 
        } 
        // return true if the graph is bipartite
        return true; 
    } 
  
    public boolean checkCycleGraph(int[][] vertices) { //checks whether a graph has a cyclical structure (each vertex has only 2 edges)

        int count = 0;  //variable which will count how many edges a vertex has
        for (int i = 0; i < vertices.length; i++) { //we loop through each vertex
  
            count = 0;  //set the count equal to 0 each time we move to a new vertex
            for (int j = 0; j < vertices.length; j++) {   //go through the edges which this vertex has
                if (vertices[i][j] == 1) {  //if there is a 1, it means there exists an edge
                    count++;  //if there exists a edge, increase the edge count
                }
            }
            if (count == 2) { //if there are only 2 edges for a vertex, this vertex fits the requirements
                continue; 
            }
            else {    //if there aren't only 2 edges for a vertex, there is no cyclical structure
                return false;
            }
        }
        return true;
    }
} 
