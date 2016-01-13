/**
 * ��������
 * 
 * @author ���Ӭ�
 *
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JFrame.*;
import java.io.*;
import java.lang.Integer;
import javax.swing.filechooser.FileNameExtensionFilter;


public class MainFrame 
{
	
	
	private JFrame f;//������
	private JButton cop;//ѹ����ť
	private JButton decop;//��ѹ��ť
	private HTNode[] ht=new HTNode[512];//���������ڵ�����
	private HCode[] hcd=new HCode[256];//��������������
	private StringBuffer[] huffcode=new StringBuffer[256];//���������
	private int frequency[]=new int[256];//�ַ����ִ�������
	private char alphabet[]=new char[256];//�ַ�����
	 
	
	/*
	 * ���캯��
	 */
	public MainFrame()
	{
		
		
		/*
		 * Ϊ���ںͰ�ť�����ı�
		 */
		f=new JFrame("�㷨����ҵ��ѹ�����");
		cop=new JButton("ѹ���ļ�");
		decop=new JButton("��ѹ�ļ�");
		
		
		/*
		 * ��ʼ������������㡢������������͹������
		 */
		for(int i=0;i<512;i++)
		{
			ht[i]=new HTNode();
		}
		for(int i=0;i<256;i++)
		{
			hcd[i]=new HCode();
		}
		for(int i=0;i<256;i++)
	    {
	    	huffcode[i]=new StringBuffer("");
	    }
		
		
		/*
		 * Ϊѹ����ť�����¼�������
		 */
		cop.addActionListener(new ActionListener()
		   {
			public void actionPerformed(ActionEvent e)
			{
				
				/*
				 * ѡ��Ҫѹ�����ļ�
				 */
				JFileChooser chooser = new JFileChooser("Open File");
				FileNameExtensionFilter filter = new FileNameExtensionFilter(null,"txt");
				chooser.setFileFilter(filter);
				int option = chooser.showOpenDialog(null);
				
				
				/*
				 * ���ִ��
				 */
				if(option == JFileChooser.APPROVE_OPTION) 
				{
					String Sourcepath = chooser.getSelectedFile().getAbsolutePath();//��ȡ·��
					compression(Sourcepath);//ִ��compression()����
				}
			}
		   });
		
		
		/*
		 * Ϊ��ѹ��ť�����¼�������
		 */
		decop.addActionListener(new ActionListener()
		   {
			public void actionPerformed(ActionEvent e)
			{
				/*
				 * ѡ��Ҫ��ѹ���ļ�
				 */
				JFileChooser chooser = new JFileChooser("Open File");
				FileNameExtensionFilter filter = new FileNameExtensionFilter(null,"txt");
				chooser.setFileFilter(filter);
				int option = chooser.showOpenDialog(null);
				
				
				/*
				 * ���ִ��
				 */
				if(option == JFileChooser.APPROVE_OPTION) 
				{
					String Compressedfilepath = chooser.getSelectedFile().getAbsolutePath();//��ȡ·��
					decompression(Compressedfilepath);//ִ��decompression()����
				}
			}
		   });
		
	}
	
	
	/*
	 * ����������
	 */
	public void buildMainFrame()
	{
		
		/*
		 * ���ý���
		 */
		f.setLayout(new GridLayout(2,1));
		f.add(cop);
		f.add(decop);
		f.setSize(300,200);
		f.setVisible(true);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	/*
	 * ����ѹ���ļ���ʾ��ϸ��Ϣ�Ĵ���
	 */
	public void buildMessageFrame
	     (String Sourcepath,String compresspath,int n)
	{
		JFrame f=new JFrame("ѹ����Ϣ");
		f.setLayout(new FlowLayout());
		JTextArea message=new JTextArea("",31,33); 
		
		
		/*
		 * ��ȡԴ�ļ���ѹ���ļ��Ĵ�С
		 */
		File fsource=new File(Sourcepath);
		long sourcesize=fsource.length();
		File fcompress=new File(compresspath);
		long compresssize=fcompress.length();
		
		
		/*
		 * ����ѹ����
		 */
		double compressratio=(double)compresssize/(double)sourcesize;
		
		
		/*
		 * д��ѹ����Ϣ
		 */
		message.append("ԭʼ�ļ���С��"+String.valueOf(sourcesize)+"�ֽ�\n");
		message.append("ѹ���ļ���С��"+String.valueOf(compresssize)+"�ֽ�\n");
		String rt=new String(String.valueOf(compressratio*100));
	    message.append("ѹ���ʣ�"+rt.substring(0,5)+"%\n");
	    
	    
	    /*
	     * д������������
	     */
	    message.append("ԭʼ�ļ����������\n");
	    for(int i=0;i<n;i++)
	    {
	    	message.append(alphabet[i]+"  :  "+huffcode[alphabet[i]]+"\n");
	    }
	    
	    
	    /*
	     * ���ý���
	     */
		JScrollPane sp=new JScrollPane(message);
		f.add(sp);
		f.setSize(400,600);
		f.setVisible(true);
	}
	
	
	
	
	/*
	 * ���ɹ�������
	 * ����Ϊ������
	 */
	public void CreateHT(int n)
	{
	     int i,k,lnode,rnode;
	     int min1,min2;
	     
	     
	     /*
	      * ��ʼ�����ֵ��Ϊ-1
	      */
	     for(i=0;i<2*n-1;i++)
	     {
	    	 ht[i].setParent(-1);
	    	 ht[i].setLchild(-1);
	    	 ht[i].setRchild(-1);
	     }
	     
	     
	     /*
	      * Ϊ������㸳ֵ
	      */
	     for(i=n;i<2*n-1;i++)
	     {
	         min1=min2=32767;
	         lnode=rnode=-1;
	         for(k=0;k<=i-1;k++)
	         if(ht[k].getParent()==-1)
	         {
	        	 if(ht[k].getWeight()<min1)
	             {
	        		 min2=min1;rnode=lnode;
	                 min1=ht[k].getWeight();lnode=k;
	             }
	             else if(ht[k].getWeight()<min2)
	             {
	            	 min2=ht[k].getWeight();rnode=k; 
	             }
	         }
	         ht[i].setWeight(ht[lnode].getWeight()+ht[rnode].getWeight());
	         ht[i].setLchild(lnode);ht[i].setRchild(rnode);
	         ht[lnode].setParent(i);ht[rnode].setParent(i);
	     }
	}
	
	
	/*
	 * ���ɴ洢ѹ���ļ���·��
	 * ����ΪԴ�ļ�·��
	 * �����ɵ��ļ���Դ�ļ���ͬһ�ļ�����
	 */
	public String getCompresspath(String Sourcepath)
	{
		StringBuffer temp=new StringBuffer(Sourcepath);
		int mark=temp.lastIndexOf(".");
		temp.insert(mark,"_compression");
		String compresspath=new String(temp);
		return compresspath;
	}
	
	
	/*
	 * ���ɽ�ѹ�ļ���Ҫ�õ����ַ�Ƶ���ļ���·��
	 * ����ΪҪ��ѹ���ļ�·��
	 * Ĭ���ַ�Ƶ���ļ���Ҫ��ѹ���ļ���ͬһ�ļ�����
	 */
	public String getDecompressfrequencypath(String path)
	{
		StringBuffer temp=new StringBuffer(path);
		int mark=temp.lastIndexOf("_");
		temp.replace(mark,mark+12,"_frequency");
		String frequencypath=new String(temp);
		return frequencypath;
	}
	
	
	/*
	 * ���ɴ洢��ѹ�ļ���·��
	 * ����ΪҪ��ѹ���ļ�·��
	 * �����ɵĽ�ѹ����ļ���ѹ���ļ���ͬһ�ļ�����
	 */
	public String getDecompresspath(String path)
	{
		StringBuffer temp=new StringBuffer(path);
		int mark=temp.lastIndexOf("_");
		temp.replace(mark,mark+12,"_decompression");
		String decompresspath=new String(temp);
		return decompresspath;
	}
	
	
	/*
	 * ���ɹ���������
	 * ����Ϊ������
	 */
	public void CreateHCode(int n)
	{
	     int i,f,c;
	     StringBuffer temp=new StringBuffer("");
	     for(i=0;i<n;i++)
	     {
	           temp=new StringBuffer("");
	           c=i;
	           f=ht[i].getParent();
	           while(f!=-1)
	           {
	        	     
	        	    /*
	        	     * �ж������ӽ������ӽ��
	        	     */
	                 if(ht[f].getLchild()==c)
	                 {
	                	 temp.append("0");
	                 }
	                 else
	                 {
	                	 temp.append("1");
	                 }
	                 c=f;f=ht[f].getParent();
	           }
	           hcd[i].cd=temp;
	     }
	}
	
	
	/*
	 * ѹ������
	 * ����ΪԴ�ļ�·��
	 */
	public void compression(String Sourcepath)
	{
		
		/*
		 * ͨ��getCompresspath()�����õ���ѹ�ļ��Ĵ���·��
		 * counterΪ�ַ�����������¼��ͬ�ַ��ĸ�������ʼΪ0
		 */
		String compresspath=getCompresspath(Sourcepath);
		int counter=0;
		try
		{
			
			/*
			 * ��ȡԴ�ļ���ͳ�Ƹ����ַ����ִ���
			 */
			DataInputStream fin=new DataInputStream
					(new FileInputStream(Sourcepath));
			counter=0;
			int input;
			while((input=fin.read())!=-1)
			{
				if(input>=0&&input<=255)
				{
					boolean flag=true;//�����жϸ��ַ���ǰ�Ƿ���ֹ�
		             for(int j=0;j<counter;j++)
		             {
							if(input==alphabet[j])//��ǰ�ַ�����ǰ��ĳ���ַ���ͬ
							{
		         						frequency[j]++;//������ַ���Ƶ�μ�һ
		         						flag=false;//��flag��Ϊfalse��˵�����ַ���ǰ���ֹ�
		         						break;//����ѭ��
							}
		             }
		             if(flag)//������ַ���ǰû���ֹ�
		             {
							alphabet[counter]=(char)input;//��alphabet[]��Ϊ�������ַ�
							frequency[counter]=1;//Ƶ����Ϊ1
							counter++;//��������һ
		             }
				}
			}
			fin.close();
			
			
			/*
			 * ���ַ������ִ���д���ļ�
			 */
			DataOutputStream fout=new DataOutputStream
					(new FileOutputStream(compresspath));
			fout.writeByte((char)counter);//д���ַ�����
			
			//д���ַ���Ƶ��
			for(int i=0;i<counter;i++)
			{
				fout.writeByte(alphabet[i]);
				fout.writeInt(frequency[i]);
			}
			
			
			
			
			/*
			 * �����ַ�Ƶ�ι��������������������������
			 */
			//Ϊǰn������������㸳ֵ
		    for(int i=0;i<counter;i++)
		    {
		             ht[i].setData(alphabet[i]);
		             ht[i].setWeight(frequency[i]);
		    }
		    CreateHT(counter);//������������
		    CreateHCode(counter);//��������������
		    
		    //���������������huffcode[]��
		    for(int i=0;i<counter;i++)
		    {
		    	huffcode[(int)alphabet[i]]=hcd[i].cd.reverse();
		    }
		     
		    
		    /*
			 * ��ȡԴ�ļ��������е��ַ�ת���ɹ���������
			 */
		    //compress�ַ����洢Դ�ļ�ת����Ľ����ȫ����0��1���
			StringBuffer compress=new StringBuffer("");
			DataInputStream fin2=new DataInputStream
					(new FileInputStream(Sourcepath));
			int input2;
			while((input2=fin2.read())!=-1)
			{
				if(input2>=0&&input2<=255)
				{
					//���ַ���Ӧ�Ĺ������������compress�ַ�����
					compress.append(huffcode[input2]);
				}
			}
		    fin2.close();
		    
		    
		    /*
			 * ���������ļ�ÿ��λ�����һ��0-255֮�������
			 * �����Ȳ�Ϊ8�������������ڽ�β��'0'
			 * �ٽ�������Ӧ���ַ�д�����ļ�
			 */
		    int lenco=compress.length();
		    int appendtail=lenco%8;
		    appendtail=8-appendtail;//appendtail�����β���'0'�ĸ���
		    if(appendtail==8)
		    {
		    	appendtail=0;
		    }
		    fout.writeByte(appendtail);//д���β��'0'�ĸ���
		    
		    //�ڽ�β��'0'����compress�ĳ��ȱ�Ϊ8��������
		    for(int i=1;i<=appendtail;i++)
		    {
		    	compress.append('0');
		    }
		    
		    //ÿ��ȡ8λ������ķ�ʽ���д洢
		    while(compress.length()>=8)
            {
                 int tempoutput=0;
                 int po=128;
                 for(int j=7;j>=0;j--)
	             {
                	 tempoutput+=po*((int)compress.charAt(7-j)-48);
               	     po/=2;
	             }
                 fout.writeByte((char)tempoutput);
                 compress.delete(0,8);
            }
		    fout.close();
		    
		    
		}
		catch(Exception e)
		{
			System.err.println("Exception :"+e);
	    	e.printStackTrace();
	    	JOptionPane.showMessageDialog(null, "�����쳣��������ֹ");
	    	System.exit(1);
		}
		
		
		/*
		 * ��ʾ��ʾ��Ϣ
		 */
		if((JOptionPane.showConfirmDialog
				(null,"ѹ���ɹ����Ƿ�鿴��ϸ��Ϣ? ","��Ϣ",JOptionPane.YES_NO_OPTION))==0)
		{
			//������ʾ��ϸ��Ϣ�Ĵ���
			buildMessageFrame(Sourcepath,compresspath,counter);
		}
	}
	
	public void decompression(String path)
	{
		
		//ͨ��getDecompresspath()�����õ���ѹ���ļ��Ĵ洢·��
		String decompresspath=getDecompresspath(path);
		try
		{
			
			/*
			 * ��ȡ��ѹ�ļ���Ҫ�ĵ��ַ������ļ�
			 */
			DataInputStream fin=new DataInputStream
					(new FileInputStream(path));
			
			//��ȡ�ַ���Ƶ��
			int counter=fin.readByte();
			for(int i=0;i<counter;i++)
			{
				alphabet[i]=(char)fin.readByte();
				frequency[i]=fin.readInt();
			}
			
			
			
			/*
			 * �����ַ�Ƶ������Դ�ļ���Ӧ�Ĺ�������
			 */
			//Ϊǰn������������㸳ֵ
			for(int i=0;i<counter;i++)
		    {
		             ht[i].setData(alphabet[i]);
		             ht[i].setWeight(frequency[i]);
		    }
			CreateHT(counter);//������������
			
			
			/*
			 * ��Ҫ��ѹ���ļ�ת���ɶ����ƹ���������
			 */
			//��ȡ��β��0�ĸ���
			int appendtail=fin.readByte();
			int input;
			//decompress�ַ����洢ת�����
			StringBuffer decompress=new StringBuffer("");
			while((input=fin.read())!=-1)
			{
				if(input>=0&&input<=255)
		        {
					/*
					 * �����0-255֮�������ת����2�����ַ���
					 * ���ڸ��ַ���������˲�0������8λ
					 * �����8λ���ַ�������decompress
					 */
					StringBuffer tempde=new StringBuffer(Integer.toBinaryString(input));
					int len=tempde.length();
					for(int i=1;i<=8-len;i++)
					{
						tempde.insert(0,'0');
					}
					decompress.append(tempde);
		        }
			}
			fin.close();
			
			//ɾ��д�ļ�ʱ�ڽ�β����0
			int lende=decompress.length();
			if(appendtail!=0)
				decompress.delete(lende-appendtail, lende-1);
			lende-=appendtail;
			
			
			/*
			 * ���ݹ���������Ҫ��ѹ�ļ��Ĺ����������ѹ�ļ�
			 * ����ѹ����ļ�д�����ļ�
			 */
			DataOutputStream fout=new DataOutputStream
					(new FileOutputStream(decompresspath));
			int jilu=0;//��¼��ǰ���ڲ������±�
			int huffpointer=2*counter-2;//���������±�
			while(jilu<lende)
			{
				if(decompress.charAt(jilu)=='0')
				{
					huffpointer=ht[huffpointer].getLchild();
				}
				else 
				{
					huffpointer=ht[huffpointer].getRchild();
				}
				//����ǰ�ڵ�ΪҶ�ӽ�㣬�����Dataֵ�������������±��ƶ��������
				if(ht[huffpointer].getLchild()==-1&&ht[huffpointer].getRchild()==-1)
				{
					fout.writeByte(ht[huffpointer].getData());
					huffpointer=2*counter-2;
				}
				jilu++;
			}
			fout.close();
			
			
		}
		catch(Exception e)
		{
			System.err.println("Exception :"+e);
	    	e.printStackTrace();
	    	JOptionPane.showMessageDialog(null, "�����쳣��������ֹ");
	    	System.exit(1);
		}
		
		
		/*
		 * ��ʾ��ʾ��Ϣ
		 */
		JOptionPane.showMessageDialog
		(null, "��ѹ�ɹ�����ѹ����ļ������ڣ� \n"+decompresspath);
	}
	
	
    /*
     * ������
     */
	public static void main(String[] args)
	{
		MainFrame a=new MainFrame();
		a.buildMainFrame();//����������
	}

}
