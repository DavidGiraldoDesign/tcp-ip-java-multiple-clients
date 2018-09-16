package cliente;

import processing.core.PApplet;

public class EjecutableCliente extends PApplet {

	static Cliente com;
	private Logica log;

	public static void main(String[] args) {
		PApplet.main("cliente.EjecutableCliente");
		// com = new Cliente();
		// com.start();
	}

	@Override
	public void settings() {
		size(500, 500);
	}

	@Override
	public void setup() {
		log = new Logica(this);
	}

	@Override
	public void draw() {
		log.ejecutar();
	}

}
