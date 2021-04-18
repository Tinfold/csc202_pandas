package pandas;

public class Credentials 
{
	private String u;
	private String p;
	
	public Credentials()
	{
	}
	
	public Credentials(String u, String p)
	{
		this.u = u;
		this.p = p;
	}

	public String toString()
	{
		return "Username: " + u + "\nPassword: " + p;
	}
	
	public String getU() {
		return u;
	}

	public void setU(String u) {
		this.u = u;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}
	
	
}
