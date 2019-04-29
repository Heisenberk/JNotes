package fr.uvsq.jnotes.function;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.uvsq.jnotes.config.Config;
import fr.uvsq.jnotes.exception.*;
import fr.uvsq.jnotes.function.Function;

public class FunctionTest {
	private Config c;
	
	@Before
	public void initialize(){
		c = new Config("target/notes-test/", "nano");
	}
	
	/**
	 * Test pour le listing avec un chemin inconnu pour le chemin du dossier contenant les notes. 
	 */
	@Test
	public void testListingPathInconnu() {
		Function f = new Function(c);
		assertEquals(f.listingString(), "Aucune note trouvee. "+"\n");
	}
	
	/**
	 * Test pour le listing avec le dossier vide. 
	 */
	@Test
	public void testListingDossierVide() {
		File file =new File("target/notes-test/");
		file.mkdir();
		Function f = new Function(c);
		assertEquals(f.listingString(), "Aucune note trouvee. "+"\n");
		file.delete();
	}
	
	/**
	 * Test pour le listing avec le dossier vide. 
	 */
	@Test
	public void testListingNote() {		
		try {
			File dossier =new File("target/notes-test/notes");
			dossier.mkdir();
			File file = new File("target/notes-test/notes/test.adoc");
			PrintWriter out=new PrintWriter(new FileWriter(file));
			Function f = new Function(c);
			assertEquals(f.listingString(), "Listing des notes : \n- test.adoc\n");
			file.delete();
			dossier.delete();
		}
		catch(Exception e) {}
	}
	
	@Test (expected=ArgumentException.class)
	public void testSearchSansArgument() {		
		String[] args= {"search"};
		Function f = new Function(c);
		f.search(args);
	}
	
	// si le dossier n'est pas suppr cela influe sur le resultat
	@Test 
	public void testSearchSansNote() {		
		String[] args= {"search"};
		Function f = new Function(c);
		String s=f.searchFile(args);
		assertEquals(s,"Aucune note trouvee. "+"\n" );
	}
	
	@Test
	public void testSearchAucunResultat() {
		File dossier1 =new File("target/notes-test-search1/");
		dossier1.mkdir();
		File dossier2 =new File("target/notes-test-search1/notes");
		dossier2.mkdir();
		File file = new File("target/notes-test-search1/notes/testsearch1.adoc");
		try {
			PrintWriter out=new PrintWriter(new FileWriter(file));
			out.println("=test recherche1");
			out.println("sarah pho");
			out.println("7 decembre 1995");
			out.println("* test search 1\n");
			out.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}

		String[] args= {"search", "valeur_inconnue"};
		Function fu = new Function(c);
		c.setPathStockage("target/notes-test-search1/");
		String s=fu.searchFile(args);
		assertEquals(s, "Aucune note ne contient cette recherche. \n");
		file.delete();
		dossier2.delete();
		dossier1.delete();
	}
	
	
//	@Test
//	public void testSearchValue() {
//		File dossier1 =new File("target/notes-test-search2/");
//		dossier1.mkdir();
//		File dossier2 =new File("target/notes-test-search2/notes");
//		dossier2.mkdir();
//		File file1 = new File("target/notes-test-search2/notes/testsearch1.adoc");
//		File file2 = new File("target/notes-test-search2/notes/testsearch2.adoc");
//		try {
//			PrintWriter out1=new PrintWriter(new FileWriter(file1));
//			out1.println("=test recherche1");
//			out1.println("sarah pho");
//			out1.println(":project: test");
//			out1.println("7 decembre 1995");
//			out1.println("* test search 1\n");
//			out1.close();
//			PrintWriter out2=new PrintWriter(new FileWriter(file2));
//			out2.println("=test recherche2");
//			out2.println("sarah pho");
//			out2.println("7 decembre 1995");
//			out2.println("* test search 2\n");
//			out2.close();
//		}
//		catch(Exception e) {
//			System.out.println(e);
//		}
//		
//		// search ":project: test"
//		String[] args= {"search", ":project: test"};
//		Function fu = new Function(c);
//		c.setPathStockage("target/notes-test-search2/");
//		String s=fu.searchFile(args);
//		assertEquals(s, "La note testsearch1.adoc contient \":project: test\" a la ligne 3.\n");
//		file1.delete();
//		file2.delete();
//		dossier2.delete();
//		dossier1.delete();
//	}
	
//	@Test
//	public void testSearch2Values() {
//		File dossier1 =new File("target/notes-test-search2/");
//		dossier1.mkdir();
//		File dossier2 =new File("target/notes-test-search2/notes");
//		dossier2.mkdir();
//		File file1 = new File("target/notes-test-search2/notes/testsearch1.adoc");
//		File file2 = new File("target/notes-test-search2/notes/testsearch2.adoc");
//		try {
//			PrintWriter out1=new PrintWriter(new FileWriter(file1));
//			out1.println("=test recherche1");
//			out1.println("sarah pho");
//			out1.println(":project: test");
//			out1.println("7 decembre 1995");
//			out1.println("* test search 1\n");
//			out1.close();
//			PrintWriter out2=new PrintWriter(new FileWriter(file2));
//			out2.println("=test recherche2");
//			out2.println("sarah pho");
//			out2.println("7 decembre 1995");
//			out2.println("* test search 2\n");
//			out2.close();
//		}
//		catch(Exception e) {
//			System.out.println(e);
//		}
//		
//		// search "test search"
//		String[] args= {"search", "test search"};
//		Function fu = new Function(c);
//		c.setPathStockage("target/notes-test-search2/");
//		String s=fu.searchFile(args);
//		assertEquals(s, "La note testsearch1.adoc contient \"test search\" a la ligne 5.\nLa note testsearch2.adoc contient \"test search\" a la ligne 4.\n");
//		file1.delete();
//		file2.delete();
//		dossier2.delete();
//		dossier1.delete();
//	}
	
