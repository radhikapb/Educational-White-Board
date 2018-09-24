package paint.tools;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;
import paint.model.CanvasImage;
import paint.model.CanvasModel;
import paint.model.ColorModel;
import paint.model.ImageBuffer;

/**
 * 
 * @author User
 *
 */
public class SprayTool extends paint.model.Tool {
	//������ ������ � �������� �������������� ������������, ����� ��� ����������� ��� �������� �������
	private static int _radius = 50;				//������ �����
	private static int _speed = 5;					//�������� �������������� ����� (����� ������ ��������� �� �������)
	private volatile boolean _paintingOn;			//���� ���������� ���������
	
	private static final int _speedMultiplier = 2;	//��������� ��������
	private CanvasModel _canvas;					//������� ���������
	private ImageBuffer _buffer; 					//����� �����������
	private ColorModel _color;						//������ �����
	private motion _curMotion;						//����� �������� �� ��������� ����
	private clicker _curClicker;					//����� �������� �� �������� ������
	private Random _randomizer = new Random();		//��������� ��������������� �����
	private Point _lastPoint = new Point(0,0);		//��������� ��������� ����� ��� ������ ���������
	private JPanel _drawingPanel;					//������ ���������
	private Timer _runnerThread;					//������, �������������� ���������
	private int _button;							//����� ������� ������ ��� ��������� (���������� ����)
	//����� ���������  ������
	class runner extends TimerTask
	{
		@Override
		public void run() 
		{
			if(_paintingOn)
			{
				CanvasImage image = _canvas.getImage();
				BufferedImage painting = image.getImage();

					
				//�������� ������������� ����� _speed * _speedMultiplier ���
				for(int i = 0; i < (_speed * _speedMultiplier); i++)
				{
					int x = _randomizer.nextInt(_radius * 2);
					//���������� ������ ����������� ��������� ������� y
					int xrad = Math.abs(x - _radius);		
					double acos = Math.acos((double)((double)xrad/(double)_radius));
					double sin = Math.sin(acos);
					int  ylimit = (int)Math.abs(_radius * sin);
					int y = 0;
					if(ylimit > 0)
						y = _randomizer.nextInt((int)Math.round(ylimit) * 2);
					
					//������ ��������� �����
					int newx = _lastPoint.x - _radius + x;
					int newy = _lastPoint.y - ylimit  + y;
					if((newx > 0)  //����������� ���������, ����� ����� �� ����� �� ������� �����������
							&& (newx < painting.getWidth() - 1) 
							&& (newy > 0) 
							&& (newy < painting.getHeight() - 1))
					{
						if(_button == 1) 	
							painting.setRGB(newx,newy,_color.getFirstColor().getRGB());
						if(_button == 3) 
							painting.setRGB(newx,newy,_color.getSecondColor().getRGB());
						image.update();
					}
				}
			}
		}
	}
	//����� �������� ����
	class  motion extends MouseMotionAdapter
	{
		@Override
		public void mouseDragged(MouseEvent arg0) 
		{
			if(_paintingOn)
			{
				int realx =  (int) (arg0.getPoint().x /  _canvas.getScale());
				int realy =  (int) (arg0.getPoint().y /  _canvas.getScale());
				_lastPoint.setLocation(realx, realy);
			}
		}
	}
	//����� ������� ����
	class clicker extends MouseAdapter
	{
		@Override
		public void mouseEntered(MouseEvent arg0) 
		{
			if(_paintingOn)
			{
				int realx =  (int) (arg0.getPoint().x /  _canvas.getScale());
				int realy =  (int) (arg0.getPoint().y /  _canvas.getScale());
				_lastPoint.setLocation(realx, realy);
			}
		}
		@Override
		public void mousePressed(MouseEvent arg0) 
		{
			_button = arg0.getButton();
			if((_button != 1) && (_button != 3))
				return;	//���� ������ �������� ������ ��� ��������� - �������� �� �����
			//���������� ������ ���������
			_paintingOn = true;
			int realx =  (int) (arg0.getPoint().x /  _canvas.getScale());
			int realy =  (int) (arg0.getPoint().y /  _canvas.getScale());
			_lastPoint.setLocation(realx, realy);
			_runnerThread = new Timer();
			_runnerThread.schedule(new runner(), 0, 10);
		}

		@Override
		public void mouseReleased(MouseEvent arg0) 
		{
			//Stop drawing
			if(_paintingOn)
			{
				_paintingOn = false;
				_buffer.turn();
				
			}
			_runnerThread.cancel();
		}
	}
	/**
	 * ����������� ����� ��������� ������ �������, ����� � ������ �����������
	 * @param radius
	 */
	public SprayTool(CanvasModel canvas, ColorModel color, ImageBuffer buffer) 
	{
		_canvas = canvas;
		_buffer = buffer;
		_color = color;
		_drawingPanel = canvas.getDrawingPane();
		_paintingOn = false;
	}
	

	@Override
	/**
	 * ���������� ����� �� ���� ���������� �������� ���������
	 */
	public void activate() {
		//��������� ��������� 
		_curMotion = new motion();
		_curClicker = new clicker();
		_drawingPanel.addMouseMotionListener(_curMotion);
		_drawingPanel.addMouseListener(_curClicker);
		_drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	}

	@Override
	public void deactivate()
	{
		//����������� ��������� 
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
	/**
	 * ���������� ������������� �����
	 * @return
	 */
	public int getSpeed()
	{
		return _speed;
	}
	/**
	 * ������������� ������������� ����� �����
	 * @param value
	 */
	public void setSpeed(int value)
	{
		_speed = value;
		update();
	}
}
