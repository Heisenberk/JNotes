package fr.uvsq.jnotes.command;

public class View implements Command {
	private Function function;
	
	public View(Function function) {
		this.function = function;
	}
	
	public void execute() {
		function.view();
	}
}
