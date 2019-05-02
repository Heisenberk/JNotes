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
 * Noyau de la fonction search : traite l'expression en entrée
 * et cherche les notes correspondantes dans un index Lucene
 */

public class Searcher {
    private static Query createDateQuery(String expression) throws ParseException {

    	//[d to *] date>=d 
    	//[* to d ] date<=d
    	//GERE ICI LES EXPRESSION
        QueryParser parser = new NumericDateRangeQueryParser(	Version.LUCENE_30, "date", 
        										new StandardAnalyzer(Version.LUCENE_30));//s
        String tmp = expression.substring(1, expression.length() - 1);
        String[] dates = tmp.split(" TO ");
        if (dates[0].compareTo("*") == 0)
        	dates[0] = "01/01/0000";//date min
        if (dates[1].compareTo("*") == 0)
        	dates[1] = "01/01/9999";//datesmax
        expression = "["+dates[0]+" TO "+dates[1]+"]";
        parser.setDateResolution("date", DateTools.Resolution.DAY);
    	parser.setLocale(Locale.FRENCH);
    	Query query = parser.parse(expression);
    	System.out.println(expression + " parsed to " + query);
    	return query;
    }
    
    /**
     * @param args
     * @return
     * @throws ParseException
     */
    public static Query createQuery (String[] args) throws ParseException {
    	// une recherche sur la date :date:date;
    	// sur auteur :author:auteur;
    	// sur titre :title:titre
    	// par tag :nomtag:tag --ou alors :nomtag:value
    	// contenu : justeunterme
    	BooleanQuery combinedQuery = new BooleanQuery();
    	for (int i = 1 ; i < args.length ; i++) {
    		if (args[i].length() != 0) {
    			//si l'argument commence par : on suppose que c'est un tag valide
    			if (args[i].charAt(0) == ':') {
    				String[] pair = args[i].split(":");
    				String tag = pair[1].toLowerCase();
    				if(tag.compareTo("date") == 0) {
    					Query query = createDateQuery(pair[2]);
    			        combinedQuery.add(	query, 
    			        					BooleanClause.Occur.MUST);
    				} else {
    					String value = "";
        				if(pair.length <= 3) {
        					value = pair[2].toLowerCase();
        				} else {
        					//on reconstitue les value si ils contiennent un ":"
    	    				for (int j = 3 ; j < pair.length - 1 ; j++)
    	    					value += pair[j].toLowerCase()+":";
    	    				value += pair[pair.length - 1].toLowerCase();
        				}
    					Term t;
    					//on cherche un field tel quel sans analyzer
    					if (value.contains(" ")) {System.out.println("contient espace");
    					System.out.println(value);
    						t = new Term("stored"+tag, value);
    					//on cherche en analysant 
    					}
    					else 
    						t= new Term(tag, value);
	    	    		combinedQuery.add(	new RegexQuery(t), 
	    	    							BooleanClause.Occur.MUST);
    				}
    			} else {
    				System.out.println("search by content");
    				String[] words = args[i].split(" ");
    				for(String word:words) {
    					System.out.println(word);
	    				System.out.println(args[i]);
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
    
    //args : String indexDir, String query1, OPT[String query2 ...]
	public static void search(String indexDir, String[] args) throws IOException, ParseException, SearchException {
        String q = "";
        for (int i = 1 ; i < args.length ; i++ )  {
        	System.out.println("q : "+q);
        	q += args[i]+" ";
        }
        
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
}

