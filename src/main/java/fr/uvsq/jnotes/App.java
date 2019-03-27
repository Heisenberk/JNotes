package fr.uvsq.jnotes;

import java.io.IOException;

import fr.uvsq.jnotes.command.*;
import fr.uvsq.jnotes.config.*;

/**
 * Enumération représentant le main de l'application. 
 * Utilisation du pattern Singleton pour construire une unique 
 * entité pour le main. 
 */
enum App {
	/**
	 * Variable d'environnement. 
	 */
	ENVIRONNEMENT;

	/**
	 * Methode run qui appelle les fonctions nécessaires à l'application. 
	 * @param args arguments en ligne de commande. 
	 */
	public void run(String[] args){
		System.out.println( "Hello JNotes!" );
        ArgumentCommand arg = new ArgumentCommand();
        arg.setArgument(args);
	}

	/**
	 * Methode main qui appelle l'unique variable d'environnement. 
	 * @param args arguments en ligne de commande. 
	 */
	public static void main(String[] args){
		ENVIRONNEMENT.run(args);
	}
}
