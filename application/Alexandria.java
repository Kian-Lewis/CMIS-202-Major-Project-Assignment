package application;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class Alexandria extends Application{
	
	private static Template<String> template = new Template<String>();//static reference to Template.java
	public static Stage stage = new Stage();//stage for event handling
	
	//main class
	@Override
	public void start(Stage primaryStage) {
		BorderPane bPane = new BorderPane();//Create border pane
		
		//place nodes
		bPane.setCenter(template.CreateMainLabel("Digital Library", "black"));
		bPane.setBottom(eventBox());
		
		//create scene and set it to the stage
		Scene scene = new Scene(bPane);
		primaryStage.setResizable(false);
		primaryStage.setHeight(700);
		primaryStage.setWidth(700);
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
		Button btView = template.CreateButton("Books");
		btView.setOnAction(eventManager.handleView);
		
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
		hBox.getChildren().addAll(btView, btAdd, btHelp, btQuit);
		
		//set alignment
		hBox.setAlignment(Pos.CENTER);
		
		return hBox;
	}
	
	//method for other classes to build the main menu
	public static Scene buildStartScene() {
		BorderPane bPane = new BorderPane();//Create border pane
		
		//place nodes
		bPane.setCenter(template.CreateMainLabel("Digital Library", ""));
		bPane.setBottom(eventBox());	
		
		Scene startScene = new Scene(bPane);
		
		return startScene;
	}
	
	//launch class
	public static void main(String[] args) {
		Application.launch(args);
	}
}
