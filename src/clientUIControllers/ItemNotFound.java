package clientUIControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ItemNotFound {
	
	@FXML
    void cancel(ActionEvent event) {
		Utils.closeCurrentFrame(event);
    }
}
