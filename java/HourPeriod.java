
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HourPeriod {
	public static void main(String[]args) throws IOException{
		File inputFile = new File("C:\\Users\\eduardo\\Music\\JM\\Coletas\\Lula vs Moro\\MERGE_dias10e11.txt");
		File tempFile = new File ("C:\\Users\\eduardo\\Music\\JM\\Coletas\\Lula vs Moro\\c3 graph content\\hour23.txt"); //VAI BRASA!

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
///avoid geral thu may 11 11 
		String target = "Thu May 11 10"; //inicial
		String avoid = "Thu May 11 11"; //final

		String currentLine;

		while((currentLine = reader.readLine()) != null) { //enquanto houver mais uma linha
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
		writer.close(); 					//por algum motivo a formatação do arquivo fica uma zona, mas acho que tá tudo certo
		reader.close(); 
	}
}
