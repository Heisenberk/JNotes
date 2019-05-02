package fr.uvsq.jnotes.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import fr.uvsq.jnotes.exception.SaisieException;

/**
 * Classe ScannerCommand qui va lire les commandes de l'utilisateur qui n'a pas mis d'arguments au depart. 
 */
public class ScannerCommand {
	
	/**
	 * Constructeur de ScannerCommand qui initialise le scanner. 
	 */
	public ScannerCommand() {}
	
	/**
	 * Methode qui affiche les differentes commandes de l'application. 
	 */
	public void afficheCommandes() {
		System.out.println("Commandes de Jnotes: ");
		System.out.println(" ");
		System.out.println("- edit/e [nom de la note] : creer ou modifier une note. ");
		System.out.println("- list/ls : lister les notes existantes. ");
		System.out.println("- delete/d [nom de la note]: supprimer une note. ");
		System.out.println("- view/v [nom de la note]: voir une note. ");
		System.out.println("- search/s [mot a rechercher] : rechercher dans les notes d'un mot clef. ");	
		System.out.println("- param/p : visualiser les parametres de configuration (application d'edition de note et le chemin du dossier des notes). ");
		System.out.println("- param/p path [chemin] : modifier les parametres de configuration (chemin du dossier des notes). ");
		System.out.println("- param/p app [nom de l'application externe] : modifier les parametres de configuration (app : application d'edition de note). ");
		System.out.println("- index/i : afficher les differentes notes");
		System.out.println("- quit : quitter l'interpreteur de JNotes. \n");
	}
	
	/**
	 * Methode saisie qui va interpreter les commandes de l'utilisateur qui n'a pas mis d'arguments au depart. 
	 */
	public void saisie() throws SaisieException {
		boolean arret = false;

    	Interpretor interpretor = new Interpretor();
		String commande;
		try(BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
			
			// *************************************
			// Continue tant que l'utilisateur n'a pas ecrit la commande 'quit'
			// *************************************
			while (!arret) {
				
				System.out.print(">");
				
				commande = in.readLine();

				String function = commande.split(" +")[0];
				
			    String[] arguments = commande.split(
			    		"(^" + function + " *$)|(^" + function + 
			    		" +\")|(\" +\")|(\" *$)");//    
			    String[] args = new String[arguments.length];
	
			    if(arguments.length > 1) {
				    args[0] = function;
				    for(int i = 1 ; i < arguments.length ; i++)
				    	args[i] = arguments[i];
			    }
			    if (commande.equals("quit")) 
			    	arret = true;
			    else 
			        interpretor.detectCommand(args);
				
			}
		} catch(IOException e) {
			throw new SaisieException();
		}
	}
}
