package fr.uvsq.jnotes.exception;

/**
 * Classe ArgumentException qui est une exception lorsque la commande est inconnu pour l'application.
 */
public class ArgumentException extends RuntimeException{
	
	/**
     * Constructeur de SearchException.
     */
    public ArgumentException(){
        super("ArgumentException : pas assez d'arguments");
    }
    
}