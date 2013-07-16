import java.util.Scanner;

public class Main 
{
	public static int size;
	public static void main(String args[]) throws InterruptedException
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the frame size 4K-16K");
		size = sc.nextInt();
		sc=new Scanner(System.in);
		System.out.println("Enter the filename");
		String Filename  = sc.nextLine();		
		Running running  = new Running();		
		
		Queue queue = new Queue();
		PhysicalMemory pm = new PhysicalMemory();
		OS_Simulation oss = new OS_Simulation(queue, running);
		Thread.currentThread();
		Thread.sleep(2000);
		MemoryMgmtHardwareSimulation ms = new MemoryMgmtHardwareSimulation(Filename , queue , pm, running);		
	}
	
	public static int log2(int size)
	{
		int i = 0;
		size = size/2;
		while(size!=0)
		{
			size = size/2;
			++i;
		}
		return i;
	}
}