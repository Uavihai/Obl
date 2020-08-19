package logic;

public class Book {
	private String name;
	private String author;
	private String subject;
	private String description;
	
	public Book(String name, String author, String subject, String description) {
		this.name = name;
		this.author = author;
		this.subject = subject;
		this.description = description;
	}
	
	public void setName (String name)	{ this.name = name; }
	public void setAuthor (String author)	{ this.author = author; }
	public void setSubject (String subject)	{ this.subject = subject; }
	public void setDescription (String description)	{ this.description = description; }
	
	public String getName ()	{ return this.name; }
	public String getAuthor ()	{ return this.author; }
	public String getSubject ()	{ return this.subject; }
	public String getDescription ()	{ return this.description; }
}
