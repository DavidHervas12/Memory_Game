package vista;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import model.Model;

/**
 * Vista que representa la pantalla inicial de l'aplicació
 */
public class VistaMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnJugar;
	private JButton btnAbrirIniciSessio;
	private JButton btnAbrirRegistrarse;
	private JButton btnAbrirRecords;
	private JButton btnTancarSessio;
	private JPanel conectat;
	private JPanel panel;
	private FormDades formIniciSessio;
	private FormDades formRegistre;
	private SeleccioDificultat seleccionarDificultat;

	public void setSeleccionarDificultat(SeleccioDificultat seleccionarDificultat) {
		this.seleccionarDificultat = seleccionarDificultat;
	}

	public void setFormIniciSessio(FormDades formIniciSessio) {
		this.formIniciSessio = formIniciSessio;
	}

	public void setFormRegistre(FormDades formRegistre) {
		this.formRegistre = formRegistre;
	}

	public VistaMenu() {
		setTitle("Inici de Sessio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 892, 690);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 105, 56));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblTitol = new JLabel("");
		lblTitol.setBounds(238, 43, 436, 80);
		ImageIcon imatgeIcona = Model.getImageIcon("C:\\Users\\dherv\\AccesoDatos\\AE3\\assets\\titol.png", 436, 80);
		contentPane.setLayout(null);
		lblTitol.setIcon(imatgeIcona);
		contentPane.add(lblTitol);

		panel = new JPanel();
		panel.setBounds(270, 174, 360, 418);
		panel.setBorder(new CompoundBorder());
		panel.setBackground(Color.WHITE);
		contentPane.add(panel);
		panel.setLayout(null);

		btnJugar = new JButton("Jugar");
		btnJugar.setEnabled(false);
		btnJugar.setBounds(72, 55, 213, 38);
		panel.add(btnJugar);
		btnJugar.setFont(new Font("SimSun", Font.BOLD, 18));

		btnAbrirIniciSessio = new JButton("Iniciar Sessió");
		btnAbrirIniciSessio.setBounds(72, 115, 213, 38);
		btnAbrirIniciSessio.setFont(new Font("SimSun", Font.BOLD, 18));
		panel.add(btnAbrirIniciSessio);

		btnAbrirRegistrarse = new JButton("Registrarse");
		btnAbrirRegistrarse.setBounds(72, 175, 213, 38);
		panel.add(btnAbrirRegistrarse);
		btnAbrirRegistrarse.setFont(new Font("SimSun", Font.BOLD, 18));

		btnAbrirRecords = new JButton("Hall of Fame");
		btnAbrirRecords.setEnabled(false);
		btnAbrirRecords.setBounds(72, 236, 213, 38);
		panel.add(btnAbrirRecords);
		btnAbrirRecords.setFont(new Font("SimSun", Font.BOLD, 18));

		conectat = new JPanel();
		conectat.setBounds(0, 0, 360, 21);
		panel.add(conectat);
		conectat.setBackground(Color.RED);

		btnTancarSessio = new JButton("Tancar Sessió");
		btnTancarSessio.setEnabled(false);
		btnTancarSessio.setFont(new Font("SimSun", Font.BOLD, 18));
		btnTancarSessio.setBounds(72, 299, 213, 38);
		panel.add(btnTancarSessio);

		setLocationRelativeTo(null);
		setVisible(true);

	}

	public JButton getBtnTancarSessio() {
		return btnTancarSessio;
	}

	public JButton getbtnAbrirIniciSessio() {
		return btnAbrirIniciSessio;
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public JButton getBtnJugar() {
		return btnJugar;
	}

	public JButton getBtnAbrirRegistrarse() {
		return btnAbrirRegistrarse;
	}

	public JButton getBtnAbrirRecords() {
		return btnAbrirRecords;
	}

	public JPanel getConectat() {
		return conectat;
	}

	public JPanel getPanel() {
		return panel;
	}

	public FormDades getFormIniciSessio() {
		return formIniciSessio;
	}

	public FormDades getFormRegistre() {
		return formRegistre;
	}

	public SeleccioDificultat getSeleccionarDificultat() {
		return seleccionarDificultat;
	}

}
