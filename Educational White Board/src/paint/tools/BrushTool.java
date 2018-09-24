package paint.tools;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import paint.model.CanvasModel;
import paint.model.ColorModel;
import paint.model.ImageBuffer;
import paint.model.Tool;
/**
 * 
 * @author User
 *
 */
public class BrushTool extends Tool
{
	private static int _radius = 15;				//������ �����
	private volatile boolean _paintingOn;			//���� ���������� ���������
	private volatile boolean _inControl;			//���� �������� ��� �������� ���������
	private ColorModel _color;						//������ �����
	private ImageBuffer _buffer; 					//����� �����������
	private CanvasModel _canvas;					//������ ������ ���������
	private clicker _curClicker;					//����� �������� �� �������� ������
	private Point _lastPoint = new Point(0,0);		//��������� ��������� ����� ��� ������ ���������
	private JPanel _drawingPanel;					//������ ���������
	private PaintingThread _drawingThread;			//�����, �������� �����
	private int _button;							//����� ������� ������ ����, ������� ������������ ���������
	/**
	 * ����� - ����� ��������� ����������
	 * ������������ ��� ����������� ����������� ����� ��������� ��� ������� �������� �������� �����
	 * @author Vit
	 *
	 */
	class PaintingThread extends Thread	
	{
		/**
		 * ������������� �����
		 */
		public synchronized void halt()
		{
			try 	
			{
				this.wait();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		/**
		 * ������������ �����
		 */
		public synchronized void go()
		{
			this.notify();
		}
		
		@Override
		public void run() 
		{
			Point location = new Point();
			while(true)
			{	
				if(_inControl)
				{
					//������ ������� ��������� ���� �� ������
					location = java.awt.MouseInfo.getPointerInfo().getLocation();
					location.x -= _drawingPanel.getLocationOnScreen().x;
					location.y -= _drawingPanel.getLocationOnScreen().y;					
					int realx = (int) (location.x / _canvas.getScale());	//��������� �� ���������� � �������
					int realy = (int) (location.y / _canvas.getScale());
					if(_paintingOn)
					{
						//������ �� ���� ����������� �����
						BufferedImage image = _canvas.getImage().getImage();
						java.awt.Graphics2D graf = (Graphics2D)image.getGraphics();
						if(_button == 1) 
							graf.setColor(_color.getFirstColor());
						if(_button == 3) 
							graf.setColor(_color.getSecondColor());
						int rad = _radius ;
						double alfa = Math.atan((double)((double)(_lastPoint.y - realy) /(double)(_lastPoint.x - realx)));
						//��������� ����� ��������������, ������ �� ���� ���������� �����
						int[] xpoints = new int[4];
						int[] ypoints = new int[4];
						xpoints[0]  = (int) Math.round (realx + (rad) * Math.sin(alfa));
						ypoints[0] = (int) Math.round (realy - (rad) * Math.cos(alfa));
						xpoints[3] = (int) Math.round (realx - (rad) * Math.sin(alfa));
						ypoints[3] = (int) Math.round (realy + (rad) * Math.cos(alfa));
						xpoints[1] = (int) Math.round (_lastPoint.x + (rad) * Math.sin(alfa));
						ypoints[1] = (int) Math.round (_lastPoint.y - (rad) * Math.cos(alfa));
						xpoints[2] = (int) Math.round (_lastPoint.x - (rad) * Math.sin(alfa));
						ypoints[2] = (int) Math.round (_lastPoint.y + (rad) * Math.cos(alfa));
						
						if(_paintingOn)
						{
							graf.drawOval(realx - _radius, realy - _radius, _radius * 2 ,_radius * 2);
							graf.fillOval(realx - _radius, realy - _radius, _radius * 2, _radius * 2);
							graf.drawPolygon( xpoints, ypoints, 4);
							graf.fillPolygon( xpoints, ypoints, 4);
						}
						//������������� ��� ���������� - ��� ���������
						_lastPoint.x = realx;
						_lastPoint.y = realy;
					}
					_canvas.getImage().update();
				}
				else
				{
					 halt();
				}
			}
		}
	};
	class clicker extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent arg0) 
		{
			_button = arg0.getButton();
			if((_button != 1) && (_button != 3))
				return;	//���� ������ �������� ������ ��� ��������� - �������� �� �����
			
			//����� ������ ������
			Point point = arg0.getPoint(); //����� ����� ������ �������� � ����������� ���� ���������
			int realx = (int) (point.x / _canvas.getScale());	//��������� �� ���������� � �������
			int realy = (int) (point.y / _canvas.getScale());
			//������ �� ���� ����������� �����
			BufferedImage image = _canvas.getImage().getImage();
			java.awt.Graphics2D graf = (Graphics2D)image.getGraphics();
			if(_button == 1) 
				graf.setColor(_color.getFirstColor());
			if(_button == 3) 
				graf.setColor(_color.getSecondColor());
			graf.drawOval(realx - _radius, realy - _radius, _radius * 2 ,_radius * 2);
			graf.fillOval(realx - _radius, realy - _radius, _radius * 2  ,_radius * 2);
			_canvas.getImage().update();
			//������������� ��� ���������� - ��� ���������
			_lastPoint.x = realx;
			_lastPoint.y = realy;
			//������� ����� ����������� ���������
			_paintingOn = true;
		}
		@Override
		public void mouseReleased(MouseEvent arg0) 
		{
			
			//Stop drawing
			if(_paintingOn == true)
			{
				_paintingOn = false;
				_buffer.turn();	
			}	
		}
	}
	/**
	 * ���������� ��� ��������� ������
	 * @param canvas
	 * @param color
	 * @param buffer
	 */
	public BrushTool(CanvasModel canvas, ColorModel color, ImageBuffer buffer) 
	{
		_color = color;
		_canvas = canvas;
		_buffer = buffer;
		_drawingPanel = canvas.getDrawingPane();
		_drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		_paintingOn = false;		
		_drawingThread  = new PaintingThread();
		_drawingThread.setPriority(_drawingThread.getPriority() + 2);
		_inControl = false;
		_drawingThread.start();
	}

	@Override
	public void activate() {	
		//��������� ��������� 
		_curClicker = new clicker();
		_drawingPanel.addMouseListener(_curClicker);
		_inControl = true;
		_drawingThread.go();
	}

	@Override
	public void deactivate() {
		//����������� ��������� 
		_paintingOn = false;
		_inControl = false;
		_drawingPanel.removeMouseListener(_curClicker);
	}
	/**
	 * ���������� ������ �����
	 * @return
	 */
	public int getRadius()
	{
		return _radius;
	}
	/**
	 * ������������� ������ �����
	 * @param value
	 */
	public void setRadius(int value)
	{
		_radius = value;
		update();
	}
}
