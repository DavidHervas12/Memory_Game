package model;

/**
 * Classe que representaria un record en la base de dades.
 */
public class Partida {

	String usuari, timeStamp;
	int duracio;

	public String getUsuari() {
		return usuari;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public int getDuracio() {
		return duracio;
	}

	public Partida(String usuari, String timeStamp, int duracio) {
		this.usuari = usuari;
		this.timeStamp = timeStamp;
		this.duracio = duracio;
	}

	public String toString() {
		return timeStamp + "_" + usuari + "_" + duracio;
	}

}
