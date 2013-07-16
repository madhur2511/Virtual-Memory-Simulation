import java.util.ArrayList;

public class OS_MemoryMgmt_SignalHandler 
{
	public static String signalHandler(Process p, InputFileInfo ifi , Queue queue, PhysicalMemory pm, ArrayList<Process> processList)
	{		
		String pageNo = ifi.getPageNo();				
		p.getPid();		
		queue.deleteFromReadyQueue(p.getPid());
		queue.addToBlockedQueue(p);				
		
		int frame_no=pm.getFreeFrame();
		if(frame_no==-1)
		{
			frame_no=pm.getNonFreeFrame(p.getPid());		
			
			int pidinframe=pm.getpidofFrameTableEntry(frame_no);
			String pagenoinframe=pm.getpagenoofFrameTableEntry(frame_no);		
		
			Process premoved=search(processList , pidinframe);  
			//
			//removeEntry(queue, processList, premoved, pm);
			
			if(Integer.parseInt(premoved.getPageTable()[Conversions.binToDec(pagenoinframe)].getModified())==0)
			{
				premoved.getPageTable()[Conversions.binToDec(pagenoinframe)].setPresent(0);
			}
			else
			{				
				
					try 
					{		
						Thread.sleep(1000);
					} 
					catch (InterruptedException e) 
					{				
						e.printStackTrace();
					}
				
				System.out.println("Swapping out process pid:" + pidinframe +",pageno:"+ 
											Conversions.binToDec(pagenoinframe) +",frameno:"+frame_no);
				premoved.getPageTable()[Conversions.binToDec(pagenoinframe)].setPresent(0);
			}
				
			
			System.out.println("Loading page process pid:" +p.getPid() + ",pageno:"
							+ Conversions.binToDec(pageNo) + ",into frame:"+frame_no);
			
		//	/Simulate disk read by sleep(1);
		//	sleep for 1 second
			try 
			{		
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{				
				e.printStackTrace();
			}
		}
		else
		{
			try 
			{		
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{				
				e.printStackTrace();
			}
		}
			
		pm.setFrameTableEntry(p.getPid(), pageNo, frame_no);											
			
		p.getPageTable()[Conversions.binToDec(pageNo)].setEntry(1, ifi.getRW(),frame_no);							
				
		queue.deleteFromBlockedQueue(p.getPid());
		queue.addToReadyQueue(p);
				
		System.out.print("|");
		for(int i=0;i<pm.getFrameTable().length; ++i)
		{
			if(pm.getFrameTableEntry(i).pid!=-1)
			{
				System.out.print(pm.getFrameTableEntry(i).pid);
			}
			else
			{
				System.out.print("-");
			}
			System.out.print("|");
		}
		System.out.println();
				
		String realAddress = Conversions.decToBin(frame_no, Main.log2(Main.size));
			
		return realAddress;
	}
	
	private static void removeEntry(Queue queue,
			ArrayList<Process> processList, Process premoved, PhysicalMemory pm)
	{
		
		int sum = 0;
		for(int i = 0;i< pm.getFrameTable().length ;++i)
		{
			if(premoved.getPid() == pm.getFrameTable()[i].pid)
				sum = sum + 1;
		}
		if(sum == 1)
		{
			Process p = search(processList, premoved.getPid());
			processList.remove(p);			
		}
		
		
		int sum1 = 0;
		for(int i = 0;i< queue.getReadyQueue().size() ;++i)
		{
			if(premoved.getPid() == pm.getFrameTable()[i].pid)
				sum1 = sum1 + 1;
		}
		if(sum1 == 1)
		{
			Process p = search(queue.getReadyQueue() , premoved.getPid());
			queue.getReadyQueue().remove(p);			
		}
		
	}

	public static Process search(ArrayList<Process> processList , int pid)
	{
		Process p = null;
		for(int i=0;i<processList.size();++i)
		{
			if(processList.get(i).getPid() == pid)
				p = processList.get(i);
		}
		return p;
	}
	
	static void printProcessList(ArrayList<Process> x)
	{
		for(int i = 0;i <x.size(); ++i)
			System.out.print("Ready Queue :" + new Integer(x.get(i).getPid()).toString() + "-");
		System.out.println();
	}
	
	
	
}

