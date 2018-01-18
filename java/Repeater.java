import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * @version 1
 */
public class Repeater {//procura registros baseado no char inicial no ARQUIVO LIMPO, "_processado"
	
	private File inputFile;
	private File outputFile;
	
	public Repeater(File inFile) throws IOException{
		inputFile = inFile;
		String pathOut = inFile.getPath() + "_REPETICAO.txt";
		outputFile = new File(pathOut);
	}
	
	public void repeat() throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

		/* INPUT
		 * 
		 * JM:3
		 * PROGRAMACAO: 2
		 * 
		 * OUTPUT
		 * 
		 * JM
		 * JM
		 * JM
		 * PROGRAMACAO
		 * PROGRAMACAO
		 */
		
		String linhaAtual;

		while((linhaAtual = reader.readLine()) != null) { //enquanto houver mais uma linha
			
			int localDelimiter = linhaAtual.indexOf(":"); //acha onde está o :
			String palavra = linhaAtual.substring(0,localDelimiter); //pega o conteúdo até o : (palavra)
			Integer quantidade = Integer.parseInt(linhaAtual.substring(localDelimiter+1,linhaAtual.length())); //pega o conteúdo depois de : (qtd) e converte para número
			
		    while(quantidade > 0){
		    	writer.write(palavra + System.getProperty("line.separator"));
		    	quantidade--;
		    }
		}
		writer.close(); 					
		reader.close(); 
	}
}
