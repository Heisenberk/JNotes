package fr.uvsq.jnotes.exception;

/**
 * Classe EditException.
 */
public class EditException extends RuntimeException{
	
	/**
     * Constructeur de EditException.
     */
    public EditException(){
        super("Impossible d'éditer une note. ");
    }
    
}