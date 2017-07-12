import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * @version 1
 */
public class Remover {
	public static void main(String[]args) throws IOException{
		File inputFile = new File("C:\\Users\\eduardo\\Desktop\\JM\\Coletas\\Lula vs Moro\\Lula_Moro_Total.txt_processado.txt");
		File tempFile = new File("C:\\Users\\eduardo\\Desktop\\JM\\Coletas\\Lula vs Moro\\words.txt");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String lineToRemove = "#";
		String lineToRemove2= "@";
		String currentLine;

		while((currentLine = reader.readLine()) != null) { //enquanto houver mais uma linha
		    // trim newline when comparing with lineToRemove
		    if(currentLine.contains(lineToRemove) || currentLine.contains(lineToRemove2)){} //do nothing
		    else{
		    	writer.write(currentLine + System.getProperty("line.separator")); //é oq escreve no arquivo
		    }
		}
		writer.close(); 					//por algum motivo a formatação do arquivo fica uma zona, mas acho que tá tudo certo
		reader.close(); 
		boolean successful = tempFile.renameTo(inputFile);
	}
}
