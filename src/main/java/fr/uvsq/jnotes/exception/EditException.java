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

	/**
     * Constructeur de EditException avec message.
     * @param message le message a afficher
     */
    public EditException(String message){
        super("Impossible d'éditer une note : " + message);
    }
    
}