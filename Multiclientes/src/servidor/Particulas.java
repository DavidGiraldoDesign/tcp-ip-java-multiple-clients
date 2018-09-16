package servidor;

import processing.core.*;

public class Particulas {
	private PApplet p;
	private int numero_cliente;
	private PVector location;
	private PVector velocity;
	private PVector acceleration;
	PVector mouse;
	private int cambio;
	// The Mover's maximum speed
	float topspeed;

	public Particulas(PApplet p, int numero_cliente) {
		// TODO Auto-generated constructor stub
		this.p = p;
		this.numero_cliente = numero_cliente;
		this.location = new PVector(p.width / 2, p.height / 2);
		this.velocity = new PVector(0, 0);
		this.topspeed = 5;
		this.cambio = (int) p.random(1, 100);
		mouse = new PVector((int) p.random(p.width), (int) p.random(p.height));
	}

	public int getNumero_cliente() {
		return numero_cliente;
	}
	public void calculo_vectores() {

		// Compute a vector that points from location to mouse
		if (p.frameCount % cambio == 0) {
			mouse = new PVector((int) p.random(p.width), (int) p.random(p.height));
			cambio = (int) p.random(50, 200);
		}

		acceleration = PVector.sub(mouse, location);
		// Set magnitude of acceleration
		acceleration.setMag((float) 0.2);

		// Velocity changes according to acceleration
		velocity.add(acceleration);
		// Limit the velocity by topspeed
		velocity.limit(topspeed);
		// Location changes by velocity
		location.add(velocity);
	}

	public void display() {
		p.noStroke();
		p.fill(255);
		p.ellipse(location.x, location.y, 48, 48);
		p.fill(0);
		p.text(numero_cliente, location.x, location.y);
	}

}
