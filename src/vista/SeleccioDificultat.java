package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class SeleccioDificultat extends JFrame {
	private JPanel contentPane;
	private JButton btnFacil;
	private JButton btnDificil;

	public JButton getBtnFacil() {
		return btnFacil;
	}

	public JButton getBtnDificil() {
		return btnDificil;
	}

	public SeleccioDificultat() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 390, 242);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnFacil = new JButton("Facil");
		btnFacil.setFont(new Font("SimSun", Font.PLAIN, 15));
		btnFacil.setBounds(40, 99, 137, 46);
		contentPane.add(btnFacil);

		btnDificil = new JButton("Dificil");
		btnDificil.setFont(new Font("SimSun", Font.PLAIN, 15));
		btnDificil.setBounds(197, 99, 137, 46);
		contentPane.add(btnDificil);

		JLabel lblNewLabel = new JLabel("Selecciona dificultat");
		lblNewLabel.setFont(new Font("SimSun", Font.BOLD, 20));
		lblNewLabel.setBounds(77, 37, 247, 40);
		contentPane.add(lblNewLabel);
		setTitle("Selecciona Dificultat");
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
