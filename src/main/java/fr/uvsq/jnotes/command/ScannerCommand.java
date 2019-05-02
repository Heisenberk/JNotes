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
	 * separator represente le separateur entre les mots des arguments. 
	 */
	private static String separator = "\" \"";
	
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
		String commande;
		try(BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
		// tant que l'utilisateur n'a pas ecrit quit, on continue a interpreter. 
			while (!arret) {
				commande = "";
				System.out.print(">");
				
				commande = in.readLine();
				
			    String[] arguments = commande.split(separator); 
			    String[] command = arguments[0].split(" ");
			    String[] args = new String[arguments.length + 1];
			    
			    if (args.length > 0) {
			    	args[0] = command[0];
			    	if (command.length > 1) {
					    args[1] = command[1];
					    if (arguments.length >= 2) {
						    for (int i = 2 ; i < arguments.length ; i++) {
						    	args[i] = arguments[i - 1];
						    }
					    }
			    	}
			    }
			    
			    if (command[0].equals("quit")) 
			    	arret = true;
			    else {
			    	Interpretor arg = new Interpretor();
			        arg.detectCommand(args);
			    } 
				
			}
		} catch(IOException e) {
			throw new SaisieException();
		}
	}
}
