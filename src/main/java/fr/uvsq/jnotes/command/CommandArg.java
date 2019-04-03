package fr.uvsq.jnotes.command;

/**
 * Interface permettant de réaliser le pattern Command pour la 
 * gestion des commandes avec argument de l'application. 
 */
public interface CommandArg {
	/**
	 * Methode execute qui sera implémentée par toutes les commandes avec argument. 
	 */
	void execute(String[] args);
}
