package fr.uvsq.jnotes.function;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import fr.uvsq.jnotes.config.Config;
import fr.uvsq.jnotes.exception.ParamException;
import fr.uvsq.jnotes.exception.UpdateException;
import fr.uvsq.jnotes.note.ComparatorContext;
import fr.uvsq.jnotes.note.ComparatorDate;
import fr.uvsq.jnotes.note.ComparatorProject;
import fr.uvsq.jnotes.note.Note;

public class Update implements Observer{
	
	public Note readNote(String path){
		String line, sub, title = "", author = "", date = "", context = "work", project = "project";
		Path inPath = Paths.get(path);
		try (BufferedReader in = Files.newBufferedReader(inPath)) {
			
			line = in.readLine(); // lecture du titre
			if (line.charAt(0) == '=') title = line.substring(2);
			line=in.readLine(); //lecture de l'auteur
			author=line;
			
			line = in.readLine(); //lecture de la date
			date = line;

			
			while ((line = in.readLine()) != null) {
				if (line.length() >= 10) {
					sub = line.substring(0, 10);
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
			throw new UpdateException();
		}
		Note n = new Note	
    			.Builder(title)
    			.author(author)
    			.date(date)
    			.context(context)
    			.project(project)
    			.build();
		return n;
	}

	public String[] getListNotes(Config c) {
		File dossier=new File(c.getPathStockage()+"notes/");
		String[] s = null;
		if (dossier.exists() && dossier.isDirectory()){
			s = dossier.list();
			for (int i=0;i<s.length; i++) {
				s[i]=c.getPathStockage()+"notes/"+s[i];
			}
		}
		return s;
	}
	
	/*public void writeAllNotes(String path, List<Note> listeNotes) {
		Path outPath=Paths.get(path);
		
	}*/
	//affiche les differents champs d'une note
	private List<String> getAffichageGlobal(List<String> lines, List<Note> listeNotes, String[] nameNotes){
		lines.add("[options=\"header\",width=\"60%\",align=\"center\",cols=\"^,^,^,^,^\"]");
		lines.add("|====================================");
		lines.add("| Titre | Auteur | Date | Contexte | Projet");
		Note temp;
		for (int i = 0 ; i < nameNotes.length ; i++) {
			temp = listeNotes.get(i);
			lines.add(	"| "+temp.getTitle() + " | "+temp.getAuthor() + " | "+temp.getDate() + 
						" | "+temp.getContext()+" | " + temp.getProject());
		}
		lines.add("|====================================");
		return lines;
	}
	
	private List<String> getAffichageContext(List<String> lines, List<Note> listeNotes, String[] nameNotes){
		lines.add("[options=\"header\",width=\"60%\",align=\"center\",cols=\"^,^,^,^,^\"]");
		lines.add("|====================================");
		lines.add("| Titre | Auteur | Date | Contexte | Projet");
		Note temp; 
		String contextTemp=null; 
		for (int i = 0; i < nameNotes.length ; i++) {
			temp = listeNotes.get(i);
			if (contextTemp != null) {
				if (!(contextTemp.equals(temp.getContext()))) {
					lines.add(" ");
					lines.add("|====================================");
					lines.add("[options=\"header\",width=\"60%\",align=\"center\",cols=\"^,^,^,^,^\"]");
					lines.add("|====================================");
					lines.add("| Titre | Auteur | Date | Contexte | Projet");
				}
			}
			lines.add("| "+temp.getTitle()+" | "+temp.getAuthor()+" | "+temp.getDate()+" | "+temp.getContext()+" | "+temp.getProject());
			contextTemp=temp.getContext();
		}
		lines.add("|====================================");
		return lines;
	}
	
	private List<String> getAffichageProject(List<String> lines, List<Note> listeNotes, String[] nameNotes){
		lines.add("[options=\"header\",width=\"60%\",align=\"center\",cols=\"^,^,^,^,^\"]");
		lines.add("|====================================");
		lines.add("| Titre | Auteur | Date | Contexte | Projet");
		Note temp; String contextProject=null; 
		for (int i=0; i<nameNotes.length; i++) {
			temp=listeNotes.get(i);
			if (contextProject!=null) {
				if (!(contextProject.equals(temp.getProject()))) {
					lines.add(" ");
					lines.add("|====================================");
					lines.add("[options=\"header\",width=\"60%\",align=\"center\",cols=\"^,^,^,^,^\"]");
					lines.add("|====================================");
					lines.add("| Titre | Auteur | Date | Contexte | Projet");
				}
			}
			lines.add("| "+temp.getTitle()+" | "+temp.getAuthor()+" | "+temp.getDate()+" | "+temp.getContext()+" | "+temp.getProject());
			contextProject=temp.getProject();
		}
		lines.add("|====================================");
		return lines;
	}
	
	@SuppressWarnings("unchecked")
	public void makeIndex(Config configuration) {
		String[] nameNotes = getListNotes(configuration);
		List<Note> listeNotes = new ArrayList<Note>();
		
		// lecture et stockage des notes
		for (int i = 0 ; i < nameNotes.length ; i++) {
			listeNotes.add(readNote(nameNotes[i]));
		}
		
		// tri des notes selon le titre
		Collections.sort(listeNotes);
		
		// ecriture de index.adoc
		List <String> lines = new ArrayList<String>();
		lines.add("= Index");
		lines.add("JNotes");
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String date = localDate.format(formatter);
		lines.add(date);
		lines.add(":context: notes");
		lines.add(":project: jnotes");
		lines.add(" ");
		
		// si il n'y a aucune note
		if (listeNotes.isEmpty()) {
			lines.add("Aucune note gérée par JNotes. ");
		}
		// si il y a au moins une note
		else {
			// ecriture de la liste integrale des notes
			lines.add("== Liste intégrale des notes");
			getAffichageGlobal(lines, listeNotes, nameNotes);
			
			// ecriture de la liste des notes par contexte
			lines.add("== Liste de notes regroupées par contexte");
			Collections.sort(listeNotes, new ComparatorContext());
			getAffichageContext(lines, listeNotes, nameNotes);
			
			//ecriture de la liste des notes par projet
			lines.add("== Liste de notes regroupées par projet");
			Collections.sort(listeNotes, new ComparatorProject());
			getAffichageProject(lines, listeNotes, nameNotes);
			
			// ecriture de la liste des notes par mois
			lines.add("== Liste de notes triées par mois");
			Collections.sort(listeNotes, new ComparatorDate());
			getAffichageGlobal(lines, listeNotes, nameNotes);
			
			lines.add(" ");
		}
		
		Path outPath = Paths.get("config/index.adoc");
		try(
				BufferedWriter out = Files.newBufferedWriter(outPath)
			){
			
			for(int i=0;i<lines.size();i++) {
				out.write(lines.get(i));
				out.newLine();
			}
		}
		catch (IOException e) {
			throw new ParamException();
		}
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Function){       
			Function f = (Function) o;
			System.out.println("Modification de l'index effectuee. ");
			makeIndex(f.getConfig());
			// faire la lecture et l'analyse des notes
        } 
		
	}

}
