package servidor;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;

public class Logica implements Observer {

	private PApplet app;

	private ArrayList<Particulas> ps = new ArrayList<>();

	public Logica(PApplet app) {
		this.app = app;
		Servidor.getInstance(this);
		// servidor = new Servidor(this);
		// new Thread(servidor).start();
	}

	public void ejecutar() {
		app.background(0);

		synchronized (ps) {
			for (Particulas p : ps) {
				p.calculo_vectores();
				p.display();
			}
		}

	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof String) {

			String mns = ((String) arg);

			if (mns.contains("add")) {
				String[] cadena = mns.split(":");
				synchronized (ps) {
					ps.add(new Particulas(app, Integer.parseInt(cadena[1])));
				}
			} else if (mns.contains("remove")) {
				String[] cadena = mns.split(":");
				synchronized (ps) {
					for (Particulas p : ps) {
						if (p.getNumero_cliente() == Integer.parseInt(cadena[1])) {
							ps.remove(p);
							break;
						}
					}

				}
			}

		} else if (arg instanceof AtencionCliente) {

		}
	}

}
