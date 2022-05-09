package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class EventManager {
	
	//references for other classes
	public HandleView handleView = new HandleView();
	public HandleAdd handleAdd = new HandleAdd();
	public HandleHelp handleHelp = new HandleHelp();
	public HandleHome handleHome = new HandleHome();
	public HandleQuit handleQuit = new HandleQuit();
	public HandleClean handleClean = new HandleClean();
	public HandleQueue handleQueue = new HandleQueue();
	public HandleSave handleSave = new HandleSave();
	public HandleDescription handleDescription = new HandleDescription();
	
	//Handle the view library buttons
	class HandleView implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			Alexandria.stage.setScene(ViewLibrary.buildLibraryScene());
		}
	}
	
	//Handle the add to library buttons
	class HandleAdd implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			Alexandria.stage.setScene(AddEntry.buildBookScene());
		}
	}
	
	//Handle the clean duplicates button
	class HandleClean implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			ViewLibrary.cleanDuplicates();
			Alexandria.stage.setScene(ViewLibrary.buildLibraryScene());
		}
	}

	//Handle the help menu buttons
	class HandleHelp implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			Alexandria.stage.setScene(Help.buildHelpScene());
		}
	}
	
	//Handle the main menu buttons
	class HandleHome implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			Alexandria.stage.setScene(Alexandria.buildStartScene());
		}
	}
	
	//Handle the quit buttons
	class HandleQuit implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			Platform.exit();
		}
	}
	
	//Handle the add to queue button
	class HandleQueue implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			AddEntry.saveBookToQueue(AddEntry.bPane);
		}
	}
	
	//Handle the save from queue button
	class HandleSave implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			AddEntry.saveBooksInQueue(AddEntry.bPane);
		}
	}
		
	//handle clicking on a title to view description
	class HandleDescription implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			//Get the text from the title label clicked
			Label lbl = ((Label) e.getSource());
			String lblText = lbl.getText();
			
			//find the book that the title is the key to.
			Book book = ViewLibrary.descriptionMap.get(lblText);
			Alexandria.stage.setScene(BookDisplay.buildBookDisplayScene(book));
			
		}
	}
}
