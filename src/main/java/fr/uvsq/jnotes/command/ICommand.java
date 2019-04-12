package fr.uvsq.jnotes.command;

/**
 * Interface permettant de réaliser le pattern Command pour la 
 * gestion des commandes de l'application. 
 */
public interface ICommand {
	
	/**
	 * Methode execute qui sera implémentée par toutes les commandes. 
	 */
	void execute();
	
	ICommand setArgument(String[] args);
}
