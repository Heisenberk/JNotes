package fr.uvsq.jnotes.exception;

/**
 * Classe SaisieException.
 */
public class SaisieException extends RuntimeException{
	
	/**
     * Constructeur de SaisieException.
     */
    public SaisieException(){
        super("Impossible de saisir les commandes dans l'interpréteur. ");
    }
    
    /**
     * Accesseur du message a afficher.
     */
    public String getMessage() {
    	return "Impossible de saisir les commandes dans l'interpréteur. ";
    }
}