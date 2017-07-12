import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TweetCounter24h {
	public static void main(String[]args) throws IOException{
		File input = new File("C:\\Users\\eduardo\\Music\\JM\\Coletas\\Temer\\temerHorarios24h.txt");

		BufferedReader reader = new BufferedReader(new FileReader(input));
		int []vetorHoras = new int[24];
		//[20,21,22,23,00,01,02,03,04,05,06,07,08,09,10,12,13,14,15,16,17,18,19]
		
		//must be string to capture hour in file
		String horaInicial = "20";
		String horaAtual = "";
		
		String currentLine;
		int counter = 0;
		int index = 0;
		boolean trava24h = false;
		boolean cheguei = false;
		String aux = horaInicial; //will be necessary when the hour changes
		boolean naoPasseMaisPorEsseIf = false;
		/* VARIAVEIS DE TESTE */
		int jm = 0;
		
		while((currentLine = reader.readLine()) != null) { //enquanto houver mais uma linha em input
			aux = horaAtual; //last things last, mas escolhi botar aqui. vai ficar sempre "desatualizado", mas muitas vezes vai ser == horaAtual
			horaAtual = currentLine.substring(12,14); //first things first. vai ficar sempre "atualizado", e == aux na maioria das vezes
			//System.out.println("laço while");
			
			if(currentLine.substring(12,14) == horaInicial && !naoPasseMaisPorEsseIf){
				cheguei = true; //destrava o if seguinte
				System.out.println("Destravou");
				naoPasseMaisPorEsseIf = true; //nao volta pra ca
			}
			
			if(currentLine.substring(12,14) != horaInicial && !cheguei){ //TODO tá sempre entrando aqui...
		    	//nada acontece. ainda não é o target
				System.out.println("laço while, if");
		    }
		    else if(currentLine.substring(12,14) == horaInicial){ //pode ser pra comeÃ§ar, ou pra parar
				System.out.println("laço while, else if");
				if(trava24h){//chegou na hora 24
					System.out.println("laço while, else if, if");
			    	break; //sai do while e termina o programa
			    }
				
				vetorHoras[index] = counter++; //index aqui Ã© 0
			    aux = currentLine.substring(12,14);
			    
			}else{ //hora seguinte
				System.out.println("laço while, else");
				if(horaAtual != aux){//isso deve acontecer 24x
					System.out.println("laço while, else, if");
					index++; //vai pra proxima posiÃ§Ã£o do vetor
					jm++;
					trava24h = true; //pra quando cumprir as 24h (uma troca de horÃ¡rio basta, mas vai ter que ficar assim)
				}
				
				vetorHoras[index] = counter++; //index aqui Ã© 1 ... 23
				
				/* DEPRECATED
				horaAtual = String.valueOf(horaAtual) + 1; 
				if(horaAtual == 24){
					horaAtual = 00;
				}
				*/
			}
			
			
		} reader.close();
		
		/* WOOF! */
		System.out.println("Teste: quantas vezes houve troca de horarios?" + jm);
		for(int i = 0; i < 24; i++){
			System.out.println(i);
			System.out.println(vetorHoras[i]);
		}
		
	}
}
