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
import java.util.Scanner;
import java.io.File;

public class App {
	
	private static String ENCODING = "UTF-8";  //ISO-8859-1 ou UTF-8
	//private static String ARQUIVO_A_SER_LIDO = "C:\\Users\\eduardo\\Desktop\\testing\\inicial.txt";
	//se for usado em Linux:
	private static String ARQUIVO_A_SER_LIDO = "//home//jmfantin//Desktop//testing//fabric.txt";
	private static boolean PRESERVAR_HASHTAGS = true;
	private static Integer NUMERO_DE_PALAVRAS = 600;
	private static boolean ORDEM_ASCENDENTE = false;

	public static void main(String args[]) throws IOException{		

		Scanner in = new Scanner(System.in);
		System.out.println("Só para garantir:\n"
				+ "Esse "+ ARQUIVO_A_SER_LIDO +" é o caminho correto? S/N");
		String ans = in.next();
		if(ans.toLowerCase().charAt(0) == 's'){
			menu();
		} else {
			System.out.println("Corrija o caminho na constante e execute o programa de novo :)");
		}
		in.close();
	}
	
	public static void menu() throws IOException{
		Scanner in = new Scanner(System.in);
		int op = 0;
		System.out.println("O que você deseja fazer com o arquivo de tweets extraídos do banco de dados?\n"
				+ "1) Gerar inputs para o Gráfico Interativo (c3.js);\n"
				+ "2) Processar/refinar os dados para usá-los no Tableau ou outros programas.\n"
				+ "Entre com a sua opção: ");
		op = in.nextInt();
		switch(op){
			case 1: 
				System.out.println("Para isso, cada tweet no arquivo deve estar disposto da seguinte forma: "
						+ "created_at_datetime, text\n"
						+ "ex: ^|2017-09-07 10:21:54^|^|O que daria para bancar com dinheiro"
						+ " atribuído a #Geddel? https://t.co/ZTP9556fcD https://t.co/jTjBPMB3eB^|\n\n"
						+ "Os tweets estão armazenados dessa forma? S/N");
				String ans = in.next();
				if(ans.toLowerCase().charAt(0) == 's'){
					geraInputs();	
				} else {
					System.out.println("Exporte outro arquivo de tweets nesse formato e execute o programa de novo :)");
				}
				break;
			case 2: 
				processa();
				refina();
				break;
		}
		
		in.close();
	}
	
	public static void processa(){

		long tempo = System.currentTimeMillis();
		HashMap<String, Palavra> palavras = new HashMap<String, Palavra>();
		System.out.println("Lendo arquivo \"" + ARQUIVO_A_SER_LIDO + "\"...");
		try(BufferedReader in = new BufferedReader(new InputStreamReader( new FileInputStream(ARQUIVO_A_SER_LIDO), ENCODING));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(ARQUIVO_A_SER_LIDO+"_processado.txt"), ENCODING))){
			
			System.out.print("- Limpeza de arquivo e contagem de palavras...");
			while(in.ready()){
				String linha = limpaLinhas(in.readLine());
				String[] tokensNaLinha = linha.split("\\s+");
				for(String token: tokensNaLinha){
					if(token.equals("")) continue; 
					Palavra palavra;
					if(palavras.containsKey(token)) palavra = palavras.get(token);
					else palavra = new Palavra(token);
					palavra.frequencia++;
					palavras.put(token, palavra);
				}	
			}
			
			System.out.print(" completa.\n- Leitura completa.\n- Organização de linhas...");
			
			ArrayList<Palavra> palavrasOrdenadas = new ArrayList<Palavra>(palavras.values());
			Collections.sort(palavrasOrdenadas, new PalavraComparator(ORDEM_ASCENDENTE));
			
			System.out.print(" completa.\n- Escrita de arquivo...");
			
			for(Palavra palavra: palavrasOrdenadas){
				if(NUMERO_DE_PALAVRAS == 0) break;
				out.write(palavra.toString() + "\r\n");
				NUMERO_DE_PALAVRAS--;
			}		
			
		} catch (IOException e) {
			System.out.println("Deu problema, que pena :<");
			e.printStackTrace();
		}
		
		System.out.println(" completa. \nTempo de execução: " + (System.currentTimeMillis() - tempo) + "ms");
		System.out.println("\n[Processo concluído com sucesso]\n\nEstá disponível o arquivo único com a contagem de PALAVRAS, HASHTAGS e PERFIS");
	}
	
	public static void refina() throws IOException{
		Scanner in = new Scanner(System.in);
		System.out.println("\nDeseja separar as PALAVRAS, HASHTAGS e PERFIS em três arquivos distintos? S/N");
		String ans = in.next();
		if(ans.toLowerCase().charAt(0) == 's'){
			System.out.println("Efetuando a separação...");
			
			//PALAVRAS --> Remover.java
			File wordsInputFile = new File(ARQUIVO_A_SER_LIDO+"_processado.txt");
			Remover wordsCOUNT = new Remover(wordsInputFile);
			wordsCOUNT.pegaPalavras();
			
			//HASHTAGS --> Selector.java (#)
			File hashtagsInputFile = new File(ARQUIVO_A_SER_LIDO+"_processado.txt");
			Selector hashtagsCOUNT = new Selector(hashtagsInputFile, "#");
			hashtagsCOUNT.select();
			
			//PERFIS --> Selector.java (@)
			File profilesInputFile = new File(ARQUIVO_A_SER_LIDO+"_processado.txt");
			Selector profilesCOUNT = new Selector(profilesInputFile, "@");
			profilesCOUNT.select();
			System.out.println("Separação completa.\n");
			
			System.out.println("Deseja formatar esses arquivos de palavras, hashtags e perfis no formato necessário\n"
					+ "para gerar tag clouds (ou outras visualizacoes) no Tableau (ou em outros programas)? S/N");
			ans = in.next();
			if(ans.toLowerCase().charAt(0) == 's'){
				System.out.println("Fazendo a separação de palavras, hashtags e perfis...");
				
				//IMPRIMINDO PALAVRAS
				Repeater wordsREP = new Repeater(wordsCOUNT.outputFile); //entrada para repetir palavras
				wordsREP.repeat();
	
				//IMPRIMINDO HASHTAGS
				Repeater hashtagsREP = new Repeater(hashtagsCOUNT.outputFile); //entrada para repetir hashtags
				hashtagsREP.repeat();
				
				//IMPRIMINDO PERFIS
				Repeater profilesREP = new Repeater(profilesCOUNT.outputFile); //entrada para repetir perfis
				profilesREP.repeat();
				
				System.out.println("Repetições completas.\n");
			}
		}
		System.out.println("Confira os novos arquivos na pasta informada.");
		in.close();
	}
	
	public static void geraInputs() throws IOException{
		File inFile = new File(ARQUIVO_A_SER_LIDO);
		GeradorC3 graphBuilder = new GeradorC3(inFile); 
		graphBuilder.rankingTweets();
		graphBuilder.rankingPerfis();
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

	private static String espaceiaHashtags(String linha) {
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