import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class RelatedWords {

   public static void main(String args[])  throws IOException{
		
	    File inputFile = new File("C:\\Users\\eduardo\\Desktop\\coleta_carlos_11.05.txt");
		File outputFile = new File("C:\\Users\\eduardo\\Desktop\\israel.txt");
		
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		int opt = 1;
		Scanner in = new Scanner(System.in);
		writer.write("termo1,termo2" + System.getProperty("line.separator"));
		
		while(opt != 0){
			
			System.out.println("Termo 1: ");
			String t1 = in.next();
			System.out.println("Entendi: " + t1);
			System.out.println("Termo 2: ");
			String t2 = in.next();
			System.out.println("Entendi: " + t2);

			String currentLine;
			while((currentLine = reader.readLine()) != null) { //enquanto houver mais uma linha
			    // trim newline when comparing with lineToRemove
			    if(currentLine.toLowerCase().contains(t1) && currentLine.toLowerCase().contains(t2)){
			    	writer.write(t1 +","+ t2 + System.getProperty("line.separator"));
			    }
			}
			reader = new BufferedReader(new FileReader(inputFile)); //reseta a leitura
			
		}
		
		reader.close();
		writer.close();
		}
}