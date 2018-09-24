package paint.model;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import paint.view.CanvasView.InnerPanel;
/**
 * ����� �������������� ��� ����������� ������� ��������� �������� ������
 * 
 * @author Vit
 *
 */
public class CanvasResizer extends Model
{
	public static final int _resizableArea = 5; 
	
	private JPanel _resizable;				//������ ���������, ������ ������� ������������ ����� ������
	private InnerPanel _surrounding;		//������, ���������� ������ ���������
	private CanvasModel _canvas;			//������ �������
	private ImageBuffer _buffer;			//����� �����������
	private MyMouseListener mouseListener = new MyMouseListener();		//��������� ��������� ���� �� ������ _surrounding
	private MyMouseMotionListener mouseMotionListener = new MyMouseMotionListener(); //��������� ������� ���� �� ������ _surrounding
	private int _area; //���� ������������ ����: 0 - ��� ���� ��������� �������, 1 - ���� ��������� X � Y, 2 - ���� ��������� X, 3 - ���� ��������� Y
	private boolean _dragged = false; //true - ������ ����������
	
	private int xoffset = 0;	//�������� ������� �� �������� �������� ������� ��������� � ������ ��������� �������� ���� �������
	private int yoffset = 0;
	private CanvasImage _image;
	int newx  =  0;	//����� �������� �������� �����������
	int newy = 0;
	/**
	 * ����� - ��������� ��������� ����
	 * @author Vit
	 *
	 */
	private class MyMouseListener extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent arg0) 
		{
			if(_area != 0)
			{
				//������� ���� � ����� �� ��������� ��������� ������� = ������ ��������� �������
				//��������� ��������
				xoffset = arg0.getX() - _resizable.getWidth();
				yoffset = arg0.getY() - _resizable.getHeight();
				//���������� ����
				_dragged = true;
			}
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {	
			if(_dragged)
			{
				//���������� ���� � �������� ��������� ������� = ��������� ��������� �������
				
				//��������� ���������� ����, ������������ � ������� ������� ���������
				//��� � �����, �������� ������� ������ �����������
				newx = (int)((arg0.getX() - xoffset) / _canvas.getScale());
				newy = (int)((arg0.getY() - yoffset) / _canvas.getScale());	
				//���� ������� < 0, �� ������������ �� ���� (�� ����� ���� ����������� � �������������� ���������)
				if(newx < 0)
					newx = 0;
				if(newy < 0)
					newy = 0;	
				switch(_area)
				{
					case 1:	//���� ���������� ��� ����������  - ������������� ��� �������
						_canvas.getImage().resize(newx ,newy);
						break;
					case 2: //���� �������� ���������� y - ������ ������ y, ��� ������ ���������� ����� ������������ ������ ������� ���������
						_canvas.getImage().resize(_image.getImage().getWidth() , newy);
						break;
					case 3: //���� �������� ���������� x - ������ ������ x
						_canvas.getImage().resize(newx,_image.getImage().getHeight());
						break;
				}	
				//��������� ����� ����������� � ������, ��������� ��������� ������� � ��������� �����������
				_buffer.turn();
				
				_dragged = false;
				
			}
			_area = 0;
			_surrounding.setCursor(Cursor.getDefaultCursor());
			_surrounding.setMode(_area);
			_surrounding.repaint();
		}
		@Override
		public void mouseExited(MouseEvent arg0) 
		{
			if(!_dragged)
			{
				_area = 0;
				_surrounding.setCursor(Cursor.getDefaultCursor());
				_surrounding.setMode(_area);
				_surrounding.repaint();
			}
		}
		
	}
	/**
	 * ����� - ��������� �������� ����
	 * @author Vit
	 *
	 */
	public class MyMouseMotionListener implements MouseMotionListener
	{
		int xdif;	//������� ������ ���� � ������� ���������� ������
		int ydif;
		
		@Override
		public void mouseDragged(MouseEvent arg0) 
		{
			if(_dragged)
			{
				//���� ���������� ������ - ���������� ���������� ����� ������ ������ ������� � ������ ������� 
				
				//��������� ���������� ���� � ��������� �� ��� �������
				newx = (int)((arg0.getX() - xoffset) / _canvas.getScale());
				newy =(int)((arg0.getY() - yoffset) / _canvas.getScale());
				//���� ������� < 0, �� ������������ �� ���� (�� ����� ���� ����������� � �������������� ���������)
				if(newx < 0)
					newx = 0;
				if(newy < 0)
					newy = 0;
				switch(_area)
				{
					case 1://���� ���������� ��� ���������� - ������������� ��� �������
						_surrounding.setCoordinates((int)(newx * _canvas.getScale()) ,(int)(newy * _canvas.getScale()));
						_resizable.setSize((int)(newx * _canvas.getScale()) ,(int)(newy * _canvas.getScale()));
						break;
					case 2: //���� �������� ���������� y - ������ ������ y, ��� ������ ���������� ����� ������������ ������ ������� ���������
						_surrounding.setCoordinates(_resizable.getWidth() , (int)(newy * _canvas.getScale()));
						_resizable.setSize(_resizable.getWidth(), (int)(newy * _canvas.getScale()));
						break;
					case 3://���� �������� ���������� x - ������ ������ x, ��� ������ ���������� ����� ������������ ������ ������� ���������
						_surrounding.setCoordinates((int)(newx * _canvas.getScale()),_resizable.getHeight());
						_resizable.setSize((int)(newx * _canvas.getScale()), _resizable.getHeight());
						break;
				}	
				//��������� � ��������������
				update();
				_surrounding.setMode(_area);
				_surrounding.repaint();
				_resizable.repaint();
			}
		}
		@Override
		public void mouseMoved(MouseEvent arg0) 
		{	
			
			if(!_dragged)
			{
				//�������� ���� ��� ������������� ������ ������� ������� ��������� ������ �������� ��������� ������ ��������� ������� 
				//� ��������������� ��� ������ ����

				xdif= arg0.getX() - _resizable.getWidth();
				ydif = arg0.getY() - _resizable.getHeight();
				//���������, �������� �� ���� � ���� �� ��� ������� ������ ���������, � ������� ����� ����������� ��������� �������
				if((xdif > 0) && (xdif < _resizableArea) && (ydif < _resizableArea) && (ydif > 0)) 
				{
					if(_area != 1)
					{
						//���� ��������� ����� ���������, � X � �
						_area = 1;
						_surrounding.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
						_surrounding.setCoordinates(_resizable.getWidth() ,_resizable.getHeight());
						_surrounding.setMode(_area);
						_surrounding.repaint();
					}
					return;		
				}
				if((ydif < _resizableArea)&& (ydif > 0) && (xdif < 0))
				{
					if(_area != 2)
					{
						//���� ��������� X
						_area = 2;
						_surrounding.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
						_surrounding.setCoordinates(_resizable.getWidth() ,_resizable.getHeight());
						_surrounding.setMode(_area);
						_surrounding.repaint();
					}
					return;
				}
				if((xdif < _resizableArea)&&(xdif > 0) && (ydif < 0)) 
				{
					if(_area != 3)
					{
						//���� ��������� Y
						_area = 3;
						_surrounding.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
						_surrounding.setCoordinates(_resizable.getWidth() ,_resizable.getHeight());
						_surrounding.setMode(_area);
						_surrounding.repaint();

					}
					return;
				}
				//����, ��� ��������� ������� ����������
				_area = 0;
				_surrounding.setCursor(Cursor.getDefaultCursor());
				_surrounding.setCoordinates(_resizable.getWidth() ,_resizable.getHeight());
				_surrounding.setMode(_area);
				_surrounding.repaint();
				return;
			}
		}
	}
	/**
	 * �����������
	 * @param canvas - ������ �������
	 * @param buffer - ����� �����������
	 */
	public CanvasResizer(CanvasModel canvas, ImageBuffer buffer)
	{
		_canvas = canvas;
		_buffer = buffer;
		_image = canvas.getImage();
	}
	/**
	 * ������������� ������, ����������� ��� ������� ��������� �������, ����� ��������� ������ �����������
	 * @param resizable
	 * @param surrounding
	 */
	public void setPanels(JPanel resizable, InnerPanel surrounding)
	{
		if(_surrounding != null)
		{
			_surrounding.removeMouseListener(mouseListener);
			_surrounding.removeMouseMotionListener(mouseMotionListener );
		}
		_resizable = resizable;
		_surrounding = surrounding;
		_surrounding.addMouseListener(mouseListener );
		_surrounding.addMouseMotionListener(mouseMotionListener);
		
	}
	/**
	 * ��� ��������� �������� ���� ���������� ������� ���������� �������� ������
	 * ��� ��������� ��������� ���� ���������� ������ �������� ������
	 * @return
	 */
	public int getWidth()
	{
		if((_area == 1) || (_area == 3))
			return newx;
		else
			return _image.getImage().getWidth();
			
	}
	/**
	 * ��� ��������� �������� ���� ���������� ������� ���������� �������� ������
	 * ��� ��������� ��������� ���� ���������� ������ �������� ������
	 * @return
	 */
	public int getHeight()
	{
		if((_area == 1) || (_area == 2))
			return newy;
		else
			return _image.getImage().getHeight();
			
	}
}
