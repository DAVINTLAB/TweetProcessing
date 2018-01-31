import java.util.Comparator;

public class TweetKey implements Comparator {

	private int hora;
	private String texto;
	private Integer reps;

	public TweetKey(int hora, String texto) {
		this.hora = hora;
		this.texto = texto;
	}

	public Integer getReps() {
		return reps;
	}

	public void setReps(int reps) {
		this.reps = reps;
	}

	public TweetKey(String texto, Integer reps) {

		this.texto = texto;
		this.reps = reps;
	}

	public int getHora() {
		return hora;
	}

	public void setHora(int hora) {
		this.hora = hora;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Integer compare(TweetKey tk1, TweetKey tk2) {
		return tk1.getReps().compareTo(tk2.getReps());

	}

	/**
	 * Permite que TweetKey seja comparável
	 */
	@Override
	public int compare(Object o1, Object o2) {
		return 0;
	}

}