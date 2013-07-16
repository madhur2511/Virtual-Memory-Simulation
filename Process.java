import java.util.ArrayList;

public class Process 
{
	private PageTable[] pageTable;
	private int pid;	
	
	Process()
	{
		pageTable = new PageTable[64];
		for(int i = 0;i<64;++i)
			pageTable[i] = new PageTable();
		this.pid = 0;		
	}
	

	Process(int pid)
	{
		pageTable = new PageTable[64];
		for(int i = 0;i<64;++i)
			pageTable[i] = new PageTable();
		this.pid = pid;
	}
	
	public PageTable[] getPageTable()
	{
		return pageTable;
	}

	public void setPageTable(PageTable[] pageTable)
	{
		this.pageTable = pageTable;
	}

	public int getPid() 
	{
		return pid;
	}

	public void setPid(int pid) 
	{
		this.pid = pid;
	}
	
}