package fr.uvsq.jnotes;
import java.time.LocalDate;

/**
 * Classe Note qui permet de générer une note. 
 * Cette classe utilise le pattern Builder car l'utilisateur 
 * peut créer des notes sans choisir certains paramètres. 
 * @author Clément Caumes & Mehdi Merimi
 */
public class Note {
	
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
		 * Auteur paramètre optionnel de Note qui est l'ensemble de l'équipe du projet 
		 * par défaut. 
		 */
		private String author="Caumes Clément, Gonthier Maxime, Merimi Mehdi, Pho Sarah";
		
		/**
		 * Projet paramètre optionnel de Note qui est "inf201" par défaut. 
		 */
		private String project="inf201";
		
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
			this.date=LocalDate.parse(date);
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
	 * Accesseur de la date de la note (String).
	 * @return String représentant la date de la note. 
	 */
	private String getDateString() {
		return date.toString();
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
	 */
	public void create() {
		// a remplir
	}
}
