package servidor;

import processing.core.*;

public class EjecutableServidor extends PApplet {

	private Logica logica;

	public static void main(String[] args) {
		PApplet.main("servidor.EjecutableServidor");
	}

	@Override
	public void settings() {
		size(500, 500);
	}

	@Override
	public void setup() {
		logica = new Logica(this);
	}

	@Override
	public void draw() {
		logica.ejecutar();
	}
}
