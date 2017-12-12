package Contador;

import java.util.Comparator;

public class PalavraComparator implements Comparator<Palavra> {
	private boolean asc;
	public PalavraComparator(boolean asc) {
		this.asc = asc;
	}
	@Override
	public int compare(Palavra p1, Palavra p2) {
		if(asc) return p1.compareTo(p2);
		return -p1.compareTo(p2);
	}

}
