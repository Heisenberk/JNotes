package fr.uvsq.jnotes.exception;

/**
 * Classe UpdateException qui est appele lors d'un probleme de gestion de l'index.
 */
public class UpdateException extends RuntimeException{
	
	/**
     * Constructeur de UpdateException.
     */
    public UpdateException(){
        super("Impossible de generer correctement index.adoc. ");
    }
}