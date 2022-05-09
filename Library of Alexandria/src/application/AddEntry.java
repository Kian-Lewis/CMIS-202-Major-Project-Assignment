//Kian Lewis
package application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.io.FileOutputStream;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

//Description Class
public class AddEntry {
	
	private static Template<String> template = new Template<String>(); //static reference to Template.java
	public static BorderPane bPane = new BorderPane(); //reference to bPane for event handler
	private static String bookPath = "C:\\temp\\library.txt";//reference to file path
	private static Queue<Book> saveQueue = new LinkedList<>();
	
	//build the add book scene
	public static Scene buildBookScene() {
		//create panes
		//place nodes
		bPane.setTop(template.CreateSimpleHBox("Enter The Correct Details of The Book"));
		bPane.setCenter(bookInfo());
		bPane.setBottom(eventBox());
		
		//set scene
		Scene bookScene = new Scene(bPane);
		return bookScene;
	}
	
	//create the information fields
	private static HBox bookInfo() {
		//create panes
		VBox authorBox = new VBox(15);
		VBox miscBox = new VBox(15);
		
		//declaring textfields
		TextField titleText = new TextField();//title
		TextField fName = new TextField();//first name
		TextField mName = new TextField();//middle initial/name
		TextField lName = new TextField();//last name
		TextField pgCount = new TextField();//page count
		
		//combobox and checkbox
		ComboBox<String> genreBox = new ComboBox<>();
		genreBox.getItems().addAll("Fantasy", "Sci-Fi", "Mystery", "Romance", "Children's", "Comedy", "Young Adult", 
				 "Biography", "Auto-Biography", "Academic", "History", "Religion");
		genreBox.setValue("Fantasy");
		
		//set contents of authorPane
		authorBox.getChildren().addAll((template.CreateSmallLabel("Title of The Book:")), titleText, template.CreateSmallLabel("Author's First Name:"), fName, 
				template.CreateSmallLabel("Author's Middle Initial:"), mName, template.CreateSmallLabel("Author's Last Name:"), lName);
		
		//Set contents of miscPane
		miscBox.getChildren().addAll(template.CreateSmallLabel("Page Count:"), pgCount, template.CreateSmallLabel("Genre:"), genreBox);
		
		//Vboxes get added to Hbox
		HBox hBox = new HBox();
		hBox.getChildren().addAll(authorBox, miscBox);
		
		hBox.setAlignment(Pos.BOTTOM_CENTER);
		hBox.setPadding(new Insets(150, 50, 50, 50));
		
		return hBox;
	}
	
	//save the book to queue
	@SuppressWarnings("unchecked")
	public static void saveBookToQueue(BorderPane bPane) {
		Book book = new Book();
		
		//record information
		//get contents of borderpane
		HBox centerBox = (HBox) bPane.getCenter();
		VBox leftBox = (VBox) centerBox.getChildren().get(0);
		VBox rightBox = (VBox) centerBox.getChildren().get(1);
		
		//record author
		String firstName = (String) ((TextField) leftBox.getChildren().get(3)).getText();
		String midInitial = (String) ((TextField) leftBox.getChildren().get(5)).getText();
		String lastName = (String) ((TextField) leftBox.getChildren().get(7)).getText();
		book.setAuthor(firstName, midInitial, lastName);		
			
		//record title
		book.setTitle((String) ((TextField) leftBox.getChildren().get(1)).getText());
			
		//record page count
		String pgCt = ((String) (((TextField) rightBox.getChildren().get(1)).getText()));
		book.setPageCount(Integer.parseInt(pgCt));
			
		//record genre
		book.setGenre((String) ((ComboBox<String>)rightBox.getChildren().get(3)).getSelectionModel().getSelectedItem());
			
		//Record book to file if not comic
		saveQueue.add(book);
		
		//Reset the textFields
		((TextField) leftBox.getChildren().get(1)).setText("");
		((TextField) leftBox.getChildren().get(3)).setText("");
		((TextField) leftBox.getChildren().get(5)).setText("");
		((TextField) leftBox.getChildren().get(7)).setText("");
		((TextField) rightBox.getChildren().get(1)).setText("");
	}
	
	//Save the books in queue to file
	public static void saveBooksInQueue(BorderPane bPane) {
		PrintWriter file;
		
		//get boxes
		HBox centerBox = (HBox) bPane.getCenter();
		VBox leftBox = (VBox) centerBox.getChildren().get(0);
		VBox rightBox = (VBox) centerBox.getChildren().get(1);
		
		//get textfields
		String Title = (String) ((TextField) leftBox.getChildren().get(1)).getText();
		String fName = (String) ((TextField) leftBox.getChildren().get(3)).getText();
		String mI = (String) ((TextField) leftBox.getChildren().get(5)).getText();
		String lName = (String) ((TextField) leftBox.getChildren().get(7)).getText();
		String pgCt = ((String) (((TextField) rightBox.getChildren().get(1)).getText()));
		
		try {
			//Check to see if the textFields have contents, if so save book
			//If nothing in the textfields then error is caught and queue is saved without adding current textfields to queue first.
			if (Title != null || fName != null || mI != null || lName != null || pgCt != null) {
				saveBookToQueue(bPane);
			}
			
			//go through the queue to save the books and delete them from queue.
			while (saveQueue.peek() != null) {
				Book book = saveQueue.poll();
				
				//try to record book to file
				try {
				file = new PrintWriter(new FileOutputStream(new File(bookPath), true));
				file.println(book.recordBook());
				file.close();
				} catch (FileNotFoundException d) {
					template.CreateErrorMessage(d.toString());
				}
			}
		}catch (NumberFormatException e) { //Checks if textFields are empty
			//try to save book again without saving textFields to queue first
			try {				
				while (saveQueue.peek() != null) {
					Book book = saveQueue.poll();
					
					file = new PrintWriter(new FileOutputStream(new File(bookPath), true));
					file.println(book.recordBook());
					file.close();
				}
			} catch (Exception c) {
				template.CreateErrorMessage(c.toString());
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
				
		//Back to main menu
		Button btHome = template.CreateButton("Home");
		btHome.setOnAction(eventManager.handleHome);
				
		//View Library
		Button btView = template.CreateButton("Books");
		btView.setOnAction(eventManager.handleView);
		
		//Save book to queue button
		Button btQueue = template.CreateButton("Add Another Book");
		btQueue.setOnAction(eventManager.handleQueue);
		
		//Save queue to file button
		Button btSave = template.CreateButton("Save Books");
		btSave.setOnAction(eventManager.handleSave);

		//Quit the application
		Button btQuit = template.CreateButton("Quit");
		btQuit.setOnAction(eventManager.handleQuit);
				
		//add buttons
		hBox.getChildren().addAll(btHome, btQueue, btSave, btView, btQuit);
				
		//set alignment
		hBox.setAlignment(Pos.CENTER);
				
		return hBox;
	}
}



