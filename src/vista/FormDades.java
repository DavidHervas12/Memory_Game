package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class FormDades extends JFrame {

	private JTextField txtNomUsuari;
	private JPasswordField passContrasenya;
	private JButton btnEnviar;
	private JPanel contentPane;

	public JTextField getTxtNomUsuari() {
		return txtNomUsuari;
	}

	public JPasswordField getPassContrasenya() {
		return passContrasenya;
	}

	public JButton getBtnEnviar() {
		return btnEnviar;
	}

	public FormDades(String title) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 460, 326);
		setBackground(SystemColor.activeCaption);
		contentPane = new JPanel();
		contentPane.setBounds(303, 70, 316, 263);
		contentPane.setBackground(SystemColor.activeCaption);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtNomUsuari = new JTextField();
		txtNomUsuari.setBounds(84, 67, 284, 32);
		txtNomUsuari.setFont(new Font("SimSun", Font.PLAIN, 16));
		contentPane.add(txtNomUsuari);
		txtNomUsuari.setColumns(10);

		passContrasenya = new JPasswordField();
		passContrasenya.setBounds(84, 134, 284, 32);
		contentPane.add(passContrasenya);

		btnEnviar = new JButton("Entrar");
		btnEnviar.setFont(new Font("SimSun", Font.PLAIN, 16));
		btnEnviar.setBackground(Color.WHITE);
		btnEnviar.setBounds(153, 204, 130, 32);
		contentPane.add(btnEnviar);

		JLabel lblNomUsuari = new JLabel("Nom d'usuari:");
		lblNomUsuari.setFont(new Font("SimSun", Font.PLAIN, 16));
		lblNomUsuari.setBounds(84, 43, 113, 25);
		contentPane.add(lblNomUsuari);

		JLabel lblContrasenya = new JLabel("Contrasenya:");
		lblContrasenya.setFont(new Font("SimSun", Font.PLAIN, 16));
		lblContrasenya.setBounds(84, 110, 113, 25);
		contentPane.add(lblContrasenya);
		setTitle(title);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
