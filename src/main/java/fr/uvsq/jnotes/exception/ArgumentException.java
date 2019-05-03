package fr.uvsq.jnotes.exception;

/**
 * Classe ArgumentException qui est une exception lorsque la commande est inconnu pour l'application.
 */
public class ArgumentException extends RuntimeException{
	
	/**
     * Constructeur de SearchException.
     */
    public ArgumentException(){
        super("Erreur d'arguments");
    }

	/**
     * Constructeur de SearchException avec message.
	 * @param string message a afficher.
	 */
	public ArgumentException(String string) {
		super("Erreur d'arguments : " + string);
	}
    
}