/**
 * 主窗口类
 * 
 * @author 李子瑨
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
	
	
	private JFrame f;//主窗口
	private JButton cop;//压缩按钮
	private JButton decop;//解压按钮
	private HTNode[] ht=new HTNode[512];//哈夫曼树节点数组
	private HCode[] hcd=new HCode[256];//哈夫曼编码数组
	private StringBuffer[] huffcode=new StringBuffer[256];//哈夫曼码表
	private int frequency[]=new int[256];//字符出现次数数组
	private char alphabet[]=new char[256];//字符数组
	 
	
	/*
	 * 构造函数
	 */
	public MainFrame()
	{
		
		
		/*
		 * 为窗口和按钮设置文本
		 */
		f=new JFrame("算法大作业：压缩软件");
		cop=new JButton("压缩文件");
		decop=new JButton("解压文件");
		
		
		/*
		 * 初始化哈夫曼树结点、哈夫曼编码结点和哈夫码表
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
		 * 为压缩按钮设置事件监听器
		 */
		cop.addActionListener(new ActionListener()
		   {
			public void actionPerformed(ActionEvent e)
			{
				
				/*
				 * 选择要压缩的文件
				 */
				JFileChooser chooser = new JFileChooser("Open File");
				FileNameExtensionFilter filter = new FileNameExtensionFilter(null,"txt");
				chooser.setFileFilter(filter);
				int option = chooser.showOpenDialog(null);
				
				
				/*
				 * 点击执行
				 */
				if(option == JFileChooser.APPROVE_OPTION) 
				{
					String Sourcepath = chooser.getSelectedFile().getAbsolutePath();//读取路径
					compression(Sourcepath);//执行compression()函数
				}
			}
		   });
		
		
		/*
		 * 为解压按钮设置事件监听器
		 */
		decop.addActionListener(new ActionListener()
		   {
			public void actionPerformed(ActionEvent e)
			{
				/*
				 * 选择要解压的文件
				 */
				JFileChooser chooser = new JFileChooser("Open File");
				FileNameExtensionFilter filter = new FileNameExtensionFilter(null,"txt");
				chooser.setFileFilter(filter);
				int option = chooser.showOpenDialog(null);
				
				
				/*
				 * 点击执行
				 */
				if(option == JFileChooser.APPROVE_OPTION) 
				{
					String Compressedfilepath = chooser.getSelectedFile().getAbsolutePath();//读取路径
					decompression(Compressedfilepath);//执行decompression()函数
				}
			}
		   });
		
	}
	
	
	/*
	 * 构建主窗口
	 */
	public void buildMainFrame()
	{
		
		/*
		 * 设置界面
		 */
		f.setLayout(new GridLayout(2,1));
		f.add(cop);
		f.add(decop);
		f.setSize(300,200);
		f.setVisible(true);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	/*
	 * 构建压缩文件显示详细信息的窗口
	 */
	public void buildMessageFrame
	     (String Sourcepath,String compresspath,int n)
	{
		JFrame f=new JFrame("压缩信息");
		f.setLayout(new FlowLayout());
		JTextArea message=new JTextArea("",31,33); 
		
		
		/*
		 * 读取源文件和压缩文件的大小
		 */
		File fsource=new File(Sourcepath);
		long sourcesize=fsource.length();
		File fcompress=new File(compresspath);
		long compresssize=fcompress.length();
		
		
		/*
		 * 计算压缩率
		 */
		double compressratio=(double)compresssize/(double)sourcesize;
		
		
		/*
		 * 写入压缩信息
		 */
		message.append("原始文件大小："+String.valueOf(sourcesize)+"字节\n");
		message.append("压缩文件大小："+String.valueOf(compresssize)+"字节\n");
		String rt=new String(String.valueOf(compressratio*100));
	    message.append("压缩率："+rt.substring(0,5)+"%\n");
	    
	    
	    /*
	     * 写入哈夫曼编码表
	     */
	    message.append("原始文件哈夫曼码表：\n");
	    for(int i=0;i<n;i++)
	    {
	    	message.append(alphabet[i]+"  :  "+huffcode[alphabet[i]]+"\n");
	    }
	    
	    
	    /*
	     * 设置界面
	     */
		JScrollPane sp=new JScrollPane(message);
		f.add(sp);
		f.setSize(400,600);
		f.setVisible(true);
	}
	
	
	
	
	/*
	 * 生成哈夫曼树
	 * 参数为结点个数
	 */
	public void CreateHT(int n)
	{
	     int i,k,lnode,rnode;
	     int min1,min2;
	     
	     
	     /*
	      * 初始化结点值均为-1
	      */
	     for(i=0;i<2*n-1;i++)
	     {
	    	 ht[i].setParent(-1);
	    	 ht[i].setLchild(-1);
	    	 ht[i].setRchild(-1);
	     }
	     
	     
	     /*
	      * 为各个结点赋值
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
	 * 生成存储压缩文件的路径
	 * 参数为源文件路径
	 * 新生成的文件与源文件在同一文件夹下
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
	 * 生成解压文件需要用到的字符频次文件的路径
	 * 参数为要解压的文件路径
	 * 默认字符频次文件与要解压的文件在同一文件夹下
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
	 * 生成存储解压文件的路径
	 * 参数为要解压的文件路径
	 * 新生成的解压后的文件与压缩文件在同一文件夹下
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
	 * 生成哈弗曼编码
	 * 参数为结点个数
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
	        	     * 判断是左子结点或右子结点
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
	 * 压缩函数
	 * 参数为源文件路径
	 */
	public void compression(String Sourcepath)
	{
		
		/*
		 * 通过getCompresspath()函数得到解压文件的储存路径
		 * counter为字符计数器，记录不同字符的个数，初始为0
		 */
		String compresspath=getCompresspath(Sourcepath);
		int counter=0;
		try
		{
			
			/*
			 * 读取源文件并统计各个字符出现次数
			 */
			DataInputStream fin=new DataInputStream
					(new FileInputStream(Sourcepath));
			counter=0;
			int input;
			while((input=fin.read())!=-1)
			{
				if(input>=0&&input<=255)
				{
					boolean flag=true;//用于判断该字符以前是否出现过
		             for(int j=0;j<counter;j++)
		             {
							if(input==alphabet[j])//当前字符与以前的某个字符相同
							{
		         						frequency[j]++;//将这个字符的频次加一
		         						flag=false;//将flag置为false，说明该字符以前出现过
		         						break;//跳出循环
							}
		             }
		             if(flag)//如果该字符以前没出现过
		             {
							alphabet[counter]=(char)input;//在alphabet[]中为储存新字符
							frequency[counter]=1;//频次置为1
							counter++;//计数器加一
		             }
				}
			}
			fin.close();
			
			
			/*
			 * 将字符及出现次数写入文件
			 */
			DataOutputStream fout=new DataOutputStream
					(new FileOutputStream(compresspath));
			fout.writeByte((char)counter);//写入字符个数
			
			//写入字符和频次
			for(int i=0;i<counter;i++)
			{
				fout.writeByte(alphabet[i]);
				fout.writeInt(frequency[i]);
			}
			
			
			
			
			/*
			 * 根据字符频次构建哈夫曼树并计算哈夫曼编码
			 */
			//为前n个哈夫曼树结点赋值
		    for(int i=0;i<counter;i++)
		    {
		             ht[i].setData(alphabet[i]);
		             ht[i].setWeight(frequency[i]);
		    }
		    CreateHT(counter);//构建哈夫曼树
		    CreateHCode(counter);//构建哈弗曼编码
		    
		    //将哈夫曼编码存入huffcode[]中
		    for(int i=0;i<counter;i++)
		    {
		    	huffcode[(int)alphabet[i]]=hcd[i].cd.reverse();
		    }
		     
		    
		    /*
			 * 读取源文件并将其中的字符转换成哈夫曼编码
			 */
		    //compress字符串存储源文件转换后的结果，全部由0、1组成
			StringBuffer compress=new StringBuffer("");
			DataInputStream fin2=new DataInputStream
					(new FileInputStream(Sourcepath));
			int input2;
			while((input2=fin2.read())!=-1)
			{
				if(input2>=0&&input2<=255)
				{
					//将字符对应的哈夫曼编码接在compress字符串后
					compress.append(huffcode[input2]);
				}
			}
		    fin2.close();
		    
		    
		    /*
			 * 将编码后的文件每八位换算成一个0-255之间的整数
			 * 若长度不为8的整数倍，则在结尾补'0'
			 * 再将整数对应的字符写入新文件
			 */
		    int lenco=compress.length();
		    int appendtail=lenco%8;
		    appendtail=8-appendtail;//appendtail代表结尾添加'0'的个数
		    if(appendtail==8)
		    {
		    	appendtail=0;
		    }
		    fout.writeByte(appendtail);//写入结尾补'0'的个数
		    
		    //在结尾补'0'，将compress的长度变为8的整数倍
		    for(int i=1;i<=appendtail;i++)
		    {
		    	compress.append('0');
		    }
		    
		    //每次取8位按上面的方式进行存储
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
	    	JOptionPane.showMessageDialog(null, "出现异常，程序终止");
	    	System.exit(1);
		}
		
		
		/*
		 * 显示提示信息
		 */
		if((JOptionPane.showConfirmDialog
				(null,"压缩成功！是否查看详细信息? ","消息",JOptionPane.YES_NO_OPTION))==0)
		{
			//构建显示详细信息的窗口
			buildMessageFrame(Sourcepath,compresspath,counter);
		}
	}
	
	public void decompression(String path)
	{
		
		//通过getDecompresspath()函数得到解压后文件的存储路径
		String decompresspath=getDecompresspath(path);
		try
		{
			
			/*
			 * 读取解压文件需要的的字符次数文件
			 */
			DataInputStream fin=new DataInputStream
					(new FileInputStream(path));
			
			//读取字符及频次
			int counter=fin.readByte();
			for(int i=0;i<counter;i++)
			{
				alphabet[i]=(char)fin.readByte();
				frequency[i]=fin.readInt();
			}
			
			
			
			/*
			 * 根据字符频次生成源文件对应的哈夫曼树
			 */
			//为前n个哈夫曼树结点赋值
			for(int i=0;i<counter;i++)
		    {
		             ht[i].setData(alphabet[i]);
		             ht[i].setWeight(frequency[i]);
		    }
			CreateHT(counter);//构建哈夫曼树
			
			
			/*
			 * 将要解压的文件转换成二进制哈夫曼编码
			 */
			//读取结尾补0的个数
			int appendtail=fin.readByte();
			int input;
			//decompress字符串存储转换结果
			StringBuffer decompress=new StringBuffer("");
			while((input=fin.read())!=-1)
			{
				if(input>=0&&input<=255)
		        {
					/*
					 * 将这个0-255之间的整数转换成2进制字符串
					 * 并在该字符串的最左端补0将其变成8位
					 * 将这个8位的字符串接入decompress
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
			
			//删掉写文件时在结尾补的0
			int lende=decompress.length();
			if(appendtail!=0)
				decompress.delete(lende-appendtail, lende-1);
			lende-=appendtail;
			
			
			/*
			 * 根据哈夫曼树和要解压文件的哈弗曼编码解压文件
			 * 将解压后的文件写入新文件
			 */
			DataOutputStream fout=new DataOutputStream
					(new FileOutputStream(decompresspath));
			int jilu=0;//记录当前正在操作的下标
			int huffpointer=2*counter-2;//哈夫曼树下标
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
				//若当前节点为叶子结点，则输出Data值并将哈夫曼树下标移动到根结点
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
	    	JOptionPane.showMessageDialog(null, "出现异常，程序终止");
	    	System.exit(1);
		}
		
		
		/*
		 * 显示提示信息
		 */
		JOptionPane.showMessageDialog
		(null, "解压成功！解压后的文件储存在： \n"+decompresspath);
	}
	
	
    /*
     * 主函数
     */
	public static void main(String[] args)
	{
		MainFrame a=new MainFrame();
		a.buildMainFrame();//构建主窗口
	}

}
