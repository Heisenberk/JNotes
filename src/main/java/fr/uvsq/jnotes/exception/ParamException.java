package fr.uvsq.jnotes.exception;

/**
 * Classe ParamException qui est utilise lors d'un probleme de leccture du fichier de configuration.
 */
public class ParamException extends RuntimeException{
	
	/**
     * Constructeur de ParamException.
     */
    public ParamException(){
        super("Impossible d'interagir avec le fichier de configuration de l'application. ");
    }
}