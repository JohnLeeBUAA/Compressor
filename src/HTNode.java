/**
 * �����������
 * 
 * @author ���Ӭ�
 *
 */


public class HTNode 
{
	private char data;//����
	private int weight;//Ȩ��
	private int parent;//���ڵ�
	private int lchild;//���ӽ��
	private int rchild;//���ӽ��
	
	
	/*
	 * ���캯��
	 */
	public HTNode() 
	{
		super();
	}


	/*
	 * ���ؽ������
	 */
	public char getData() 
	{
		return data;
	}
	
	
	/*
	 * ���ý������
	 */
	public void setData(char data) 
	{
		this.data = data;
	}
	
	
	/*
	 * ���ؽ��Ȩ��
	 */
	public int getWeight() 
	{
		return weight;
	}
	
	
	/*
	 * ���ý��Ȩ��
	 */
	public void setWeight(int weight) 
	{
		this.weight = weight;
	}
	
	
	/*
	 * ���ظ��ڵ��±�
	 */
	public int getParent() 
	{
		return parent;
	}
	
	
	/*
	 * ���ø��ڵ��±�
	 */
	public void setParent(int parent) 
	{
		this.parent = parent;
	}
	
	
	/*
	 * �������ӽ���±�
	 */
	public int getLchild() 
	{
		return lchild;
	}
	
	
	/*
	 * �������ӽ���±�
	 */
	public void setLchild(int lchild) 
	{
		this.lchild = lchild;
	}
	
	
	/*
	 * �������ӽ���±�
	 */
	public int getRchild() 
	{
		return rchild;
	}
	
	
	/*
	 * �������ӽ���±�
	 */
	public void setRchild(int rchild) 
	{
		this.rchild = rchild;
	}
	
	
	/*
	 * ��ʾ����
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
