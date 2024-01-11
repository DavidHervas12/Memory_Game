package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.text.html.HTMLDocument;

import model.Model;
import model.Partida;
import vista.FormDades;
import vista.SeleccioDificultat;
import vista.VistaMemory;
import vista.VistaMenu;
import vista.VistaRecords;

public class Controlador {

	private Model model;
	private VistaMenu menu;
	private VistaMemory memory;
	private VistaRecords vistaRecords;
	private final int DIFICULTAT_FACIL = 8;
	private final int DIFICULTAT_DIFICIL = 16;
	private int puntuacio = 0;
	private int cartesGirades = 0;
	private int segons;
	private Boto botoAnterior;
	private Timer timer;
	private String nomUsuari;

	/**
	 * Constructor de la clase Controlador.
	 * 
	 * @param model
	 * @param menu
	 */
	public Controlador(Model model, VistaMenu menu) {
		this.model = model;
		this.menu = menu;
		abrirInciarSessio();
		abrirRegistrarse();
		abrirJuego();
		abrirVistaRecords();
		tancarSessio();
		tancarConexio();
	}

	// Lógica Inicio sesión.

	public void abrirInciarSessio() {
		menu.getbtnAbrirIniciSessio().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu.setFormIniciSessio(new FormDades("Iniciar Sessió"));
				iniciarSessio();
			}
		});
	}

	/**
	 * Realitza l'inici de sessió y comprova si es correcte o no.
	 */
	private void iniciarSessio() {
		menu.getFormIniciSessio().getBtnEnviar().addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if (model.autenticacioUsuari(menu.getFormIniciSessio().getTxtNomUsuari().getText(),
						menu.getFormIniciSessio().getPassContrasenya().getText())) {
					nomUsuari = menu.getFormIniciSessio().getTxtNomUsuari().getText();
					menu.getFormIniciSessio().dispose();
					JOptionPane.showMessageDialog(null, "Benvingut " + nomUsuari);
					menu.getBtnJugar().setEnabled(true);
					menu.getbtnAbrirIniciSessio().setEnabled(false);
					menu.getBtnAbrirRegistrarse().setEnabled(false);
					menu.getBtnAbrirRecords().setEnabled(true);
					menu.getBtnTancarSessio().setEnabled(true);
					menu.getConectat().setBackground(Color.GREEN);
					model.setSessioIniciada(true);
				} else {
					JOptionPane.showMessageDialog(null, "Les dades d'atenticacion no son válides");
				}
			}
		});
	}

	public void abrirRegistrarse() {
		menu.getBtnAbrirRegistrarse().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu.setFormRegistre(new FormDades("Registre"));
				registrarse();
			}
		});
	}

	/**
	 * Realitza el registre de l'usuari
	 */
	private void registrarse() {
		menu.getFormRegistre().getBtnEnviar().addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				model.registre(menu.getFormRegistre().getTxtNomUsuari().getText(),
						menu.getFormRegistre().getPassContrasenya().getText());
			}
		});
	}

	// Lógica del juego.

	public void abrirJuego() {
		menu.getBtnJugar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu.setSeleccionarDificultat(new SeleccioDificultat());
				abrirJuegoFacil();
				abrirJuegoDificil();
			}
		});
	}

	public void abrirJuegoFacil() {
		menu.getSeleccionarDificultat().getBtnFacil().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu.getSeleccionarDificultat().dispose();
				menu.setVisible(false);
				memory = new VistaMemory(DIFICULTAT_FACIL);
				tornarAlMenuDesdeMemory();
				comenzarPartida(DIFICULTAT_FACIL);
			}
		});
	}

	public void abrirJuegoDificil() {
		menu.getSeleccionarDificultat().getBtnDificil().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu.getSeleccionarDificultat().dispose();
				menu.setVisible(false);
				memory = new VistaMemory(DIFICULTAT_DIFICIL);
				tornarAlMenuDesdeMemory();
				comenzarPartida(DIFICULTAT_DIFICIL);
			}
		});
	}

	public void comenzarPartida(int dificultat) {
		memory.getBtnComenzar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciarTemps();
				if (dificultat == 8) {
					generarBotons(dificultat, 137, 171);
				} else {
					generarBotons(dificultat, 105, 131);
				}
				memory.getBtnComenzar().setEnabled(false);
				memory.getBtnMenu().setEnabled(false);
			}
		});
	}

	/**
	 * Genera els botons de la partida amb la lógica del joc segons la dificultat
	 * indicada.
	 * 
	 * @param cantidad
	 * @param width
	 * @param height
	 */
	private void generarBotons(int cantidad, int width, int height) {
		ArrayList<String> llistaImatges = new ArrayList<>();
		ImageIcon fons_carta = Model.getImageIcon("C:\\Users\\dherv\\AccesoDatos\\AE3\\assets\\fondo_carta.png", width,
				height);

		try {
			llistaImatges = model.obtenirImatges(cantidad / 2);
			System.out.println("Imágenes obtenidas");
		} catch (IOException e) {
			e.printStackTrace();
		}

		Collections.shuffle(llistaImatges);

		for (int i = 0; i < cantidad; i++) {
			Boto boto = memory.getLlistaBotons().get(i);
			boto.setName(llistaImatges.get(i));
			boto.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!boto.isRevelat() && cartesGirades < 2) {
						cartesGirades++;
						boto.setIcon(Model.getImageIcon(boto.getName(), width, height));
						if (botoAnterior == null) {
							botoAnterior = boto;
							boto.setRevelat(true);
						} else {
							if (!botoAnterior.getName().equals(boto.getName())) {
								Timer timer1 = new Timer(400, new ActionListener() {
									public void actionPerformed(ActionEvent evt) {
										botoAnterior.setIcon(fons_carta);
										boto.setIcon(fons_carta);
										botoAnterior.setRevelat(false);
										boto.setRevelat(false);
										botoAnterior = null;
										System.out.println("Fallo");
										cartesGirades = 0;
										((Timer) evt.getSource()).stop();
									}
								});
								timer1.setRepeats(false);
								timer1.start();
							} else {
								puntuacio++;
								System.out.println("Puntuación: " + puntuacio);
								boto.setRevelat(true);
								botoAnterior = null;
								cartesGirades = 0;
								if ((cantidad == DIFICULTAT_FACIL && puntuacio == 4)
										|| (cantidad == DIFICULTAT_DIFICIL && puntuacio == 8)) {
									System.out.println("Victoria!! en " + segons + " segundos");
									timer.stop();
									if (segons < model.obtindreMaximaPuntuacio(cantidad)) {
										JOptionPane.showMessageDialog(null, "Felicitats has superat el record!!");
									}
									int respuesta = JOptionPane.showConfirmDialog(null,
											"¿Deseas guardar los datos de la partida?", "Guardar partida",
											JOptionPane.YES_NO_OPTION);
									if (respuesta == JOptionPane.YES_OPTION) {
										model.guardarResultat(nomUsuari, cantidad, generarTimeStamp(), segons);
									}
									memory.getBtnComenzar().setEnabled(true);
									memory.getBtnMenu().setEnabled(true);
									reiniciarPartida(fons_carta);
								}
							}
						}

					}
				}
			});
		}
	}

	/**
	 * Reinicia la lógica del joc per a poder jugar una nova partida.
	 * 
	 * @param fons_carta
	 */
	public void reiniciarPartida(ImageIcon fons_carta) {
		puntuacio = 0;
		botoAnterior = null;
		cartesGirades = 0;
		memory.getLblTempsJoc().setText("0");
		for (Boto boto : memory.getLlistaBotons()) {
			boto.setIcon(fons_carta);
			boto.setRevelat(false);
			for (ActionListener al : boto.getActionListeners()) {
				boto.removeActionListener(al);
			}
		}
	}

	/**
	 * Inicia el timer que controla el temps de joc.
	 */
	public void iniciarTemps() {
		segons = 0;
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				segons++;
				memory.getLblTempsJoc().setText(String.valueOf(segons));
			}
		});
		timer.start();

	}

	public void tornarAlMenuDesdeMemory() {
		memory.getBtnMenu().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memory.dispose();
				menu.setVisible(true);
			}
		});
	}

	public void tornarAlMenuDesdeRecords() {
		vistaRecords.getBtnMenu().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vistaRecords.dispose();
				menu.setVisible(true);
			}
		});
	}

	/**
	 * Genera un timeStamp.
	 * 
	 * @return timeStamp.
	 */
	private String generarTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy_HHmmss");
		return sdf.format(new Date());
	}

	// Hall of fame.

	public void abrirVistaRecords() {
		menu.getBtnAbrirRecords().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu.setVisible(false);
				vistaRecords = new VistaRecords();
				mostrarRecordsFacil();
				mostrarRecordsDificil();
				tornarAlMenuDesdeRecords();
			}
		});
	}

	public void mostrarRecordsFacil() {
		vistaRecords.getBtnMostrarRecordsFacil().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarRecords(DIFICULTAT_FACIL);
			}
		});
	}

	public void mostrarRecordsDificil() {
		vistaRecords.getBtnMostrarRecordsDificil().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarRecords(DIFICULTAT_DIFICIL);
			}
		});
	}

	/**
	 * Métode per a mostrar els records en una tabla html.
	 * 
	 * @param difucultat
	 */
	private void mostrarRecords(int difucultat) {
		vistaRecords.getEditorPane().setText("");
		String tabla = "";

		tabla += "<html><body><div style='width:100%;'><table border='1' style='width:100%;'><tr><th>TimeStamp</th><th>Usuari</th><th>Duració</th></tr>";

		for (Partida p : model.obtenirRecords(difucultat)) {
			tabla += "<tr><td>" + p.getUsuari() + "</td><td>" + p.getTimeStamp() + "</td><td>" + p.getDuracio()
					+ "</td></tr>";
		}

		tabla += "</table></div></body></html>";

		vistaRecords.getEditorPane().setText(tabla);
	}

	public void tancarSessio() {
		menu.getBtnTancarSessio().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Sessió tancada " + nomUsuari);
				nomUsuari = null;
				menu.getBtnJugar().setEnabled(false);
				menu.getbtnAbrirIniciSessio().setEnabled(true);
				menu.getBtnAbrirRegistrarse().setEnabled(true);
				menu.getBtnAbrirRecords().setEnabled(false);
				menu.getConectat().setBackground(Color.RED);
				menu.getBtnTancarSessio().setEnabled(false);
				model.setSessioIniciada(false);
			}
		});
	}

	public void tancarConexio() {
		menu.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				model.tancarConexio();
				System.out.println("Conexio tancada");
			}
		});
	}

}
