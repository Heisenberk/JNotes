package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.ICommand;

public class View implements ICommand {
	
	/**
	 * arguments entrés par l'utilisateur
	 */
	private String[]args;
	
	/**
	 * function representant ce qui va appeler la commande. 
	 */
	private Function function;
	
	public View(Function function) {
		this.function = function;
	}

	/**
	 * Execute la commande. 
	 */
	public void execute() {
		try {
			function.view(this.args);
		}
		catch(Exception e) {
			System.out.println(e.getMessage()); //A MODIFIER AVEC UNE EXCEPTION PERSO
		}
	}

	/**
	 * Donne à la commande les arguments auxquelles elle devra répondre;
	 */
	public ICommand setArgument(String[] args) {
		this.args = args;
		return this;
	}
}
