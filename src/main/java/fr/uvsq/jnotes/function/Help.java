package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.ICommand;

/**
 * Classe qui permet d'afficher toutes les commandes de jnotes
 *
 */
public class Help implements ICommand {
		
		/**
		 * function representant ce qui va appeler la commande. 
		 */
		private Function function;
		
		/**
		 * Constructeur de Listing.
		 * @param function
		 */
		public Help(Function function) {
			this.function = function;
		}
		
		/**
		 * Execute la commande. 
		 */
		public void execute() {
			function.help();
		}

		@Override
		public ICommand setArgument(String[] args) {
			return null;
		}

}
