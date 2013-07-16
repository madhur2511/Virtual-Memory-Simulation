import java.util.StringTokenizer;

class InputFileInfo
{
	private int pid;
	private int RW;					// R => 0 , W => 1
	private String VirtualAddress;
	private String PageNo;
	private String Offset;	
	

	InputFileInfo(String line)
	{		
		StringTokenizer st = new StringTokenizer(line, " ");
		if(st.hasMoreTokens())
		{
			pid = Integer.parseInt(st.nextToken());		
		}
		if(st.hasMoreTokens())
		{
			String temp = st.nextToken();
			if(temp.equalsIgnoreCase("R"))
				RW = 0;
			else 
				RW = 1;
		}
		if(st.hasMoreTokens())
		{
			VirtualAddress = Conversions.hexToBin(st.nextToken());
			PageNo = VirtualAddress.substring(0,6);
			Offset = VirtualAddress.substring(6);
		}
	}
	
	public int getPid() 
	{
		return pid;
	}

	public void setPid(int pid) 
	{
		this.pid = pid;
	}

	public int getRW() 
	{
		return RW;
	}

	public void setRW(int rW) 
	{
		RW = rW;
	}

	public String getVirtualAddress() 
	{
		return VirtualAddress;
	}

	public void setVirtualAddress(String virtualAddress) 
	{
		VirtualAddress = virtualAddress;
	}

	public String getPageNo() 
	{
		return PageNo;
	}

	public void setPageNo(String pageNo)
	{
		PageNo = pageNo;
	}

	public String getOffset()
	{
		return Offset;
	}

	public void setOffset(String offset)
	{
		Offset = offset;
	}

}