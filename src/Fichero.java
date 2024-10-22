import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Fichero {
	
	public static void generarCSV(String rutaXML) throws ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document documento = db.parse(new File(rutaXML));
		
		BufferedWriter bw = Files.newBufferedWriter(Paths.get("D:\\PRUEBAS\\alumnos.csv"));
		
		NodeList nombres = documento.getElementsByTagName("nombre");
		NodeList horas = documento.getElementsByTagName("horas");
		NodeList profesores = documento.getElementsByTagName("profesor");
		
		for(int i=0; i<nombres.getLength(); i++) {
			bw.write(nombres.item(i).getTextContent() + ";" + 
					 horas.item(i).getTextContent() + ";" + 
					 profesores.item(i).getTextContent() + "\n");
		}
		bw.flush();
	}

	public static void generarXMLFiltrado(String rutaCSV) throws IOException, ParserConfigurationException, TransformerException {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();
			Element asignaturas = doc.createElement("asignaturas");
			doc.appendChild(asignaturas);
		   
			BufferedReader br = Files.newBufferedReader(Paths.get(rutaCSV));
			Stream<String> lineas = br.lines();
			lineas.forEach(l -> {
				String campos[] = l.split(";");
				if (Integer.parseInt(campos[1]) > 5) {
					
					Element asignatura = doc.createElement("asignatura");
					asignaturas.appendChild(asignatura);
					Element nombre = doc.createElement("nombre");
					nombre.setTextContent(campos[0]);
					asignatura.appendChild(nombre);
					
					Element horas = doc.createElement("horas");
					horas.setTextContent(campos[1]);
					asignatura.appendChild(horas);
					
					Element profesor = doc.createElement("profesor");
					profesor.setTextContent(campos[2]);
					asignatura.appendChild(profesor);
					
				}
			});
			
			StreamResult sr = new StreamResult(new File("D:\\PRUEBAS\\alumnosFiltrados.xml"));
			DOMSource dom = new DOMSource(doc);
		
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(dom, sr);
	}

}
