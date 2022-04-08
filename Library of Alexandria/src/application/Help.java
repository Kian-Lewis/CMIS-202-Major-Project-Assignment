//Kian Lewis
package application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

//Description Class
public class Help {
	
	private static Template<String> template = new Template<String>();//static reference to Template.java
	
	public static String Description() {
		//Summary of program
		String summary = "Welcome to the Library of Alexandria!\n\n" + 
				"Unlike its real life counterpart, this is a small program designed " + 
				"to house a small library of books.\n" + 
				"It may have limited functionality now, but its features will slowly " + 
				"grow in the future from now.\n\n";
		
		//what does it do
		String functionality = "Currently, this program allows users to enter " + 
				"information about their phyisical books in order to auto sort \nthem by " + 
				"abc order via the author's last names. The best use case for the program " + 
				"is to record the small library \nof a user from their home computer.\n\n";
		
		//why use the program and who should use it
		String whyUseThis = "Honestly, at the current moment this program doesn't offer " + 
				"very much incentive for the user to adopt it. It \ndoes, however contain " +
				"the strong suit of having no in-app purchasing system for ebooks, which " +
				"is nearly \nunanimously a feature of every digital library app.\nEssentially, " + 
				"for the user who wishes for a concise and focused way to strictly record " + 
				"information about \ntheir physical library without any distractions, this " + 
				"program may prove to be a good fit.\n\n";
		
		//full return
		String fullSummary = summary + functionality + whyUseThis;
		
		return fullSummary;
	}
	
	public static Scene buildHelpScene() {
		BorderPane bPane = new BorderPane();//Create border pane
		
		//place nodes
		bPane.setCenter(template.CreateSmallLabel(Description()));
		bPane.setBottom(EventBox());	
		
		Scene helpScene = new Scene(bPane);
		
		return helpScene;
	}
	
	private static HBox EventBox() {
		//set up hbox
				HBox hBox = new HBox(15);
				hBox.setPadding(new Insets(15, 15, 15, 15));
				hBox.setStyle("-fx-background-color: gray");
				EventManager eventManager = new EventManager();
				
				//Back to main menu
				Button btHome = template.CreateButton("Home");
				btHome.setOnAction(eventManager.handleHome);

				//Quit the application
				Button btQuit = template.CreateButton("Quit");
				btQuit.setOnAction(eventManager.handleQuit);
				
				//add buttons
				hBox.getChildren().addAll(btHome, btQuit);
				
				//set alignment
				hBox.setAlignment(Pos.CENTER);
				
				return hBox;
	}
}

