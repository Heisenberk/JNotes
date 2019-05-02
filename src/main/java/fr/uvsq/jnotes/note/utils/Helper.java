package fr.uvsq.jnotes.note.utils;

/**
 * Classe d'assistance, notamment pour la manipulation de chaines de caracteres
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

}
