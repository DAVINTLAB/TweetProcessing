import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class VersusCounterPlus {
	public static void main(String[]args) throws IOException{
		File inputFile = new File("C:\\Users\\eduardo\\Desktop\\JM\\final_with_date.txt");
		File outputFile = new File("C:\\Users\\eduardo\\Desktop\\JM\\try_it.txt");

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
		    //criar algo que pegue o created_at tb
			
			String input = currentLine.toLowerCase();
			
			if(input.length()>19){ //se a linha não quebrar antes do created_at
				
				String created_at = "*" + input.substring(12,20) + ";";
				System.out.println(currentLine); //sai "Thu Apr 13 23:59:59 +0000 2017" OU "RT @rbdcancioneshoy: PARCIAL 10"
				System.out.println(created_at); // sai 			  *23:59:59; 			OU 			  *ioneshoy;
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
				
				int index3 = input.indexOf("marcos");
				int count3 = 0;
				while (index3 != -1) {
				    count3++;
				    input = input.substring(index3 + 1);
				    index3 = input.indexOf("marcos");
				}			
				
				if(count1 > count2 && count1 > count3){
					System.out.println("V" + created_at);
					writer.write("V" + System.getProperty("line.separator")); //vivian, V, blue
				} else if (count2 > count1 && count2 > count3){
					System.out.println("E" + created_at);
					writer.write("E" + System.getProperty("line.separator")); //emilly, E, green
				} else if (count3 > count1 && count3 > count2){
					System.out.println("M" + created_at);
					writer.write("M" + System.getProperty("line.separator")); //marcos, M, red
				}
				
			}
					//casos apenas para menção de um > que a de cada outro
			
			/*else {
				System.out.println("2");
				writer.write("2" + System.getProperty("line.separator")); //tie, 3, gray
			}*/
			
		}
		writer.close();
		reader.close(); 
	}
}
