package application;

//consructors, mutators, and accessors for book objects.
public class Media {
	
	//Declaring variables
	private String title; //title
	private String genre; //book genre, displayed according to number
	
	//Constructors
	public Media(String title, String genre) {
		this.title = title;
		this.genre = genre;
	}
	
	public Media() {
		title = "";
		genre = "";
	}
	
	//Mutators
	public void setTitle(String title) {//set book title
		this.title = title;
	}
	public void setGenre(String genre) {//set genre
		this.genre = genre;
	}
	
	//Accessors

	public String getTitle() {
		return (String) titleCase(title);
	}
	public String getGenre() {
		return genre;
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


