package contagem;

public class Palavra implements Comparable<Palavra> {
	public String palavra;
	public int frequencia;
	public Palavra(String palavra){
		this.palavra = palavra;
		this.frequencia = 0;
	}
	public Palavra(){	
		palavra = null;
		frequencia = 0;
	}
	@Override
	public int compareTo(Palavra palavra) {
		return this.frequencia - palavra.frequencia;
	}
	@Override
	public String toString() {
		return palavra + ":" + frequencia;
	}
}
