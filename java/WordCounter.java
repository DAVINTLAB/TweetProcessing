import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WordCounter {
	public static void main(String[]args) throws IOException{
		File inputFile = new File("C:\\Users\\eduardo\\Desktop\\JM\\jaggi.txt");
		File outputFile = new File("C:\\Users\\eduardo\\Desktop\\JM\\israel.txt");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

		String currentLine;
		int countTotal = 0;
		int armazena = 0;
		int countUniqueMention = 0;
		
		while((currentLine = reader.readLine()) != null) { 
			String input = currentLine.toLowerCase();
			int index1 = input.indexOf("marcos nosso campeao");
			while (index1 != -1) {
			    countTotal++;
			    input = input.substring(index1 + 1);
			    index1 = input.indexOf("marcos nosso campeao");
			}
			if(countTotal > armazena){
				countUniqueMention++;
			}
			armazena = countTotal;
		}

		writer.write("Tweets com \"INSERT OR CHANGE\": " + countUniqueMention + " --- Número de menções: " + countTotal);
		
		writer.close();
		reader.close(); 
	}
}
