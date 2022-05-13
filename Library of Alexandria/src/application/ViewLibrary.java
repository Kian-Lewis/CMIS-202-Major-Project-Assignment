package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//Class to view books
public class ViewLibrary {
	private static Template<String> template = new Template<String>();
	private static Template<Integer> intTemplate = new Template<Integer>();
	private static String bookPath = "src\\library.txt";//reference to file path
	private static ArrayList<Book> bookArray;
	
	//Hash map to connect a description to its title
	public static MyHashMap<String, Book> descriptionMap = new MyHashMap<>();
	
	//build the scene for displaying books
	public static Scene buildLibraryScene() {
		//create the arraylist of type Book to store books from file
		try {
			cleanDuplicates(); //clean the file of duplicates before building ui
			bookArray = assembleLibrary(); //build array of books from file
			
			//Build hash map for displaying descriptions
			buildDescriptionMap();
		} catch (NullPointerException e){
			template.createErrorMessage(e.toString());
		}
		
		//sort the array list based on author last name
		bookArray = MyMergeSort.threadedMergeSort(bookArray);
		
		//build ScrollPane so that if large enough the user can scroll through their books
		ScrollPane sPane = new ScrollPane(buildLibrary());
		sPane.setFitToHeight(true);
		sPane.setFitToWidth(true);
		
		//create borderpane
		BorderPane bPane = new BorderPane();
		bPane.setTop(template.createSimpleHBox("Click on The Title To See Description"));
		bPane.setCenter(sPane); //put scroll pane in center
		bPane.setBottom(eventBox()); //put buttons on bottom
		
		//create scene using borderpane
		Scene scene = new Scene(bPane);
		return scene;
	}
	
	//Assemble the arraylist
	public static ArrayList<Book> assembleLibrary() {
		try {
			File file = new File(bookPath);
			Scanner scan = new Scanner(file);
			
			//book list using generics to make arraylist of type Book
			ArrayList<Book> bookList  = new ArrayList<Book>();
			ArrayList<Book> returnList = buildList(scan, bookList);
			
			return returnList;
		} catch (Exception e) {
			e.printStackTrace();;
		}
		return null;
	}
	
	//add each line from file to a spot on the arraylist
	public static ArrayList<Book> buildList(Scanner scan, ArrayList<Book> list) {
		//Scan for next book	
		while (scan.hasNextLine()) {
			//Create tokenizer
			String temp = (String) scan.nextLine();
			if (temp.length() < 1) {
				System.out.println("next line empty");
				break;
			}
			StringTokenizer str = new StringTokenizer((String)temp, "|");
			
			//make a new book
			Book book = new Book();
			
			//separate tokens
			for (int i = 0; str.hasMoreTokens(); i++) {
				if (i == 0) {
					book.setTitle(str.nextToken());//get title of book
				}
				else if (i == 1) {
					book.setAuthor(str.nextToken());//get author name
				}
				else if (i == 2) {
					book.setGenre(str.nextToken());//get genre
				}
				else if (i == 3){
					book.setPageCount(Integer.parseInt(str.nextToken()));//get page count
				}
				else {
					book.setDescription(str.nextToken());
				}
			}
			//Add assembled book to list
			list.add(book);
		}
		return list;
	}
	
	//Build main pane UI
	private static HBox buildLibrary() {
		//establish boxes
		HBox hBox = new HBox();
		VBox titleBox = new VBox(6);
		VBox authorBox = new VBox(6);
		VBox genreBox = new VBox(6);
		VBox pageBox = new VBox(6); 
		
		//pad the vboxes
		titleBox.setPadding(new Insets(15, 15, 15, 15));
		authorBox.setPadding(new Insets(15, 15, 15, 15));
		genreBox.setPadding(new Insets(15, 15, 15, 15));
		pageBox.setPadding(new Insets(15, 15, 15, 15));
		
		//add labels
		titleBox.getChildren().add(template.createBigLabel("Title")); //create title header
		authorBox.getChildren().add(template.createBigLabel("Genre")); //create author header
		genreBox.getChildren().add(template.createBigLabel("Author")); //create genre header
		pageBox.getChildren().add(template.createBigLabel("Pages")); //create page count header
			
		//set list size variable 
		int listSize = bookArray.size();
		//Add each individual book to display
		for (int i = 0; i < listSize; i++) {
			//get the book at the specified spot
			Book book = (Book) bookArray.get(i);
			//add each part of the book to display in order
			titleBox.getChildren().add(template.createSmallTitleLabel(book.getTitle()));
			authorBox.getChildren().add(template.createSmallLabel(book.getAuthor()));
			genreBox.getChildren().add(template.createSmallLabel(book.getGenre()));
			pageBox.getChildren().add(intTemplate.createNumSmallLabel(book.getPageCount()));
		}
			
		//add vboxes to hbox
		hBox.getChildren().addAll(titleBox, authorBox, genreBox, pageBox);
		hBox.setAlignment(Pos.CENTER);
			
		return hBox;
	}
	
