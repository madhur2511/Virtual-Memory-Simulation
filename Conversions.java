public class Conversions 
{
	static String[] hexVal = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
	static String[] binVal = {"0000","0001","0010","0011","0100","0101","0110","0111","1000","1001",
								"1010","1011","1100","1101","1110","1111"};
	
	static String hexToBin(String hex)
	{
		String bin = "";
		for(int i = 0;i<hex.length();++i)
		{
			for(int j = 0;j<16;++j)
				if(hex.charAt(i) == hexVal[j].charAt(0))
				{
					bin = bin + binVal[j];
				}
		}
		return bin;
	}
	
	static String binToHex(String bin)
	{
		String hex = "";
		for(int i = 0;i<bin.length();i=i+4)
		{
			for(int j = 0;j<16;++j)
			{
				if(i <= bin.length()-4)
				{
					String temp = bin.substring(i, i+4);
					if(temp.equalsIgnoreCase(binVal[j]))
					{
						hex = hex + hexVal[j];
					}
				}
				
			}
		}
		return hex;		
	}
	
	static int hexToDec(String hex)
	{
        return  Integer.parseInt(hex, 16);  
	}
	
	static int binToDec(String bin)
	{
        return  Integer.parseInt(bin, 2);  
	}
	
	static String decToHex(int dec)
	{
		String hex = "";
		 
	    while (dec != 0) {
	       int hexValue = dec % 16; 
	       hex = toHexChar(hexValue) + hex;
	       dec = dec / 16;
	    }
	    return hex;
	}
	
	static char toHexChar(int hexValue) 
	{
	    if (hexValue <= 9 && hexValue >= 0)
	       return (char)(hexValue + '0');
	    else 
	       return (char)(hexValue - 10 + 'A');
	}
	
	static String decToBin(int decimal, int length)  					//convert to make a 14 bit string
	{
		String binary="";
        while(decimal > 0 )
        {
        	binary = decimal%2 + binary  ;
        	decimal/=2;
        }
        int bin_length = binary.length();
        for(int i =0;i<length-bin_length;++i)
        {
        	binary = "0" + binary;
        }        
        return binary;
	}

}