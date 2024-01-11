package vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Boto;
import model.Model;

/**
 * Vista del joc
 */
public class VistaMemory extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblTempsJoc;
	private JButton btnMenu;
	private JButton btnComenzar;
	private ArrayList<Boto> llistaBotons = new ArrayList<Boto>();
	private int dificultat;

	public int getDificultat() {
		return dificultat;
	}

	public ArrayList<Boto> getLlistaBotons() {
		return llistaBotons;
	}

	public JLabel getLblTempsJoc() {
		return lblTempsJoc;
	}

	public JButton getBtnMenu() {
		return btnMenu;
	}

	public JButton getBtnComenzar() {
		return btnComenzar;
	}

	public VistaMemory(int dificulat) {
		this.dificultat = dificulat;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		if (dificultat == 8) {
			setBounds(100, 100, 932, 629);
			generarBotonsFacil();
		} else {
			setBounds(100, 100, 934, 772);
			generarBotonsDificil();
		}
		contentPane.setBackground(new Color(51, 105, 56));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnMenu = new JButton("Menu");
		btnMenu.setBounds(12, 23, 97, 25);
		contentPane.add(btnMenu);

		btnComenzar = new JButton("Comenzar");
		btnComenzar.setBounds(12, 61, 97, 25);
		contentPane.add(btnComenzar);

		JPanel panel = new JPanel();
		panel.setBounds(810, 23, 69, 55);
		contentPane.add(panel);
		panel.setLayout(null);

		lblTempsJoc = new JLabel("0");
		lblTempsJoc.setBounds(10, 11, 69, 34);
		panel.add(lblTempsJoc);
		lblTempsJoc.setFont(new Font("SimSun", Font.BOLD, 16));

		setLocationRelativeTo(null);
		setVisible(true);
	}
/**
 * Métode que genera els botons del mode de joc fàcil.
 */
	public void generarBotonsFacil() {

		ImageIcon fons_carta = Model.getImageIcon("C:\\Users\\dherv\\AccesoDatos\\AE3\\assets\\fondo_carta.png", 137,
				171);
		int[] medidas_X = { 85, 280, 488, 686, 85, 280, 488, 686 };
		int[] medidas_Y = { 139, 139, 139, 139, 346, 346, 346, 346 };

		for (int i = 0; i < 8; i++) {
			Boto boto = new Boto(medidas_X[i], medidas_Y[i], 137, 171, fons_carta);
			llistaBotons.add(boto);
		}

		for (Boto boto : llistaBotons) {
			contentPane.add(boto);
		}

	}
	/**
	 * Métode que genera els botons del mode de joc difícil.
	 */
	public void generarBotonsDificil() {

		ImageIcon fons_carta = Model.getImageIcon("C:\\Users\\dherv\\AccesoDatos\\AE3\\assets\\fondo_carta.png", 105,
				131);
		int[] medidas_X = { 192, 337, 479, 619, 192, 337, 479, 619, 192, 337, 479, 619, 192, 337, 479, 619 };
		int[] medidas_Y = { 90, 90, 90, 90, 246, 246, 246, 246, 401, 401, 401, 401, 558, 558, 558, 558 };

		for (int i = 0; i < 16; i++) {
			Boto boto = new Boto(medidas_X[i], medidas_Y[i], 105, 131, fons_carta);
			llistaBotons.add(boto);
		}

		for (Boto boto : llistaBotons) {
			contentPane.add(boto);
		}

	}

}
