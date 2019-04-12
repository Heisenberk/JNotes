package fr.uvsq.jnotes.function;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.uvsq.jnotes.config.Config;
import fr.uvsq.jnotes.note.Note;


public class IndexTest {
	private Config c;
	
	@Before
	public void initialize() throws Exception{
		Path outPath=Paths.get("target/note_indexTest.adoc");
		try(
			BufferedWriter out = Files.newBufferedWriter(outPath);
		){
			out.write("= Reunion CDS");
			out.newLine();
			out.write("Stephane Lopes");
			out.newLine();
			out.write("22/02/2018");
			out.newLine();
			out.write(":context: work");
			out.newLine();
			out.write(":project: cds");
			out.newLine();
			out.newLine();
			out.write("* Edgar Allen Poe");
			out.newLine();
			out.write("* Sheri S. Tepper");
			out.newLine();
			out.write("* Bill Bryson");
			out.newLine();
		}
		catch(Exception e) {
			System.out.println("Erreur Initialize IndexTest");
		}
	}
	
	/**
	 * Test pour le listing avec un chemin inconnu pour le chemin du dossier contenant les notes. 
	 */
	/*@Test
	public void testListingPathInconnu() {
		Index i = new Index();
		Note n =i.readNote("target/note_indexTest.adoc");
		assertEquals(n.getTitle(), "Reunion CDS");
		assertEquals(n.getAuthor(), "Stephane Lopes");
		assertEquals("22/02/2018", n.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		assertEquals(n.getContext(), "work");
		assertEquals(n.getProject(), "cds");
	}*/
	
	@After 
	public void finalize() {
		File fichier = new File("target/note_indexTest.adoc");
		fichier.delete();
	}
	
}