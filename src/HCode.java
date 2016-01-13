/**
 * 哈夫曼编码类
 * 
 * @author 李子瑨
 *
 */


public class HCode 
{
	public StringBuffer cd;//cd为对应结点哈夫曼编码的逆序

	
	/*
	 * 构造函数
	 */
	public HCode() 
	{
		super();
	}


	/*
	 * 返回编码逆序值
	 */
	public StringBuffer getCd() 
	{
		return cd;
	}


	/*
	 * 设置编码逆序值
	 */
	public void setCd(StringBuffer cd) 
	{
		this.cd = cd;
	}
	
	
	/*
	 * 输出编码逆序值
	 */
	public void display()
	{
		System.out.println(cd);
	}
	
	
}
