package contagem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.OutputStreamWriter;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
	private static String ENCODING = "UTF-8";  //ISO-8859-1 ou UTF-8
	private static String ARQUIVO_A_SER_LIDO = "C:\\Users\\eduardo\\Desktop\\Vinícius\\Palavras.txt";
	private static boolean PRESERVAR_HASHTAGS = true;
	private static Integer NUMERO_DE_PALAVRAS = 600;
	private static boolean ORDEM_ASCENDENTE = false;

	public static void main(String args[]){		
		long tempo = System.currentTimeMillis();
		HashMap<String, Palavra> palavras = new HashMap<String, Palavra>();
				
		System.out.println("Lendo arquivo \"" + ARQUIVO_A_SER_LIDO + "\"...");
		try(BufferedReader in = new BufferedReader(new InputStreamReader( new FileInputStream(ARQUIVO_A_SER_LIDO), ENCODING));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(ARQUIVO_A_SER_LIDO+"_processado.txt"), ENCODING))){
			
			System.out.print("\tLimpeza de arquivo e contagem de palavras...");
			while(in.ready()){
				String linha = limpaLinhas(in.readLine());
				String[] tokensNaLinha = linha.split("\\s+");
				for(String token: tokensNaLinha){
					if(token.equals("")) continue; //TODO Descobrir onde essas palavras vazias estão sendo geradas - ou só deixar assim mesmo, talvez seja melhor
					Palavra palavra;
					if(palavras.containsKey(token)) palavra = palavras.get(token);
					else palavra = new Palavra(token);
					palavra.frequencia++;
					palavras.put(token, palavra);
				}	
			}
			
			System.out.print(" completas.\nLeitura completa.\nOrganização de linhas...");
			
			ArrayList<Palavra> palavrasOrdenadas = new ArrayList<Palavra>(palavras.values());
			Collections.sort(palavrasOrdenadas, new PalavraComparator(ORDEM_ASCENDENTE));
			
			System.out.print(" completa.\nEscrita de arquivo...");
			
			for(Palavra palavra: palavrasOrdenadas){
				if(NUMERO_DE_PALAVRAS == 0) break;
				out.write(palavra.toString() + "\r\n");
				NUMERO_DE_PALAVRAS--;
			}
			
			System.out.println(" completa.\nProcesso concluído com sucesso.");		
			
		} catch (IOException e) {
			System.out.println("Deu problema, que pena :<");
			e.printStackTrace();
		}
		
		System.out.println("Tempo de execução: " + (System.currentTimeMillis() - tempo) + "ms");
		
	}
	
	public static String limpaLinhas(String linha){
		linha = linha.toLowerCase();
		linha = limpaAcentos(linha);
		linha = espaceiaHashtags(linha);
		linha = limpaLinks(linha);
		linha = limpaNaoLetras(linha);
		linha = limpaNumeros(linha);
		linha = limpaStopwords(linha);
		return linha;
	}
	
	private static String limpaAcentos(String linha){
		return Normalizer.normalize(linha, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}

	private static String espaceiaHashtags(String linha) { // Necessário pois algumas pessoas escrevem as hashtags juntas. e.g.: #blizzard#Overwatch
		Pattern p;
		if(PRESERVAR_HASHTAGS) p = Pattern.compile("(#[\\w\\d]+)");
		else p = Pattern.compile("([\\w\\d]+)");
		Matcher m = p.matcher(linha);
		StringBuffer s = new StringBuffer();
		while(m.find()){
			m.appendReplacement(s, " "+m.group(1)+" ");
		}
		return s.toString();
	}

	private static String limpaLinks(String linha){
		StringBuilder sb = new StringBuilder();
		CharSequence cs1 = "http";
		CharSequence cs2 = "www.";
		for(String palavra: linha.split("\\s+")){
			if(!palavra.contains(cs1) && !palavra.contains(cs2)) sb.append(palavra + " ");
		}
		return sb.toString();
	}
	
	private static String limpaNaoLetras(String linha) {
		return linha.replaceAll("[^\\w\\s#@]", "");
	}
	
	private static String limpaNumeros(String linha){
		return linha.replaceAll("\\b[0-9]+\\b", "");
	}
	
	private static String limpaStopwords(String linha) {
		String[] stopwords = {
				"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
				"vc", "ta", "kk", "kkk", "kkkk", "kkkkk", "kkkkkk", "ne", "via", "pq", "sr",
				"sao", "agora", "ainda", "alguem", "algum", "alguma",
		        "algumas", "alguns", "ampla", "amplas", "amplo", "amplos", "ante",
		        "antes", "ao", "aos", "apos", "aquela", "aquelas", "aquele", "aqueles",
		        "aquilo", "as", "ate", "atraves", "cada", "coisa", "coisas", "com",
		        "como", "contudo", "da", "daquele", "daqueles", "das", "de",
		        "dela", "delas", "dele", "deles", "depois", "dessa", "dessas", "desse",
		        "desses", "desta", "destas", "deste", "deste", "destes", "deve", "devem",
		        "devendo", "dever", "devera", "deverao", "deveria", "deveriam", "devia",
		        "deviam", "disse", "disso", "disto", "dito", "diz", "dizem", "do", "dos",
		        "e", "ela", "elas", "ele", "eles", "em", "enquanto", "entre", "era",
		        "essa", "essas", "esse", "esses", "esta", "esta", "estamos", "estao",
		        "estas", "estava", "estavam", "estavamos", "este", "estes", "estou", "eu",
		        "fazendo", "fazer", "feita", "feitas", "feito", "feitos", "foi", "for",
		        "foram", "fosse", "fossem", "grande", "grandes", "ha", "isso", "isto",
		        "ja", "la", "la", "lhe", "lhes", "lo", "mas", "me", "mesma", "mesmas",
		        "mesmo", "mesmos", "meu", "meus", "minha", "minhas", "muita", "muitas",
		        "muito", "muitos", "na", "nao", "nas", "nem", "nenhum", "nessa",
		        "nessas", "nesta", "nestas", "ninguem", "no", "nos", "nos", "nossa",
		        "nossas", "nosso", "nossos", "num", "numa", "nunca", "o", "os", "ou",
		        "outra", "outras", "outro", "outros", "para", "pela", "pelas", "pelo",
		        "pelos", "pequena", "pequenas", "pequeno", "pequenos", "per", "perante",
		        "pode", "pude", "podendo", "poder", "poderia", "poderiam", "podia",
		        "podiam", "pois", "por", "porem", "porque", "posso", "pouca", "poucas",
		        "pouco", "poucos", "primeiro", "primeiros", "propria", "proprias",
		        "proprio", "proprios", "quais", "qual", "quando", "quanto", "quantos",
		        "que", "quem", "sa", "se", "seja", "sejam", "sem", "sempre", "sendo",
		        "sera", "serao", "seu", "seus", "si", "sim", "sido", "so", "sob", "sobre", "sua",
		        "suas", "talvez", "tambem", "tampouco", "te", "tem", "tendo", "tenha",
		        "ter", "teu", "teus", "ti", "tido", "tinha", "tinham", "toda", "todas",
		        "todavia", "todo", "todos", "tu", "tua", "tuas", "tudo", "ultima",
		        "ultimas", "ultimo", "ultimos", "um", "uma", "umas", "uns", "vendo",
		        "ver", "vez", "vindo", "vir", "vos", "vo", "pelo", "pelas", "pelos", "pra",
		        "RT", "dos", "das", "@rt","rt","pro"};
		
		StringBuilder sb = new StringBuilder();
		
		boolean concatenar;
		for(String palavra: linha.split("\\s+")){
			concatenar = true;
			for(String stopword: stopwords){
				if(palavra.equals(stopword)){
					concatenar = false;
					break;
				}
			}
			if(concatenar) sb.append(palavra + " ");
		}
		return sb.toString();
	}

}
