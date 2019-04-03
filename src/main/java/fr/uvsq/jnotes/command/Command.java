package fr.uvsq.jnotes.command;

/**
 * Interface permettant de réaliser le pattern Command pour la 
 * gestion des commandes sans argument de l'application. 
 */
public interface Command {
	
	/**
	 * Methode execute qui sera implémentée par toutes les commandes sans argument. 
	 */
	void execute();
}
