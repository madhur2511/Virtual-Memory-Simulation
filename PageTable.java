public class PageTable 
{
	private String pageTableEntry;
	
	PageTable()
	{
		this.pageTableEntry = "00000000000000000000";
	}
	
	PageTable(String pageTableEntry)
	{
		this.pageTableEntry = pageTableEntry;
	}

	//Getter
	public String getpageTableEntry() 
	{
		return pageTableEntry;
	}
	
	//Setter
	public void setpageTableEntry(String pageTableEntry) 
	{
		this.pageTableEntry = pageTableEntry;
	}
	
	public String getFrame()
	{
		return pageTableEntry.substring(14-Main.log2(Main.size),14);                    //Main.size is the final static variable in Main class
	}
	
	void setEntry(int present, int RW, int frame)
	{			
			pageTableEntry = Conversions.decToBin(frame, 14)+ present + RW;					
	}
	
	// returns frame number
	String getEntry(int page) 
	{
		return null;		
	}
	
	void UseEntry(int page, int readorwrite)
	{
		
	}
	
	String getPresent()
	{		
		return pageTableEntry.substring(14, 15);
	}
	
	String getModified()
	{
		return pageTableEntry.substring(15);
	}
	
	void setPresent(int present)
	{
		if(present == 1)
			pageTableEntry = pageTableEntry.substring(0, 14) + "1" + pageTableEntry.substring(15);
		else
			pageTableEntry = pageTableEntry.substring(0, 14) + "0" + pageTableEntry.substring(15);
	}
	
	void setModified(int modified)
	{
		if(modified == 1)
			pageTableEntry = pageTableEntry.substring(0, 15) + "1";
		else
			pageTableEntry = pageTableEntry.substring(0, 15) + "0";
	}
}