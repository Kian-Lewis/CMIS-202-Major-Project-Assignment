package application;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

//main class 
public class Alexandria extends Application{
	
	private static Template<String> template = new Template<String>();//static reference to Template.java
	public static Stage stage = new Stage();//stage for event handling
	
	//main class
	@Override
	public void start(Stage primaryStage) {
		BorderPane bPane = new BorderPane(); //Create main border pane
		GridPane gPane = new GridPane(); //Create gridPane for center of bPane
		HBox innerBox = new HBox();//Create the hbox for the title
		
		//set properties of the grid pane
		gPane.setVgap(10);
		gPane.setPadding(new Insets(10,10,10,10));
		gPane.setAlignment(Pos.CENTER);
		innerBox.setAlignment(Pos.CENTER);
		
		//Instantiate image variables
		Image image = null;
		ImageView logo = null;
		
		//Create the logo image, not licensed
		try {
			image = new Image(new FileInputStream("src\\Book-Icon.jpg"));		//set the file path for the logo image
			logo = new ImageView(image);										//set the image view to the image file
			logo.setFitHeight(400); 											//set the height of the image to 500px
			logo.setFitWidth(500); 												//set the width of the image to 500px
			logo.setPreserveRatio(true);										//make the image preserve its aspect ratio(square)
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//Add contents of grid pane
		innerBox.getChildren().add(template.createHugeLabel("Digital Library"));
		gPane.add(innerBox, 0, 1);
		gPane.add(logo, 0, 2);
		
		//place nodes
		bPane.setCenter(gPane);
		bPane.setBottom(eventBox());
		
		//create scene and set it to the stage
		Scene scene = new Scene(bPane);
		primaryStage.setResizable(false);
		primaryStage.setHeight(800);
		primaryStage.setWidth(800);
		primaryStage.setTitle("Library of Alexandria");
		primaryStage.setScene(scene);
		stage = primaryStage;
		primaryStage.show();
	}
	
	//create buttons for UI
	private static HBox eventBox() {
		//set up hbox
		HBox hBox = new HBox(15);
		hBox.setPadding(new Insets(15, 15, 15, 15));
		hBox.setStyle("-fx-background-color: gray");
		EventManager eventManager = new EventManager();
		
		//create buttons
		Button btView = template.createButton("Books");
		btView.setOnAction(eventManager.handleView);
		
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
		hBox.getChildren().addAll(btView, btAdd, btHelp, btQuit);
		
		//set alignment
		hBox.setAlignment(Pos.CENTER);
		
		return hBox;
	}
	
	//method for other classes to build the main menu
	public static Scene buildStartScene() {
		BorderPane bPane = new BorderPane();//Create border pane
		GridPane gPane = new GridPane(); //Create gridPane for center of bPane
		HBox innerBox = new HBox();//Create the hbox for the title
		
		//set properties of the grid pane
		gPane.setVgap(10);
		gPane.setPadding(new Insets(10,10,10,10));
		gPane.setAlignment(Pos.CENTER);
		innerBox.setAlignment(Pos.CENTER);
		
		//Instantiate image variables
		Image image = null;
		ImageView logo = null;
		
		//Create the logo image, not licensed
		try {
			image = new Image(new FileInputStream("src\\Book-Icon.jpg"));		//set the file path for the logo image
			logo = new ImageView(image);										//set the image view to the image file
			logo.setFitHeight(400); 											//set the height of the image to 500px
			logo.setFitWidth(500); 												//set the width of the image to 500px
			logo.setPreserveRatio(true);										//make the image preserve its aspect ratio(square)
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//Add contents of grid pane
		innerBox.getChildren().add(template.createHugeLabel("Digital Library"));
		gPane.add(innerBox, 0, 1);
		gPane.add(logo, 0, 2);
		
		//place nodes
		bPane.setCenter(gPane);
		bPane.setBottom(eventBox());
		
		Scene startScene = new Scene(bPane);
		
		return startScene;
	}
	
	//launch class
	public static void main(String[] args) {
		Application.launch(args);
	}
}
