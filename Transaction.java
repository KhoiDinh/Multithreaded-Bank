
public class Transaction 
{
	private int accountFrom;
	private int accountTo;
	private int amount;
	
	public Transaction()
	{
		accountFrom = 0;
		accountTo = 0;
		amount=0;
	}
	
	public Transaction(int from,int to, int quantity)
	{
		accountFrom = from;
		accountTo = to;
		amount=quantity;
	}
	
	
	public void storeInformation(String line)
	{

		if(line.contains("[a-zA-Z]+") )
		//if(line.contains("/s*[0-9]+/s+[0-9]+/s+[0-9]+/s*") == false)
		{
			System.out.println("Invalid format at: " + line);
		}
		
		else
		{
			String[] parts = line.split(" ");
			try
			{
				accountFrom = Integer.parseInt(parts[0]);
				accountTo = Integer.parseInt(parts[1]);
				amount = Integer.parseInt(parts[2]);
				
			}
			catch(NumberFormatException f)
			{
				System.out.println("invalid string: " + line);
			}
		}
	}
	
	public int getAccountFrom()
	{
		return accountFrom;
	}
	
	public int getAccountTo()
	{
		return accountTo;
	}
	
	public int getAmount()
	{
		return amount;
	}

	public String toString()
	{
		return "Account From: " + getAccountFrom() + " " + "Account to: " + getAccountTo() +" " + "Amount: " + getAmount();
	}
}
