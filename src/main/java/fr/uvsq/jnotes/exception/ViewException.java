package fr.uvsq.jnotes.exception;

/**
 * Classe ViewException qui est une exception lorsque la commande est inconnu pour l'application.
 */
public class ViewException extends RuntimeException{
	
	/**
     * Constructeur de ViewException.
     */
    public ViewException(){
        super("Impossible de visualiser la note. ");
    }
    
    public ViewException(String string) {
    	super("Impossible de visualiser la note : " + string);
	}
}