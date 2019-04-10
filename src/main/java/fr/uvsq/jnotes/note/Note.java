package fr.uvsq.jnotes.note;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Classe Note qui permet de générer une note. 
 * Cette classe utilise le pattern Builder car l'utilisateur 
 * peut créer des notes sans choisir certains paramètres. 
 */
public class Note implements Comparable{
	
	/**
	 * Titre de la note. 
	 */
	private final String title;
	
	/**
	 * Auteur de la note. 
	 */
	private final String author;
	
	/**
	 * Date de la note. 
	 */
	private final LocalDate date;
	
	/**
	 * Projet de la note. 
	 */
	private final String project;
	
	/**
	 * Contexte de la note. 
	 */
	private final String context;
	
	/**
	 * Classe Builder pour suivre le pattern Builder. 
	 * @author Clément Caumes & Mehdi Merimi
	 */
	public static class Builder{

		/**
		 * Titre paramètre obligatoire de Note.
		 */
		private final String title;
		
		/**
		 * Date paramètre optionnel de Note qui a la date du jour par défaut. 
		 */
		private LocalDate date=LocalDate.now();
		
		/**
		 * Auteur paramètre optionnel de Note qui est un auteur inconnu. 
		 */
		private String author="Auteur inconnu";
		
		/**
		 * Projet paramètre optionnel de Note qui est "projet" par défaut. 
		 */
		private String project="project";
		
		/**
		 * Contexte paramètre optionnel de Note qui est "work" par défaut. 
		 */
		private String context="work";

		/**
		 * Constructeur de Builder
		 * @param title titre qui est un paramètre obligatoire de Note.
		 */
		public Builder(String title){
			this.title=title;
		}

		/**
		 * Méthode de création pour la date de Note.
		 * @param date date qui est un paramètre optionnel de Note.
		 * @return Builder avec date initialisé.
		 */
		public Builder date(String date) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			this.date=LocalDate.parse(date, formatter);
			return this;
		}
		
		/**
		 * Méthode de création pour l'auteur de Note.
		 * @param author auteur qui est un paramètre optionnel de Note.
		 * @return Builder avec author initialisé.
		 */
		public Builder author(String author){
			this.author=author;
			return this;
		}
		
		/**
		 * Méthode de création pour le projet de Note.
		 * @param project projet qui est un paramètre optionnel de Note.
		 * @return Builder avec project initialisé.
		 */
		public Builder project(String project){
			this.project=project;
			return this;
		}
		
		/**
		 * Méthode de création pour le contexte de Note.
		 * @param context contexte qui est un paramètre optionnel de Note.
		 * @return Builder avec context initialisé.
		 */
		public Builder context(String context) {
			this.context=context;
			return this;
		}

		/**
		 * Méthode de construction de Note. 
		 * @return Note initialisé à partir d'un Builder. 
		 */
		public Note build(){
			return new Note(this);
		}
	}
	
	/**
	 * Constructeur de Note. 
	 * @param builder ayant les mêmes paramètres que Note et ayant ses paramètres
	 * initialisés. 
	 */
	private Note(Builder builder){
		//required parameters
		title = builder.title;

		//optional parameters
		date = builder.date;
		author = builder.author;
		project = builder.project;
		context = builder.context;
	}

	
	/**
	 * Accesseur du titre. 
	 * @return String représentant le titre. 
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Accesseur de l'auteur. 
	 * @return String représentant l'auteur. 
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * Accesseur de la date de la note (LocalDate). 
	 * @return LocalDate représentant la date de la note. 
	 */
	public LocalDate getDate() {
		return date;
	}
	
	/**
	 * Accesseur du projet. 
	 * @return String représentant le projet de la note. 
	 */
	public String getProject() {
		return project;
	}
	
	/**
	 * Accesseur du contexte. 
	 * @return String représentant le contexte de la note. 
	 */
	public String getContext() {
		return context;
	}
	
	/**
	 * Méthode qui va créer la note à partir des paramètres de celle-ci. 
	 * @throws IOException 
	 */
	public void create(BufferedWriter out) throws IOException {
		out.write("= "+ title);
		out.newLine();
		out.write(author);
		out.newLine();
		out.write(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		out.newLine();
		out.write(":context: "+context);
		out.newLine();
		out.write(":project: "+project);
		out.newLine();
	}


	@Override
	public int compareTo(Object o) {
		Note n=(Note)o;
		return this.title.compareTo(n.getTitle());
	}

}
