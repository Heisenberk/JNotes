package fr.uvsq.jnotes.index;

import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

import fr.uvsq.jnotes.utils.Helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.FileReader;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe chargee de l'indexation des notes. 
 * Elle preleve les informations importantes des notes et les stocke dans un index lucene que le moteur lucene 
 * pourra interpreter lors de la recherche.
 */
public class Indexer {

  	/**
  	 * Formateur de date qui interprete les dates au format francais.
  	 */
  	private static DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  	/**
  	 * Formateur de date qui interprete les dates au format enregistre dans l'index lucene.
  	 */
  	private static DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * Objet qui cree et modifie l'index.
     */
    private IndexWriter writer;
    
    /**
     * Constructeur de l'Indexer.
     * @param indexDir l'adresse relative du dossier qui contiendra l'index
     * @throws IOException
     */
    public Indexer(String indexDir) throws IOException {
        Directory dir = FSDirectory.open(new File(indexDir));
        writer = new IndexWriter(dir, new StopAnalyzer(Version.LUCENE_30), 
        						true, IndexWriter.MaxFieldLength.UNLIMITED);
    }
    
	/**
	 * Methode statique qui indexe les documents se trouvant dans le dossier passe en parametre.
	 * @param indexDir l'adresse du dossier contenant l'index lucene
	 * @param dataDir l'adresse du dossier contenant les donnees a indexer
	 * @throws Exception
	 */
	public static void indexer(String indexDir, String dataDir) throws Exception {
		dataDir += "notes";
		Helper.deleteFolder(new File(indexDir));
		Indexer indexer = new Indexer(indexDir);
		int numIndexed;
		try {
			numIndexed = indexer.index(dataDir, new TextFilesFilter());
		} finally {
			indexer.close();
		}
		System.out.println("Indexation de " + numIndexed + " fichiers");
	}

	/**
	 * Classe qui filtre les fichiers qui ne sont pas des notes 
	 *
	 */
	private static class TextFilesFilter implements FileFilter {
		public boolean accept(File path) {
			return path.getName().toLowerCase().endsWith(".adoc");
		}
	}
	
	/**
	 * Indexe chaque note contenu dans le nom de dossier passe en parametre.
	 * @param dataDir l'adresse relative du dossier contenant les donnees
	 * @param filter filtre les fichiers qui ne doivent pas etre traites
	 * @return le nombre de fichiers indexes
	 * @throws Exception
	 */
	public int index(String dataDir, FileFilter filter) throws Exception {
		File[] files = new File(dataDir).listFiles();
    	for (File f: files) {
    		if (!f.isDirectory() && !f.isHidden() && f.exists()
    		&& f.canRead() && (filter == null || filter.accept(f)))
    			indexFile(f);
    	}
    	return writer.numDocs();
	}

	/**
	 * Ferme l'indexWriter
	 * @throws IOException
	 */
	public void close() throws IOException {
		writer.close();
	}

	/**
	 * Indexe le fichier passe en parametre.
	 * @param f la note a indexer
	 * @throws Exception
	 */
	private void indexFile(File f) throws Exception {
		Document doc = getDocument(f);
		writer.addDocument(doc);                              
	}
	
	/**
	 * Cree un document contenant toutes les informations des notes.
	 * Ce document sera interprete par writer puis stocke dans l'index.
	 * @param f la note a indexer
	 * @return le document à stocker
	 * @throws Exception
	 */
	private static Document getDocument(File f) throws Exception {
		Document doc = new Document();
		//*********************************************************
		//	Champs contenant un Reader sur le corps uniquement de la note uniquement
		//*********************************************************
		doc.add(new Field("content", new ContentBufferedReader(new FileReader(f))));

		//*********************************************************
		//	Champs contenant le nom du fichier
		//********************************************************
		doc.add(new Field("filename", f.getName(),              
					Field.Store.YES, Field.Index.NOT_ANALYZED));
		
		addTags(f, doc);
		return doc;
	}
	
