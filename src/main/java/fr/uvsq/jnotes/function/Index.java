package fr.uvsq.jnotes.function;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import fr.uvsq.jnotes.exception.IndexException;
import fr.uvsq.jnotes.note.Note;

public class Index implements Observer{
	
	public Note readNote(String path){
		String line, sub, title="", author="", date="", context="work", project="project";
		Path inPath = Paths.get(path);
		try (
			BufferedReader in = Files.newBufferedReader(inPath);
		){
			
			line=in.readLine(); // lecture du titre
			if (line.charAt(0)=='=') title=line.substring(2);
			
			line=in.readLine(); //lecture de l'auteur
			author=line;
			
			line=in.readLine(); //lecture de la date
			date=line;
			
			while((line=in.readLine())!=null) {
				if (line.length()>=10) {
					sub=line.substring(0, 10);
					if (sub.equals(":context: ")) {
						context=line.substring(10);
					}
					else if (sub.equals(":project: ")) {
						project=line.substring(10);
					}
				}
			}
		}
		catch(Exception e) {
			throw new IndexException();
		}
		Note n = new Note	
    			.Builder(title)
    			.author(author)
    			.context(context)
    			.project(project)
    			.build();
		return n;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Function){       
			Function f = (Function) o;
			System.out.println("Modification de l'index effectuee. ");
			// faire la lecture et l'analyse des notes
        } 
		
	}

}
