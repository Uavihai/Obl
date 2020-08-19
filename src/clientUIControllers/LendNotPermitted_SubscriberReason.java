package clientUIControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LendNotPermitted_SubscriberReason {
	
	@FXML
    void cancel(ActionEvent event) {
		Utils.closeCurrentFrame(event);
    }
}
