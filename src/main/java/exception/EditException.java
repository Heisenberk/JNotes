package exception;

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
     * Accesseur du message a afficher.
     */
    public String getMessage() {
    	return "Impossible de lister les notes. ";
    }
}