/**
 * ������������
 * 
 * @author ���Ӭ�
 *
 */


public class HCode 
{
	public StringBuffer cd;//cdΪ��Ӧ�����������������

	
	/*
	 * ���캯��
	 */
	public HCode() 
	{
		super();
	}


	/*
	 * ���ر�������ֵ
	 */
	public StringBuffer getCd() 
	{
		return cd;
	}


	/*
	 * ���ñ�������ֵ
	 */
	public void setCd(StringBuffer cd) 
	{
		this.cd = cd;
	}
	
	
	/*
	 * �����������ֵ
	 */
	public void display()
	{
		System.out.println(cd);
	}
	
	
}
