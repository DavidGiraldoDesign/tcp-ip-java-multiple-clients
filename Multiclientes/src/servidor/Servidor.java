package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Servidor extends Observable implements Runnable, Observer {

	/**
	 * ServerSocket que abre una comunicac servidor Un Lista de Atencion para
	 * Clientes
	 */
	private ServerSocket ss;
	private ArrayList<AtencionCliente> clientes;
	private static Servidor instance = null;
	private boolean online;

	/**
	 * El servidor recibe un obeservador en su construtor, para notificar a
	 * logica cualquier cambio
	 */
	private Servidor(Observer observador) {
		addObserver(observador);
		iniciarServerSocket();
		/* Inicializa la lista de Atencion */
		System.out.println("Server_online");
		clientes = new ArrayList<AtencionCliente>();
		this.online = true;

	}

	private void iniciarServerSocket() {
		try {
			/* Inicializa el servidor */
			ss = new ServerSocket(5000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Servidor getInstance(Observer observador) {
		if (instance == null) {
			instance = new Servidor(observador);
			new Thread(instance).start();
		}

		return instance;
	}

	@Override
	public void run() {
		while (online) {
			try {
				if (ss != null) {
					/*
					 * Entra a operacon bloquante, esperando a un nuevo cliente
					 */
					Socket nuevo_cliente = ss.accept();
					/*
					 * Crear un neuvo cliente con el socket que lo esta
					 * atendiendo, y un nuevo de identificador
					 */
					AtencionCliente nuevo = new AtencionCliente(nuevo_cliente, clientes.size());
					/*
					 * Agrega al servidor como un observador del nuevo cliente
					 * creado
					 */
					nuevo.addObserver(this);

					/*
					 * Agrega a la coleccion de clientes al nuevo cliente
					 */
					clientes.add(nuevo);
					/*
					 * Notifica a Logica de la creacion de un nuevo cliente
					 */
					setChanged();
					notifyObservers("add:" + nuevo.getId());
					clearChanged();

					new Thread(nuevo).start();
					System.out.println("se ha recibido un nuevo cliente, vamos en: " + clientes.size());

				} else {
					iniciarServerSocket();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

		if (arg instanceof String) {
			if (((String) arg).contains("off")) {
				AtencionCliente ac = (AtencionCliente) o;
				setChanged();
				notifyObservers("remove:" + ac.getId());
				clearChanged();
				clientes.remove(ac);
				System.out.println("Cantidad de clientes: " + clientes.size());
			}
		}
	}
}
