
package fr.uvsq.jnotes.exception;

/**
 * Classe SaisieException lors d'un probleme lors de la saisie.
 */
public class SaisieException extends RuntimeException{
	
	/**
     * Constructeur de SaisieException.
     */
    public SaisieException(){
        super("Impossible de saisir les commandes dans l'interpréteur. ");
    }
    
    /**
     * onstructeur de SaisieException avec message
     * @param m message
     */
    public SaisieException(String m){
        super("Impossible de saisir les commandes dans l'interpréteur : "+m);
    }
}