package fr.uvsq.jnotes.command;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import fr.uvsq.jnotes.exception.SaisieException;


/**
 * Classe ScannerCommand qui va lire les commandes de l'utilisateur qui n'a pas mis d'arguments au départ. 
 */
public class ScannerCommand {
	
	/**
	 * Constructeur de ScannerCommand qui initialise le scanner. 
	 */
	public ScannerCommand() {}
	
	/**
	 * Methode qui affiche les différentes commandes de l'application. 
	 */
	public void afficheCommandes() {
		System.out.println("Commandes de Jnotes: ");
		System.out.println(" ");
		System.out.println("- edit/e [nom de la note] : créer ou modifier une note. ");
		System.out.println("- list/ls : lister les notes existantes. ");
		System.out.println("- delete/d [nom de la note]: supprimer une note. ");
		System.out.println("- view/v [nom de la note]: voir une note. ");
		System.out.println("- search/s [mot a rechercher] : rechercher dans les notes d'un mot clé. ");	
		System.out.println("- param/p : visualiser les paramètres de configuration (application d'édition de note et le chemin du dossier des notes). ");
		System.out.println("- param/p path [chemin] : modifier les paramètres de configuration (chemin du dossier des notes). ");
		System.out.println("- param/p app [nom de l'application externe] : modifier les paramètres de configuration (app : application d'édition de note). ");
		System.out.println("- quit : quitter l'interpréteur de JNotes. \n");
	}
	
	/**
	 * Méthode saisie qui va interpréter les commandes de l'utilisateur qui n'a pas mis d'arguments au départ. 
	 */
	public void saisie() throws SaisieException {
		boolean arret = false;
		String commande;
		
		while (arret == false) {
			commande="";
			try {
				
				System.out.print(">");
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				commande=in.readLine();
			    String[] args = commande.split(" ", -1); 
			    
			    if (args[0].equals("quit")) arret=true;
			    else {
			    	Interpretor arg = new Interpretor();
			        arg.setArgument(args);
			    } 
			}
			catch(Exception e) {
				throw new SaisieException();
			}
			
			
		}
	}
}
