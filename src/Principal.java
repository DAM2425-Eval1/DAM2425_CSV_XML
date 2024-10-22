import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class Principal {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		boolean pulsado = false;
		Scanner scanner = new Scanner(System.in);
		int opcion = -1;

		while (true) {
			mostrarMenu(pulsado);
			opcion = leerTeclado(scanner);
			switch (opcion) {
				case 1: {
					Fichero.generarCSV("D:\\PRUEBAS\\alumnos.xml");
					pulsado = true;
					break;
				}
				case 2: {
					Fichero.generarXMLFiltrado("D:\\PRUEBAS\\alumnos.csv");
					break;
				}
				case 0:
					System.exit(0);
				default:
					System.out.println("Valor no válido: " + opcion);
			}
		}

	}

	private static int leerTeclado(Scanner scanner) {

		int opcion;
		try {
			opcion = Integer.parseInt(scanner.nextLine());
		}catch(NumberFormatException e) {
			opcion = -1;
		}
		return opcion;

	}

	private static void mostrarMenu(boolean pulsado) {

		System.out.println("1. Leer XML y generar CSV");
		if (pulsado) System.out.println("2. Leer CSV y generar XML");
		System.out.println("0. Salir");

	}


}
