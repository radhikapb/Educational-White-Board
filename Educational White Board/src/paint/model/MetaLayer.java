package paint.model;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 * 
 * @author Vit
 *
 */
public class MetaLayer extends Model
{
	private volatile BufferedImage _image;	//���� ����������� ����
	/**
	 * ����������� �������
	 * @param size ����������� ������ ����-����
	 */
	public MetaLayer(Dimension size)
	{
		_image = new BufferedImage(size.width,size.height, BufferedImage.TYPE_INT_ARGB);	//������� ����������� ������� ����
	}
	/**
	 * ������� ����-����, ���������� ��� ���������� �������
	 */
	public void Clear()
	{
		_image = new BufferedImage(_image.getWidth(),_image.getHeight(), BufferedImage.TYPE_INT_ARGB);	//������� ����������� ������� ����
		update();
	}
	/**
	 * ���������� ������� �����������
	 * @return ������� �����������
	 */
	public BufferedImage getImage()
	{	
		return _image;
	}
	/**
	 * �������������� ������
	 * @param size ����� ������ �����������
	 */
	public void resize(int width, int height)
	{
		_image = new BufferedImage(width,height,  BufferedImage.TYPE_INT_ARGB);	//������� ����������� ������� ����
		Clear();
		update();
	}
}
