package fr.uvsq.jnotes.command;

import java.util.Scanner;

public class ScannerCommand {
	private Scanner scanner;
	
	public ScannerCommand() {
		this.scanner = new Scanner(System.in);
	}
	
	public void afficheCommandes() {
		System.out.println("Commandes de Jnotes: ");
		System.out.println(" ");
		System.out.println("- edit/e [nom de la note] : créer ou modifier une note. ");
		System.out.println("- list/ls : lister les notes existantes. ");
		System.out.println("- delete/d [nom de la note]: supprimer une note. ");
		System.out.println("- view/v [nom de la note]: voir une note. ");
		System.out.println("- search/s [mot a rechercher] : rechercher dans les notes d'un mot clé. ");	
	}
	
	public void saisie() {
		
	}
}