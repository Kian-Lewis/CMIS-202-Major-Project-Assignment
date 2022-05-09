package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

//class just creates a scene to dsiplay the attributes of a book object
public class BookDisplay {
	//Creating references to generic template class
	static Template<String> template = new Template<>();
	static Template<Integer> intTemplate = new Template<>();
	
	//Create the scene for displaying book information
	public static Scene buildBookDisplayScene(Book book) {
		//create main pane
		BorderPane pane = new BorderPane();
		
		//set pane positions for ui
		pane.setCenter(buildDisplay(book));
		pane.setBottom(eventBox());
		
		//create new scene using the pane
		Scene scene = new Scene(pane);
		return scene;
	}
	
	//Create the pane to showcase the book attributes
	public static BorderPane buildDisplay(Book book) {
		BorderPane bPane = new BorderPane(); //create main Vbox
		
		//Create HBoxes
		HBox titleBox = new HBox();
		HBox descriptionBox = new HBox();
		HBox bottomBox = new HBox(50);
		
		//Inserting book contents
		titleBox.getChildren().add(template.CreateHugeLabel(book.getTitle()));//Creating title text
		descriptionBox.getChildren().add(template.CreateBigLabel(book.getDescription()));
		bottomBox.getChildren().addAll(template.CreateBigLabel(book.getAuthor()), template.CreateBigLabel(book.getGenre()), intTemplate.CreateNumBigLabel(book.getPageCount()));
		
		//Setting alignment & padding
		titleBox.setAlignment(Pos.BOTTOM_CENTER);
		titleBox.setPadding(new Insets(15, 15, 50, 50));
		descriptionBox.setAlignment(Pos.CENTER);
		descriptionBox.setPadding(new Insets(15, 15, 50, 50));
		bottomBox.setAlignment(Pos.CENTER);
		bottomBox.setPadding(new Insets(15, 15, 50, 50));
		
		//Set spaces on the border pane
		bPane.setTop(titleBox); 
		bPane.setCenter(descriptionBox);
		bPane.setBottom(bottomBox);
		
		return bPane;
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

		//Quit the application
		Button btQuit = template.CreateButton("Quit");
		btQuit.setOnAction(eventManager.handleQuit);
					
		//add buttons
		hBox.getChildren().addAll(btHome, btView, btQuit);
					
		//set alignment
		hBox.setAlignment(Pos.CENTER);
					
		return hBox;
	}
}
