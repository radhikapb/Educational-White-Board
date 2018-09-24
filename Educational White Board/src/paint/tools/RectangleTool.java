package paint.tools;


import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import paint.model.CanvasModel;
import paint.model.ColorModel;
import paint.model.ImageBuffer;
import paint.model.MetaLayer;
import paint.model.Tool;
/**
 * 
 * @author User
 *
 */
public class RectangleTool extends Tool {

	private volatile boolean _paintingOn;			//���� ���������� ���������
	private volatile boolean _inControl;			//���� �������� ��� �������� ���������
	private ColorModel _color;						//������ �����
	private ImageBuffer _buffer; 					//����� �����������
	private CanvasModel _canvas;					//������ ������ ���������
	private JPanel _drawingPanel;					//������ ���������
	private motion _curMotion;						//������ ������������� �������� ���� �� ������� ���������
	private clicker _curClicker;					//������ ������������� ������� ������ ����
	private Point _lastPoint = new Point(0,0);		//����� ������ ���������
	private MetaLayer _layer;						//���������� ���� ��� ����������� ������� ��������������
	private int _button; 							//������ ����, ������������ ���������� ���������
	private static Modes _mode = Modes.FillBorder;						//������� ����� ������ ����������� - ���������
	/**
	 * ������ ������ ����������� ��������� 
	 */
	public enum Modes
	{
		Fill, //������ ������� 
		Border, //������ �������
		FillBorder //� ������� � �������
	}
	private class  motion extends MouseMotionAdapter
	{
		@Override
		public void mouseDragged(MouseEvent arg0) 
		{
			if(_paintingOn && _inControl)
			{
				 _layer.Clear();
				//��������� � �������� � ����� �������� ��������� �������������
				Point point = arg0.getPoint();
				point.setLocation((int)(point.x / _canvas.getScale()),(int)( point.y / _canvas.getScale()));
				Graphics2D graf = (Graphics2D)_layer.getImage().getGraphics();
				if(_button == 3)
					graf.setColor(_color.getSecondColor());
				if(_button == 1)
					graf.setColor(_color.getFirstColor());
				int width = Math.abs(point.x - _lastPoint.x);
				int heigth = Math.abs(point.y - _lastPoint.y);
				//� ����������� �� ������� ��������� ����� � �������� ����� ���������
				//����� �� ������� ������������ ������������ drawRect
				if(point.x > _lastPoint.x)
					if(point.y > _lastPoint.y)
						//���� ������������� ��������� ������ ����, ����� �� �����
						graf.drawRect(_lastPoint.x, _lastPoint.y,width,heigth);	
					else
						//���� ������������� ��������� ����� �����, ����� �� �����
						graf.drawRect(_lastPoint.x,point.y,width,heigth);
				else
					if(point.y > _lastPoint.y)
						//���� ������������� ��������� ������ ����, ������ �� ����
						graf.drawRect(point.x,_lastPoint.y,width,heigth);	
					else
						//���� ������������� ��������� ����� �����, ������ �� ����
						graf.drawRect(point.x,point.y,width,heigth);
				_canvas.getImage().update();
			}
		}
	}
	class clicker extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent arg0) 
		{
			_button = arg0.getButton();
			if((_button == 1)||(_button == 3))
			{
				//��������� ���������� � �� �������� ��������� �����
				_paintingOn = true;
				_canvas.addMetaLayer(_layer);
				_lastPoint.setLocation((int)(arg0.getPoint().x / _canvas.getScale()), (int)(arg0.getPoint().y / _canvas.getScale()));
			}
		}
		@Override
		public void mouseReleased(MouseEvent arg0) 
		{
			if(_paintingOn && _inControl)
			{
				_canvas.removeMetaLayer(_layer);
				//��������� ����������� � �� ������ �������������
				_paintingOn = false;
				//���������������� ����� ��������� ���������
				Point point = arg0.getPoint();
				point.setLocation((int)(point.x / _canvas.getScale()),(int)( point.y / _canvas.getScale()));
				
				BufferedImage image = _canvas.getImage().getImage();
				java.awt.Graphics2D graf = (Graphics2D)image.getGraphics();
			
				int width = Math.abs(point.x - _lastPoint.x);
				int heigth = Math.abs(point.y - _lastPoint.y);
				
				if((_mode == Modes.FillBorder) || (_mode == Modes.Fill))
				{
					//���������� ��������� �������, ���� ��� ��������� ����� ������
					//�������� ���� � ����������� �� ������� ������
					if(_button == 1)
						graf.setColor(_color.getSecondColor());
					if(_button == 3)
						graf.setColor(_color.getFirstColor());
					//� ����������� �� ������� ��������� ����� � �������� ����� ���������
					if(point.x > _lastPoint.x)
						if(point.y > _lastPoint.y)
							//���� ������������� ��������� ������ ����, ����� �� �����
							graf.fillRect(_lastPoint.x, _lastPoint.y,width,heigth);	
						else
							//���� ������������� ��������� ����� �����, ����� �� �����
							graf.fillRect(_lastPoint.x,point.y,width,heigth);
					else
						if(point.y > _lastPoint.y)
							//���� ������������� ��������� ������ ����, ������ �� ����
							graf.fillRect(point.x,_lastPoint.y,width,heigth);	
						else
							//���� ������������� ��������� ����� �����, ������ �� ����
							graf.fillRect(point.x,point.y,width,heigth);
				}
				if((_mode == Modes.FillBorder) || (_mode == Modes.Border))
				{
					//���������� ��������� �������, ���� ��� ��������� ����� ������
					//�������� ���� � ����������� �� ������� ������
					if(_button == 3)
						graf.setColor(_color.getSecondColor());
					if(_button == 1)
						graf.setColor(_color.getFirstColor());
					//� ����������� �� ������� ��������� ����� � �������� ����� ���������
					if(point.x > _lastPoint.x)
						if(point.y > _lastPoint.y)
							//���� ������������� ��������� ������ ����, ����� �� �����
							graf.drawRect(_lastPoint.x, _lastPoint.y,width,heigth);	
						else
							//���� ������������� ��������� ����� �����, ����� �� �����
							graf.drawRect(_lastPoint.x,point.y,width,heigth);
					else
						if(point.y > _lastPoint.y)
							//���� ������������� ��������� ������ ����, ������ �� ����
							graf.drawRect(point.x,_lastPoint.y,width,heigth);	
						else
							//���� ������������� ��������� ����� �����, ������ �� ����
							graf.drawRect(point.x,point.y,width,heigth);
				}
				_buffer.turn();
				_canvas.getImage().update();
			}
		}
	}
	/**
	 * ���������� ��� ��������� ���������������
	 * @param canvas
	 * @param color
	 * @param buffer
	 */
	public RectangleTool(CanvasModel canvas, ColorModel color, ImageBuffer buffer) 
	{
		_color = color;
		_canvas = canvas;
		_buffer = buffer;
		_drawingPanel = canvas.getDrawingPane();
		BufferedImage image = canvas.getImage().getImage();
		_layer = new MetaLayer(new Dimension(image.getWidth(),image.getHeight()));
		_paintingOn = false;
		_inControl = false;
	}
	@Override
	public void activate() 
	{
		//��������� ��������� 
		_curMotion = new motion();
		_curClicker = new clicker();
		_drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		_drawingPanel.addMouseListener(_curClicker);
		_drawingPanel.addMouseMotionListener(_curMotion);
		_inControl = true;
	}

	@Override
	public void deactivate() 
	{
		_inControl = false;
		_paintingOn = false;
		_drawingPanel.removeMouseListener(_curClicker);
		_drawingPanel.removeMouseMotionListener(_curMotion);	
	}
	public void setMode(Modes value)
	{
		_mode = value;
		update();
	}
	public Modes getMode ()
	{
		return _mode;
	}
}
