package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.CommandArg;

/**
 * Classe qui permet de voir une note a l'aide d'une application externe (firefox ici).
 */
public class View implements CommandArg {
	
	/**
	 * function representant ce qui va appeler la commande. 
	 */
	private Function function;
	
	/**
	 * Constructeur de View.
	 * @param function
	 */
	public View(Function function) {
		this.function = function;
	}
	
	/**
	 * Execute la commande et affiche un message d'erreur en cas d'exception. 
	 */
	public void execute(String[] args) {
		try {
			function.view(args);
		}
		catch(Exception e) {
			System.out.println("ERREUR"); //A MODIFIER AVEC UNE EXCEPTION PERSO
		}
	}
}
