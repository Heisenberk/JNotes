package fr.uvsq.jnotes.index;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * BufferedReader sur le corps d'une note uniquement. 
 * Elle ignore les lignes premieres lignes de la note contenant 
 * ses differents champs de la note.
 *
 */
public class ContentBufferedReader extends BufferedReader {
	/**
	 * Constructeur du BufferedReader.
	 * Positionne directement le pointeur a la sixieme ligne
	 * pour ignorer les differents tags
	 * @param reader
	 * @throws IOException
	 */
	public ContentBufferedReader (Reader reader) throws IOException {
		super(reader);
		super.readLine();
		super.readLine();
		super.readLine();
		super.readLine();
		super.readLine();

	}

}
