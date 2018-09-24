package paint.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
/**
 * 
 * @author Vit
 */
public class MetaLayerList
{
	//������ �����
	private ArrayList <BufferedImage> _list = new ArrayList<BufferedImage>(0);
	//���������� ���� ����
	Color _transparent = new Color(0,0,0,0);
	/**
	 * ��������� ���� � ������ �����
	 * @param image
	 */
	public void add(BufferedImage image)
	{
		_list.add(image);
	};
	/**
	 * ������� ���� �� ������ �����
	 * @param image
	 */
	public void delete(BufferedImage image)
	{
		_list.remove(image);
	};
	/**
	 * ������� ����, ������� ���������� �������
	 * @param image
	 */
	public void clear(BufferedImage image)
	{	
		Graphics2D graph = (Graphics2D)image.getGraphics();
		graph.setColor(_transparent);
		graph.fillRect(0, 0, image.getWidth(), image.getHeight());
	};
	/**
	 * ���������� ���� �� ������ �� �������
	 * @param image
	 */
	public BufferedImage get(int index)
	{
		return _list.get(index);
	};
	/**
	 * ���������� ����� ����� � ������
	 * @param image
	 */
	public int getLength()
	{
		return _list.size();
	}
}
