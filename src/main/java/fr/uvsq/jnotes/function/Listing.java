package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.Command;

public class Listing implements Command {
	private Function function;
	
	public Listing(Function function) {
		this.function = function;
	}
	
	public void execute() {
		function.listing();
	}
}