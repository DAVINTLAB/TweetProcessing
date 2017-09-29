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
	public static void main(String[]args) throws IOException{
		File inputFile = new File("C:\\Users\\eduardo\\Desktop\\Vin�cius\\PalavrasChave.txt");
		File tempFile = new File("C:\\Users\\eduardo\\Desktop\\Vin�cius\\PalavrasChave(Repeticao).txt");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		//merece:5
		//me revela o 'merece'
		//me revela a qtd
		
		String linhaAtual;

		while((linhaAtual = reader.readLine()) != null) { //enquanto houver mais uma linha
			
			int localDelimiter = linhaAtual.indexOf("="); //acha onde est� o :
			String palavra = linhaAtual.substring(0,localDelimiter); //pega o conte�do at� o : (palavra)
			Integer quantidade = Integer.parseInt(linhaAtual.substring(localDelimiter+1,linhaAtual.length())); //pega o conte�do depois de : (qtd) e converte para n�mero
			
		    while(quantidade > 0){
		    	writer.write(palavra + System.getProperty("line.separator"));
		    	quantidade--;
		    }
		}
		writer.close(); 					
		reader.close(); 
		boolean successful = tempFile.renameTo(inputFile);
	}
}
