package logic;

import Entities.Book;
import Entities.Subscriber;
import clientUIControllers.Utils;

public class Lend {
	private Subscriber subscriber;
	private Book book;
	private String copySerial;
	
	private static final int TWO_WEEKS = 14;
	
	private Date lend_date;
	private Date return_date;
	
	public Lend(Subscriber subscriber, Book book) {
		this.subscriber = subscriber;
		this.book = book;
		this.lend_date = new Date();
		this.return_date = new Date(lend_date.add(TWO_WEEKS));
	}

	public Subscriber getSubscriber() {	return this.subscriber; }
	public Book getBook() {	return this.book; }
	public Date getLendDate() { return this.lend_date; }
	public Date getReturnDate() { return this.return_date; }
	public String getCopySerial() { return this.copySerial; }

	public void setSubscriber(Subscriber subscriber) { this.subscriber = subscriber; }
	public void setBook(Book book) { this.book = book; }
	public void setLendDate(Date lend_date) { this.lend_date = lend_date; }
	public void setReturnDate(Date return_date) { this.return_date = return_date; }
	public void setCopySerial(String copySerial) { this.copySerial = copySerial; }

	public String toString () {
		return Utils.join(new String[] {this.subscriber.toString(), this.book.getTitle()}, ("" + Utils.SEPARATOR));
	}
}
