/**
 * ������
 * ��д����ʱ����д������
 * 
 * @author ���Ӭ�
 * 
 */


import java.io.*;


public class testread 
{
	
	/*
	 * main����
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
			 * ��ȡ����
			 */
		    int counter=f.readByte();
		    System.out.println(counter);
		    
		    
		    /*
		     * ��ȡ�ַ���Ƶ��
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
			 * ������
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
