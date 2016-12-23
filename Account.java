public class Account
{
	private int accountNumber;
	private int currentBalance;
	private int transactionNumber;
	private final int START = 1000;

	public Account()
	{
		currentBalance =START;
		transactionNumber = 0;
	}

	public Account(int number, int start) 
	{
		accountNumber=number;
		currentBalance =start;
	}

	public void setAccountNumber(int value)
	{
		accountNumber=value;
	}

	public int getAccountNumber()
	{
		return accountNumber;
	}

	public int getTransactionNumber()
	{
		return transactionNumber;
	}

	public void setBalance(int num)
	{
		currentBalance = num;
	}
	public int getBalance()
	{
		return currentBalance;
	}

	public synchronized void makeTransaction(Transaction t)
	{
			transactionNumber++;
			currentBalance += t.getAmount();
	}

	public String toString()
	{
		return "Account Number: " + getAccountNumber() + " " + "Balance: " + getBalance()
		+ " " + "Transaction Amount: " + getTransactionNumber();
	}
}
