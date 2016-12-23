import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Bank 
{
	private  int NUM_OF_THREADS;
	private final Transaction FINAL_TRANSACTION = new Transaction(-1,0,0);

	private  BlockingQueue<Transaction> block = new LinkedBlockingQueue<Transaction>();
	private  ArrayList<Account> accounts = new ArrayList<Account>();
	private  ArrayList<Worker> workers = new ArrayList<Worker>();
	private  Account[] accounts2;

	public static void main(String []args)
	{
		try
		{
			if(Integer.parseInt(args[1]) >0)
			{
				Bank bank = new Bank(args[0], Integer.parseInt(args[1]));
				bank.readFile(args[0]);
				bank.close();
				bank.print();
			}
			else
			{
				System.out.println("Rerun program with correct file name and/or postive integer for thread number");
			}
			
		}
		catch(Exception e)
		{
			System.out.println("Invalid file name or thread number");
		}
		
	}

	public Bank(String filename, int workersNumber) 
	{

		NUM_OF_THREADS = workersNumber;
		createArray(filename);
		int hold = accounts.size();
		accounts2 = new Account[hold];
		for (int i = 0; i < workersNumber; i++)
		{
			workers.add(new Worker());
		}

		for (Worker w : workers)
		{
			w.start();
		}

	}

	public void createArray(String filename)
	{
		try 
		{
			Scanner scan = new Scanner(new File(filename));
			int max=0;
			while(scan.hasNextLine())
			{

				int compare=0;
				String next = scan.nextLine();
				if(next.length()>0)
				{
					String[] split = next.split(" ");

					try
					{
						if(Integer.parseInt(split[0]) > Integer.parseInt(split[1]))
						{
							compare = Integer.parseInt(split[0]);
						}
						else
						{
							compare = Integer.parseInt(split[1]);
						}

						if(max < compare)
						{
							max=compare;
						}
					}
					catch(NumberFormatException e)
					{
						continue;
					}

				}
				else
				{
					continue;
				}


			}

			for(int i=0; i <= max; i ++)
			{
				Account temp = new Account();
				temp.setAccountNumber(i);
				accounts.add(temp);
			}
		} 
		catch (FileNotFoundException e)
		{
			System.out.println("File not found: " + filename);
		}

	}
	private void readFile(String filename)
	{
		try {

			Scanner scan = new Scanner(new File(filename));
			while(scan.hasNextLine())
			{
				String line = scan.nextLine();

				Transaction temp = new Transaction();
				temp.storeInformation(line);
				if(temp.getAccountFrom()==0 && temp.getAccountTo()==0 && temp.getAmount()==0)
				{

					continue;
				}

				block.put(temp);
				block.put(new Transaction(temp.getAccountTo(), temp.getAccountFrom(),-temp.getAmount()));

			}

			for(int i = 0; i < NUM_OF_THREADS; i++)
			{
				block.put(FINAL_TRANSACTION);
			}

		} 
		catch (IOException | InterruptedException e)
		{
			System.out.println(e.getMessage());
		}
	}


	private void close() 
	{
		try 
		{
			for (Worker w : workers)
			{
				w.join();
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println("Bank is closed");
	}

	public class Worker extends Thread
	{
		@Override
		public void run()
		{
			while (true) 
			{
				try 
				{

					Transaction nextTransaction = block.take();

					if(nextTransaction.equals(FINAL_TRANSACTION))
						break;

					Account acc = accounts.get(nextTransaction.getAccountTo());
					Account accFrom = accounts.get(nextTransaction.getAccountFrom());

					acc.makeTransaction(nextTransaction);

					accounts2[acc.getAccountNumber()] = acc;

				} 
				catch (InterruptedException e)
				{
					e.printStackTrace();

				}
			}
		}

	}

	public void print()
	{
		int count=0;
		for(int i=0; i < accounts2.length; i++)
		{
			if(accounts2[i]!=null)
			{
				System.out.println(accounts2[i].toString());
			}

			//count = count+accounts2[i].getTransactionNumber();
		}
		//System.out.println(count);
	}



}