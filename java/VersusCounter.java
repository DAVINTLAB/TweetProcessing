import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class VersusCounter {
	public static void main(String[]args) throws IOException{
		File inputFile = new File("C:\\Users\\eduardo\\Desktop\\JM\\jaggi.txt");
		File outputFile = new File("C:\\Users\\eduardo\\Desktop\\JM\\israel.txt");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

		String currentLine;

		/*
		 * maneira de fazer o versus funcionar.
		 * vivian red (0)
		 * emilly green (1)
		 * both gray (2)
		 * 
		 * contador no tweet. (rts valem, pq também são opinião)
		 */
		
		//mandar tudo pra lower case
		
		while((currentLine = reader.readLine()) != null) { //enquanto houver mais uma linha
		    
			String input = currentLine.toLowerCase();
			
			int index1 = input.indexOf("vivian");
			int count1 = 0;
			while (index1 != -1) {
			    count1++;
			    input = input.substring(index1 + 1);
			    index1 = input.indexOf("vivian");
			}
			
			int index2 = input.indexOf("emilly");
			int count2 = 0;
			while (index2 != -1) {
			    count2++;
			    input = input.substring(index2 + 1);
			    index2 = input.indexOf("emilly");
			}
			
			if(count1 > count2){
				System.out.println("0");
				writer.write("0" + System.getProperty("line.separator")); //vivian, 0, red
			} else if (count1 < count2){
				System.out.println("1");
				writer.write("1" + System.getProperty("line.separator")); //emilly, 1, green
			}
			/*else {
				System.out.println("2");
				writer.write("2" + System.getProperty("line.separator")); //tie, 2, gray
			}*/
			
		}
		writer.close();
		reader.close(); 
	}
}
