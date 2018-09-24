package paint.tools;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.swing.JPanel;
import paint.model.*;
/**
 * ����� ���������
 */
public class SelectorTool extends Tool {
	
	private boolean _inControl;				//���� �������� ��� �������� ���������
	private boolean _selectionOn;			//���� ������������ ������
	private boolean _selected;				//����������� ������� � ��������������
	private boolean _dragged;				//����������� ������������ ������
	private boolean _dragPossible;			//�������� �� ���� �����
	
	private ImageBuffer _buffer; 					//����� �����������
	private CanvasModel _canvas;					//������ ������ ���������
	private JPanel _drawingPanel;					//������ ���������
	private CanvasImage _image;						//�������� �����������
	private motion _curMotion;						//������ ������������� �������� ���� �� ������� ���������
	private clicker _curClicker;					//������ ������������� ������� ������ ����
	private Point _lastPoint = new Point(0,0);		//����� ������ ���������
	private MetaLayer _layer;						//���������� ���� ��� ����������� ������� ��������������
	private BufferedImage _selectedImagePart;		//�����������, ���������� ����������
	private Point _imagePosition = new Point(0,0);	//������� �����������, ���������� ����������
	private Point _startPoint = new Point(0,0);		//����� ������ �������������� ���������� �����������
	/**
	 * �������� ��� �����������
	 */
	public void selectAll()
	{
		_selectImageFromCanvas(0, 0, _image.getImage().getWidth() - 1,  _image.getImage().getHeight() - 1);
	}
	/**
	 * ������� ����������� �� ���������
	 */
	public void deleteSelector()
	{
		if(_selected)
		{
			_selected = false;
			_dragPossible = false;
			_drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			_layer.Clear();
			_image.update();
		}
	}
	/**
	 * �������� ����������� � ��������
	 * @param image
	 */
	public void copyToSelector(Image image)
	{
		_selected = true;
		_dragged = false;
		_selectedImagePart = new BufferedImage(image.getWidth(null),image.getHeight(null),_image.getImage().getType());
		_selectedImagePart.getGraphics().drawImage(image,0, 0, null);
		_imagePosition.x = 0;
		_imagePosition.y = 0;
		_redrawSelected();
	}
	/**
	 * �������� ����������� �� ���������
	 * @param image
	 */
	public BufferedImage cutFromSelector()
	{
		if(_selected)
		{
			_selected = false;
			_dragPossible = false;
			_drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			_layer.Clear();
			_image.update();
			return _selectedImagePart;
		}
		else
			return null;
	}
	/**
	 * ���������� ����� �����������
	 * @return
	 */
	public BufferedImage getSelectedImage()
	{
		if(_selected)
			return _selectedImagePart;
		else
			return null;
	}
	/**
	 * ������������� ��������� ���������� ����������� � ��� ������� �� �������� �����������
	 * @param image
	 * @param x
	 * @param y
	 */
	public void setSelectedImage(BufferedImage image, int x, int y)
	{
		_selected = true;
		_dragged = false;
		_selectedImagePart = image;
		_imagePosition.x = x;
		_imagePosition.y = y;
		_redrawSelected();
	}
	/**
	 * �������� ����������� ���������� ����������� ����� �� �������� �����������
	 */
	private void _dropImage()
	{
		_image.getImage().getGraphics().drawImage(_selectedImagePart,_imagePosition.x,_imagePosition.y,null);
		_selected = false;
		_dragPossible = false;
		_drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		_buffer.turn();
		_layer.Clear();
		_image.update();
	}
	/**
	 * ���������� ����� � ����� ��������� �����������
	 * @param point
	 */
	private void _boundToImage(Point point)
	{
		if(point.x < 0)
			point.x = 0;
		if(point.y < 0)
			point.y = 0;
		if(point.x > _image.getImage().getWidth() - 1)
			point.x = _image.getImage().getWidth() - 1;
		if(point.y > _image.getImage().getHeight() - 1)
			point.y = _image.getImage().getHeight() - 1;
	}
	/**
	 * ���������� ��������� ��������� �� ���������� ����
	 */
	private void _redrawSelected()
	{
		_layer.Clear();
		Graphics2D meta = (Graphics2D)_layer.getImage().getGraphics();
		meta.drawImage(_selectedImagePart, _imagePosition.x, _imagePosition.y, null);
		int period = 1;
		if((_canvas.getScale() == 2) || (_canvas.getScale() == 4))
			period = 3;
		if(_canvas.getScale() == 1)
			period = 5;
		drawRectangle(meta, _imagePosition.x, _imagePosition.y, _selectedImagePart.getWidth(), _selectedImagePart.getHeight(),period);
		_image.update();
	}
	/**
	 * ��������� ������ ���������� ����������� �� ��������� �������
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	private void _selectImageFromCanvas(int x, int y, int width, int height)
	{
		BufferedImage source = _canvas.getImage().getImage();
		WritableRaster raster = source.copyData(null);
		//���������� �������, ��������� � ������������� �����������, ��� �������� �����
        BufferedImage copy = new BufferedImage(source.getColorModel(),
        		raster,
        		source.isAlphaPremultiplied(),
        		null);
		BufferedImage imagePart = copy.getSubimage(x, y, width, height);
		Graphics2D graph = (Graphics2D)_canvas.getImage().getImage().getGraphics();
		graph.setColor(Color.WHITE);
		graph.fillRect(x, y, width, height);
		setSelectedImage(imagePart,x,y);
	}
	/**
	 * ���������� ������������� ���������
	 * @param graf
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	private void drawRectangle(Graphics2D graf, int x, int y, int width, int height, int period)
	{	
		graf.setColor(Color.WHITE);
		graf.drawRect(x,y,width,height);
		graf.setColor(Color.BLUE);
		int xquantity = (int) (width / (period * 2));
		int yquantity = (int) (height / (period * 2));
		for(int i = 0; i < xquantity; i++)
		{
			graf.drawLine(x + i*period*2, y, x + i*period*2+ period - 1, y);
			graf.drawLine(x + i*period*2, y + height, x + i*period*2 + period - 1, y + height);
		}
		graf.drawLine(x + width - period, y, x + width, y);
		graf.drawLine(x + width - period, y + height, x + width, y + height);
		for(int i = 0; i < yquantity; i++)
		{
			graf.drawLine(x, y + i*period*2, x, y + i*period*2 + period - 1);
			graf.drawLine(x + width, y + i*period*2, x + width, y + i*period*2 - 1 + period);
		}
		graf.drawLine(x, y + height - period, x, y + height);
		graf.drawLine(x + width, y + height - period, x + width, y + height );
		
	}
	private class  motion extends MouseMotionAdapter
	{
		@Override
		public void mouseMoved(MouseEvent arg0)
		{
			if(_selected && !_dragged)
			{
				//���� ���� ��������� ��� ��������� ��������, ������ �� ����� ����������
				Point point = arg0.getPoint();
				point.setLocation((int)(point.x / _canvas.getScale()),(int)( point.y / _canvas.getScale()));
				if((point.x > _imagePosition.x) &&
					(point.y > _imagePosition.y) &&
					(point.y < _selectedImagePart.getHeight() + _imagePosition.y) &&
					(point.x < _selectedImagePart.getWidth() + _imagePosition.x))
				{
					 //���������� �����������
					_drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
					_dragPossible = true;
				}
				else
				{
					//����� - ������� �����������
					_drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
					_dragPossible = false;
				}
			}
		}
		@Override
		public void mouseDragged(MouseEvent arg0) 
		{
			if(_selectionOn && _inControl)
			{
				 _layer.Clear();
				//��������� � �������� � ����� �������� ��������� �������������
				Point point = arg0.getPoint();
				point.setLocation((int)(point.x / _canvas.getScale()),(int)( point.y / _canvas.getScale()));
				_boundToImage(point);
				Graphics2D graf = (Graphics2D)_layer.getImage().getGraphics();
				if(arg0.getButton() == 1)
					graf.setColor(Color.BLACK);
				int width = Math.abs(point.x - _lastPoint.x);
				int height = Math.abs(point.y - _lastPoint.y);
				int period = 1;
				if((_canvas.getScale() == 2) || (_canvas.getScale() == 4))
					period = 3;
				if(_canvas.getScale() == 1)
					period = 5;
				//� ����������� �� ������� ��������� ����� � �������� ����� ���������
				//����� �� ������� ������������ ������������ drawRect
				if(point.x > _lastPoint.x)
					if(point.y > _lastPoint.y)
						//���� ������������� ��������� ������ ����, ����� �� �����
						drawRectangle(graf, _lastPoint.x, _lastPoint.y,width,height, period);
					else
						//���� ������������� ��������� ����� �����, ����� �� �����
						drawRectangle(graf, _lastPoint.x,point.y,width,height, period);
				else
					if(point.y > _lastPoint.y)
						//���� ������������� ��������� ������ ����, ������ �� ����
						drawRectangle(graf,point.x,_lastPoint.y,width,height, period);
					else
						//���� ������������� ��������� ����� �����, ������ �� ����
						drawRectangle(graf,point.x,point.y,width,height, period);
				_canvas.getImage().update();
			}
			if(_dragged)
			{
				Point point = arg0.getPoint();
				point.setLocation((int)(point.x / _canvas.getScale()),(int)( point.y / _canvas.getScale()));
				_imagePosition.x = point.x - _startPoint.x;
				_imagePosition.y = point.y - _startPoint.y;
				_redrawSelected();
			}
		}
	}
	class clicker extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent arg0) 
		{		
			if((arg0.getButton() == 1))
			{
				if(_dragPossible)
				{
					//���� ������ ����� ������ � �������� �������������� - �������������� ��������������
					Point point = arg0.getPoint();
					_startPoint.x = (int)(point.x / _canvas.getScale()) - _imagePosition.x;
					_startPoint.y = (int)(point.y / _canvas.getScale()) - _imagePosition.y;
					_dragged = true;
					return;
				}
				else
				{
					if(_selected)
						_dropImage();
					_selectionOn = true;
					_lastPoint.setLocation(arg0.getPoint().x / _canvas.getScale(), arg0.getPoint().y/ _canvas.getScale());
					return;
				}
			}	
			if((arg0.getButton() == 3) && (_selected))
			{
				_dropImage();
				return;
			}		
		}
		@Override
		public void mouseReleased(MouseEvent arg0) 
		{
			if(_selectionOn && _inControl)
			{		
				Point point = arg0.getPoint();
				point.setLocation((int)(point.x / _canvas.getScale()),(int)( point.y / _canvas.getScale()));
				_boundToImage(point);
				int width = Math.abs(point.x - _lastPoint.x);
				int height = Math.abs(point.y - _lastPoint.y);
				if((width != 0) && (height != 0))
				{			
					if(point.x > _lastPoint.x)
						if(point.y > _lastPoint.y)
							//���� ������������� ��������� ������ ����, ����� �� �����
							_selectImageFromCanvas(_lastPoint.x, _lastPoint.y,width,height);
						else
							//���� ������������� ��������� ����� �����, ����� �� �����
							_selectImageFromCanvas(_lastPoint.x,point.y, width,height);
							
					else
						if(point.y > _lastPoint.y)
							//���� ������������� ��������� ������ ����, ������ �� ����
							_selectImageFromCanvas(point.x,_lastPoint.y, width,height);
						else
							//���� ������������� ��������� ����� �����, ������ �� ����
							_selectImageFromCanvas(point.x,point.y, width,height);
					_selectionOn = false;
				}
			}
			if(_dragged)
			{
				_dragged = false;
			}
		}
	}
	/**
	 * ���������� ��� ��������� ���������������
	 * @param canvas
	 * @param color
	 * @param buffer
	 */
	public SelectorTool(CanvasModel canvas, ColorModel color, ImageBuffer buffer) 
	{
		_canvas = canvas;
		_buffer = buffer;
		_drawingPanel = canvas.getDrawingPane();
		_image = canvas.getImage();
		BufferedImage image = canvas.getImage().getImage();
		_layer = new MetaLayer(new Dimension(image.getWidth(),image.getHeight()));	
		_drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		_selectionOn = false;
		_inControl = false;
	}
	@Override
	public void activate() 
	{
		//��������� ��������� 
		_curMotion = new motion();
		_curClicker = new clicker();
		_canvas.addMetaLayer(_layer);
		_drawingPanel.addMouseListener(_curClicker);
		_drawingPanel.addMouseMotionListener(_curMotion);
		_inControl = true;
	}

	@Override
	public void deactivate() 
	{
		if(_selected)
			_dropImage();
		_inControl = false;
		_selectionOn = false;
		_canvas.removeMetaLayer(_layer);
		_drawingPanel.removeMouseListener(_curClicker);
		_drawingPanel.removeMouseMotionListener(_curMotion);	
	}
}