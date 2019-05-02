package fr.uvsq.jnotes.function;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Observable;

import fr.uvsq.jnotes.exception.*;
import fr.uvsq.jnotes.index.Indexer;
import fr.uvsq.jnotes.index.Searcher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import fr.uvsq.jnotes.note.Note;
import fr.uvsq.jnotes.utils.Helper;
import fr.uvsq.jnotes.config.Config;

/**
 * Classe qui va etre appelle par toutes les fonctionnalitees de l'application : edit, delete, search, listing, param et view. 
 * Elle contient toutes les methodes necessaires au bon fonctionement des ces fonctionalites.
 */
public class Function extends Observable {
	
	/**
	 * Configuration associee aux fonctionnalites. 
	 */
	private Config c;
	
	
	/**
	 * Constructeur vide de Function.
	 */
	public Function() {
		try {
			c=new Config();
		}
		catch(ConfigurationException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * Constructeur de Function.
	 * @param Config
	 */
	public Function(Config c) {
		this.c=c;
	}
	
	/**
	 * Accesseur de Config. 
	 * @return Configuration. 
	 */
	public Config getConfig() {
		return c;
	}
	
	/**
	 * Methode de Edit. 
	 * Si le fichier n'existe pas il est creer. 
	 * Le process attend que l'utilisateur ait fini d'editer la note.
	 */
	public void edit(String[] args) throws EditException, IOException, InterruptedException {
		
		// si on tape "jnotes edit/e" sans autre argument
		if (args.length<=1) throw new EditException(); 
		
		// si le fichier existe
		String fileName = this.findPath(args[1]);
		File file = new File(fileName);
		if  (file.exists() && !file.isDirectory()) {
			String prompt = "xterm -e " + c.getNameAppExtern() + " " + fileName;
			Process process = Runtime.getRuntime().exec(prompt);
			process.waitFor();
			System.out.println("Edition de la note " + fileName);
		}
		//********************************
		// Si le fichier n'existe pas
		// *******************************
		else {
			//**********************************************
			// Conversion de "ma premiere note" en "ma_premiere_note.adoc"
			//**********************************************
			String name = Helper.join(args, " ", 1);
			
			System.out.println("Initialisation de la note " + name +".");
			
			Note note=new Note.Builder(name).build();
			name = name.replace(" ", "_");
			name += ".adoc";
			System.out.println("Creation de la note " + name + " dans "+ c.getPathStockage() +".");
			
			fileName = this.findPath(name);
			
			System.out.println("Nom du nouveau fichier : " + fileName);
			
			try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName))) {
				note.create(out);
			}
			String prompt = "xterm -e " + c.getNameAppExtern() + " " + fileName;
			Process process = Runtime.getRuntime().exec(prompt);
			process.waitFor();
			System.out.println("Edition de la nouvelle note " + fileName);
					
		}
		
