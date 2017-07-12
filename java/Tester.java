import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Tester {
	public static void main(String[]args) throws IOException{
		File input = new File("C:\\Users\\eduardo\\Music\\JM\\Coletas\\Temer\\temerHorarios24h.txt");

		BufferedReader reader = new BufferedReader(new FileReader(input));
		
		String horaInicial = "20"; //manter
		
		String currentLine;
		
		while((currentLine = reader.readLine()) != null) { //enquanto houver mais uma linha em input
			if(currentLine.substring(12,14) == horaInicial){
				System.out.println(currentLine.substring(12,14));
			}
			
			
		} reader.close();
		
	}
}
