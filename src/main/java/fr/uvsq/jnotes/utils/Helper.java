package fr.uvsq.jnotes.utils;

import java.io.File;

/**
 * Classe d'assistance
 *
 */
public class Helper {
    /**
     * Concatene les chaines du tableau de chaines de caracteres
     * en les separant avec un delimiteur.
     * @param args les arguments a concatener
     * @param delimiter le delimiteur entre chaque element
     * @param start l'indice du premier element a concatener
     * @return
     */
    public static String join(String[] args, String delimiter, int start) {
		String prefix = "";
		StringBuilder sb = new StringBuilder();
		for (int i = start ; i < args.length ; i++){
			sb.append(prefix + args[i]);
			prefix = delimiter;
		}
		return sb.toString();
	}
    
    /**
     * Transforme met toutes les chaines contenues dans un tableau
     * de chaines de caracteres en minuscules
     * @param args le tableau de strings a transformer
     */
    public static void toLowerCase(String[] args) {
    	for (int i = 0 ; i < args.length ; i++) {
    		args[i] = args[i].toLowerCase();
    	}
    }
    
    /**
     * Supprime un dossier et sons contenu recursivement
     * @param folder dossier a supprimer
     */
    public static boolean deleteFolder(File folder) {
	    File[] files = folder.listFiles();
	    if (files != null) {
	        for(File f : files) {
	            if(f.isDirectory()) {
	                deleteFolder(f);
	            } else {
	                f.delete();
	            }
	        }
	    }
	    return folder.delete();
	}

}
