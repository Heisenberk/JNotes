package fr.uvsq.jnotes.exception;

/**
 * Classe ParamException.
 */
public class ParamException extends RuntimeException{
	
	/**
     * Constructeur de ParamException.
     */
    public ParamException(){
        super("Impossible d'interagir avec le fichier de configuration de l'application. ");
    }
}