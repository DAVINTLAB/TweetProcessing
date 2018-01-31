import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class GeradorC3 { //makes lists of tweets, hashtags and profiles based on number of occurrences
	
	File inputFile;
	HashMap <TweetKey, Integer> tweetMap = new HashMap<TweetKey, Integer>();
	HashMap <TweetKey, Integer> profileMap = new HashMap<TweetKey, Integer>();
	HashMap <TweetKey, Integer> hashtagMap = new HashMap<TweetKey, Integer>();
	
	public GeradorC3(File inFile) throws IOException{
		inputFile = inFile;
	}
	
	/**
	 * Monta o Hash de tweets mais populares enquanto lê o arquivo de tweets (created_at_datetime:text)
	 * @throws IOException
	 */
	public void rankingTweets() throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		String linhaAtual;
		while((linhaAtual = reader.readLine()) != null) { //enquanto houver mais uma linha
			if(linhaAtual.length() > 6 && linhaAtual.substring(0,5).equals("|^201")){ //busca ano na linha
				Integer hora = Integer.parseInt(linhaAtual.substring(13,15));
				String texto = linhaAtual.substring(25, linhaAtual.length()-2);
				//System.out.println(hora.toString() + " - " + texto);
				String tweet = hora.toString() + "h - " + texto;
				boolean flag = false;
				// procura nas chaves do hash
				for (TweetKey tk : tweetMap.keySet()) {
					// se achar uma chave onde bate o texto lido
					if (tk.getTexto().equals(tweet)) {
						// se achar uma chave onde bate a hora lida
						if (tk.getHora() == hora) {
							// pega a tweet key e sobe o contador
							tweetMap.replace(tk, tweetMap.get(tk) + 1);
							flag = true;
							break;
						}
		
					}
				}
				//se percorreu o hash inteiro e não achou nada
				if (!flag) {
					// pega a hora e o texto
					TweetKey tk = new TweetKey(hora, tweet);
					// e faz o put como primeira ocorrência
					tweetMap.put(tk, 1);
				}
			}
		}
		
		//it goes on and on and on
		/**
		 * Manda o conteúdo do hash para uma array
		 */
		TweetKey hashReader;
		ArrayList<TweetKey> array = new ArrayList<>();
		for (TweetKey tk : tweetMap.keySet()) {
			array.add(hashReader = new TweetKey(tk.getTexto(), tweetMap.get(tk)));
		}

		/**
		 * Ordena a array de maior a menor
		 */
		Collections.sort(array, (TweetKey tk1, TweetKey tk2) -> tk1.getReps().compareTo(tk2.getReps()));
		System.out.println("--TOP TWEETS--");
		for (int i = array.size() - 1; i >= 0; i--) {
			System.out.println("(" + array.get(i).getReps() + ") " + array.get(i).getTexto());
		}
	}
	
	/** 
	 * Monta o Hash de perfis mais mencionados enquanto lê o arquivo de tweets (created_at_datetime:text)
	 * @throws IOException
	 */
	public void rankingPerfis() throws IOException{
		ArrayList<TweetKey> array = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		String linhaAtual;
		while((linhaAtual = reader.readLine()) != null) { //enquanto houver mais uma linha
			if(linhaAtual.contains("@") && linhaAtual.length() > 6 && linhaAtual.substring(0,5).equals("|^201")){
				Integer hora = Integer.parseInt(linhaAtual.substring(13,15));
				//builda o perfil e, no início, é só um '@'
				String found = linhaAtual.substring(linhaAtual.lastIndexOf("@"));
				int cont = 2;
				//enqto o último caractere não for espaço (middle) ou separador (end)
				while(found.charAt(found.length()-1) != ' ' && found.charAt(found.length()-1) != '|'){
					found = linhaAtual.substring(linhaAtual.lastIndexOf("@"), linhaAtual.lastIndexOf("@") + cont);
					cont++;
				}
				//corte de potenciais ' ' ou '|' (últimos caracteres)
				found = found.substring(0, found.length()-1);
				//System.out.println(found);
				String perfil = hora.toString() + "h - " + found;
				boolean flag = false;

				// procura nas chaves do hash
				for (TweetKey tk : profileMap.keySet()) {

					// achou uma chave onde bate o texto lido
					if (tk.getTexto().equals(perfil)) {
						if (tk.getHora() == hora) {
							// pega a tweet key e sobe o contador
							profileMap.replace(tk, profileMap.get(tk) + 1);
							flag = true;
							break;
						}

					}
				}
				// flag ainda é falsa (não há esse tweet para a hora lida)
				if (!flag) {
					// pega esses atributos, instancia a tk
					TweetKey tk = new TweetKey(hora, perfil);
					// e faz o put da primeira ocorrência
					profileMap.put(tk, 1);
				}
			}
		}
		TweetKey tkAux;
		for (TweetKey tk : profileMap.keySet()) {
			array.add(tkAux = new TweetKey(tk.getTexto(), profileMap.get(tk)));
		}

		Collections.sort(array, (TweetKey tk1, TweetKey tk2) -> tk1.getReps().compareTo(tk2.getReps()));
		System.out.println("--TOP PROFILES--");
		for (int i = array.size() - 1; i >= 0; i--) {
			System.out.println("(" + array.get(i).getReps() + ") " + array.get(i).getTexto());
		}
		reader.close();
	}

	public void rankingHashtags() throws IOException{
		ArrayList<TweetKey> array = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		String linhaAtual;
		while((linhaAtual = reader.readLine()) != null) { //enquanto houver mais uma linha
			if(linhaAtual.contains("#") && linhaAtual.length() > 6 && linhaAtual.substring(0,5).equals("|^201")){
				Integer hora = Integer.parseInt(linhaAtual.substring(13,15));
				//builda o perfil e, no início, é só um '@'
				String found = linhaAtual.substring(linhaAtual.lastIndexOf("#"));
				int cont = 2;
				//enqto o último caractere não for espaço (middle) ou separador (end)
				while(found.charAt(found.length()-1) != ' ' && found.charAt(found.length()-1) != '|'){
					found = linhaAtual.substring(linhaAtual.lastIndexOf("#"), linhaAtual.lastIndexOf("#") + cont);
					cont++;
				}
				//corte de potenciais ' ' ou '|' (últimos caracteres)
				found = found.substring(0, found.length()-1);
				//System.out.println(found);
				String perfil = hora.toString() + "h - " + found;
				boolean flag = false;

				// procura nas chaves do hash
				for (TweetKey tk : profileMap.keySet()) {

					// achou uma chave onde bate o texto lido
					if (tk.getTexto().equals(perfil)) {
						if (tk.getHora() == hora) {
							// pega a tweet key e sobe o contador
							profileMap.replace(tk, profileMap.get(tk) + 1);
							flag = true;
							break;
						}

					}
				}
				// flag ainda é falsa (não há esse tweet para a hora lida)
				if (!flag) {
					// pega esses atributos, instancia a tk
					TweetKey tk = new TweetKey(hora, perfil);
					// e faz o put da primeira ocorrência
					profileMap.put(tk, 1);
				}
			}
		}
		TweetKey tkAux;
		for (TweetKey tk : profileMap.keySet()) {
			array.add(tkAux = new TweetKey(tk.getTexto(), profileMap.get(tk)));
		}

		Collections.sort(array, (TweetKey tk1, TweetKey tk2) -> tk1.getReps().compareTo(tk2.getReps()));
		System.out.println("--TOP PROFILES--");
		for (int i = array.size() - 1; i >= 0; i--) {
			System.out.println("(" + array.get(i).getReps() + ") " + array.get(i).getTexto());
		}
		reader.close();
	}

	public void imprimeQuantidades(){
		
	}
}