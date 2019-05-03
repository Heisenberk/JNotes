package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.ICommand;

/**
 * Classe qui permet de supprimer une note. 
 */
public class Listing implements ICommand {
	
	/**
	 * function representant ce qui va appeler la commande. 
	 */
	private Function function;
	
	/**
	 * Constructeur de Listing.
	 * @param function
	 */
	public Listing(Function function) {
		this.function = function;
	}
	
	/**
	 * Execute la commande. 
	 */
	public void execute() {
		function.listing();
	}

	/**
	 * Donne a la commande les arguments auxquelles elle devra repondre.
	 * Ne sert à rien dans ce cas là.
	 */
	@Override
	public ICommand setArgument(String[] args) {
		return null;
	}
}