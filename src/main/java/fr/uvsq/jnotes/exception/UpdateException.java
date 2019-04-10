package fr.uvsq.jnotes.exception;

/**
 * Classe UpdateException.
 */
public class UpdateException extends RuntimeException{
	
	/**
     * Constructeur de DeleteException.
     */
    public UpdateException(){
        super("Impossible de generer correctement index.adoc. ");
    }
    
    /**
     * Accesseur du message a afficher.
     */
    public String getMessage() {
    	return "Impossible de generer correctement index.adoc. ";
    }
}