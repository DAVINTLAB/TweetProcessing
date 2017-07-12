import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HourPeriod { //writes tweets from target hour to any other hour
	public static void main(String[]args) throws IOException{
		File inputFile = new File("C:\\Users\\...\\input.txt");
		File tempFile = new File ("C:\\Users\\...\\output.txt"); 

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
		String target = "insert target string";
		String avoid = "insert final string";

		String currentLine;

		while((currentLine = reader.readLine()) != null) {
		    // trim newline when comparing with lineToRemove
		    if(currentLine.contains(target)){
		    	while((currentLine = reader.readLine()) != null){ 
		    		writer.write(currentLine + System.getProperty("line.separator"));
		    		
		    		if(currentLine.contains(avoid)){ // writes until 'avoid'
		    			break;
		    		}
		    	} //inner break
		    	break; //outer break
		    } 
		}
		writer.close();
		reader.close(); 
	}
}
