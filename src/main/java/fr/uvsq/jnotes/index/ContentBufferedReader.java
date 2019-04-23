package fr.uvsq.jnotes.index;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class ContentBufferedReader extends BufferedReader {
	public ContentBufferedReader (Reader reader) throws IOException {
		super(reader);
		//Reader that skips the 5 first lines because they are not content
		super.readLine();
		super.readLine();
		super.readLine();
		super.readLine();
		super.readLine();

	}

}
