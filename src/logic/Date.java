package logic;

@SuppressWarnings("serial")
public class Date extends java.util.Date{
	private static final long DAYS_IN_MILLISECONDS = 86400000;
	
	@SuppressWarnings("deprecation")
	public Date(Date other)	{
		super(other.getYear() - 1900, other.getMonth() - 1, other.getDate());
	}
	public Date() { super(); }
	
	@SuppressWarnings("deprecation")
	public int getDate () { return super.getDate();	}
	@SuppressWarnings("deprecation")
	public int getMonth () { return super.getMonth() + 1; }
	@SuppressWarnings("deprecation")
	public int getYear () { return super.getYear() + 1900; }
	
	public Date add (int days) {
		Date ret = new Date(this);
		ret.setTime(ret.getTime() + days * DAYS_IN_MILLISECONDS);
		return ret;
	}
}
