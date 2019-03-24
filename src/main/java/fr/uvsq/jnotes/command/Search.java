package fr.uvsq.jnotes.command;

public class Search implements Command{
	private Function function;
	
	public Search(Function function) {
		this.function = function;
	}
	
	public void execute() {
		function.search();
	}
}
