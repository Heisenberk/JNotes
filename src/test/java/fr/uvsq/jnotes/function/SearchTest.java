package fr.uvsq.jnotes.function;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.uvsq.jnotes.config.Config;
import fr.uvsq.jnotes.exception.*;
import fr.uvsq.jnotes.function.Function;

public class SearchTest {
	private Config c;
	//private File dir1;
	private File dir2;
	File f1;
	File f2;
	File f3;
	@Before
	public void initialize(){
		c = new Config("target/notes-test/", "nano");
		//dir1 = new File("target/notes-test/");
		//dir1.mkdir();
		dir2 = new File("target/notes-test/notes/");
		dir2.mkdir();

		f1 = new File("target/notes-test/notes/test_search1.adoc");
		f2 = new File("target/notes-test/notes/test_search2.adoc");
		f3 = new File("target/notes-test/notes/test_search3.adoc");
		try {
			FileWriter fw = new FileWriter(f1, true);
			fw.append("=test recherche1\n");
			fw.append("sarah pho\n");
			fw.append("7 décembre 1995\n");
			fw.close();
			fw = new FileWriter(f2, true);
			fw.append("=test recherche2\n");
			fw.append("sarah pho\n");
			fw.append("7 décembre 1995\n");
			fw.close();
			fw = new FileWriter(f3, true);
			fw.append("=test recherche3\n");
			fw.append("sarah pho\n");
			fw.append("7 décembre 1995\n");
			fw.close();
		} catch (IOException e) { 
			System.out.println("init");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Vérifie que la methode search envoie bien une exception SearchException quand elle 
	 * est appelée sans paramètre.
	 */
	@Test (expected = SearchException.class)
	public void testSearchNoArguments(){
		String[] args = {"search"};
		Function f = new Function(c);
		f.search(args);
	}
	/**
	 * Vérifie que l'exception SearchException contient le message adapté quand elle 
	 * est appelée sans paramètre.
	 */
	@Test
	public void testSearchNoArgumentsMessage(){
		Exception e = null;
		String[] args = {"search"};
		Function f = new Function(c);
		try{
			f.search(args);
		}catch(SearchException e2){
			e = e2;
		}
		assertEquals("Erreur dans la recherche : Aucun mot à chercher.", e.getMessage());
	}
	
	//test_search1 contient test search 1
	//test_search2 contient test search 2
	//test_search3 contient test search 1 et 2
	/**
	 * Ce test vérifie que la liste retournée par search est de la bonne longueur
	 */
	@Test
	public void testSearch1(){
		try {
			FileWriter fw = new FileWriter(f1, true);
			fw.append("* test search 1\n");
			fw.close();
			fw = new FileWriter(f2, true);
			fw.append("* test search 2\n");
			fw.close();
			fw = new FileWriter(f3, true);
			fw.append("* test search 1\n");
			fw.append("* test search 2\n");
			fw.close();
		} catch (IOException e) { 
			System.out.println("search1");
			e.printStackTrace();
		}
		Function f = new Function(c);
		String[] args = {"search", "test", "search", "2"};
		List<String> strings = null;
		try{
			strings = f.search(args);
		}catch(SearchException e){
		}
		assertEquals(2, strings.size());
	}
	/**
	 * Teste vérifie la validité de la liste retournée par search
	 */
	@Test
	public void testSearch2(){
		try {
			FileWriter fw = new FileWriter(f1, true);
			fw.append("* test search 1\n");
			fw.close();
			fw = new FileWriter(f2, true);
			fw.append("* test search 2\n");
			fw.close();
			fw = new FileWriter(f3, true);
			fw.append("* test search 1\n");
			fw.append("* test search 2\n");
			fw.close();
		} catch (IOException e) { 
			System.out.println("search2");
			e.printStackTrace();
		}
		Function f = new Function(c);
		String[] args = {"search", "test", "search", "2"};
		List<String> strings = null;
		try{
			strings = f.search(args);
		}catch(SearchException e){}
		assertFalse(strings.contains("test_search1.adoc"));
	}
	
	/**
	 * Teste vérifie la validité de la liste retournée par search avec un tag
	 */
	@Test
	public void testSearchTags1(){
		String[] args = {"search", "test", ":context:", "work"};
		try {
			FileWriter fw = new FileWriter(f1, true);
			fw.append(":context: work\ntest search 1\n");
			fw.close();
			fw = new FileWriter(f2, true);
			fw.append(":context: work\n* test search 2\n");
			fw.close();
		} catch (IOException e) { 
			System.out.println("tag1");
			e.printStackTrace();
		}
		Function f = new Function(c);
		List<String> strings = null;
		try{
			strings = f.search(args);
		}catch(SearchException e){
		}
		assertEquals(2, strings.size());
	}
	
	/**
	 * Teste vérifie la validité de la liste retournée par search avec deux tags
	 */
	@Test
	public void testSearchTags2(){
		String[] args = {"search", "test", ":context:", "work", "project", "p1"};
		try {
			FileWriter fw = new FileWriter(f1, true);
			fw.append(":context: work\ntest search 1\n");
			fw.close();
			fw = new FileWriter(f2, true);
			fw.append(":context: work\n*:project: p1\n*test search 2\n");
			fw.close();
		} catch (IOException e) {
			System.out.println("tag2");
			e.printStackTrace();
		}
		Function f = new Function(c);
		List<String> strings = null;
		try{
			strings = f.search(args);
		}catch(SearchException e){
		}
		assertEquals(1, strings.size());
	}
	
	/**
	 * Teste que search gère bien la recherche par tags et par contenu en même temps
	 */
	@Test
	public void testSearchTags3(){
		String[] args = {"search", "1", ":context:", "work"};
		try {
			FileWriter fw = new FileWriter(f1, true);
			fw.append(":context: work\n*test search 1\n");
			fw.close();
			fw = new FileWriter(f2, true);
			fw.append(":context: work\n*test search 2\n");
			fw.close();
		} catch (IOException e) { 
			System.out.println("tag3");
			e.printStackTrace();
		}
		Function f = new Function(c);
		List<String> strings = null;
		try{
			strings = f.search(args);
		}catch(SearchException e){
		}
		assertEquals(1, strings.size());
	}
	
	/**
	 * Vérifie que la recherche par tag s'arrête bien après l'en-tête
	 */
	@Test
	public void testSearchTagsStops(){
		String[] args = {"search", ":context:", "work"};
		File f1 = new File("target/notes-test/notes/test_search1.adoc");
		try {
			FileWriter fw = new FileWriter(f1, true);
			fw.append("*test\n:context: work\n");
			fw.close();
		} catch (IOException e) { 
			System.out.println("tagstops");
			e.printStackTrace();
		}
		Function f = new Function(c);
		List<String> strings = null;
		try{
			strings = f.search(args);
		}catch(SearchException e){
		}
		assertEquals(1, strings.size());
	}
	
	/**
	 * Vérifie que search renvoie bien une exception lorsqu'il y a un tag vide à la fin
	 */
	@Test (expected = SearchException.class)
	public void testSearchTagsErrorInArgs(){
		String[] args = {"search", ":context:"};
		try {
			FileWriter fw = new FileWriter(f1, true);
			fw.append(":context: work\ntest search 1\n");
			fw.append("*test\n:context: work\n");
			fw.close();
		} catch (IOException e) { 
			System.out.println("tagserrorinargs");
			e.printStackTrace();
		}
		Function f = new Function(c);
		List<String> strings = null;
		strings = f.search(args);
		assertEquals(1, strings.size());
	}
	
	
	/**
	 * Vérifie que la recherche par tag s'arrête bien après l'en-tête
	 */
	@Test
	public void testSearchTagsErrorInNote(){
		String[] args = {"search", ":context:", "work"};
		try {
			FileWriter fw = new FileWriter(f1, true);
			fw.append(":context: \ntest search 1\n");
			fw.close();
		} catch (IOException e) { 
			System.out.println("tagserrorinnote");
			e.printStackTrace();
		}
		Function f = new Function(c);
		List<String> strings = null;
		try{
			strings = f.search(args);
		}catch(SearchException e){
		}
		
		assertEquals(0, strings.size());
	}
	
	@After
	public void end(){
		System.out.println("suppression de " + f1.getAbsolutePath() + " " + ((f1.delete())?"réussie":"échouée"));
		System.out.println("suppression de " + f2.getAbsolutePath() + " " + ((f2.delete())?"réussie":"échouée"));

		System.out.println("suppression de " + f3.getAbsolutePath() + " " + ((f3.delete())?"réussie":"échouée"));

		System.out.println("suppression de " + dir2.getAbsolutePath() + " " + ((dir2.delete())?"réussie":"échouée"));
		//System.out.println("suppression de " + dir1.getAbsolutePath() + " " + ((dir1.delete())?"réussie":"échouée"));
	}
}