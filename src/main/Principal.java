package main;

import controlador.Controlador;
import model.Model;
import vista.VistaMenu;

public class Principal {

	public static void main(String[] args) {
		Model model = new Model();
		VistaMenu menu = new VistaMenu();
		Controlador controlador = new Controlador(model, menu);

	}

}