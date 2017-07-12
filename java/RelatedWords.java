import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class RelatedWords { //builds lines to form bubble graphs.. still TBD

   public static void main(String args[])  throws IOException{
		
	    File inputFile = new File("C:\\Users\\...\\input.txt");
		File outputFile = new File("C:\\Users\\...\\output.txt");
		
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		int opt = 1;
		Scanner in = new Scanner(System.in);
		writer.write("term1,term2" + System.getProperty("line.separator"));
		
		while(opt != 0){
			
			System.out.println("Term no1: ");
			String t1 = in.next();
			System.out.println("Got it.");
			System.out.println("Term no2: ");
			String t2 = in.next();
			System.out.println("Got it.");

			String currentLine;
			while((currentLine = reader.readLine()) != null) {
			    // trim newline when comparing with lineToRemove
			    if(currentLine.toLowerCase().contains(t1) && currentLine.toLowerCase().contains(t2)){
			    	writer.write(t1 +","+ t2 + System.getProperty("line.separator"));
			    }
			}
			reader = new BufferedReader(new FileReader(inputFile)); //resets reading
			
		}
		
		reader.close();
		writer.close();
		}
}