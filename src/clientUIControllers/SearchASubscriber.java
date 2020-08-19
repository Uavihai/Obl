package clientUIControllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DB_Communicator.Communicate;
import Entities.Book;
import Entities.MembershipStatus;
import Entities.Subscriber;
import ServerClientRequests.SubscriberManageRequest;
import ServerClientRequests.SubscriberManageRequest.ManageType;
import ServerClientRequests.SubscriberManageRequest.SearchBy;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class SearchASubscriber implements Initializable {

	@FXML
	private TableView<Subscriber> subscribersList;
	@FXML
	private TableColumn<Subscriber, Integer> IDColumn;
	@FXML
	private TableColumn<Subscriber, String> firstNameColumn;
	@FXML
	private TableColumn<Subscriber, String> lastNameColumn;
	@FXML
	private TableColumn<Subscriber, MembershipStatus> statusColumn;
	
	@FXML
	private RadioButton byFirstName;

	@FXML
	private RadioButton byLastName;

	@FXML
	private RadioButton byID;

	@FXML
	private TextField searchRow;

	private ArrayList<RadioButton> properties;

	private RadioButton selectedProperty;

	static Subscriber selectedSubscriber;

	// parent frame
	public static LendABook sourceFrameLend=null;
	
	public static ReturnABook sourceFrameReturn=null;

	private static SearchASubscriber instance;

	@FXML
	public void chooseASubscriberBtn(ActionEvent event) throws Exception {
		if (this.subscribersList.getSelectionModel().getSelectedItem() == null)
			// sourceFrame.subscriberRow.setText("");
			return;
		else {
			selectedSubscriber = subscribersList.getSelectionModel().getSelectedItem();
			if(sourceFrameLend!=null) {
		sourceFrameLend.setSelectedSubscriber(this.selectedSubscriber);
		sourceFrameLend.getSubscriberRow().setText(selectedSubscriber.getStudentID() + " | " + selectedSubscriber.getFirstName()
				+ " " + selectedSubscriber.getLastName() + " | " + selectedSubscriber.getStatus());
		
		if(selectedSubscriber.getStatus()==MembershipStatus.Frozen || selectedSubscriber.getStatus()==MembershipStatus.Locked)
			sourceFrameLend.getLendBtn().setDisable(true);
		else
			sourceFrameLend.getLendBtn().setDisable(false);
		sourceFrameLend=null;
			}
			else if(sourceFrameReturn!=null) {
				sourceFrameReturn.setSelectedSubscriber(this.selectedSubscriber);
				sourceFrameReturn.getSubscriberRow().setText(selectedSubscriber.getStudentID() + " | " + selectedSubscriber.getFirstName()
						+ " " + selectedSubscriber.getLastName() + " | " + selectedSubscriber.getStatus());
				sourceFrameReturn=null;
			}
		}
		this.cancel(event);
	}

	@FXML
	public void searchBy(ActionEvent event) {
		this.selectedProperty.setSelected(false);
		(this.selectedProperty = ((RadioButton) event.getSource())).setSelected(true);
		this.searchRow.requestFocus();
	}

	private void search() {
		String propertyStr = searchRow.getText();
		if (propertyStr.length() == 0)
			return;

		SubscriberManageRequest request = new SubscriberManageRequest();
		SubscriberManageRequest.SearchBy sb = null;
		if (this.selectedProperty == this.byFirstName) {
			if(!Utils.checkStudentName(propertyStr))
			{
				Utils.showAlertDialog(AlertType.ERROR, "Error","Error In Input Data", "First name can only contain letters!");
				return;
			}
			sb = SearchBy.FIRST_NAME;
			request.setFirstName(propertyStr);
		} else if (this.selectedProperty == this.byLastName) {
			if(!Utils.checkStudentName(propertyStr))
			{
				Utils.showAlertDialog(AlertType.ERROR, "Error","Error In Input Data", "Last name can only contain letters!");
				return;
			}
			sb = SearchBy.LAST_NAME;
			request.setLastName(propertyStr);
		} else if (this.selectedProperty == this.byID) {
			if(!Utils.checkStudentID(propertyStr))
			{
				Utils.showAlertDialog(AlertType.ERROR, "Error","Error In Input Data", "ID can only contain numbers!");
				return;
			}
			sb = SearchBy.ID;
			request.setID(Integer.parseInt(propertyStr));
		}
		request.setSearchBy(sb);
		request.setRequestType(ManageType.SEARCH);
		Communicate.getInstance().sendRequestToServer(request);
		/*
		 * ArrayList<String> subscribersNames = new ArrayList<String>(); for (Subscriber
		 * i : matchSubscribers) subscribersNames.add(i.toString());
		 * this.subscribersList.setItems(FXCollections.observableArrayList(
		 * subscribersNames));
		 */
	}

	@FXML
	public void searchBtn(ActionEvent event) {
		search();
	}

	@FXML
	public void searchRowKeyResponse(KeyEvent event) {
		if (event.getCode().toString().equals("ENTER"))
			search();
	}

	@FXML
	public void getSubscriberDetails(MouseEvent event) throws Exception {
		if (this.subscribersList.getSelectionModel().getSelectedItem() == null)
			return;
		selectedSubscriber = this.subscribersList.getSelectionModel().getSelectedItem();
		if (event.getClickCount() == 2) {
			SubscriberDetails.selectedSubscriber = selectedSubscriber;
			SubscriberDetails controller = (SubscriberDetails) Utils.OpenFrame("/gui/SubscriberDetails.fxml",
					"Subscriber details", this, event, false);
			controller.getEditBtn().setVisible(false);
			controller.getLogout().setVisible(false);
			SubscriberDetails.setInstance(controller);

		}

	}

	public void updateUiPostSubSearch(SubscriberManageRequest answer) {
		Platform.runLater(() -> {
			// Update UI here.
			/*this.subscribersList.setItems(FXCollections.observableArrayList(answer.getSubscribers()));
			this.subscribersList.setCellFactory(param -> new ListCell<Subscriber>() {
				@Override
				protected void updateItem(Subscriber item, boolean empty) {
					super.updateItem(item, empty);

					if (empty || item == null || item.getFirstName() == null) {
						setText(null);
					} else {
						setText(item.getStudentID() + " | " + item.getFirstName() + " " + item.getLastName() + " | "
								+ item.getStatus());
					}
				}
			});*/
			ObservableList<Subscriber> list =FXCollections.observableArrayList(answer.getSubscribers());
			subscribersList.setItems(list);
		});
	}

	/**
	 * @return the instance
	 */
	public static SearchASubscriber getInstance() {
		return instance;
	}

	/**
	 * @param instance the instance to set
	 */
	public static void setInstance(SearchASubscriber instance) {
		SearchASubscriber.instance = instance;
	}

	@FXML
	public void cancel(ActionEvent event) throws Exception {
		Utils.closeCurrentFrame(event);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.properties = new ArrayList<RadioButton>();
		this.properties.add(this.selectedProperty = this.byID);
		this.properties.add(this.byLastName);
		this.properties.add(this.byFirstName);
		IDColumn.setCellValueFactory(new PropertyValueFactory("studentID"));
		firstNameColumn.setCellValueFactory(new PropertyValueFactory("firstName"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory("lastName"));
		statusColumn.setCellValueFactory(new PropertyValueFactory("status"));
	}
}
