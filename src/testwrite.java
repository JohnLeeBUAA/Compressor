/**
 * ������
 * �������������һ���ĵ���������
 * 
 * @author ���Ӭ�
 * 
 */


import java.io.*;


public class testwrite 
{
	
	/*
	 * main����
	 */
	public static void main(String[] args)
	{
		try
		{
			BufferedWriter f = new BufferedWriter
					(new FileWriter("C:/test.txt"));
			
			
			/*
			 * д�ļ�
			 */
			for(int i=0;i<24;i++)//iΪ�ַ�ֵ
			{
				for(int j=0;j<256;j++)//jΪ����
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
