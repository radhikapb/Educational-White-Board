package paint.tools;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import paint.model.CanvasModel;
import paint.model.ImageBuffer;
import paint.model.Tool;
/**
 * 
 * @author User
 *
 */
public class EraserTool extends Tool
{
	private static int _radius = 15;				//������ �����

	private ImageBuffer _buffer; 					//����� �����������
	private CanvasModel _canvas;					//������ ������ ���������
	private boolean _paintingOn;					//���� ���������� ���������
	private boolean _inControl;						//���� �������� ��� �������� ���������
	private clicker _curClicker;					//����� �������� �� �������� ������
	private Point _lastPoint = new Point(0,0);		//��������� ��������� ����� ��� ������ ���������
	private JPanel _drawingPanel;					//������ ���������
	private PaintingThread _drawingThread;			//�����, �������� ������
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
			try 	{
				this.wait();
			} catch (InterruptedException e) {
				
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
				//���� �������� ��������� (������������ ����� � ����� ���� �� ���� - 
				//������ ����� �� ���������� ����� � �������
				if(_paintingOn && _inControl)
				{
					//������ ������� ��������� ���� �� ������
					location = java.awt.MouseInfo.getPointerInfo().getLocation();
					location.x -= _drawingPanel.getLocationOnScreen().x;
					location.y -= _drawingPanel.getLocationOnScreen().y;					
					int realx = (int) (location.x / _canvas.getScale());	//��������� �� ���������� � �������
					int realy = (int) (location.y / _canvas.getScale());
					
					//������ �� ���� ����������� �����
					BufferedImage image = _canvas.getImage().getImage();
					java.awt.Graphics2D graf = (Graphics2D)image.getGraphics();
					graf.setColor(Color.white);
					double alfa = Math.atan((double)((double)(_lastPoint.y - realy) /(double)(_lastPoint.x - realx)));
					
					//��������� ����� ��������������, ������ �� ���� ���������� �����
					int[] xpoints = new int[4];
					int[] ypoints = new int[4];
					xpoints[0]  = (int) Math.round (realx + _radius * Math.sin(alfa));
					ypoints[0] = (int) Math.round (realy - _radius * Math.cos(alfa));
					xpoints[3] = (int) Math.round (realx - _radius * Math.sin(alfa));
					ypoints[3] = (int) Math.round (realy + _radius * Math.cos(alfa));
					xpoints[1] = (int) Math.round (_lastPoint.x + _radius * Math.sin(alfa));
					ypoints[1] = (int) Math.round (_lastPoint.y - _radius * Math.cos(alfa));
					xpoints[2] = (int) Math.round (_lastPoint.x - _radius * Math.sin(alfa));
					ypoints[2] = (int) Math.round (_lastPoint.y + _radius* Math.cos(alfa));
					
					graf.drawOval(realx - _radius, realy - _radius, _radius * 2 ,_radius * 2);
					graf.fillOval(realx - _radius, realy - _radius, _radius * 2, _radius * 2);
					graf.drawPolygon( xpoints, ypoints, 4);
					graf.fillPolygon( xpoints, ypoints, 4);
					_canvas.getImage().update();
					//������������� ��� ���������� - ��� ���������
					_lastPoint.x = realx;
					_lastPoint.y = realy;
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
			
			//����� ������ ����������
			Point point = arg0.getPoint(); //����� ����� ������ �������� � ����������� ���� ���������
			int realx = (int) (point.x / _canvas.getScale());	//��������� �� ���������� � �������
			int realy = (int) (point.y / _canvas.getScale());
			//������ �� ���� ����������� �����
			BufferedImage image = _canvas.getImage().getImage();
			java.awt.Graphics2D graf = (Graphics2D)image.getGraphics();
			graf.setColor(Color.white);
			graf.drawOval(realx - _radius, realy - _radius, _radius * 2 ,_radius * 2);
			graf.fillOval(realx - _radius, realy - _radius, _radius * 2  ,_radius * 2);
			_canvas.getImage().update();
			//������������� ��� ���������� - ��� ���������
			_lastPoint.x = realx;
			_lastPoint.y = realy;
			//������� ����� ����������� ���������
			_paintingOn = true;
			_drawingThread.go();
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
	 * ����������� �������
	 * @param canvas ������� ���������
	 * @param buffer ����� �����������
	 */
	public EraserTool(CanvasModel canvas, ImageBuffer buffer) 
	{
		_canvas = canvas;
		_buffer = buffer;
		_drawingPanel = canvas.getDrawingPane();
		_paintingOn = false;
		_drawingThread  = new PaintingThread();
		_drawingThread.setPriority(_drawingThread.getPriority() + 2);
		_drawingThread.start();
	}

	@Override
	public void activate() {	
		//��������� ��������� 
		_inControl = true;
		_curClicker = new clicker();
		_drawingPanel.addMouseListener(_curClicker);
		_drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	}

	@Override
	public void deactivate() {
		//����������� ��������� 
		_inControl = false;
		_paintingOn = false;
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
