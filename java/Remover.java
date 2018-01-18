import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Remover {
	
	File inputFile;
	File outputFile;
	
	public Remover(File inFile) throws IOException{
		inputFile = inFile;
		String pathOut = inFile.getPath() + "_PALAVRAS.txt";
		outputFile = new File(pathOut);
	}
	
	public void pegaPalavras() throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

		String currentLine;

		while((currentLine = reader.readLine()) != null) {
		    // trim newline when comparing with lineToRemove
		    if(!currentLine.contains("#") && !currentLine.contains("@")){ //not a hashtag, nor a profile
		    	writer.write(currentLine + System.getProperty("line.separator")); //then write it
		    }
		}
		writer.close(); 
		reader.close(); 
	}
}
