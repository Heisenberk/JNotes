package fr.uvsq.jnotes.index;


import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.FileReader;

public class Indexer {

  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
    	System.out.println("nb args : " + args.length);
      throw new IllegalArgumentException("Usage: java " + Indexer.class.getName() + " <index dir> <data dir>");
    }
    String indexDir = args[0];         //1
    String dataDir = args[1];          //2
    System.out.println("index = "+indexDir+", dataDir = "+dataDir);
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

  private IndexWriter writer;

  public Indexer(String indexDir) throws IOException {
    Directory dir = FSDirectory.open(new File(indexDir));
    //writer = new IndexWriter(dir, new StopAnalyzer(Version.LUCENE_30), true, IndexWriter.MaxFieldLength.UNLIMITED);
  writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_30), true, IndexWriter.MaxFieldLength.UNLIMITED);
  }

  public void close() throws IOException {
    writer.close();
  }

  public int index(String dataDir, FileFilter filter) throws Exception {
    File[] files = new File(dataDir).listFiles();
    for (File f: files) {
        if (!f.isDirectory() && !f.isHidden() && f.exists() && f.canRead() && (filter == null || filter.accept(f)))
            indexFile(f);
    }
    return writer.numDocs();
  }

  private static class TextFilesFilter implements FileFilter {
    public boolean accept(File path) {
      return path.getName().toLowerCase().endsWith(".adoc");
    }
  }
//  ANALYZED
//  Index the tokens produced by running the field's value through an Analyzer.
//  ANALYZED_NO_NORMS
//  Expert: Index the tokens produced by running the field's value through an Analyzer, and also separately disable the storing of norms.
//  NO
//  Do not index the field value.
//  NOT_ANALYZED
//  Index the field's value without using an Analyzer, so it can be searched.
//  NOT_ANALYZED_NO_NORMS
//  Expert: Index the field's value without an Analyzer, and also disable the storing of norms.
  protected void addTags(File f, Document doc) throws IOException {
	  //analyse les tags dans le fichier et les rajoute dans le fichier f
	  	BufferedReader in = new BufferedReader(new FileReader(f));
		in.readLine();
		in.readLine();
		in.readLine();
		String line = in.readLine();
		while (line != null && (line.length() == 0 ||
				( line.length() >= 2 && line.charAt(0) == ':'))) {
			if(line.length() == 0) {
				line = in.readLine();
				continue;
			}
			//skip les lignes vides

			System.out.println("line : " + line);
			String[] words = line.split(" ");
			String tag = words[0].split(":")[1];
			System.out.println("tag : " + tag);
			if (words.length == 1) {
				System.out.println("NO VALUE");
				line = in.readLine();
				continue;//new line/tag
			}
			String values = "";
			for (int i = 1 ; i != words.length ; i++) {
				values += words[i] + " ";
			}
			System.out.println("values : " + values);
		    doc.add(new Field(tag, "\""+values+"\"",     //10
	                Field.Store.YES, Field.Index.ANALYZED));//10
		    line = in.readLine();
		}
		in.close();
  }
  protected Document getDocument(File f) throws Exception {
    Document doc = new Document();
    BufferedReader in = new BufferedReader(new FileReader(f));
    doc.add(new Field("title", in.readLine(),
    		Field.Store.YES, Field.Index.NOT_ANALYZED));
    doc.add(new Field("author", in.readLine(),
    		Field.Store.YES, Field.Index.NOT_ANALYZED));
    doc.add(new Field("date", in.readLine(),//indexer une date direct plutôt qu'un string ?
    		Field.Store.YES, Field.Index.NOT_ANALYZED));
    //doc.add(new Field("contents", new FileReader(f)));      //7
    doc.add(new Field("contents", new ContentBufferedReader(new FileReader(f))));      //7
    doc.add(new Field("filename", f.getName(),              //8
                Field.Store.YES, Field.Index.NOT_ANALYZED));//8
    doc.add(new Field("fullpath", f.getCanonicalPath(),     //9
                Field.Store.YES, Field.Index.NOT_ANALYZED));//9
    addTags(f, doc);
    in.close();
    return doc;
  }

  private void indexFile(File f) throws Exception {
    System.out.println("Indexing " + f.getCanonicalPath());
    Document doc = getDocument(f);
    writer.addDocument(doc);                              //10
  }

	/*
	#1 Create index in this directory
	#2 Index *.txt files from this directory
	#3 Create Lucene IndexWriter
	#4 Close IndexWriter
	#5 Return number of documents indexed
	#6 Index .txt files only, using FileFilter
	#7 Index file content
	#8 Index file name
	#9 Index file full path
	#10 Add document to Lucene index
	*/


}
