package fr.uvsq.jnotes;

//System.out.println(new java.util.Date().getTime());
public class Note {
	private final String title;
	
	private final String author;
	private final String date;
	private final String project;
	private final String context;
	
	public static class Builder{
		//required parameters
		private final String title;
		// optional parameters initialize with default values
		private String date="2019-01-01";
		private String author="Caumes Clément, Gonthier Maxime, Merimi Mehdi, Pho Sarah";
		private String project="inf201";
		private String context="work";

		public Builder(String title){
			this.title=title;
		}

		public Builder date(String date) {
			this.date=date;
			return this;
		}
		
		public Builder author(String author){
			this.author=author;
			return this;
		}
		
		public Builder project(String project){
			this.project=project;
			return this;
		}
		
		public Builder context(String context) {
			this.context=context;
			return this;
		}

		public Note build(){
			return new Note(this);
		}
	}
	
	private Note(Builder builder){
		//required parameters
		title = builder.title;

		//optional parameters
		date = builder.date;
		author = builder.author;
		project = builder.project;
		context = builder.context;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getProject() {
		return project;
	}
	
	public String getContext() {
		return context;
	}
}
