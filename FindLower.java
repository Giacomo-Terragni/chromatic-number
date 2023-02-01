
import java.io.*; 
import java.util.*; 

// this class outputs the lower bound of the given graph 
public class FindLower {

    // number of nodes
    int countNodes;  
    //varaible used to compare the size of the maximal cliques
    // and find the one with the higher number of vertices
    public static int terra1 = 0; 

    //list of vertex objects that stores the given graph
    ArrayList<Vertex> graph = new ArrayList<Vertex>(); 

    class Vertex implements Comparable<Vertex> {
        int a;  

        int degreeA;  
        ArrayList<Vertex> zz = new ArrayList<Vertex>();  

        public int getA() {
            return a; 
        } 

        public void setA(int a) {
            this.a = a; 
        } 

        public int getDegreeA() {
            return degreeA; 
        } 

        public void setDegreeB(int degreeA) {
            this.degreeA = degreeA; 
        } 

        public ArrayList<Vertex> getZz() {  
            return zz; 
        } 

        public void setZz(ArrayList<Vertex> zz) {
            this.zz = zz; 
        } 
        //method called from readNextGraph method 
        //with vertexV as parameter
        public void addZz(Vertex y) { 
            this.zz.add(y); 
            if (!y.getZz().contains(y)) { 
                y.getZz().add(this); 
                y.degreeA++; 
            } 
           this.degreeA++; 
        } 
        public void removeZz(Vertex y) {
            this.zz.remove(y); 
            if (y.getZz().contains(y)) { 
                y.getZz().remove(this); 
                y.degreeA--; 
            } 
            this.degreeA--; 
        } 
        @Override 
        public int compareTo(Vertex o) {
            if (this.degreeA < o.degreeA) {
                return -1; 
            } 
            if (this.degreeA > o.degreeA) {
                return 1;
            } 
            return 0; 
        } 

        public String toString() { 
            return "" + a; 
        } 
    } 
    //method called from readNextGraph method
    void initOriginalGraph() { 
        //crearing the arraylist
        graph.clear(); 
        //adding every vertex to the arraylist
        for (int i = 0; i < countNodes; i++) {
            Vertex V = new Vertex(); 
            V.setA(i); 
            graph.add(V); 
        } 
    } 

    int finalGraphCount(BufferedReader bufReader) throws Exception {

        return Integer.parseInt(bufReader.readLine()); 
    } 
    
    //method called from run method
    // reading the input 
    void readNextGraph(BufferedReader bufReader) throws Exception {
        try { 
            //casting from String to integer
            countNodes = Integer.parseInt(bufReader.readLine());
            //counting the number of edges 
            int countEdges = Integer.parseInt(bufReader.readLine());  
            initOriginalGraph(); 

            for (int l = 0; l < countEdges; l++) {
                //splitting the bufReader lines into words
                String[] strArr = bufReader.readLine().split(" "); 
                //casting from string to integer
                int u = Integer.parseInt(strArr[0]);
                //casting from string to integer
                int v = Integer.parseInt(strArr[1]);
                Vertex vertexU = graph.get(u); 
                Vertex vertexV = graph.get(v); 
                vertexU.addZz(vertexV); 

            } 
        //catch possible exceptions
        } catch (Exception u) { 
            u.printStackTrace(); 
            throw u; 
        } 
    } 
    

    // Finds zz of vertex s 
    ArrayList<Vertex> getZz(Vertex v) { 
        int s = v.getA(); 
        return graph.get(s).zz; 
    } 
 
    ArrayList<Vertex> intersect(ArrayList<Vertex> arlFirst, 
            ArrayList<Vertex> arlSecond) { 
        ArrayList<Vertex> hold = new ArrayList<Vertex>(arlFirst); 
        hold.retainAll(arlSecond); 
        return hold; 
    } 

    ArrayList<Vertex> union(ArrayList<Vertex> arlFirst, 
            ArrayList<Vertex> arlSecond) { 
        ArrayList<Vertex> hold = new ArrayList<Vertex>(arlFirst); 
        hold.addAll(arlSecond); 
        return hold; 
    } 

    ArrayList<Vertex> removeNbrs(ArrayList<Vertex> arlFirst, Vertex v) { 
        ArrayList<Vertex> arlHold = new ArrayList<Vertex>(arlFirst); 
        arlHold.removeAll(v.getZz()); 
        return arlHold; 
    } 

    void Bron_KerboschWithPivot(ArrayList<Vertex> R, ArrayList<Vertex> P,
        ArrayList<Vertex> X, String pre) { 
        if ((P.size() == 0) && (X.size() == 0)) {
            cliquePrinter(R); 
            return; 
        } 
        ArrayList<Vertex> P1 = new ArrayList<Vertex>(P); 
        // Find Pivot 
        Vertex h = degreeMax(union(P, X)); 
        // P = P / Nbrs(h) 
        P = removeNbrs(P, h); 
        for (Vertex v : P) { 
            R.add(v); 
            Bron_KerboschWithPivot(R, intersect(P1, getZz(v)), 
                    intersect(X, getZz(v)), pre + "\t"); 
            R.remove(v); 
            P1.remove(v); 
            X.add(v); 
        } 
    } 
    Vertex degreeMax(ArrayList<Vertex> g) { 
        Collections.sort(g); 
        return g.get(g.size() - 1);
    } 

    void executeBron_Kerbosch() { 

        ArrayList<Vertex> X = new ArrayList<Vertex>(); 
        ArrayList<Vertex> R = new ArrayList<Vertex>(); 
        ArrayList<Vertex> P = new ArrayList<Vertex>(graph); 
       Bron_KerboschWithPivot(R, P, X, ""); 
    } 

    void cliquePrinter(ArrayList<Vertex> R) { 
        //System.out.print("  --- Maximal Clique : "); 
        int count = 0;
        for (Vertex v : R) { 
            //System.out.print(" " + (v.getX())); 
            count = count + 1;
        }
        int terra2 = count;
        if (terra2 > terra1){
            terra1 = terra2;
        }  
    } 

    String printSet(ArrayList<Vertex> Y) { 
        StringBuilder build = new StringBuilder(); 

        build.append("{"); 
        for (Vertex v : Y) { 
            build.append("" + (v.getA()) + ","); 
        } 
        if (build.length() != 1) {
            build.setLength(build.length() - 1); 
        } 
        build.append("}"); 
        return build.toString(); 
    } 

    //first method of this class that get executed
    public static int run(String newFile, String[] args) throws IOException{

        FindLower gg = new FindLower();

        BufferedReader reader = null; 

        //if (args.length > 0) { 
            //using the buffer reader
          //  reader = new BufferedReader(new StringReader("1\n5\n7\n0 1\n0 2\n0 3\n0 4\n1 2\n2 3\n3 4\n"));    
        //} else { 
            //text file imported from the Find class
            File file = new File(newFile); 
            try { 
                //reading the file
                reader = new BufferedReader(new FileReader(file));
                //catch possible exceptions
            } catch (Exception e) { 
                e.printStackTrace();     
            } 
        //} 
        try { 
            int graphsQ = gg.finalGraphCount(reader); 

            for (int i = 0; i < graphsQ; i++) {
                //calling the readNextGraph method with the reader as paramether
                gg.readNextGraph(reader); 
                gg.executeBron_Kerbosch();
            } 
            //catch possible exceptions
        } catch (Exception e) { 
            e.printStackTrace(); 
        } finally { 
            try {
                //closing the file reader 
                reader.close(); 
                //catch possible exceptions
            } catch (Exception f){ 

            } 
        } 
        //returning the value of the lower bound
       return terra1; 
    } 
}
