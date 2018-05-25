package customDatapack;

public class customer {

//
	private int cust_id;
	private String f_n;
	private String l_n;
	private String email;

	
	public customer(int customer_id,String first_name,String last_name,String cust_email) {
		
		this.cust_id=customer_id;
		this.f_n=first_name;
		this.l_n=last_name;
		this.email=cust_email;
		
		
		
		// TODO Auto-generated constructor stub
	}
	
	public int getcust_id(){
		return cust_id;
	}
	
	public String getf_name()
	{
		return f_n;
	}
	public String getl_name()
	{
		return l_n;
	}
	public String getemail()
	{
		return email;
	}
	

}