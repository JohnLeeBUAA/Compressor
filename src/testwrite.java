/**
 * 测试类
 * 可用这个类生成一个文档来做测试
 * 
 * @author 李子瑨
 * 
 */


import java.io.*;


public class testwrite 
{
	
	/*
	 * main函数
	 */
	public static void main(String[] args)
	{
		try
		{
			BufferedWriter f = new BufferedWriter
					(new FileWriter("C:/test.txt"));
			
			
			/*
			 * 写文件
			 */
			for(int i=0;i<24;i++)//i为字符值
			{
				for(int j=0;j<256;j++)//j为个数
				{
					f.write((char)(i+97));
				}
			}
			
			f.close();
		}
		catch(Exception e)
		{
			System.err.println("Exception :"+e);
	    	e.printStackTrace();	
		}
	}
}
