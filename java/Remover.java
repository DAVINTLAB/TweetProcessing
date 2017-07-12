import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Remover {
	public static void main(String[]args) throws IOException{
		File inputFile = new File("C:\\Users\\...\\processed_input.txt");
		File tempFile = new File("C:\\Users\\...\\output.txt");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String currentLine;

		while((currentLine = reader.readLine()) != null) {
		    // trim newline when comparing with lineToRemove
		    if(!currentLine.contains("#") && !currentLine.contains("@")){ //not a hashtag, nor a profile
		    	writer.write(currentLine + System.getProperty("line.separator")); //then write it
		    }
		}
		writer.close(); 
		reader.close(); 
		boolean successful = tempFile.renameTo(inputFile);
	}
}
