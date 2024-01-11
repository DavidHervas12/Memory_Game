package controlador;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * JButton especial per a aquesta aplicació, que almacena si la carta està
 * girada o no.
 */
public class Boto extends JButton {

	boolean revelat = false;

	public boolean isRevelat() {
		return revelat;
	}

	public void setRevelat(boolean revelat) {
		this.revelat = revelat;
	}

	public Boto(int medida_x, int medida_y, int width, int height, ImageIcon fons) {
		setBackground(Color.WHITE);
		setBounds(medida_x, medida_y, width, height);
		setIcon(fons);
	}
}
