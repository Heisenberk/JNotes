package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.Command;

public class Search implements Command{
	private Function function;
	
	public Search(Function function) {
		this.function = function;
	}
	
	public void execute() {
		function.search();
	}
}
