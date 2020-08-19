package clientUIControllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.mysql.jdbc.StringUtils;

import DB_Communicator.Communicate;
import Entities.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class BookDetails implements Initializable {
	private boolean editing = false;
	@FXML
    private Button editBtn;
	
	@FXML
    private TextField bookName;
		
    @FXML
    private TextField bookAuthor;

    @FXML
    private TextField bookSubject;

    @FXML
    private TextArea bookDescription;

    @FXML
    private Button contentTableBtn;
    
    @FXML
    private CheckBox IsPopulatChckbox;

    public static Book selectedBook;
    
    private static BookDetails instance;
    
	@FXML 
	private TextField shelfPosition;
    
    @FXML
    public void cancel(ActionEvent event) {
    	Utils.closeCurrentFrame(event);
    	//Utils.OpenFrame(path, title, source, event, hideCurrentFrame);
    }

    @FXML
    public void editBookDetailsBtn(ActionEvent event) {
    	if (!this.editing) {
    		this.editBtn.setText("Update");
    		this.bookName.setEditable(true);
    		this.bookAuthor.setEditable(true);
    		this.bookSubject.setEditable(true);
    		this.bookDescription.setEditable(true);
    		
    		this.editing = true;
    	}
    	else {
    		selectedBook.setTitle(this.bookName.getText());
    		selectedBook.setAuthors(new ArrayList<>(Arrays.asList(this.bookAuthor.getText().split(","))));
    		selectedBook.setSubjects(new ArrayList<>(Arrays.asList(this.bookSubject.getText().split(","))));
    		selectedBook.setSummery(this.bookDescription.getText());
    		
    		Communicate.getInstance().updateBookDetails(selectedBook.getTitle(), selectedBook);
    		this.cancel(event);
    	}
    }

    @FXML
    public void ViewContentTable(ActionEvent event) {

    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.bookName.setText(selectedBook.getTitle());
		this.bookAuthor.setText(String.join(",",selectedBook.getAuthors()));
		this.bookSubject.setText(String.join(",",selectedBook.getSubjects()));
		this.shelfPosition.setText(selectedBook.getShelfPosition());
		this.bookDescription.setText(selectedBook.getSummery());
		this.IsPopulatChckbox.setSelected(selectedBook.isPopular());
		this.editBtn.setDisable(true);
		this.IsPopulatChckbox.setDisable(true);
		
	}
//----------GETTERS AND SETTERS-----------------
	/**
	 * @return the editing
	 */
	public boolean isEditing() {
		return editing;
	}

	/**
	 * @param editing the editing to set
	 */
	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	/**
	 * @return the editBtn
	 */
	public Button getEditBtn() {
		return editBtn;
	}

	/**
	 * @param editBtn the editBtn to set
	 */
	public void setEditBtn(Button editBtn) {
		this.editBtn = editBtn;
	}

	/**
	 * @return the bookName
	 */
	public TextField getBookName() {
		return bookName;
	}

	/**
	 * @param bookName the bookName to set
	 */
	public void setBookName(TextField bookName) {
		this.bookName = bookName;
	}

	/**
	 * @return the bookAuthor
	 */
	public TextField getBookAuthor() {
		return bookAuthor;
	}

	/**
	 * @param bookAuthor the bookAuthor to set
	 */
	public void setBookAuthor(TextField bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	/**
	 * @return the bookSubject
	 */
	public TextField getBookSubject() {
		return bookSubject;
	}

	/**
	 * @param bookSubject the bookSubject to set
	 */
	public void setBookSubject(TextField bookSubject) {
		this.bookSubject = bookSubject;
	}

	/**
	 * @return the bookDescription
	 */
	public TextArea getBookDescription() {
		return bookDescription;
	}

	/**
	 * @param bookDescription the bookDescription to set
	 */
	public void setBookDescription(TextArea bookDescription) {
		this.bookDescription = bookDescription;
	}

	/**
	 * @return the contentTableBtn
	 */
	public Button getContentTableBtn() {
		return contentTableBtn;
	}

	/**
	 * @param contentTableBtn the contentTableBtn to set
	 */
	public void setContentTableBtn(Button contentTableBtn) {
		this.contentTableBtn = contentTableBtn;
	}

	/**
	 * @return the instance
	 */
	public static BookDetails getInstance() {
		return instance;
	}

	/**
	 * @param instance the instance to set
	 */
	public static void setInstance(BookDetails instance) {
		BookDetails.instance = instance;
	}

	/**
	 * @return the shelfPosition
	 */
	public TextField getShelfPosition() {
		return shelfPosition;
	}

	/**
	 * @param shelfPosition the shelfPosition to set
	 */
	public void setShelfPosition(TextField shelfPosition) {
		this.shelfPosition = shelfPosition;
	}
	
}
