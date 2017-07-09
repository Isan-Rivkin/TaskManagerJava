package view;

public class AlertCallback implements CallBack {

	private String answer;
	public AlertCallback() {
		// TODO Auto-generated constructor stub
	
	answer="";
	}
	@Override
	public void execute(String... strings) 
	{
		answer=strings[0];
	}
	public String getAnswer()
	{
		return answer;
	}

}
