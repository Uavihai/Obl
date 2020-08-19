package clientUIControllers;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Utils {
	public static final char SEPARATOR = (char) 160; // It's not a regular space ((char)32)

	public static Object OpenFrame(String path, String title, Object source, Event event, boolean hideCurrentFrame)
			throws Exception {
		if (hideCurrentFrame)
			Utils.closeCurrentFrame(event); // hiding primary window
		Object controller;

		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(source.getClass().getResource(path).openStream());
		controller = loader.getController();
		Scene scene = new Scene(root);
		if (title != null)
			primaryStage.setTitle(title);
		primaryStage.setScene(scene);
		primaryStage.show();
		return controller;
	}

	public static void closeCurrentFrame(Event event) {
		Platform.runLater(() -> {
			// Update UI here.
			((Node) event.getSource()).getScene().getWindow().hide();
		});

	}

	/**
	 * Method checks if the id contains numbers only
	 * 
	 * @param id student's id to check
	 * @return true if id contains only numbers, false otherwise
	 */
	public static boolean checkStudentID(String id) {
		Matcher matcher = Pattern.compile("[^0-9]").matcher(id);
		if (matcher.find())
			return false;
		return true;

	}

	/**
	 * Method checks if the name contains letters only
	 * 
	 * @param id student's id to check
	 * @return true if id contains only numbers, false otherwise
	 */
	public static boolean checkStudentName(String name) {
		Matcher matcher = Pattern.compile("[^a-zA-z ]").matcher(name);
		if (matcher.find())
			return false;
		return true;
	}

	/**
	 * Displays an alert on the UI
	 * 
	 * @param alertType
	 * @param title
	 * @param header
	 * @param context
	 */
	public static void showAlertDialog(AlertType alertType, String title, String header, String context) {
		// Avoid throwing IllegalStateException by running from a non-JavaFX thread.
		Platform.runLater(() -> {
			// Update UI here.
			Alert alert = new Alert(alertType);
			alert.setTitle(title);
			alert.setHeaderText(header);
			alert.setContentText(context);
			alert.showAndWait();
		});
	}

	/**
	 * 
	 * - Implemented, because String.join doesn't work well for the current needs. -
	 * 
	 * @param strings
	 * @param regex
	 * @return
	 */
	public static final String join(Iterable<String> strings, String regex) {
		int strsLength = 0, iterableLength = 0;
		for (String str : strings) {
			strsLength += str.length();
			++iterableLength;
		}
		if (strsLength == 0)
			return "";

		StringBuilder ret = new StringBuilder(strsLength + regex.length() * (iterableLength - 1));
		int offset = 0, regexLength = regex.length();

		for (String str : strings) {
			ret.insert(offset, str);
			offset += str.length();
			if (offset == ret.capacity())
				break;
			ret.insert(offset, regex);
			offset += regexLength;
		}
		return ret.toString();
	}

	public static final String join(final String[] strings, String regex) {
		return join(new Iterable<String>() {
			public Iterator<String> iterator() {
				return new Iterator<String>() {
					private int index = 0;

					public void remove() {
					}

					public String next() {
						return strings[this.index++];
					}

					public boolean hasNext() {
						return this.index != strings.length;
					}
				};
			}
		}, regex);
	}
}
