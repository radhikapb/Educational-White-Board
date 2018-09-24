package paint.model;

import java.awt.Color;
import java.io.*;
/**
 * ����� ��� �������� �������� � ������������ (���������) ������
 * @author User
 *
 */
public class ColorModel extends paint.model.Model
{
	Color[] _standartPalete = new Color[20];		//����������� �������
	Color[] _customPalete = new Color[20];			//������� ������������
	Color _firstColor;								//�������� ����	
	Color _secondColor;								//�������������� ����
	/**
	 * ���������� �������� ����� ������
	 * @return �������� ����� ������
	 */
	public Color[] getStandartPalete()
	{
		return _standartPalete;
	}
	/**
	 * ���������� ������������ ����� ������
	 * @return ������������ ����� ������
	 */
	public Color[] getCustomPalete()
	{
		return _customPalete;
	}
	/**
	 * ���������� �������� ����
	 * @return �������� ����
	 */
	public Color getFirstColor()
	{
		return _firstColor;
	}
	/**
	 * ������������� �������� ����
	 * @param value ����� ����
	 */
	public void setFirstColor(Color value)
	{
		_firstColor = value;
		update();
	}
	/**
	 * ���������� ������ ����
	 * @return ������ ����
	 */
	public Color getSecondColor()
	{
		return _secondColor;
	}
	/**
	 * ������������� ������ ����
	 * @param value ����� ����
	 */
	public void setSecondColor(Color value)
	{
		_secondColor = value;
		update();
	}
	/**
	 * ��������� ������� �� �����
	 */
	private void load()
	{
		java.io.ObjectInputStream stream;
		FileInputStream input = null;
		try { 
			input = new FileInputStream(new File("colors.dat"));
			stream = new java.io.ObjectInputStream(input );
			for(int i = 0; i < _customPalete.length; i++)
				_customPalete[i] = (Color) stream.readObject();
		}
		catch (FileNotFoundException e) 
		{
			//������ �������� - �� ������ ���� ( ����� �� ������)
		} 
		catch (IOException e) 
		{
			//������ �������� � �������� ������� - ��������� ������� �����
			try
			{
				if(input != null)
					input.close();
			} 
			catch (IOException e1) 
			{
				//������ ��� ��������� �������� ������, ������ ����� ��� ������ 
			}
		}
		catch (ClassNotFoundException e)
		{
			//������ �������� � �������� ������� - ��������� ������� �����
			try
			{
				if(input != null)
					input.close();
			} 
			catch (IOException e1) 
			{
				//������ ��� ��������� �������� ������, ������ ����� ��� ������ 
			}
		}
	}
	/**
	 * ��������� ������� � ����
	 */
	public void save()
	{
		java.io.ObjectOutputStream stream;
		FileOutputStream output = null;
		try 
		{
			output = new FileOutputStream(new File("colors.dat"));
			stream = new java.io.ObjectOutputStream(output);
			for(int i = 0; i < _customPalete.length; i++)
				stream.writeObject( _customPalete[i]);
			output.close();
		} 
		catch (FileNotFoundException e) 
		{
			//������ ���������� - �� ������ ���� ( ����� �� ������)	
		} 
		catch (IOException e) 
		{
			//������ ���������� � �������� ������� - ��������� ������� �����
			try
			{
				if(output != null)
					output.close();
			} 
			catch (IOException e1) 
			{
				//������ ��� ��������� �������� ������, ������ ����� ��� ������ 
			}
		}
	}
	/**
	 * ����������� ������ ������
	 */
	public ColorModel()
	{
		//��������� �������
		_standartPalete[0] = Color.BLACK;
		_standartPalete[1] = Color.WHITE;	 
		_standartPalete[2] = Color.DARK_GRAY;
		_standartPalete[3] = Color.GRAY;
		_standartPalete[4] = new Color(128,0,0); //�����-�������
		_standartPalete[5] = Color.RED;	
		_standartPalete[6] = new Color(128,64,0); //����������
		_standartPalete[7] = Color.ORANGE;	
		_standartPalete[8] = new Color(128,128,0); //�����-�������
		_standartPalete[9] = Color.YELLOW;	
		_standartPalete[10] = new Color(0,128,0); //�����-�������
		_standartPalete[11] = Color.GREEN;	
		_standartPalete[12] = new Color(0,128,192); //�����-�����
		_standartPalete[13] = Color.CYAN;
		_standartPalete[14] = new Color(0,0,128); //�����-�����
		_standartPalete[15] = Color.BLUE;
		_standartPalete[16] = new Color(128,0,128); //������ magenta
		_standartPalete[17] = Color.MAGENTA;	
		_standartPalete[18] = new Color(64,0,128);	//����� - ����������	
		_standartPalete[19] = new Color(128,0,255);	//����������
		
		_firstColor = Color.BLACK;
		_secondColor = Color.WHITE;
		load();
	}
}