	/**
	 * Test pour le delete avec une note inexistante. 
	 */
	@Test (expected=DeleteException.class)
	public void testDeleteInconnu() {		
		File dossier =new File("target/notes-test/notes");
		dossier.mkdir();
		Function f = new Function(c);
		String[] args= {"delete", "inconnu.adoc"};
		f.delete(args);
		dossier.delete();
	}
	
	/**
	 * Test pour le delete avec une note a supprimer. 
	 */
	@Test
	public void testDeleteNote() {
		try {
			File dossier =new File("target/notes-test/notes");
			dossier.mkdir();
			File file = new File("target/notes-test/notes/test.adoc");
			PrintWriter out=new PrintWriter(new FileWriter(file));
			Function f = new Function(c);
			String[] args= {"delete", "test.adoc"};
			assertEquals(f.deleteString(args), "Suppression de la note AsciiDoctor target/notes-test/notes/test.adoc\n");
			f.delete(args);
			dossier.delete();
		}
		catch(Exception e) {}
	}
	
	@Test (expected=ViewException.class)
	public void testViewSansDossier() {
		String[] args= {"view", "fichier_inconnu.adoc"};
		Function f = new Function(c);
		f.view(args);
	}
	
	@Test (expected=ViewException.class)
	public void testViewNoteInconnu() {
		File dossier =new File("target/notes-test/notes");
		dossier.mkdir();
		String[] args= {"view", "fichier_inconnu.adoc"};
		Function f = new Function(c);
		f.view(args);
		dossier.delete();
	}
	
	@Test
	public void testViewNote() {
		try {
			File dossier =new File("target/notes-test/notes");
			dossier.mkdir();
			File file = new File("target/notes-test/notes/test.adoc");
			PrintWriter out=new PrintWriter(new FileWriter(file));
			Function f = new Function(c);
			String[] args= {"view", "test.adoc"};
			assertEquals(f.findPathView(args[1]), "target/notes-test/notes/test.adoc");
			f.delete(args);
			dossier.delete();
		}
		catch(Exception e) {}
	}
	
	@Test
	public void testParam() {
		
		Config c = new Config();
		String nameApp=c.getNameAppExtern();
		String path=c.getPathStockage();
		
		Function f = new Function();
		String args1[] = {"param", "path", "./target/"};
		f.param(args1);
		String args2[] = {"param", "app", "vim"};
		f.param(args2);
		assertEquals(f.getConfig().getNameAppExtern(), "vim");
		assertEquals(f.getConfig().getPathStockage(), "./target/");
		String args3[] = {"param", "path", path};
		f.param(args3);
		String args4[] = {"param", "app", nameApp};
		f.param(args4);
		assertEquals(f.getConfig().getNameAppExtern(), nameApp);
		assertEquals(f.getConfig().getPathStockage(), path);
		
	}

}
