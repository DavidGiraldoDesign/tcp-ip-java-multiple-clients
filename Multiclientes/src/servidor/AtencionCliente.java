package servidor;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.FilterInputStream;
import java.io.IOException;

import java.net.Socket;

import java.util.Observable;

public class AtencionCliente extends Observable implements Runnable {
	/*
	 * Un socket de escucha y escritura, que conecta al servidor con el cliente
	 */
	private Socket socket_atencion;
	private boolean online;
	private int id;

	public AtencionCliente(Socket referencia, int id) {
		this.socket_atencion = referencia;
		this.online = true;
		this.id = id;
		responder_peticion_cliente();
	}

	public int getId() {
		return id;
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

	private void responder_peticion_cliente() {
		// TODO Auto-generated method stub
		DataInputStream entrada = null;
		try {
			entrada = new DataInputStream(socket_atencion.getInputStream());
			String peticion = entrada.readUTF();
			if (peticion.contains("conect")) {
				System.out.println("conexion_aceptada");
			}
		} catch (IOException e) {
			desconectar_cliente(entrada);
		}

	}

	private void desconectar_cliente(DataInput entrada) {
		// TODO Auto-generated method stub
		setChanged();
		System.out.println("conetion lost with:" + id);
		online = false;
		try {
			((FilterInputStream) entrada).close();
			socket_atencion.close();
			socket_atencion = null;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		notifyObservers("off");
		clearChanged();
	}

	private void recibirString() {
		DataInputStream entrada = null;
		try {
			entrada = new DataInputStream(socket_atencion.getInputStream());
			int val = Integer.parseInt(entrada.readUTF());
			System.out.println("se recibio: " + val);
		} catch (IOException e) {
			desconectar_cliente(entrada);
		}
	}

	public void enviarString(String mensaje) {
		try {
			DataOutputStream salida = new DataOutputStream(socket_atencion.getOutputStream());
			salida.writeUTF(mensaje);
			System.out.println("Envio mensaje _ cliente: " + id);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
