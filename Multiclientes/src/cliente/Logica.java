package cliente;

import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;

public class Logica implements Observer {
	private PApplet app;

	public Logica(PApplet app) {
		this.app = app;
		// this.cliente = new Cliente(this);
		// new Thread(cliente).start();
		Cliente.getInstance(this);

	}

	public void ejecutar() {
		// TODO Auto-generated method stub
		app.background(0);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof String) {
			if (((String) arg).contains("servidor_outline")) {
				System.err.println("La conexion con el Servidor fallo, por favor intente nuevamente");
			}
		}

	}

}
