package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import java.util.Observable;
import java.util.Observer;

public class Cliente extends Observable implements Runnable {

	private static Cliente instance = null;
	private Socket s;
	private boolean online;

	private Cliente(Observer observer) {
		addObserver(observer);
		iniciarSocket();
	}

	private void iniciarSocket() {
		// TODO Auto-generated method stub
		try {
			s = new Socket(InetAddress.getByName("127.0.0.1"), 5000);
			peticion_conexion();
			online = true;
		} catch (IOException e) {
			// e.printStackTrace();
			setChanged();
			notifyObservers("servidor_outline");
			clearChanged();
		}
	}

	public static Cliente getInstance(Observer observer) {
		if (instance == null) {
			instance = new Cliente(observer);
			new Thread(instance).start();
		}

		return instance;
	}

	@Override
	public void run() {
		while (online) {
			recibirString();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void recibirString() {
		try {
			DataInputStream entrada = new DataInputStream(s.getInputStream());
			String mensaje = entrada.readUTF();
			System.out.println("se recibio en el cliente: " + mensaje);
		} catch (IOException e) {
			online = false;
			setChanged();
			notifyObservers("servidor_outline");
			clearChanged();

		}
	}

	public void enviarString(String mensaje) {
		try {
			DataOutputStream salida = new DataOutputStream(s.getOutputStream());

			salida.writeUTF(mensaje);
			salida.flush();
			System.out.println("se envio: " + mensaje);
		} catch (IOException e) {
			e.printStackTrace();
			online = false;
		}
	}

	private void peticion_conexion() {
		// TODO Auto-generated method stub
		try {
			DataOutputStream salida = new DataOutputStream(s.getOutputStream());
			// int envio = (int) (Math.random() * 255);
			salida.writeUTF("conect");
			salida.flush();
			System.out.println("se envio una peticion de coneccion");
		} catch (IOException e) {
			e.printStackTrace();
			online = false;
			System.out.println("moriiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
		}
	}

}
