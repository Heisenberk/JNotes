package fr.uvsq.jnotes.command;

import java.util.Scanner;

/**
 * Classe ScannerCommand qui va lire les commandes de l'utilisateur qui n'a pas mis d'arguments au départ. 
 */
public class ScannerCommand {
	
	/**
	 * Scanner permettant d'interpréter les commandes de l'utilisateur. 
	 */
	private Scanner scanner;
	
	/**
	 * Constructeur de ScannerCommand qui initialise le scanner. 
	 */
	public ScannerCommand() {
		this.scanner = new Scanner(System.in);
	}
	
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
	}
	
	/**
	 * Méthode saisie qui va interpréter les commandes de l'utilisateur qui n'a pas mis d'arguments au départ. 
	 */
	public void saisie() {
		// A remplir comme CalculatriceRPN
	}
}
