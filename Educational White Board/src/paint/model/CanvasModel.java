package paint.model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import paint.view.View;
/**
 * 
 * 
 * @author Vit
 *
 */
public class CanvasModel extends Model implements View
{
	private CanvasImage _image;				//�����������
	private volatile float _scale;					
	private Point _viewPosition;			//����� ������ - �� �������������������� 
	private Dimension	_canvasSize;		//������ ������� ������� ���� ��������� - ��������������������
	private JPanel _drawingPane;			//������ ��������� 
	private	JTextField _textLabel;				//������ ������
	private ArrayList<MetaLayer> _metaLayers = new ArrayList<MetaLayer>(0);		//���������� ���� ������� ���������
	/**
	 * ���������� ����� �������������� ������
	 * @return
	 */
	public JTextField getTextLabel()
	{
		return _textLabel;
	}
	/**
	 * ��������� � ������ ����� ��������� ������
	 * ���������������, ��� �� ����� ������� ����� � ������ ������ ����������
	 * @param textfield
	 */
	public void setTextLabel(JTextField textfield)
	{
		_textLabel = textfield;
	}
	/**
	 * �������� ������ ������ ��� ���������
	 * @param pane
	 */
	public void setDrawingPane(JPanel pane)
	{
		_drawingPane = pane;
	}
	/**
	 * ���������� ������ ��� ���������
	 * @return	������ ��� ���������
	 */
	public JPanel getDrawingPane()
	{
		return _drawingPane;
	}
	/**
	 * ������������� ������ � ������ ������� ������� ���� ��������� - �� �������������������� ��������
	 * @param width - ������
	 * @param height - ������
	 */
	public void setSize(int width, int height)
	{
		_canvasSize.width = width;
		_canvasSize.height = height;
	}
	/**
	 * ���������� ������ ������� ������� ���� ���������
	 * @return ������  - �� �������������������� ��������
	 */
	public int getWidth()
	{
		return _canvasSize.width;
	}
	/**
	 * ���������� ������ ������� ������� ���� ���������
	 * @return ������  - �� �������������������� ��������
	 */
	public int getHeight()
	{
		return _canvasSize.height ;
	}
	/**
	 * ���������� ������ ����������� 
	 * @return ������ - � �������� �����������
	 */
	public int getScaledWidth()
	{
		return Math.round(_image.getImage().getWidth() * _scale) ;
	}
	/**
	 * ���������� ������ �����������
	 * @return ������  - � �������� �����������
	 */
	public int getScaledHeight()
	{
		return Math.round(_image.getImage().getHeight() * _scale) ;
	}
	/**
	 * ���������� �����������, ���������� � ������
	 * @return �����������
	 */
	public CanvasImage getImage()
	{
		return _image;
	}
	/**
	 * ���������� ������� �����������
	 * @return	�������
	 */
	public float getScale()
	{
		return _scale;
	}
	/**
	 * ������������ ������� �����������
	 * @param value ����� �������
	 */
	public void setScale(float value)
	{
		_scale = value;
	}
	/**
	 * ���������� ����� ������ ���� ��� ���������
	 * @return ������� ����� ������
	 */
	public Point getViewPosition()
	{
		return _viewPosition;
	}
	/**
	 * ������������� ����� ������ ���� ��� ���������
	 * @param point ����� ����� ������
	 */
	public void setViewPosition(int x, int y)
	{
		_viewPosition.x = x;
		_viewPosition.y = y;
		update();
	}
	@Override
	public void refresh()
	{
		update();
	}
	/**
	 * ���������  ���������� ����
	 * @param layer - ����� ���������� ����
	 */
	public void addMetaLayer(MetaLayer layer)
	{
		_metaLayers.add(layer);
	}
	/**
	 * ������� ���������� ����
	 * @param layer - ��������� ���������� ����
	 */
	public void removeMetaLayer(MetaLayer layer)
	{
		_metaLayers.remove(layer);
	}
	/**
	 * ���������� ������� ���������� ����
	 * @return
	 */
	public Iterable<MetaLayer> getLayers()
	{
		return (Iterable<MetaLayer>)_metaLayers;
	}
	public CanvasModel(CanvasImage image)
	{
		_image = image;
		_image.attach(this);
		_viewPosition = new Point(0,0);
		_canvasSize = new Dimension(0,0);
		_scale = 1;
	}
}
