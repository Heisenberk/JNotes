package fr.uvsq.jnotes.index;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.regex.RegexQuery;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

import fr.uvsq.jnotes.exception.SearchException;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * Noyau de la fonction search : traite l'expression en entree
 * et cherche les notes correspondantes  dont les informations
 * sont stockees dans un index Lucene.
 */
public class Searcher {
	
	/**
	 * Liste non exhaustive des caracteres speciaux a traiter par le Searcher.
	 */
	private static String delimiters = " &\"#'-|^@=+£$¨*µ%;:,!§<>()[]{}\\/\"";
	
	/**
	 * Methode se chargeant de la recherche de notes.
	 * Elle affiche le nom de fichier des notes qui repondent aux criteres 
	 * entres en parametre.
	 * @param indexDir l'adresse relative de l'index lucene. C'est dans ce 
	 * 			dossier que se situent les documents que lucene interrogera 
	 * 			pour effectuer des recherches.
	 * @param args tableau de chaines de caracteres contenant les criteres 
	 * 			de selections des notes
	 * @throws IOException
	 * @throws ParseException
	 * @throws SearchException
	 */
	public static void search(String indexDir, String[] args) 
			throws IOException, ParseException, SearchException {
		
        String q = join(args, " ; ", 1);
        System.out.println("Query : " + q);
        
		Directory dir = FSDirectory.open(new File(indexDir));
        IndexSearcher is = new IndexSearcher(dir);
        
        Query query = createQuery(args);
        
        long start = System.currentTimeMillis();
        TopDocs hits = is.search(query, 10);
        long end = System.currentTimeMillis();
        
        System.err.println("Found " + hits.totalHits + " document(s) (in " + (end - start) + " milliseconds) that matched query '" + q + "':");
        for(ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = is.doc(scoreDoc.doc); 
            System.out.println(doc.get("filename")); 
        }

        is.close();
    }
	
	 /**
     * Cree une requete a partir de la liste d'arguments passee en parametre.
     * Chaque argument produit une ou plusieurs requetes qui seront combinee 
     * en une BooleanQuery.
     * @param args liste de criteres que les fichiers en sortie doivent respecter
     * @return la requete combinee
     * @throws ParseException
     */
    public static Query createQuery (String[] args) throws ParseException {
    	BooleanQuery combinedQuery = new BooleanQuery();
    	for (int i = 1 ; i < args.length ; i++) {
    		
    		String condition = args[i];
    		
    		if (condition.length() != 0) {
    			//***********************************
    			// si on traite un tag ou une date
    			//***********************************
    			if (args[i].charAt(0) == ':') {
    				
    				String[] pair = condition.split(":");
    				String tag = pair[1].toLowerCase();
    				// **************************
    				// si on traite une date
    				// **************************
    				if(tag.compareTo("date") == 0) {
    					
    					Query query = createDateQuery(pair[2]);
    			        combinedQuery.add(	query, 
    			        					BooleanClause.Occur.MUST);
    			        
    			     // *************************
    			     // si on traite un tag
        			 // *************************
    				} else {
    					
        				// on reconstitue les value dans le cas ou ils contiennent un ':'
        				String value = join(pair, ":", 2);
        				
    					Term t;
    					// recherche exacte
    					if (containsStopWords(value)) 
    						t = new Term("stored"+tag, value);
    					// sinon, recherche par mot
    					else 
    						t= new Term(tag, value);
    					
	    	    		combinedQuery.add(	new RegexQuery(t), 
	    	    							BooleanClause.Occur.MUST);
    				}
    			// *************************
   			    // recherche sur le corps
       			// *************************
    			} else {
    				String[] words = condition.split(" ");
    				
    				for(String word:words) {
	    				Term t = new Term("content", word.toLowerCase());
	    				Query newquery = new RegexQuery(t);
	    	    		combinedQuery.add(	newquery, 
	    	    							BooleanClause.Occur.MUST);
    				}
    			}
    		}
    	}
    	return combinedQuery;
    }
    
    /**
     * Interprete une expression de maniere a generer une requete sur le champs date
     * que le moteur Lucene pourra comprendre. Cette requete inclut toutes les notes
     * dont la date est entre date1 et date2 passees en parametres.
     * @param expression chaine de caracteres de la forme "[date1 TO date2]" avec 
     * 				date1 et date2 de la forme dd/MM/YYYY
     * @return une requete lucene
     * @throws ParseException
     */
    private static Query createDateQuery(String expression) throws ParseException {
    	
        QueryParser parser = new NumericDateRangeQueryParser(	Version.LUCENE_30, "date", 
        										new StandardAnalyzer(Version.LUCENE_30));//s
        String tmp = expression.substring(1, expression.length() - 1);
        String[] dates = tmp.split(" TO ");
        
        if (dates[0].compareTo("*") == 0)
        	dates[0] = "01/01/0000";
        
        if (dates[1].compareTo("*") == 0)
        	dates[1] = "01/01/9999";
        
        expression = "["+dates[0]+" TO "+dates[1]+"]";
        
        parser.setDateResolution("date", DateTools.Resolution.DAY);
    	parser.setLocale(Locale.FRENCH);
    	
    	Query query = parser.parse(expression);
    	
    	//System.out.println(expression + " parsed to " + query);
    	
    	return query;
    }
    
 
    /**
     * Verifie si un mot contient un caractere special
     * @param value le mot a sonder
     * @return	true si value contient un caractere special
     * 			false sinon
     */
    private static boolean containsStopWords(String value) {
    	for (int i = 0 ; i < value.length() ; i++) {
    		String c = value.substring(i, i + 1);
    		if (delimiters.contains(c)) {
    			return true;
    		}
    	}
		return false;
	}
    
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

