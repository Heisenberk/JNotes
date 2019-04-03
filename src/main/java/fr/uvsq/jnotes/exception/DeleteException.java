package fr.uvsq.jnotes.exception;

/**
 * Classe DeleteException.
 */
public class DeleteException extends RuntimeException{
	
	/**
     * Constructeur de DeleteException.
     */
    public DeleteException(){
        super("Impossible de supprimer une note. ");
    }
    
    /**
     * Accesseur du message a afficher.
     */
    public String getMessage() {
    	return "Impossible de supprimer une note. ";
    }
}