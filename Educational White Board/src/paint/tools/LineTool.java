package paint.tools;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import paint.model.*;
/**
 * 
 * @author User
 *
 */
public class LineTool extends Tool{

	private volatile boolean _paintingOn;			//���� ���������� ���������
	private volatile boolean _inControl;			//���� �������� ��� �������� ���������
	private ColorModel _color;						//������ �����
	private ImageBuffer _buffer; 					//����� �����������
	private CanvasModel _canvas;					//������ ������ ���������
	private JPanel _drawingPanel;					//������ ���������
	private motion _curMotion;						//������ ������������� �������� ���� �� ������� ���������
	private clicker _curClicker;					//������ ������������� ������� ������ ����
	private Point _lastPoint = new Point(0,0);		//����� ������ ���������
	private MetaLayer _layer;						//���������� ���� ��� ����������� ������� �����
	private int _button; 							//������ ����, ������������ ���������� ���������
	private boolean _shift = false;
	private KeyDispatcher _dispatcher = new KeyDispatcher();
	/**
	 * ����� ��� ����� ���� ������� ������� ������
	 * @author User
	 *
	 */
	public class KeyDispatcher implements KeyEventDispatcher
	{
		@Override
		public boolean dispatchKeyEvent(KeyEvent arg0) 
		{
	        if (arg0.getID() == KeyEvent.KEY_PRESSED) 
	        {     	
	        	if(arg0.getKeyCode() == KeyEvent.VK_SHIFT)
	        		_shift = true;
	        }
	        if (arg0.getID() == KeyEvent.KEY_RELEASED) 
	        {	
	        	if(arg0.getKeyCode() == KeyEvent.VK_SHIFT)
	        		_shift = false;
	        }
			return false;
		}
		
	}
	/**
	 * ������ ������ ����������� ��������� 
	 */
	public enum Modes
	{
		Fill, //������ ������� 
		Border, //������ �������
		FillBorder //� ������� � �������
	}
	/**
	 * ������ ����� �� ����� ����� � ������ �� �����������
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param image
	 */
	private void drawLine(int x1, int y1, int x2, int y2, BufferedImage image)
	{
		Graphics2D graf = (Graphics2D)image.getGraphics();
		
		//�������� ���� � ����������� �� ������� ������
		if(_button == 3)
			graf.setColor(_color.getSecondColor());
		if(_button == 1)
			graf.setColor(_color.getFirstColor());
		if(_shift)
		{
			//���� ����� shift - ������ ������ (�������������� ��� ������������ �����, � ����������� �� ��������� x)
			if(Math.abs(x2 - x1) > Math.abs(y2 - y1))
				graf.drawLine(x1, y1, x2, y1);
			else
				graf.drawLine(x1, y1, x1, y2);
		}
		else
		{
			graf.drawLine(x1, y1, x2, y2);
		}
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
				
				BufferedImage image = _layer.getImage();
				drawLine(_lastPoint.x, _lastPoint.y, point.x, point.y, image);
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
				//��������� ����������� � �� ������ ����� �� �������� �����������
				_paintingOn = false;
				//���������������� ����� ��������� ���������
				Point point = arg0.getPoint();
				point.setLocation((int)(point.x / _canvas.getScale()),(int)( point.y / _canvas.getScale()));
				
				BufferedImage image = _canvas.getImage().getImage();
				drawLine(_lastPoint.x, _lastPoint.y, point.x, point.y, image);
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
	public LineTool(CanvasModel canvas, ColorModel color, ImageBuffer buffer) 
	{
		_color = color;
		_canvas = canvas;
		_buffer = buffer;
		_drawingPanel = canvas.getDrawingPane();
		BufferedImage image = canvas.getImage().getImage();
		_layer = new MetaLayer(new Dimension(image.getWidth(),image.getHeight()));
		_drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		_paintingOn = false;
		_inControl = false;
	}
	@Override
	public void activate() 
	{
		//��������� ��������� 
		_curMotion = new motion();
		_curClicker = new clicker();
		_drawingPanel.addMouseListener(_curClicker);
		_drawingPanel.addMouseMotionListener(_curMotion);
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(_dispatcher);
		_inControl = true;
	}

	@Override
	public void deactivate() 
	{
		_inControl = false;
		_paintingOn = false;
		_drawingPanel.removeMouseListener(_curClicker);
		_drawingPanel.removeMouseMotionListener(_curMotion);
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.removeKeyEventDispatcher(_dispatcher);
	}
}

