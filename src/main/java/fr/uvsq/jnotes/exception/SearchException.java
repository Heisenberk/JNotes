package fr.uvsq.jnotes.exception;

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
     * Constructeur de SearchException avec message.
     * @param message a afficher. 
     */
    public SearchException(String message){
        super("Erreur dans la recherche : " + message);
    }
}