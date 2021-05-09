package pandas;

/**
 * @author jholl
 *	This class contains the necessary information for user login
 */
public class Credentials 
{
	/**
	 * contains the username of a user
	 */
	private String u;
	/**
	 * contains the password of a user
	 */
	private String p;
	
	/**
	 * Empty constructor
	 */
	public Credentials()
	{
	}
	
	/**
	 * Full constructor
	 * @param u username to be set
	 * @param p password to be set
	 */
	public Credentials(String u, String p)
	{
		this.u = u;
		this.p = p;
	}

	/* 
	 * Returns formatted string containing relevant fields
	 */
	public String toString()
	{
		return "Username: " + u + "\nPassword: " + p;
	}
	
	/**
	 * returns username
	 * @return username
	 */
	public String getU() {
		return u;
	}

	/**
	 * sets username
	 * @param u username to be set
	 */
	public void setU(String u) {
		this.u = u;
	}

	/**
	 * returns password
	 * @return password
	 */
	public String getP() {
		return p;
	}

	/**
	 * sets password
	 * @param p password to be set
	 */
	public void setP(String p) {
		this.p = p;
	}
	
	
}
