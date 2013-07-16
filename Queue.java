import java.util.ArrayList;
import java.util.Random;
public class Queue 
{
	private ArrayList<Process> readyQueue;
	private ArrayList<Process> blockedQueue;
	
	Queue()
	{		
		readyQueue = new ArrayList<Process>(); 
		blockedQueue = new ArrayList<Process>(); 
	}

	public ArrayList<Process> getReadyQueue() 
	{
		return readyQueue;
	}

	public void setReadyQueue(ArrayList<Process> readyQueue) 
	{
		this.readyQueue = readyQueue;
	}

	public ArrayList<Process> getBlockedQueue() 
	{
		return blockedQueue;
	}

	public void setBlockedQueue(ArrayList<Process> blockedQueue) 
	{
		this.blockedQueue = blockedQueue;
	}
	
	int getReadyQueueSize()
	{
		return readyQueue.size();
	}
	
	int getBlockedQueueSize()
	{
		return blockedQueue.size();
	}
	
	void addToReadyQueue(Process p)
	{
		readyQueue.add(p);
	}
	
	boolean deleteFromReadyQueue(int pid)
	{
		boolean check  = false;
		for(int i = 0;i<readyQueue.size(); ++i)
		{
			if(pid == readyQueue.get(i).getPid())
			{
				readyQueue.remove(i);
				check = true;
				
			}
		}
		return check;
	}
	
	synchronized Process getRandomRQ()
	{
		int size = getReadyQueueSize();
		if(size > 0)
		{
			Random r = new Random();
			int randomNumber = r.nextInt(size);
			return readyQueue.get(randomNumber);
		}
		return null;
	}
	
	void addToBlockedQueue(Process p)
	{
		blockedQueue.add(p);
	}
	
	boolean deleteFromBlockedQueue(int pid)
	{
		boolean check  = false;
		for(int i = 0;i<blockedQueue.size(); ++i)
		{
			if(pid == blockedQueue.get(i).getPid())
			{
				blockedQueue.remove(i);
				check = true;
				
			}
		}
		return check;
	}
	
	void printReadyQueue()
	{
		int i=0;
		System.out.print("Ready Queue :");
		if(readyQueue.size() > 0)
		{
			for( i = 0;i <readyQueue.size() - 1; ++i)
				System.out.print(new Integer(readyQueue.get(i).getPid()).toString() + "-");
			System.out.println(new Integer(readyQueue.get(i).getPid()).toString());
		}
		else
		{
			System.out.println();
		}
		
	}
	
	
}