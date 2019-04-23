package fr.uvsq.jnotes;

import java.util.ArrayList;
import java.util.List;

import fr.uvsq.jnotes.command.*;
import fr.uvsq.jnotes.function.Function;
import fr.uvsq.jnotes.function.Update;
import fr.uvsq.jnotes.note.Note;

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
        Interpretor arg = new Interpretor();
        arg.detectCommand(args);
			
	}

	/**
	 * Methode main qui appelle l'unique variable d'environnement. 
	 * @param args arguments en ligne de commande. 
	 */
	public static void main(String[] args){
		ENVIRONNEMENT.run(args);
	}
}
