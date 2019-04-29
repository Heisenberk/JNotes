package fr.uvsq.jnotes;

import fr.uvsq.jnotes.command.*;

/**
 * Enumeration representant le main de l'application. 
 * Utilisation du pattern Singleton pour construire une unique 
 * entite pour le main. 
 */
enum App {
	
	/**
	 * Variable d'environnement. 
	 */
	ENVIRONNEMENT;

	/**
	 * Methode run qui appelle les fonctions necessaires à l'application. 
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
