import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GeradorC3 { //writes tweets from target hour to any other hour
	
	File inputFile;
	File outputTweetQuantity;
	File outputTopRetweets;
	File outputTopHashtags;
	File outputTopProfiles;
	
	public GeradorC3(File inFile) throws IOException{
		inputFile = inFile;
		String pathOutQ = inFile.getPath() + "_QUANTIDADES.txt";
		String pathOutR = inFile.getPath() + "_TOP_RTS.txt";
		String pathOutH = inFile.getPath() + "_TOP_HTS.txt";
		String pathOutP = inFile.getPath() + "_TOP_PROFILES.txt";
		outputTweetQuantity = new File(pathOutQ);
		outputTopRetweets = new File(pathOutR);
		outputTopHashtags = new File(pathOutH);
		outputTopProfiles = new File(pathOutP);
	}
	
	public void buscaQuantidades() throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writerQUANTITY = new BufferedWriter(new FileWriter(outputTweetQuantity));
		BufferedWriter writerRETWEETS = new BufferedWriter(new FileWriter(outputTopRetweets));
		BufferedWriter writerHASHTAGS = new BufferedWriter(new FileWriter(outputTopHashtags));
		BufferedWriter writerPROFILES = new BufferedWriter(new FileWriter(outputTopProfiles));
		
		String currentLine;

		while((currentLine = reader.readLine()) != null) {
		    //writer.write(currentLine + System.getProperty("line.separator"));
		}
	}
}
