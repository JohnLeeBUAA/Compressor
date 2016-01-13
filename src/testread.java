/**
 * 测试类
 * 在写代码时做读写测试用
 * 
 * @author 李子瑨
 * 
 */


import java.io.*;


public class testread 
{
	
	/*
	 * main函数
	 */
	public static void main(String[] args)
	{
		try
		{
			DataInputStream f=new DataInputStream
					(new FileInputStream("C:/test.txt"));
			
			//BufferedReader f = new BufferedReader(
					//new FileReader("C:/The Gift of Magi_huffmancode.txt"));
			
			
			/*
			 * 读取个数
			 */
		    int counter=f.readByte();
		    System.out.println(counter);
		    
		    
		    /*
		     * 读取字符和频次
		     */
		    char[] al=new char[counter];
		    int[] fre=new int[counter];
			for(int i=0;i<counter;i++)
			{
				al[i]=(char)f.readByte();
				fre[i]=f.readInt();
			}
			f.close();
			
			
			/*
			 * 输出结果
			 */
			for(int i=0;i<counter;i++)
			{
				System.out.println(al[i]+" : "+fre[i]);
			}
		}
		catch(Exception e)
		{
			System.err.println("Exception :"+e);
	    	e.printStackTrace();	
		}
		
	}

}
