package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//Class to view books
public class ViewLibrary {
	private static Template<String> template = new Template<String>();
	private static Template<Integer> intTemplate = new Template<Integer>();
	private static String bookPath = "C:\\temp\\library.txt";//reference to file path
	private static ArrayList<Book> bookArray;
	
	//build the scene for displaying books
	public static Scene buildLibraryScene() {
		//create the arraylist of type Book to store books from file
		int listSize = 0;
		try {
			bookArray = assembleLibrary();
			listSize = bookArray.size();
		} catch (NullPointerException e){
			template.CreateErrorMessage(e.toString());
		}
		
		//sort the arraylist
		mergeSort(0, listSize - 1);
		
		//create borderpane
		BorderPane bPane = new BorderPane();
		bPane.setCenter(buildLibrary());
		bPane.setBottom(eventBox());
		
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
			template.CreateErrorMessage(e.toString());
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
				else {
					book.setPageCount(Integer.parseInt(str.nextToken()));//get page count
				}
			}
			//Add assembled book to list
			list.add(book);
		}
		return list;
	}
	
	//Comments on merge sort efficiency
	/*
	 The way that merge sort works is by taking on a divide and conquer type approach. It recursively breaks down a problem into two or more 
	 sub-problems, and then breaking the sub-problems into more sub-problems, etc... eventually the problems will be simple enough to be solved 
	 in a direct manner. After a problem reaches that point, the sub-problems will have their solutions combined(merged) back into one big solution 
	 to the original problem.
	 
	 The steps taken consist of:
	 	 - checking whether there is more than 1 element in the list, if there isn't then the sort does not execute
	 	 - recursively divide the list into halves until it can't be divided anymore
	 	 - merge the smaller list back into a new list in the correct order.
	 	 
	 The space complexity for merge sort is O(n) but shouldn't be too relevant anyway, as most users of this program won't have a large enough library 
	 for this to be relevant.
	 
	 The time complexity is the O(nLogn) in all cases. This means that as the program expands and as user libraries grow larger the complexity will 
	 not increase and will remain consistent. This means that the program may slow down as libraries get extremely large, but this program was meant 
	 for smaller libraries in the first place, so such a problem shouldn't be relevant.
	 
	 Overall complexity wasn't much of a consideration because, as stated earlier libraries of users shouldn't be large enough for that to be relevant.
	 As a whole though, if a users library were to be extremely large, merge sort would be more efficient than, for example, quick sort. This is because 
	 the worst case for merge sort is O(nLogn), whereas quick sorts is O(n).
	 */
	
	//starts the sorting process
	public static void mergeSort(int startIndex, int endIndex) {
		if (startIndex < endIndex && (endIndex - startIndex) >= 1) {
			//separate the halves
			int midPoint = (endIndex + startIndex) / 2;

			mergeSort(startIndex, midPoint);
			mergeSort(midPoint + 1, endIndex);
			
			//merge the indexes into one
			merge(startIndex, midPoint, endIndex);
		}
	}
	
	//puts the arraylist back together
	public static void merge(int startIndex, int midPoint, int endIndex) {
		
		ArrayList<Book> mergeSortedArray = new ArrayList<Book>();
		
		int leftIndex = startIndex;
		int rightIndex = midPoint + 1;
		
		while(leftIndex <= midPoint && rightIndex <= endIndex) {

			if (bookArray.get(leftIndex).getLastName().charAt(0) <= bookArray.get(rightIndex).getLastName().charAt(0)) {
				mergeSortedArray.add(bookArray.get(leftIndex));
				leftIndex++;
			} else {
				mergeSortedArray.add(bookArray.get(rightIndex));
				rightIndex++;
			}
		}
		
		//One while loop below will execute
		while(leftIndex <= midPoint) {
			mergeSortedArray.add(bookArray.get(leftIndex));
			leftIndex++;
		}
		
		while(rightIndex <= endIndex) {
			mergeSortedArray.add(bookArray.get(rightIndex));
			rightIndex++;
		}

		int i = 0;
		int k = startIndex;
		while (i < mergeSortedArray.size()) {
			bookArray.set(k, (Book) mergeSortedArray.get(i++));
			k++;
		}
	}
	
	//Build UI
	private static HBox buildLibrary() {
		//establish boxes
		HBox hBox = new HBox();
		VBox titleBox = new VBox();
		VBox authorBox = new VBox();
		VBox genreBox = new VBox();
		VBox pageBox = new VBox(); 
		
		//pad the vboxes
		titleBox.setPadding(new Insets(15, 15, 15, 15));
		authorBox.setPadding(new Insets(15, 15, 15, 15));
		genreBox.setPadding(new Insets(15, 15, 15, 15));
		pageBox.setPadding(new Insets(15, 15, 15, 15));
		
		//add labels
		titleBox.getChildren().add(template.CreateMainLabel("Title", "black")); //create title header
		authorBox.getChildren().add(template.CreateMainLabel("Author", "black")); //create author header
		genreBox.getChildren().add(template.CreateMainLabel("Genre", "black")); //create genre header
		pageBox.getChildren().add(template.CreateMainLabel("Pages", "black")); //create page count header
		
		//set list size variable 
		int listSize = bookArray.size();
		//Add each individual book to display
		for (int i = 0; i < listSize; i++) {
			//get the book at the specified spot
			Book book = (Book) bookArray.get(i);
			//add each part of the book to display in order
			titleBox.getChildren().add(template.CreateSmallLabel(book.getTitle()));
			authorBox.getChildren().add(template.CreateSmallLabel(book.getAuthor()));
			genreBox.getChildren().add(template.CreateSmallLabel(book.getGenre()));
			pageBox.getChildren().add(intTemplate.CreateNumSmallLabel(book.getPageCount()));
		}
		
		//add vboxes to hbox
		hBox.getChildren().addAll(titleBox, authorBox, genreBox, pageBox);
		hBox.setAlignment(Pos.CENTER);
		
		return hBox;
	}

	//create buttons for ui
	private static HBox eventBox() {
		//set up hbox
		HBox hBox = new HBox(15);
		hBox.setPadding(new Insets(15, 15, 15, 15));
		hBox.setStyle("-fx-background-color: gray");
		EventManager eventManager = new EventManager();
		
		//create buttons
		Button btHome = template.CreateButton("Home");
		btHome.setOnAction(eventManager.handleHome);
		
		//add a new book
		Button btAdd = template.CreateButton("New Book");
		btAdd.setOnAction(eventManager.handleAdd);
		
		//Open the description
		Button btHelp = template.CreateButton("Help");
		btHelp.setOnAction(eventManager.handleHelp);
		
		//Quit the application
		Button btQuit = template.CreateButton("Quit");
		btQuit.setOnAction(eventManager.handleQuit);
		
		//add buttons
		hBox.getChildren().addAll(btHome, btAdd, btHelp, btQuit);
		
		//set alignment
		hBox.setAlignment(Pos.CENTER);
		
		return hBox;
	}
}