		try {
			Indexer.update(c.getPathIndex(), c.getPathStockage(), fileName);
		} catch (Exception e) {
			throw new EditException(e.getMessage());
		}
		setChanged(); //notification pour modifier l'index
		notifyObservers();
	}
	
	/**
	 * Methodes creant une liste contenant l'ensemble des notes presente dans le dossier.
	 * @return string s
	 */
	public String listingString() {
		String s="";
		File dossier=new File(c.getPathStockage()+"notes/");
		if (dossier.exists() && dossier.isDirectory()){
			// lire les fichiers contenus dans le dossier 
	        String liste[] = dossier.list();      
	 
	        if (liste != null) {  
	        	s+="Listing des notes : "+"\n";
	            for (int i = 0; i < liste.length; i++) {
	                s+="- "+liste[i]+"\n";
	            }
	        }
	        if (liste.length==0) return "Aucune note trouvee. "+"\n";
		}
		else {
			 return "Aucune note trouvee. "+"\n";
		}
		return s;
	}
	
	/**
	 * Methode de Listing des notes, affichant le string produit par listingString.
	 */
	public void listing() {
		System.out.println(listingString());

	}
	
	/**
	 * Methode de lecture de l'index des notes, met a jour l'index lucene
	 */
	public void index() {
		try {

			Indexer.indexer(c.getPathIndex(), c.getPathStockage());
			Process p=Runtime.getRuntime().exec("firefox config/index.adoc");
			p.waitFor();
			System.out.println("Visualisation de l'index JNotes ");
		}
		catch(IOException e) {
			throw new IndexException();
		}
		catch(Exception e) {
			throw new IndexException();
		}
	}
	
	/**
	 * Methode supprimant la note dont le nom est passe en argument si elle existe
	 * et la supprime de l'index
	 * @return string s un message de reussite
	 */
	public String deleteString(String[] args) throws DeleteException{
		String s = "";
		// si on tape "jnotes delete/d" sans autre argument
		if (args.length<=1) throw new DeleteException(); 
		
		String fileName = c.getPathStockage()+"notes/"+args[1];			
		File fichier = new File(fileName);
		if (fichier.exists()) 
			s += "Suppression de la note AsciiDoctor " + c.getPathStockage() + "notes/" + args[1];
		else throw new DeleteException();
							
		fichier.delete();
		
		try {
			Indexer.delete(c.getPathIndex(), fileName);
		} catch (Exception e) {
			throw new DeleteException(e.getMessage());
		}
		
		return s;
	}
	
	/**
	 * Methode de suppression d'une note, affichant le string produit par deleteString.
	 */
	public void delete(String[] args) throws DeleteException{
		System.out.println(deleteString(args));
		setChanged(); //notification pour la modification de l'index
		notifyObservers();
	}
	
	/**
	 * Methode de View. 
	 * Affiche la note sur firefox.
	 */
	public void view(String[] args) throws ViewException {
		
		// si on tape "jnotes view/v" sans autre argument
		if (args.length <= 1) throw new ViewException(); //creer une exception personnelle ArgumentException
		try {
			String absolutePath=null;
			File fichier = new File(this.findPath(args[1]));
			if (fichier.exists()) 
				absolutePath = fichier.getAbsolutePath();
			else 
				throw new ViewException();
			Process process = Runtime.getRuntime().exec("firefox " + absolutePath);
			process.waitFor();
			System.out.println("Visualisation de la note AsciiDoctor " + absolutePath);
		}
		catch(IOException e) {
			throw new ViewException();
		}
		catch(Exception e) {
			throw new ViewException();
		}
	}
	

	/**
	 * Methode de Search. 
	 * Permet une recherche par mot cle, date, auteur, titre etc
	 * @param args la liste des arguments contenant "search" "condition1" "condition2"...
	 * @throws SearchException quand il n'y pas de conditions
	 */
	public int search(String args[]) throws SearchException {
		if (args.length == 1) throw new ArgumentException("Fonction search : vous n'avez pas entré de conditions");
		int result;
		try {
			result = Searcher.search(c.getPathIndex(), args);

		} catch (Exception e) {
			throw new SearchException("format incorrect");
		}
		return result;
	}
	
	/**
	 * Methode de Param. 
	 * Elle permet de visualiser les parametres de configurations.
	 * C'est a dire l'application du dossier des notes et le chemin du dossier des notes.
	 */
	public void param(String args[]) throws ParamException{
		// si on tape "jnotes param/p" sans argument
		if (args.length <= 1) {
			System.out.println("- Chemin du dossier contenant les notes JNotes : "+ c .getPathStockage());
			System.out.println("- Application permettant d'Ã©diter les notes JNotes : " + c.getNameAppExtern());
		}
		else if (args.length>=3) {
			// si on tape "jnotes param/p path [chemin]"
			if (args[1].equals("path")) {
				Path outPath = Paths.get("config/config-jnotes");
				try (BufferedWriter out = Files.newBufferedWriter(outPath)) {
					out.write(args[2]);
					out.newLine();
					out.write(c.getNameAppExtern());
					System.out.println("Chemin du dossier contenant les notes JNotes modifie : "+args[2]);
					c.setPathStockage(args[2]);
					setChanged(); //notification pour la modification de l'index
					notifyObservers();
				}
				catch (IOException e) {
					throw new ParamException();
				}
			}
			// si on tape "jnotes param/p app [application]"
			else if (args[1].equals("app")) {
				Path outPath = Paths.get("config/config-jnotes");
				try(
						BufferedWriter out = Files.newBufferedWriter(outPath)
					){
					out.write(c.getPathStockage());
					out.newLine();
					out.write(args[2]);
					c.setNameAppExtern(args[2]);
					System.out.println("Application utilisee pour l'edition des notes JNotes modifiee : "+args[2]);
					setChanged(); //notification pour la modification de l'index
					notifyObservers();
				}
				catch (IOException e) {
					throw new ParamException();
				}
			}
		}
		else throw new ParamException();
	}
	
	/**
	 * Methode qui affiche les differentes commandes de l'application. 
	 */
	public void help() {
		System.out.println("Commandes de Jnotes: ");
		System.out.println(" ");
		System.out.println("- help/h : affiche les commandes.");
		System.out.println("- edit/e [note] : cree ou modifie une note. ");
		System.out.println("- list/ls : liste les notes existantes. ");
		System.out.println("- delete/d [note]: supprime une note. ");
		System.out.println("- view/v [note]: affiche le contenu d'une note. ");
		System.out.println("- search/s [condition] ..: rechercher des notes. ");	
		System.out.println("- param/p : affiche les parametres de configuration. ");
		System.out.println("- param/p path [chemin] : modifie le chemin du dossier des notes. ");
		System.out.println("- param/p app [appli] : modifie l'application d'edition de note. ");
		System.out.println("- index/i : affiche les notes triees");
		System.out.println("- quit : quitte l'interpreteur JNotes. \n");
	}

	/**
	 * Methode affichant le chemin d'une note.
	 */
	public String findPath(String args) {
		return c.getPathStockage() + "notes/" + args;
	}
}
