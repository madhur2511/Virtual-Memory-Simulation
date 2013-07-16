public class PhysicalMemory
{	
	private FrameTableEntry[] frameTable;
	
	PhysicalMemory()
	{		
		this.frameTable = new FrameTableEntry[Main.size];
		for(int i = 0; i < Main.size; ++i)
		{
			this.frameTable[i] = new FrameTableEntry(); 
		}
	}
	
	FrameTableEntry getFrameTableEntry(int index)
	{
		return frameTable[index];
	}
	
	void setFrameTableEntry(int pid, String pageNo, int index)
	{
		frameTable[index].pid = pid;
		frameTable[index].pageNo = pageNo;
	}
	int getpidofFrameTableEntry(int index)
	{
		return frameTable[index].pid;
	}
	String getpagenoofFrameTableEntry(int index)
	{
		return frameTable[index].pageNo;
	}
	int getFreeFrame()
	{
		int frameNo = -1;
		for(int i = 0; i < Main.size  ; ++i)
		{
			if(frameTable[i].pid == -1)
			{
				frameNo = i;
				break;
			}
		}		
		return frameNo;
	}
	
	public FrameTableEntry[] getFrameTable()
	{
		return frameTable;
	}

	public void setFrameTable(FrameTableEntry[] frameTable) 
	{
		this.frameTable = frameTable;
	}

	//get the first different process frame
	int getNonFreeFrame(int pid)
	{
		int frameNo = -1;
		for(int i = 0;i< Main.size; ++i)
		{
			if(frameTable[i].pid != -1 && frameTable[i].pid != pid)
			{
				frameNo = i;
				break;
			}
			else
			{
				frameNo=0;
			}
		}
		return frameNo;
	}
	
	
}

class FrameTableEntry
{
	int pid;
	String pageNo;
	FrameTableEntry()
	{
		pid = -1;
		pageNo = "000000";		// page number size
	}
}