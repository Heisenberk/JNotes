package exception;

/**
 * Classe SearchException qui est une exception lorsque la commande est inconnu pour l'application.
 */
public class SearchException extends RuntimeException{
	
	/**
     * Constructeur de SearchException.
     */
    public SearchException(){
        super("Impossible de rechercher un élément dans les notes. ");
    }
    
    /**
     * Accesseur du message a afficher.
     */
    public String getMessage() {
    	return "Impossible de rechercher un élément dans les notes. ";
    }
}