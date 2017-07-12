import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * @version 1
 */
public class Selector {//procura registros baseado no char inicial no ARQUIVO LIMPO, "_processado"
	public static void main(String[]args) throws IOException{
		File inputFile = new File("C:\\Users\\eduardo\\Music\\JM\\Coletas\\Lula vs Moro\\c3 graph content\\hour23.txt_processado.txt");
		File tempFile = new File("C:\\Users\\eduardo\\Music\\JM\\Coletas\\Lula vs Moro\\c3 graph content\\hour23hashtags.txt");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String lineToSelect = "#"; 
		String currentLine;

		while((currentLine = reader.readLine()) != null) { //enquanto houver mais uma linha
		    // trim newline when comparing with lineToRemove
		    if(currentLine.contains(lineToSelect)){
		    	writer.write(currentLine + System.getProperty("line.separator")); //é oq escreve no arquivo
		    }
		}
		writer.close(); 					
		reader.close(); 
		boolean successful = tempFile.renameTo(inputFile);
	}
}
