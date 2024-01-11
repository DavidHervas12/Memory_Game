package vista;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 * Vista on es mostren els récords de l'aplicació.
 */
public class VistaRecords extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnMostrarRecordsFacil;
	private JButton btnMostrarRecordsDificil;
	private JButton btnMenu;
	private JEditorPane editorPane;

	public JEditorPane getEditorPane() {
		return editorPane;
	}

	public JButton getBtnMenu() {
		return btnMenu;
	}

	public JButton getBtnMostrarRecordsFacil() {
		return btnMostrarRecordsFacil;
	}

	public JButton getBtnMostrarRecordsDificil() {
		return btnMostrarRecordsDificil;
	}

	public VistaRecords() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 572, 615);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(51, 105, 56));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnMostrarRecordsFacil = new JButton("Fàcil");
		btnMostrarRecordsFacil.setBounds(163, 26, 115, 34);
		contentPane.add(btnMostrarRecordsFacil);

		btnMostrarRecordsDificil = new JButton("Difícil");
		btnMostrarRecordsDificil.setBounds(277, 26, 115, 34);
		contentPane.add(btnMostrarRecordsDificil);

		btnMenu = new JButton("Menu");
		btnMenu.setBounds(10, 11, 97, 25);
		contentPane.add(btnMenu);

		editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.setBounds(39, 81, 486, 474);
		editorPane.setContentType("text/html");
		JScrollPane scrollPane = new JScrollPane(editorPane);
		scrollPane.setBounds(39, 81, 486, 474);
		contentPane.add(scrollPane);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
