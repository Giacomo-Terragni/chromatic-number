import java.io.*;
import java.util.*;  


public class TransformGraph{

	public static String originalFile;

	public TransformGraph (String originalFile){
		this.originalFile = originalFile;
	} 
	public static String transform () throws IOException {

		//generates a random number with three digits in order to create a new file with a different name each time 
		Random random = new Random();
		int x = random.nextInt(900) + 100;

		String aa = Integer.toString(x);

		//name of the new file
		String newFile = "newFile" + aa +".txt";

		//System.out.println(newFile);

		File newTxt = new File (newFile);

		//buffer to write on the new file
		BufferedWriter writer = new BufferedWriter(new FileWriter(newTxt, true));
		//write the number 1 on the first line of the new file, it represent the number of graphs in the text file 
      	writer.write("1");
      	writer.newLine();

      	//file reader to read the original text file
  		FileReader reader = new FileReader(originalFile);  
  		//create a scanner to scan the original file  
		Scanner in = new Scanner(reader);

		try { 
			//this string is the "VERTICES = XXX" string in the second line of the original file 
			String lineOne = in.nextLine();
			//put each word of the line in an array 
			String[] arrOne = lineOne.split(" ");
			//cast the number of vertices from a string into an integer 
			int numberOne = Integer.parseInt(arrOne[2]);
			//increase by one the number of vertices 
			int numberTwo = numberOne + 1;
			//cast the number of vertices +1 from an integer to a string 
			String numberThree = Integer.toString(numberTwo);
			//write the number of vertices + 1 in the second line of the new file 
			writer.write(numberThree);
			writer.newLine();

			//this string is the "EDGES = XXX" string in the third line of the original file 
			String lineTwo = in.nextLine();
			//put each word of the line in an array 
			String[] arrTwo = lineTwo.split(" ");
			//write the number of EDGES in the third line of the new file 
			writer.write(arrTwo[2]);

			writer.newLine();

			//copy and paste all the connection between the vertices from the original file into the new one
			while (in.hasNextLine()){
				String line = in.nextLine();
				writer.write(line);
				writer.newLine();
			}
			writer.close();	
		}
		catch (Exception z){
		}
		return newFile;	
	}
}
				






	
