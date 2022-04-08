package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class EventManager {
	
	//references for other classes
	public HandleView handleView = new HandleView();
	public HandleAdd handleAdd = new HandleAdd();
	public HandleHelp handleHelp = new HandleHelp();
	public HandleHome handleHome = new HandleHome();
	public HandleQuit handleQuit = new HandleQuit();
	
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
}