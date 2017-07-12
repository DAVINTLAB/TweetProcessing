import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LineCounter{ //counts how many lines mention some string
	public static void main(String[]args) throws IOException{
		File input = new File("C:\\Users\\...\\input.txt");

		BufferedReader reader = new BufferedReader(new FileReader(input));

		String currentLine;
		int count = 0;
		String subs = "insert some string";
		
		while((currentLine = reader.readLine()) != null) { //enquanto houver mais uma linha em Jaggi
		    if(currentLine.contains(subs)){count++;}
		} 
		reader.close();
		
		System.out.print("Found: " + count);
		
	}
}
