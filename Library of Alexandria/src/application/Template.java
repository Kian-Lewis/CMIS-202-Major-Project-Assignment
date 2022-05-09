package application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.*;

//class for saving space, mostly in style options.
//Makes use of generics so that certain methods can take in a number of variable types in order to create labels and buttons.
public class Template <T> {
	
	EventManager eventManager = new EventManager();
	
	//create a button
	public Button CreateButton(T name) {
		Button bt = new Button(name.toString());
		bt.setStyle("-fx-border-color: lightgray; -fx-background-color: darkgray; "
				+ "-fx-text-fill: white");
		bt.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
		
		return bt;
	}
	
	//create a button from any integer, double, etc...
	public Button CreateNumButton(T name) {
		Button bt = new Button(String.valueOf(name));
		bt.setStyle("-fx-border-color: lightgray; -fx-background-color: darkgray; "
				+ "-fx-text-fill: white");
		bt.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
		
		return bt;
	}
	
	//Create a huge label
	public Label CreateHugeLabel(T name) {
		Label lbl = new Label(name.toString());
		lbl.setStyle("-fx-text-fill: black");
		lbl.setFont(Font.font("Times New Roman", 36));
		lbl.setWrapText(true);
		
		return lbl;
	}
	
	//create a big label with color specified
	public Label CreateMainLabel(T name, String color) {
		Label lbl = new Label(name.toString());
		lbl.setStyle("-fx-text-fill: " + color);
		lbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));
		
		return lbl;
	}
	
	//create a big label with no color specified
	public Label CreateBigLabel(T name) {
		Label lbl = new Label(name.toString());
		lbl.setStyle("-fx-text-fill: black");
		lbl.setFont(Font.font("Times New Roman", 25));
		lbl.setWrapText(true);
			
		return lbl;
	}
	
	//create big label from integer, double, etc...
	public Label CreateNumMainLabel(T name, String color) {
		Label lbl = new Label(String.valueOf(name));
		lbl.setStyle("-fx-text-fill: " + color);
		lbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));
		
		return lbl;
	}
	//create big label from integer, double, etc...
		public Label CreateNumBigLabel(T name) {
		Label lbl = new Label(String.valueOf(name));
		lbl.setStyle("-fx-text-fill: black");
		lbl.setFont(Font.font("Times New Roman", 26));
		lbl.setWrapText(true);
			
		return lbl;
		}
	
	//create a smaller label for titles to be clicked on
	public Label CreateSmallLabel(T name) {
		Label lbl = new Label(name.toString());
		lbl.setStyle("-fx-text-fill: black");
		lbl.setFont(Font.font("Times New Roman", 16));
		
		return lbl;
	}
	
	//create a smaller label
	public Label CreateSmallTitleLabel(T name) {
		Label lbl = new Label(name.toString());
		lbl.setStyle("-fx-text-fill: blue");
		lbl.setFont(Font.font("Times New Roman", 16));
		lbl.setUnderline(true);
		
		lbl.setOnMouseClicked(eventManager.handleDescription);
		
		return lbl;
		}
	
	//create a small label from an integer, double, etc...
	public Label CreateNumSmallLabel(T count) {
		String lblName = String.valueOf(count);
		
		Label lbl = new Label(lblName);
		lbl.setStyle("-fx-text-fill: black");
		lbl.setFont(Font.font("Times New Roman", 14));
		
		return lbl;
	}
	
	//creates an hbox
	public HBox CreateSimpleHBox(T contents) {
		HBox hBox = new HBox(15);
		hBox.setPadding(new Insets(15, 15, 15, 15));
		hBox.setStyle("-fx-background-color: gray");
		hBox.setAlignment(Pos.CENTER);
		
		//create
		Label header = CreateMainLabel(contents, "white");
		
		//add label
		hBox.getChildren().add(header);
		
		return hBox;
	}
	
	//creates an error message
	@SuppressWarnings("unchecked")
	public Stage CreateErrorMessage(String error) {
		//Set stage and scene containing error message
		Stage stage = new Stage();
		Scene scene = new Scene(CreateSmallLabel((T) ("There was an error: " + error)));
		
		//Set qualities of stage
		stage.setResizable(false);
		stage.setHeight(400);
		stage.setWidth(400);
		stage.setTitle("Error Message");
		stage.setScene(scene);
		
		return stage;
	}
}
