import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Selector {
	
	File inputFile;
	File outputFile;
	String prefixoDesejado;
	
	public Selector(File inFile, String prefixo) throws IOException{
		inputFile = inFile;
		String pathOut;
		prefixoDesejado = prefixo;
		
		if(prefixo == "#"){
			pathOut = inFile.getPath() + "_HASHTAGS.txt";
		}else{ //@
			pathOut = inFile.getPath() + "_PERFIS.txt";
		}
		outputFile = new File(pathOut);
	}
	
	public void select() throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

		String currentLine;

		while((currentLine = reader.readLine()) != null) {
		    // trim newline when comparing with lineToRemove
		    if(currentLine.contains(prefixoDesejado)){ //usually # or @
		    	writer.write(currentLine + System.getProperty("line.separator"));
		    }
		}
		writer.close(); 					
		reader.close();
	}
}
