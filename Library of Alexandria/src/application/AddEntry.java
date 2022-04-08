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
import java.io.PrintWriter;
import java.io.FileOutputStream;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

//Description Class
public class AddEntry {
	
	private static Template<String> template = new Template<String>(); //static reference to Template.java
	private static BorderPane bPane = new BorderPane(); //reference to bPane for event handler
	private static String bookPath = "C:\\temp\\library.txt";//reference to file path
	
	//build the add book scene
	public static Scene buildBookScene() {
		//create panes
		//place nodes
		bPane.setTop(template.CreateSimpleHBox("Enter The Correct Details of The Book"));
		bPane.setCenter(BookInfo());
		bPane.setBottom(eventBox());
		
		//set scene
		Scene bookScene = new Scene(bPane);
		return bookScene;
	}
	
	//create the ui
	private static HBox BookInfo() {
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
		
		//Vbox
		HBox hBox = new HBox();
		hBox.getChildren().addAll(authorBox, miscBox);
		hBox.setAlignment(Pos.BOTTOM_CENTER);
		hBox.setPadding(new Insets(150, 50, 50, 50));
		
		return hBox;
	}
	
	//save the book to file
	@SuppressWarnings("unchecked")
	public static void saveBook(BorderPane bPane) {
		PrintWriter file;
		Book book = new Book();
		
		//record information
		try {
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
			file = new PrintWriter(new FileOutputStream(new File(bookPath), true));
			file.println(book.recordBook());
			file.close();
			
		}catch (Exception e) {
			template.CreateErrorMessage(e.toString());
			
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
			
		//Add book, restarts the add book menu without saving
		Button btSave = template.CreateButton("Save Book");
		btSave.setOnAction(new EventHandler < ActionEvent > () {
            @Override
            public void handle(ActionEvent e) {
                saveBook(bPane);
            }
        });

		//Quit the application
		Button btQuit = template.CreateButton("Quit");
		btQuit.setOnAction(eventManager.handleQuit);
				
		//add buttons
		hBox.getChildren().addAll(btHome, btView, btSave, btQuit);
				
		//set alignment
		hBox.setAlignment(Pos.CENTER);
				
		return hBox;
	}
}



