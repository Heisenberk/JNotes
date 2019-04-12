package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.ICommand;

/**
 * Classe qui permet de supprimer une note. 
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

	@Override
	public ICommand setArgument(String[] args) {
		// TODO Auto-generated method stub
		return null;
	}
}