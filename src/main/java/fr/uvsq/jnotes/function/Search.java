package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.CommandArg;

public class Search implements CommandArg{
	private Function function;
	
	public Search(Function function) {
		this.function = function;
	}
	
	public void execute(String[] args) {
		try {
			function.search(args);
		}
		catch(Exception e) {
			System.out.println("ERREUR"); //A MODIFIER AVEC UNE EXCEPTION PERSO
		}
	}
}
