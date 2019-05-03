package fr.uvsq.jnotes.command;

/**
 * Interface permettant de realiser le pattern Command pour la 
 * gestion des commandes de l'application. 
 */
public interface ICommand {
	
	/**
	 * Methode execute qui sera implementee par toutes les commandes. 
	 */
	void execute();
	
	/**
	 * Methode qui mettra a jour les arguments. 
	 * @param args
	 * @return ICommand
	 */
	public ICommand setArgument(String[] args);
	
}
