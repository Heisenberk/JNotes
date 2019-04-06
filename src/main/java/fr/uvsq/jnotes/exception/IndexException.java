package fr.uvsq.jnotes.exception;

/**
 * Classe IndexException.
 */
public class IndexException extends RuntimeException{
	
	/**
     * Constructeur de DeleteException.
     */
    public IndexException(){
        super("Impossible de generer correctement index.adoc. ");
    }
    
    /**
     * Accesseur du message a afficher.
     */
    public String getMessage() {
    	return "Impossible de generer correctement index.adoc. ";
    }
}