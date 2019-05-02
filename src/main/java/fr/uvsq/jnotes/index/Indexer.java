package fr.uvsq.jnotes.index;

import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.FileReader;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class Indexer {

  	static DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  	static DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMdd");

    private IndexWriter writer;
    
    public Indexer(String indexDir) throws IOException {
        Directory dir = FSDirectory.open(new File(indexDir));
        writer = new IndexWriter(dir, new StopAnalyzer(Version.LUCENE_30), true, IndexWriter.MaxFieldLength.UNLIMITED);
    //  writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_30), true, IndexWriter.MaxFieldLength.UNLIMITED);
      }
public static void indexer(String indexDir, String dataDir) throws Exception {
	dataDir += "notes";
	//System.out.println("index = "+indexDir+", dataDir = "+dataDir);
	long start = System.currentTimeMillis();
	Indexer indexer = new Indexer(indexDir);
	int numIndexed;
	try {
		numIndexed = indexer.index(dataDir, new TextFilesFilter());
	} finally {
		indexer.close();
	}
	long end = System.currentTimeMillis();
	System.out.println("Indexing " + numIndexed + " files took " + (end - start) + " milliseconds");
}

  

	public void close() throws IOException {
		writer.close();
	}

	public int index(String dataDir, FileFilter filter) throws Exception {
		File[] files = new File(dataDir).listFiles();
    	for (File f: files) {
    		if (!f.isDirectory() && !f.isHidden() && f.exists()
    		&& f.canRead() && (filter == null || filter.accept(f)))
    			indexFile(f);
    	}
    	return writer.numDocs();
	}

	private static class TextFilesFilter implements FileFilter {
		public boolean accept(File path) {
			return path.getName().toLowerCase().endsWith(".adoc");
		}
	}

	protected void addTags(Document doc, String tag, String value) {
		//System.out.println(tag + " : " + value);
		//plusieurs types de field; ceux qui vont être analysés ( donc tokenizés ), ceux qui contiennent une suite avec ' '
		doc.add(new Field(tag, value.toLowerCase(),     //10
				Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("stored" + tag, value.toLowerCase(),
				Field.Store.YES, Field.Index.NOT_ANALYZED));
	}
	protected void addTags(File f, Document doc) throws IOException, ParseException {
		//analyse les tags dans le fichier et les rajoute dans le fichier f
	  	BufferedReader in = new BufferedReader(new FileReader(f));
	  	String line = in.readLine();
	  	//on enleve le =
	  	addTags(doc, "title", line.substring(2));
	  	
	  	line = in.readLine();
	  	addTags(doc, "author", line);
	  	
	  	line = in.readLine();	  	
	  	String date = LocalDate.parse(line, dtf1).format(dtf2);
	  	//date = "01/02/2018 devient 20180201 dans l'index
	  	doc.add(new NumericField("date")
	  			.setIntValue(Integer.parseInt(date))); 
	  	
	  	line = in.readLine();
	  	addTags(doc, "context", line.split(":")[2].substring(1));
	  	
	  	line = in.readLine();
	  	addTags(doc, "project", line.split(":")[2].substring(1));
		in.close();
	}
	protected Document getDocument(File f) throws Exception {
		Document doc = new Document();
		doc.add(new Field("content", new ContentBufferedReader(new FileReader(f))));      //7
		doc.add(new Field("filename", f.getName(),              //8
					Field.Store.YES, Field.Index.NOT_ANALYZED));//8
		addTags(f, doc);
		return doc;
	}

	private void indexFile(File f) throws Exception {
		//System.out.println("\nIndexing " + f.getCanonicalPath());
		Document doc = getDocument(f);
		writer.addDocument(doc);                              
	}
}

