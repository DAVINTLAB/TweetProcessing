public class TimePicker {
	public static void main(String[]args) throws StringIndexOutOfBoundsException{
		String input = "くThu Apr 13 00:00:55 +0000 2017くくVotei na opção Emilly em BBB17: Quem é sua campeã? https://t.co/QmLPiPHfCHくくThu Apr 13 00:15:06 +0000 2017くくCHAMADA DA NOITE: Vocês estão votando: #BBB17 #RedeBBB RT - #EmillyCampeãFAV - #VivianCampeãく";
		int enclosedIndex = input.indexOf("く");
		int enclosedCounter = 0;
		
		String date = "";
		String text = "";
		int keeper = 0;
		
		while (enclosedIndex != -1) { //enquanto achar く
		    enclosedCounter++; //incrementa. no primeiro く será 1
		    
		    if(enclosedCounter == 1){
		    	//o counter se refere ao く de antes de data
		    	//primeiro char da hora estará no +12
		    	//ultimo char da hora estará no +19
		    	date = input.substring(enclosedIndex + 12, enclosedIndex + 20);
		    	System.out.println(date);
		    	//capturou a data
		    }
		    
		    else if(enclosedCounter == 2){
		    	//se refere ao く que está depois da data. nothing much
		    }
		    
		    else if(enclosedCounter == 3){
		    	//se refere ao く que está antes do texto, e pode ser necessário
		    	keeper = input.indexOf(enclosedIndex); 
		    	System.out.println(keeper);
		    	//isso tá guardando a posição do início do texto
		    }
		    
		    else if(enclosedCounter == 4){
		    	//agora vem a explosão de lógica
		    	text = input.substring(keeper + 1, enclosedIndex);
		    	System.out.println(date + " " + text);
		    	//guardaremos o texto, que está entre os últimos く, sendo
		    	//keeper, o く que antecede o texto, e
		    	//enclosedIndex o atual, depois do texto
		    	
		    	//uma vez feito, zera tudo
		    	date = "";
		    	text = "";
		    	keeper = 0;
		    	
		    }
		    
		    input = input.substring(enclosedIndex + 1); 
		    //tá levando um caractere ao lado
		    
		    enclosedIndex = input.indexOf("く");
		    
		}
		System.out.println("Number of the word in the input is : " + enclosedCounter);
	
	}//main
}//classe
