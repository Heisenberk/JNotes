package fr.uvsq.jnotes.function;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.uvsq.jnotes.config.Config;
import fr.uvsq.jnotes.exception.*;
import fr.uvsq.jnotes.function.Function;
import fr.uvsq.jnotes.index.Indexer;
import fr.uvsq.jnotes.utils.Helper;

/**
 * @author dell
 *
 */
public class FunctionTest {
	/**
	 * Configuration utilisee dans les test unitaires
	 */
	private Config c;
	private static String indexDir = "target/index-test";
	/**
	 * Fonction qui initialise la configuration c avant chaque test
	 */
	@Before
	public void initialize(){
		c = new Config("target/notes-test/", "nano");
		c.setPathIndex(indexDir);
	}
	
	@After
	public void finish(){
		File d3 = new File(indexDir);
		Helper.deleteFolder(d3);
	}
	/**
	 * Cree sur disque une note avec le champs passe en parametre
	 * @param f le fichier qui contient la note
	 * @param tag le tag du champs
	 * @param value la valeur du champs
	 * @throws IOException
	 */
	private void createNote(File f, String tag, String value) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(f));
		
		if (tag.compareTo("title") == 0)
			out.println("= "+value);
		else
			out.println("= test recherche");
		
		if (tag.compareTo("author") == 0)
			out.println(value);
		else
			out.println("Sarah Pho");
		
		if (tag.compareTo("date") == 0)
			out.println(value);
		else
			out.println("07/12/1995");
		
		if (tag.compareTo("context") == 0)
			out.println(":project: " + value);
		else
			out.println(":context: testContext");
		
		if (tag.compareTo("project") == 0)
			out.println(":project: " + value);
		else
			out.println(":project: testProject");
		
		if (tag.compareTo("content") == 0)
			out.println(value);
		else
			out.println("ceci est du contenu.");
		
		out.close();
	}
	
	
	/**
	 * Cree sur disque une note standarde
	 * @param f
	 * @throws IOException
	 */
	private void createNote(File f) throws IOException {
		createNote(f, "", "");
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
			out.close();

			Helper.deleteFolder(dossier);

		}
		catch(Exception e) {}
	}
	
	@Test (expected = ArgumentException.class)
	public void testSearchSansArgument() {		
		String[] args= {"search"};
		Function f = new Function(c);
		f.search(args);
	}
	
	/**
	 * On test ici search dans le cas ou la requete ne correspond a aucune
	 * note.
	 * @throws Exception 
	 */
	@Test
	public void testSearchAucunResultat() throws Exception {
		File d1 =new File("target/notes-test-search1/");
		File d2 =new File("target/notes-test-search1/notes");

		d1.mkdir();
		d2.mkdir();
		
		File file = new File("target/notes-test-search1/notes/testsearch1.adoc");
		

		Function fu = new Function(c);
		c.setPathStockage("target/notes-test-search1/");

		createNote(file);
		Indexer.indexer(c.getPathIndex(), c.getPathStockage());

		String[] args= {"search", "valeur_inconnue"};
		
		int res =fu.search(args);
		
		Helper.deleteFolder(d1);

		assertEquals(res, 0);

	}
	
	
	/**
	 * On teste ici que la recherche exacte sur tout le champs fonctionne bien, 
	 * les deux notes temporaires contiennent un champs :project: différent "t 1"
	 * et "t 2". On veut que la recherche ":project:t 1" ne renvoie qu'un seul 
	 * resultat
	 * @throws Exception 
	 */
	@Test
	public void testSearchValueComplete() throws Exception {
		File d1 =new File("target/notes-test-search2/");
		File d2 =new File("target/notes-test-search2/notes");

		d1.mkdir();
		d2.mkdir();
		;
		
		File f1 = new File("target/notes-test-search2/notes/testsearch1.adoc");
		File f2 = new File("target/notes-test-search2/notes/testsearch2.adoc");
		
		Function f = new Function(c);
		c.setPathStockage("target/notes-test-search2/");
		
		createNote(f1, "project", "test 1");
		createNote(f2, "project", "test 2");
		Indexer.indexer(c.getPathIndex(), c.getPathStockage());

		String[] args= {"search", ":project:test 1"};
		int res = f.search(args);

		Helper.deleteFolder(d1);
		
		assertEquals(res, 1);
	}
	
	/**
	 * On teste ici que la recherche mot par mot fonctionne bien.
	 * les deux notes temporaires contiennent un champs :project: différent "test 1"
	 * et "test 2". On veut que la recherche ":project:test" renvoie deux puisque les
	 * notes ont un champs ":project:" contenant le mot "test".
	 * @throws Exception 
	 */
	@Test
	public void testSearchValueWordByWord() throws Exception {
		File d1 =new File("target/notes-test-search2/");
		File d2 =new File("target/notes-test-search2/notes");

		d1.mkdir();
		d2.mkdir();
		;
		
		File f1 = new File("target/notes-test-search2/notes/testsearch1.adoc");
		File f2 = new File("target/notes-test-search2/notes/testsearch2.adoc");
		
		Function f = new Function(c);
		c.setPathStockage("target/notes-test-search2/");
		
		createNote(f1, "project", "test 1");
		createNote(f2, "project", "test 2");
		Indexer.indexer(c.getPathIndex(), c.getPathStockage());

		String[] args= {"search", ":project:test 1"};
		int res = f.search(args);
		

		Helper.deleteFolder(d1);
	
		assertEquals(res, 1);
	}
	
	/**
	 * On teste ici que la recherche mot par mot et regex fonctionne bien.
	 * les deux notes temporaires contiennent un champs :project: différent "test 1"
	 * et "test 2". On veut que la recherche ":project:test" renvoie deux puisque les
	 * notes ont un champs ":project:" contenant le mot "test".
	 * @throws Exception 
	 */
	@Test
	public void testSearchValueWordByWordRegex() throws Exception {
		File d1 = new File("target/notes-test-search2/");
		File d2 = new File("target/notes-test-search2/notes");

		d1.mkdir();
		d2.mkdir();
		;
		File f1 = new File("target/notes-test-search2/notes/testsearch1.adoc");
		File f2 = new File("target/notes-test-search2/notes/testsearch2.adoc");
		File f3 = new File("target/notes-test-search2/notes/testsearch3.adoc");
		
		Function f = new Function(c);
		c.setPathStockage("target/notes-test-search2/");
		
		createNote(f1, "project", "test");
		createNote(f2, "project", "toast");
		createNote(f3, "project", "null");
		Indexer.indexer(c.getPathIndex(), c.getPathStockage());

		String[] args= {"search", ":project:t.+t"};
		int res = f.search(args);

		Helper.deleteFolder(d1);

		assertEquals(res, 2);
	}
	
	/**
	 * On teste ici le fonctionnement de la recherche pour une date exacte.
	 * On a pour 	f1 date1 = 01/03/2019
	 * 				f2 date2 = 02/03/2019
	 * 				f3 date3 = 03/03/2019
	 * 				f4 date4 = 04/03/2019
	 * Une recherche exacte sur 03/03/2019 renvoie les notes de dates 03/03/2019 et
	 * de date 03/03/2019 - 1 c'est à dire 02/03/2019
	 * @throws Exception 
	 */
	@Test
	public void testSearchDateExacte() throws Exception {
		File d1 =new File("target/notes-test-search2/");
		File d2 =new File("target/notes-test-search2/notes");

		d1.mkdir();
		d2.mkdir();
		;
		File f1 = new File("target/notes-test-search2/notes/testsearch1.adoc");
		File f2 = new File("target/notes-test-search2/notes/testsearch2.adoc");
		File f3 = new File("target/notes-test-search2/notes/testsearch3.adoc");
		File f4 = new File("target/notes-test-search2/notes/testsearch4.adoc");

		Function f = new Function(c);
		c.setPathStockage("target/notes-test-search2/");
		
		createNote(f1, "date", "01/03/2019");
		createNote(f2, "date", "02/03/2019");
		createNote(f3, "date", "03/03/2019");
		createNote(f4, "date", "04/03/2019");
		Indexer.indexer(c.getPathIndex(), c.getPathStockage());

		String[] args= {"search", ":date:[03/03/2019 TO 03/03/2019]"};
		int res = f.search(args);
		
		Helper.deleteFolder(d1);

		assertEquals(res, 2);
	}
	
	/**
	 * On teste ici le fonctionnement de la recherche pour une date exacte
	 * en faisant varier les mois.
	 * Une recherche exacte sur 03/03/2019 renvoie les notes de dates 03/03/2019 et
	 * de date 03/03/2019 - 1 c'est à dire 02/03/2019
	 * @throws Exception 
	 */
	@Test
	public void testSearchDateExacteMois() throws Exception {
		File d1 =new File("target/notes-test-search2/");
		File d2 =new File("target/notes-test-search2/notes");

		d1.mkdir();
		d2.mkdir();
		;
		File f1 = new File("target/notes-test-search2/notes/testsearch1.adoc");
		File f2 = new File("target/notes-test-search2/notes/testsearch2.adoc");

		Function f = new Function(c);
		c.setPathStockage("target/notes-test-search2/");
		
		createNote(f1, "date", "02/02/2019");
		createNote(f2, "date", "02/03/2019");
		Indexer.indexer(c.getPathIndex(), c.getPathStockage());

		String[] args= {"search", ":date:[02/03/2019 TO 02/03/2019]"};
		int res = f.search(args);

		Helper.deleteFolder(d1);
		
		assertEquals(res, 1);
	}
	
	/**
	 * On teste ici le fonctionnement de la recherche par date 
	 * avec intervalle ouvert : [date to max]
	 * @throws Exception 
	 */
	@Test
	public void testSearchDateApres() throws Exception {
		File d1 =new File("target/notes-test-search2/");
		File d2 =new File("target/notes-test-search2/notes");
		
		d1.mkdir();
		d2.mkdir();
		
		File f1 = new File("target/notes-test-search2/notes/testsearch1.adoc");
		File f2 = new File("target/notes-test-search2/notes/testsearch2.adoc");
		File f3 = new File("target/notes-test-search2/notes/testsearch3.adoc");
		File f4 = new File("target/notes-test-search2/notes/testsearch4.adoc");

		Function f = new Function(c);
		c.setPathStockage("target/notes-test-search2/");
		
		createNote(f1, "date", "01/03/2019");
		createNote(f2, "date", "02/03/2019");
		createNote(f3, "date", "03/03/2019");
		createNote(f4, "date", "04/04/2019");
		Indexer.indexer(c.getPathIndex(), c.getPathStockage());

		String[] args= {"search", ":date:[03/03/2019 TO *]"};
		int res = f.search(args);
		
		Helper.deleteFolder(d1);
		
		assertEquals(res, 3);
	}
	
	/**
	 * On teste ici le fonctionnement de la recherche par date 
	 * avec intervalle ouvert : [min to date]
	 * @throws Exception 
	 */
	@Test
	public void testSearchDateAvant() throws Exception {
		File d1 =new File("target/notes-test-search2/");
		File d2 =new File("target/notes-test-search2/notes");
		
		d1.mkdir();
		d2.mkdir();
		;
		
		File f1 = new File("target/notes-test-search2/notes/testsearch1.adoc");
		File f2 = new File("target/notes-test-search2/notes/testsearch2.adoc");
		File f3 = new File("target/notes-test-search2/notes/testsearch3.adoc");
		File f4 = new File("target/notes-test-search2/notes/testsearch4.adoc");

		Function f = new Function(c);
		c.setPathStockage("target/notes-test-search2/");
		
		createNote(f1, "date", "03/02/2019");
		createNote(f2, "date", "02/03/2019");
		createNote(f3, "date", "03/03/2019");
		createNote(f4, "date", "04/03/2019");
		Indexer.indexer(c.getPathIndex(), c.getPathStockage());

		String[] args= {"search", ":date:[* TO 03/03/2019]"};
		int res = f.search(args);
		
		Helper.deleteFolder(d1);
		
		assertEquals(res, 3);
	}
	
	/**
	 * On teste ici le fonctionnement de la recherche par date 
	 * avec intervalle ferme.
	 * @throws Exception 
	 */
	@Test
	public void testSearchDateInterval() throws Exception {
		File d1 =new File("target/notes-test-search2/");
		File d2 =new File("target/notes-test-search2/notes");
		
		d1.mkdir();
		d2.mkdir();
		
		File f1 = new File("target/notes-test-search2/notes/testsearch1.adoc");
		File f2 = new File("target/notes-test-search2/notes/testsearch2.adoc");
		File f3 = new File("target/notes-test-search2/notes/testsearch3.adoc");
		File f4 = new File("target/notes-test-search2/notes/testsearch4.adoc");
		File f5 = new File("target/notes-test-search2/notes/testsearch5.adoc");

		Function f = new Function(c);
		c.setPathStockage("target/notes-test-search2/");
		
		createNote(f1, "date", "01/03/2019");
		createNote(f2, "date", "02/03/2019");
		createNote(f3, "date", "03/03/2019");
		createNote(f4, "date", "04/03/2019");
		createNote(f5, "date", "05/03/2019");
		
		Indexer.indexer(c.getPathIndex(), c.getPathStockage());

		String[] args= {"search", ":date:[03/03/2019 TO 04/03/2019]"};
		int res = f.search(args);
		
		Helper.deleteFolder(d1);

		assertEquals(res, 3);
	}
	
	/**
	 * On teste ici le fonctionnement de la recherche par date 
	 * avec intervalle ferme.
	 * @throws Exception 
	 */
	@Test
	public void testSearchRequetesCombinees() throws Exception {
		File d1 =new File("target/notes-test-search2/");
		File d2 =new File("target/notes-test-search2/notes");
		
		d1.mkdir();
		d2.mkdir();
		
		File f1 = new File("target/notes-test-search2/notes/testsearch1.adoc");
		File f2 = new File("target/notes-test-search2/notes/testsearch2.adoc");

		Function f = new Function(c);
		c.setPathStockage("target/notes-test-search2/");
		
		createNote(f1, "author", "Mister Dummy");

		PrintWriter out = new PrintWriter(new FileWriter(f2));
		
		out.println("= test recherche");
		out.println("Mister Dummy");
		out.println("07/12/1995");
		out.println(":context: SpecialContent");
		out.println(":project: testProject");
		out.println("ceci est du contenu.");
		
		out.close();
		
		Indexer.indexer(c.getPathIndex(), c.getPathStockage());

		String[] args= {"search", ":author:mister dummy", ":context:special.*"};
		
		int res = f.search(args);

		Helper.deleteFolder(d1);

		assertEquals(res, 1);
	}
	
	/**
	 * On teste ici que l'indexation est bien insensible aux majuscules
	 * @throws Exception 
	 */
	@Test
	public void testSearchRequetesCaseSensitive() throws Exception {
		File d1 =new File("target/notes-test-search2/");
		File d2 =new File("target/notes-test-search2/notes");
		
		d1.mkdir();
		d2.mkdir();
		
		File f1 = new File("target/notes-test-search2/notes/testsearch1.adoc");

		Function f = new Function(c);
		c.setPathStockage("target/notes-test-search2/");
		
		createNote(f1, "author", "Sarah");
		
		Indexer.indexer(c.getPathIndex(), c.getPathStockage());

		String[] args= {"search", ":author:sarah"};
		
		int res = f.search(args);

		Helper.deleteFolder(d1);

		assertEquals(res, 1);
	}

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
			
			Helper.deleteFolder(dossier);

		}
		catch(Exception e) {}
	}
	
	/**
	 * Verifie qu'une commande demandant a voir un fichier non existant 
	 * renvoie bien une exception.
	 */
	@Test (expected=ViewException.class)
	public void testViewNoteInconnuSansDossier() {
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
			assertEquals(f.findPath(args[1]), "target/notes-test/notes/test.adoc");
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
