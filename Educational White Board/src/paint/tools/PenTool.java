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
 */
public class PenTool extends Tool{
	
	private volatile boolean _paintingOn;	//���� ���������� ���������
	
	private ColorModel _color;
	private ImageBuffer _buffer; 	//����� �����������
	private CanvasModel _canvas;	//������ ������ ���������
	private clicker _curClicker;	//����� �������� �� �������� ������
	private Point _lastPoint = new Point(0,0);		//��������� ��������� ����� ��� ������ ���������
	private JPanel _drawingPanel;	//������ ���������
	private PaintingThread _drawingThread;			//�����, �������� �����
	private int _button;		//����� ������� ������ ����, ������� ������������ ���������
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
				if(_paintingOn)
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
					if(_button == 1) 
						graf.setColor(_color.getFirstColor());
					if(_button == 3) 
						graf.setColor(_color.getSecondColor());
					graf.drawLine(_lastPoint.x, _lastPoint.y, realx,realy);
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
			if(_button == 1) 
				graf.setColor(_color.getFirstColor());
			if(_button == 3) 
				graf.setColor(_color.getSecondColor());
			graf.drawLine(realx, realy, realx,realy);
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
	 * ���������� - �������� ��������� ���������� � ���� ��������� 
	 * @param canvas - ���� ��������� (����)
	 */
	public PenTool(CanvasModel canvas, ColorModel color, ImageBuffer buffer)
	{
		_canvas = canvas;
		_color = color;
		_buffer = buffer;
		_drawingPanel = canvas.getDrawingPane();
		_drawingPanel.setFocusable(true);
		_paintingOn = false;
		_drawingThread  = new PaintingThread();
		_drawingThread.setPriority(_drawingThread.getPriority() + 2);
		_drawingThread.start();
	}
	@Override
	public void activate() 
	{
		//��������� ��������� ����������
		_curClicker = new clicker();
		_drawingPanel.addMouseListener(_curClicker);
		_drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	}

	@Override
	public void deactivate()
	{
		//����������� ��������� ����
		_drawingPanel.removeMouseListener(_curClicker);
	}

}
