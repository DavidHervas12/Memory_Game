package model;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Sorts.ascending;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.apache.commons.codec.binary.Base64;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * Al model es gestionen les operacions amb la base de dades y la logica.
 */
public class Model {

	private boolean sessioIniciada = false;

	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;
	private JSONArray coleccions;

	public boolean getSessioIniciada() {
		return sessioIniciada;
	}

	public void setSessioIniciada(boolean sessio) {
		sessioIniciada = sessio;
	}

	public Model() {
		iniciarConexio();
	}

	// ...Lógica de gestión de Usuarios...
	/**
	 * Métode encarregat de conectarse a la base de dades.
	 */
	private void iniciarConexio() {
		JSONObject obj = llegirJSON("config.json");
		if (obj != null) {
			String ip = obj.getString("ip");
			int port = obj.getInt("port");
			String database = obj.getString("database");
			coleccions = obj.getJSONArray("coleccions");
			mongoClient = new MongoClient(ip, port);
			mongoDatabase = mongoClient.getDatabase(database);
		} else {
			JOptionPane.showMessageDialog(null, "No es pot realitzar la conexio, el fitxer JSON no existeix.");
		}
	}

	/**
	 * Métode encarregat de tancar al conexió amb la base de dades.
	 */
	public void tancarConexio() {
		if (mongoClient != null) {
			mongoClient.close();
		}
	}

