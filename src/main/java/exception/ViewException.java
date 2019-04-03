package exception;

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
    
    /**
     * Accesseur du message a afficher.
     */
    public String getMessage() {
    	return "Impossible de visualiser la note. ";
    }
}