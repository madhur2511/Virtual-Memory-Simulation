import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

public class MemoryMgmtHardwareSimulation extends Thread 
{
	private String FileName;
	private Queue queue;
	private PhysicalMemory pm;
	private Scanner sc;
	private ArrayList<Process> processList;
	Hashtable<String, ArrayList<InputFileInfo>> inputTable;
	Running running;
	int runningProcess;

	MemoryMgmtHardwareSimulation(String FileName, Queue queue, PhysicalMemory pm, Running running)
	{
		super();
		this.FileName = FileName;
		this.queue = queue;
		this.pm = pm;
		this.processList = new ArrayList<Process>();
		this.inputTable = new Hashtable<String, ArrayList<InputFileInfo>>();
		this.running = running;
		loadInputFileInfo(FileName);
		try 
		{
			sc = new Scanner(new File(this.FileName));
			if(sc.hasNext())
			{
				running.setRunning(sc.nextLine().charAt(0)-48);
				runningProcess = running.getRunning();
			}
			
		} 
		catch (FileNotFoundException e) 
		{						
			System.out.println("Input File not found.....Exiting");
			System.exit(1);
		}
		start();
	}
	
	@Override
	public void run()
	{
		//creating processes which have a request
		
		while(true)
		{
			runningProcess = running.getRunning();
			InputFileInfo ifi = getNextMemAccess();
			if(ifi!=null)
			{
				System.out.println("Start------------------------------------------------------------");
				String realAddress = VirtualToPhysAddrTranslate(ifi);
				if(realAddress == null)
				{								
					Process p = searchAndAdd(ifi.getPid());							
					p.getPageTable()[Conversions.binToDec(ifi.getPageNo())].setModified(ifi.getRW());
					System.out.println("Getting the page " + Conversions.binToDec(ifi.getPageNo()) + " of " +
							" Process " + ifi.getPid() +" of access type " + ifi.getRW() + " to real memory");
					realAddress = OS_MemoryMgmt_SignalHandler.signalHandler(p, ifi, queue, pm, processList);
						
																			
				}
				else
				{
					System.out.println("Page already present in real memory");
				}
				System.out.println("Process "+ifi.getPid()+" accessing Page No "+
							Conversions.binToDec(ifi.getPageNo())+" in "+ ((ifi.getRW()==0)?"R":"W")+" mode in real address "+
								Conversions.binToDec(realAddress) + " with offset:" + Conversions.binToDec(ifi.getOffset()));				
				System.out.println("End------------------------------------------------------------");
			}						
		}
	}
	
	InputFileInfo getNextMemAccess()
	{
		InputFileInfo ifi = null;
		if(runningProcess != -1)
			{							
				ArrayList<InputFileInfo> inputList = 
						(ArrayList<InputFileInfo>)inputTable.get(new Integer(runningProcess).toString());
				if(inputList.size() > 0)
				{
					ifi = inputList.get(0);
					inputList.remove(0);
				}
				else
				{
					synchronized(queue)
					{
						queue.deleteFromReadyQueue(runningProcess);
					}					
				}
			}
		
		return ifi;
	}	
	
	String VirtualToPhysAddrTranslate(InputFileInfo ifi)
	{
		String realAddress = null;
		Process p = searchAndAdd(ifi.getPid());		
		int pageTableIndex = Conversions.binToDec(ifi.getPageNo());		
		if(p.getPageTable()[pageTableIndex].getPresent().equalsIgnoreCase("1"))
		{
			realAddress = p.getPageTable()[pageTableIndex].getFrame();      		//put offset later on
			p.getPageTable()[pageTableIndex].setModified(ifi.getRW());
			if(ifi.getRW() == 1)
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
		}
		return realAddress;
	}
	
	
	Process searchAndAdd(int pid)
	{
		for(int i = 0;i<processList.size();++i)
		{
			if(processList.get(i).getPid() == pid)
			{
				return processList.get(i);
			}
		}		
		Process p = new Process(pid);
		processList.add(p);
		return p;
	}
	
	void printProcessList()
	{
		for(int i = 0;i <processList.size(); ++i)
			System.out.print("ProcessList :" + new Integer(processList.get(i).getPid()).toString());
		System.out.println();
	}
	
	void loadInputFileInfo(String FileName)
	{
		Scanner sc = null;
		try 
		{
			sc = new Scanner(new File(FileName));
			while(sc.hasNext())
			{
				InputFileInfo ifi = new InputFileInfo(sc.nextLine());
				if(inputTable.containsKey(new Integer(ifi.getPid()).toString()) == false)
				{
					inputTable.put( new Integer(ifi.getPid()).toString(), new ArrayList<InputFileInfo>());
					ArrayList<InputFileInfo> inputList = inputTable.get(new Integer(ifi.getPid()).toString());
					inputList.add(ifi);
				}
				else
				{
					ArrayList<InputFileInfo> inputList = inputTable.get(new Integer(ifi.getPid()).toString());
					inputList.add(ifi);
				}
				
			}
			
			Enumeration e = inputTable.keys();
			while(e.hasMoreElements())
			{
				Process p = new Process(Integer.parseInt((String)e.nextElement()));
				queue.addToReadyQueue(p);
			}
			
		} 
		catch (FileNotFoundException e) 
		{			
			e.printStackTrace();
		}				
	}
	
	
}