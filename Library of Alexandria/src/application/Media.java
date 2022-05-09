package application;

//consructors, mutators, and accessors for media objects.
public class Media {
	
	//Declaring variables
	private String title; //title
	private String genre; //book genre, displayed according to number
	private String description; //description of the media

	//Constructors
	public Media(String title, String genre, String description) {
		this.title = title;
		this.genre = genre;
		this.description = description;
	}
	
	public Media() {
		title = "";
		genre = "";
		description = "";
	}
	
	//Mutators
	public void setTitle(String title) {//set book title
		this.title = title;
	}
	public void setGenre(String genre) {//set genre
		this.genre = genre;
	}
	public void setDescription(String description) {//set description
		this.description = description;
	}
	
	//Accessors

	public String getTitle() {
		return (String) titleCase(title);
	}
	public String getGenre() {
		return genre;
	}
	public String getDescription() {
		return description;
	}
	
	//make string title case
	public String titleCase(String title) {
		String words[] = title.split("\\s");
		String capital = "";
		for(String w:words) {
			String first = w.substring(0,1);
			String next = w.substring(1);
			capital += first.toUpperCase() + next + " ";
		}
		
		return capital.trim();
	}
	
	//put into file format
	public String recordBook() {
		//Break up book into components
		String recordTitle = getTitle();
		String recordGenre = getGenre();
		
		//make record to be added to file
		String record = (titleCase(recordTitle) + "|" + recordGenre);
		
		return record;
	}
}