	//Method that will build a hash map from the book array
	//Will have the title of the book as the key and the value being the description
	private static void buildDescriptionMap() {
		int listSize = bookArray.size();
		
		for (int i = 0; i < listSize; i++) {
			Book book = bookArray.get(i);
			String bookTitle = book.getTitle();

			descriptionMap.put(bookTitle, book);
		}
	}
	
	//Use a binary search tree to check the file for duplicate titles;
	//Improvement over the hash set solution applied before, allowing less code.
	public static void cleanDuplicates() {
		//create sets for the books, and for amount of duplicates
		ArrayList<Book> cleanList = new ArrayList<>();
		
		//Binary Search Tree(BST) for detecting duplicate Titles
		JunkBST<String> dupeTitleTree = new JunkBST<>();
		
		//Read from file for duplicate titles.
		try {
			File file = new File(bookPath);
			Scanner scan = new Scanner(file);
			
			while (scan.hasNextLine()) {
				//Create tokenizer
				String temp = (String) scan.nextLine();
				if (temp.length() < 1) {
					System.out.println("next line empty");
					break;
				}
				StringTokenizer str = new StringTokenizer((String)temp, "|");
				
				//make a new book
				Book book = new Book();
				
				//get title
				String bookTitle = str.nextToken();
				
				//check if the BST contains the title already
				if (dupeTitleTree.contains(bookTitle)) {
					System.out.println("Duplicate Book Title");
				}
				else { //build the book like normal to be sent to overwrite the file without dupes
					dupeTitleTree.insert(bookTitle);
					
					//building book from file
					book.setTitle(bookTitle);
					for (int i = 0; str.hasMoreTokens(); i++) {
						if (i == 0) {
							book.setGenre(str.nextToken());
						}
						else if (i == 1) {
							book.setAuthor(str.nextToken());
						}
						else if (i == 2){
							book.setPageCount(Integer.parseInt(str.nextToken()));//get page count
						}
						else {
							book.setDescription(str.nextToken());
						}
					}
				}
				//add the book to the array list
				cleanList.add(book);
			}
			scan.close(); //close scanner
			
			//call method to create list without duplicates
			overwriteFile(cleanList);
		} catch (Exception e) {
			template.createErrorMessage(e.toString());
		}
	}

	//Overwrite old file with new file
	public static void overwriteFile(ArrayList<Book> list) {
		PrintWriter file;
		
		for (int i = 0; i < list.size(); i++) {			
			//make a new book
			Book book = list.get(i);
			
			//try to record book to file
			try {
				//if first book then overwrite the file
				if (i == 0) {
					file = new PrintWriter(new FileOutputStream(new File(bookPath), false));
					file.println(book.recordBook());
					file.close();
				} 
				else { //if not first book then don't overwrite, append the file instead
					file = new PrintWriter(new FileOutputStream(new File(bookPath), true));
					file.println(book.recordBook());
					file.close();
				}
			} catch (Exception e){
				template.createErrorMessage(e.toString());
			}

		}
	}

	//create buttons for ui
	private static HBox eventBox() {
		//set up hbox
		HBox hBox = new HBox(15);
		hBox.setPadding(new Insets(15, 15, 15, 15));
		hBox.setStyle("-fx-background-color: gray");
		EventManager eventManager = new EventManager();
		
		//create buttons
		Button btHome = template.createButton("Home");
		btHome.setOnAction(eventManager.handleHome);
		
		//Clean the library of duplicates
		Button btClean = template.createButton("Remove Duplicates");
		btClean.setOnAction(eventManager.handleClean);
		
		//add a new book
		Button btAdd = template.createButton("New Book");
		btAdd.setOnAction(eventManager.handleAdd);
		
		//Open the description
		Button btHelp = template.createButton("Help");
		btHelp.setOnAction(eventManager.handleHelp);
		
		//Quit the application
		Button btQuit = template.createButton("Quit");
		btQuit.setOnAction(eventManager.handleQuit);
		
		//add buttons
		hBox.getChildren().addAll(btHome, btClean, btAdd, btHelp, btQuit);
		
		//set alignment
		hBox.setAlignment(Pos.CENTER);
		
		return hBox;
	}
}