	/**
	 * Genera un objecte JSON a partir del fitxer amb la ruta proporcionada
	 * 
	 * @param ruta del fitxer JSON.
	 * @return objecte JSON amb les dades.
	 */
	private JSONObject llegirJSON(String ruta) {
		try (BufferedReader br = new BufferedReader(new FileReader(new File(ruta)))) {
			String contingut = "";
			String linia;
			while ((linia = br.readLine()) != null) {
				contingut += linia;
			}
			return new JSONObject(contingut);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Realitza l'autenticació de l'usuari comprovant les credencials.
	 * 
	 * @param nomUsuari nom del usuari
	 * @param clave     contrasenya
	 * @return true si es correcta | false si no es correcta
	 */
	public boolean autenticacioUsuari(String nomUsuari, String clave) {
		if (mongoDatabase != null) {
			MongoCollection<Document> coleccion = mongoDatabase.getCollection(coleccions.getString(1));
			Bson autenticacio = and(eq("user", nomUsuari), eq("pass", encriptarContrasenya(clave)));
			MongoCursor<Document> cursor = coleccion.find(autenticacio).cursor();

			return cursor.hasNext();
		}
		return false;
	}

	/**
	 * Realitza el registre del usuari
	 * 
	 * @param nomUsuari nom del usuari
	 * @param clave     contrasenya
	 */
	public void registre(String nomUsuari, String clave) {
		if (mongoDatabase != null) {
			if (!usuariExisteix(nomUsuari)) {
				MongoCollection<Document> coleccion = mongoDatabase.getCollection(coleccions.getString(1));
				Document doc = new Document();
				doc.append("user", nomUsuari);
				doc.append("pass", encriptarContrasenya(clave));
				coleccion.insertOne(doc);
				JOptionPane.showMessageDialog(null, "Usuari registrat");
			} else {
				JOptionPane.showMessageDialog(null, "El nom d'usuari ja existeix");
			}
		}
	}

	/**
	 * Comprova si el nom s'usuari ya está registrat en la base de dades.
	 * 
	 * @param nomUsuari nom del usuari
	 * @return true si está registrat | false si no está registrat
	 */
	private boolean usuariExisteix(String nomUsuari) {
		if (mongoDatabase != null) {
			MongoCollection<Document> coleccion = mongoDatabase.getCollection(coleccions.getString(1));
			MongoCursor<Document> cursor = coleccion.find(eq("user", nomUsuari)).cursor();
			boolean result = cursor.hasNext();
			return result;
		}
		return true;
	}

	/**
	 * Encripta la contrasenya a SHA-256.
	 * 
	 * @param clave
	 * @return La contrasenya encriptada.
	 */
	private String encriptarContrasenya(String clave) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(clave.getBytes());
			String claveEncriptada = "";
			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					claveEncriptada += "0";
				}
				claveEncriptada += hex;
			}
			return claveEncriptada;

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}

	// ...Lógica del juego...
	/**
	 * Recupera els strings base64 de la base de dades y les crea en una carpeta en
	 * local, a més de retornar una llista dels noms de les rutes.
	 * 
	 * @param numImatges el nombre d'imatges que cal generar.
	 * @return llista dels noms de les rutes.
	 * @throws IOException
	 */
	public ArrayList<String> obtenirImatges(int numImatges) throws IOException {
		buidarCarpeta("img");
		ArrayList<String> llistaRutes = new ArrayList<String>();
		if (mongoDatabase != null) {
			MongoCollection<Document> coleccion = mongoDatabase.getCollection(coleccions.getString(0));
			MongoCursor<Document> cursor = coleccion.find().iterator();
			int cont = 0;

			Document samplePipeline = new Document("$sample", new Document("size", numImatges));
			AggregateIterable<Document> randomDocuments = coleccion.aggregate(List.of(samplePipeline));

			for (Document doc : randomDocuments) {
				JSONObject obj = new JSONObject(doc.toJson());
				String ruta = "img\\" + obj.getString("id");
				llistaRutes.add(ruta);
				llistaRutes.add(ruta);
				String stringBase64 = obj.getString("base64");
				byte[] btDataFile = Base64.decodeBase64(stringBase64);
				BufferedImage imatge = ImageIO.read(new ByteArrayInputStream(btDataFile));
				ImageIO.write(imatge, "png", new File(ruta));
			}
		}
		return llistaRutes;
	}

	/**
	 * Elimina tots els fitxers del fitxers de la ruta seleccionada.
	 * 
	 * @param ruta del directori a buidar.
	 */
	private void buidarCarpeta(String ruta) {
		File dir = new File(ruta);
		if (dir.isDirectory()) {
			File[] fitxers = dir.listFiles();
			for (File fitxer : fitxers) {
				if (fitxer.exists()) {
					fitxer.delete();
				}
			}
		}

	}

	/**
	 * Guarda el resultat a la base de dades una vegada finalitzada la partida.
	 * 
	 * @param usuari
	 * @param dificultat
	 * @param timeStamp
	 * @param duracio
	 */
	public void guardarResultat(String usuari, int dificultat, String timeStamp, int duracio) {
		if (mongoDatabase != null) {
			MongoCollection<Document> coleccion = mongoDatabase.getCollection(coleccions.getString(2));
			Document doc = new Document();
			doc.append("usuario", usuari);
			doc.append("dificultad", dificultat);
			doc.append("timestamp", timeStamp);
			doc.append("duracion", duracio);
			coleccion.insertOne(doc);
			JOptionPane.showMessageDialog(null, "Record registrat");
		}
	}

	/**
	 * Obté el ImageIcon de la imatge amb la ruta y el tamany especificat.
	 * 
	 * @param ruta
	 * @param width
	 * @param height
	 * @return ImageIcon de la imatge amb la ruta y el tamany especificat.
	 */
	public static ImageIcon getImageIcon(String ruta, int width, int height) {
		try {
			File fitxer = new File(ruta);
			Image imatge = ImageIO.read(fitxer);
			return new ImageIcon(imatge.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// ...Hall of fame...
	/**
	 * Obté els records de la base de dades ordenats de menos a major.
	 * 
	 * @param dificultad
	 * @return llista de records.
	 */
	public ArrayList<Partida> obtenirRecords(int dificultad) {
		ArrayList<Partida> llistaRecords = new ArrayList<Partida>();
		if (mongoDatabase != null) {
			MongoCollection<Document> coleccion = mongoDatabase.getCollection(coleccions.getString(2));
			MongoCursor<Document> cursor = coleccion.find(eq("dificultad", dificultad)).iterator();
			while (cursor.hasNext()) {
				JSONObject obj = new JSONObject(cursor.next().toJson());
				llistaRecords
						.add(new Partida(obj.getString("timestamp"), obj.getString("usuario"), obj.getInt("duracion")));
			}
			Collections.sort(llistaRecords, new Comparator<Partida>() {
				public int compare(Partida r1, Partida r2) {
					return Integer.compare(r1.getDuracio(), r2.getDuracio());
				}
			});
		}
		return llistaRecords;
	}

	/**
	 * Obté la máxima puntuació de la partida
	 * 
	 * @param dificultat
	 * @return la minima duracio.
	 */
	public int obtindreMaximaPuntuacio(int dificultat) {
		int menorQuantitat = Integer.MAX_VALUE;
		if (mongoDatabase != null) {
			String campo = "duracion";
			MongoCollection<Document> coleccion = mongoDatabase.getCollection(coleccions.getString(2));
			Document doc = new Document();
			FindIterable<Document> result = coleccion.find(eq("dificultad", dificultat)).sort(ascending(campo))
					.limit(1);
			MongoCursor<Document> cursor = result.iterator();
			if (cursor.hasNext()) {
				menorQuantitat = cursor.next().getInteger("duracion");
			}
		}
		return menorQuantitat;
	}

}
