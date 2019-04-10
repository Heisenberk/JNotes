package fr.uvsq.jnotes.exception;

/**
 * Classe EditException.
 */
public class IndexException extends RuntimeException{
	
	/**
     * Constructeur de EditException.
     */
    public IndexException(){
        super("Impossible de visualiser l'index JNotes. ");
    }
    
    /**
     * Accesseur du message a afficher.
     */
    public String getMessage() {
    	return "Impossible de visualiser l'index JNotes. ";
    }
}