/**
 * 哈夫曼结点类
 * 
 * @author 李子瑨
 *
 */


public class HTNode 
{
	private char data;//数据
	private int weight;//权重
	private int parent;//父节点
	private int lchild;//左子结点
	private int rchild;//右子结点
	
	
	/*
	 * 构造函数
	 */
	public HTNode() 
	{
		super();
	}


	/*
	 * 返回结点数据
	 */
	public char getData() 
	{
		return data;
	}
	
	
	/*
	 * 设置结点数据
	 */
	public void setData(char data) 
	{
		this.data = data;
	}
	
	
	/*
	 * 返回结点权重
	 */
	public int getWeight() 
	{
		return weight;
	}
	
	
	/*
	 * 设置结点权重
	 */
	public void setWeight(int weight) 
	{
		this.weight = weight;
	}
	
	
	/*
	 * 返回父节点下标
	 */
	public int getParent() 
	{
		return parent;
	}
	
	
	/*
	 * 设置父节点下标
	 */
	public void setParent(int parent) 
	{
		this.parent = parent;
	}
	
	
	/*
	 * 返回左子结点下标
	 */
	public int getLchild() 
	{
		return lchild;
	}
	
	
	/*
	 * 设置左子结点下标
	 */
	public void setLchild(int lchild) 
	{
		this.lchild = lchild;
	}
	
	
	/*
	 * 返回右子结点下标
	 */
	public int getRchild() 
	{
		return rchild;
	}
	
	
	/*
	 * 设置右子结点下标
	 */
	public void setRchild(int rchild) 
	{
		this.rchild = rchild;
	}
	
	
	/*
	 * 显示函数
	 */
	public void display()
	{
		System.out.println(getData());
		System.out.println(getWeight());
		System.out.println(getParent());
		System.out.println(getLchild());
		System.out.println(getRchild());
		
	}
}
