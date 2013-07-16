public class OS_Simulation extends Thread
{
	private Running running;
	private Queue queue;
	
	OS_Simulation(Queue queue, Running running)
	{
		super();		
		this.running = running;
		this.queue = queue;
		start();
				
	}

	@Override
	public void run() 
	{		
		while(true)
		{
			//sleep for 5 seconds simulating running time for the current process
			try 
			{		
				Thread.sleep(5000);
			} 
			catch (InterruptedException e) 
			{				
				e.printStackTrace();
				
			}
			
			synchronized(queue)
			{				
				Process p = queue.getRandomRQ();            //synchronize this with the signal handler queue operations
				if(p!=null)
				{
					running.setRunning(p.getPid());					
				}
				else
				{
					running.setRunning(-1);							
				}
				System.out.println("*******************************");
				System.out.println("Running Process =>" +  running.getRunning());								
				queue.printReadyQueue();
				System.out.println("*******************************");
			}										
		}
	}		
}