package fr.uvsq.jnotes.command;

public class Listing {
	private Function function;
	
	public Listing(Function function) {
		this.function = function;
	}
	
	public void execute() {
		function.listing();
	}
}