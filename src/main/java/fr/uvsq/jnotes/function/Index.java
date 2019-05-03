package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.ICommand;

/**
 * Classe qui permet d'indexer une note. 
 */
public class Index implements ICommand {
	
	/**
	 * function representant ce qui va appeler la commande. 
	 */
	private Function function;
	
	/**
	 * Constructeur de Listing.
	 * @param function
	 */
	public Index(Function function) {
		this.function = function;
	}
	
	/**
	 * Execute la commande. 
	 */
	public void execute() {
		function.index();
	}

	/**
	 * Renvoie null car la commande n'a pas besoin d'arguments. 
	 */
	@Override
	public ICommand setArgument(String[] args) {
		return null;
	}

}