	/**
	 * Ajoute au document passe en parametre tous les champs de la note.
	 * @param f la note a indexer
	 * @param doc le document a stocker et complementer
	 * @throws IOException
	 * @throws ParseException
	 */
	private static void addTags(File f, Document doc) throws IOException, ParseException {
		
		// On va lire le contenu de la note f et dispatcher son contenu
		// dans les differents champs du document
	  	BufferedReader in = new BufferedReader(new FileReader(f));
	  	

		//*********************************************************
		//	Champs contenant le titre de la note
		//********************************************************
	  	String line = in.readLine().toLowerCase();
	  	addTags(doc, "title", line.substring(2));
	  	
	  	//*********************************************************
	  	//	Champs contenant le nom de l'auteur de la note
	  	//********************************************************
	  	line = in.readLine().toLowerCase();
	  	addTags(doc, "author", line);
	  	
	  	//*********************************************************
	  	//	Champs contenant la date de la note
	  	//********************************************************
	  	line = in.readLine().toLowerCase();	  	
	  	String date = LocalDate.parse(line, dtf1).format(dtf2);
	  	doc.add(new NumericField("date")
	  			.setIntValue(Integer.parseInt(date))); 
	  	
	  	//*********************************************************
	  	//	Champs contenant le tag 'context' de la note
	  	//********************************************************
	  	line = in.readLine().toLowerCase();
	  	addTags(doc, "context", line.split(":")[2].substring(1));
	  	
	  	//*********************************************************
	  	//	Champs contenant le tag 'project' de la note
	  	//********************************************************
	  	line = in.readLine().toLowerCase();
	  	addTags(doc, "project", line.split(":")[2].substring(1));
	  	
	  	
		in.close();
	}
	
	/**
	 * Ajoute au document passe en parametre les champs/tags qui 
	 * vont etre tokenises mais egalement stockes tels quels.
	 * 
	 * Il y a plusieurs types de champs : 
  	 * - ceux qui vont etre tokenises et analyses mot par mot par l'analyseur.
	 * - ceux qui sont stockes tels quels et non analyses.
	 * @param doc le document a complementer
	 * @param tag le champs a fournir
	 * @param value la valeur du champs a fournir
	 */
	private static void addTags(Document doc, String tag, String value) {
		
		doc.add(new Field(tag, value,     			// tokenized
				Field.Store.YES, Field.Index.ANALYZED));
		
		doc.add(new Field("stored" + tag, value,		// stored
				Field.Store.YES, Field.Index.NOT_ANALYZED));
	}

	
	/**
	 * Supprime de l'index le fichier dont le nom est passe en parametre
	 * @param indexDir emplacement de l'index
	 * @param fileName nom de la note supprimee
	 * @throws Exception
	 */
	public static void delete(String indexDir, String fileName) throws Exception {
		Indexer indexer = new Indexer(indexDir);
		try {
			indexer.writer.deleteDocuments(new Term("filename", fileName));
		} finally {
			indexer.close();
		}
		System.out.println("Suppression de " + fileName + " de l'index");
	}
	
	/**
	 * Met a jour dans l'index les informations de la note passee en 
	 * parametre
	 * @param indexDir emplacement de l'index lucene
	 * @param dataDir emplacement des notes
	 * @param fileName nom de la note
	 * @throws Exception
	 */
	public static void update(String indexDir, String dataDir, String fileName) throws Exception {
		Indexer indexer = new Indexer(indexDir);
		
		String fullFileName = dataDir+"/"+fileName;
		System.out.println("Debut suppression de " + fullFileName);
		
		File f = new File(fullFileName);
		
		Document doc = 	getDocument(f);

		try {
			indexer.writer.updateDocument(new Term("filename", fileName), doc);
		} finally {
			indexer.close();
		}
		System.out.println("Mise a jour de " + fileName + " de l'index");
	}
}

