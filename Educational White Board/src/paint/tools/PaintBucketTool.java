package paint.tools;

import java.awt.Cursor;
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
public class PaintBucketTool extends Tool{
	
	private ColorModel _color;						//������ �����
	private ImageBuffer _buffer; 					//����� �����������
	private CanvasModel _canvas;					//������ ������ ���������
	private JPanel _drawingPanel;					//������ ���������
	private boolean _inControl;						//���� ����, ��� ������ ���������� ������ ������������
	private clicker _curClicker;					//������ ������������� ������� ������ ����
	/**
	 * ����� ��� ������ ������� ���� � ���������� �������� ��������
	 * @author Vit
	 *
	 */
	class clicker  extends MouseAdapter 
	{
		@Override
		public void mousePressed(MouseEvent arg0) 
		{
			if(_inControl)
			{
				//������� ������������
				int button = arg0.getButton();
				int newRGB;
				if(button == 3) 
					newRGB = _color.getSecondColor().getRGB();
				else if(button == 1) 
					newRGB = _color.getFirstColor().getRGB();
				else return;
				
				Point point = arg0.getPoint();
				point.x = (int) (point.x / _canvas.getScale());
				point.y = (int) (point.y / _canvas.getScale());
			
				BufferedImage im = _canvas.getImage().getImage();
				int width = im.getWidth();
				int height = im.getHeight();
				int RGB = im.getRGB(point.x, point.y);				//�������� Int ������������� ����������� �����
		
				if(RGB != newRGB)		//���� ���� ����� ������ � ������ ������� - ������ � �� ���� �������
				{
					im.setRGB(point.x, point.y, newRGB);	//������ ����������� �����
					//�������� �������� ����������� (������������������ ������)
					//�������� ������ � ���� �������� ����� ��� ��� �������� ��������
					//� �������� ����� ������ �� ������� (����� ���������� ����� �� ��������� ������)
					//�������� �����, ���������� � ����� �������� ������� �� ������ / ������ ���� ���������
					int maxsize;
					if(width > height)
						maxsize = width * 2;
					else
						maxsize = height * 2;
					
					//������ �����, ������� � ������� �������� �����
					int[] currentXp = new int[maxsize];
					int[] currentYp = new int[maxsize];
					int currentlength = 1;
					
					//������ ����, ������� � ���� �������� �����
					int[] newXp = new int[maxsize];
					int[] newYp = new int[maxsize];
					int newlength = 0; 			
					
					//�������� ��������� �� ������� ( ��� ����� �������)
					int[] barray;
					
					//���������� ��������� �����
					currentlength = 1;
					currentXp[0] = point.x;
					currentYp[0] = point.y;
					
					int bX,bY;	//�������� ���������� - ����������
					while(currentlength > 0)
					{
						newlength = 0;
					
						//���������� ��� ����� , ��������� � ������� �������� �����
						for(int i = 0; i < currentlength; i++)
						{
							
							//��������� �������� � ���� ����� �� ����
							if(currentXp[i] < width - 1)
							{
								bX = currentXp[i] + 1;
								bY = currentYp[i];
								if( im.getRGB(bX,bY ) == RGB)
								{
									newXp[newlength] = bX;
									newYp[newlength] = bY;
									im.setRGB(bX, bY, newRGB);
									newlength++;
								}
							}
							if(currentXp[i] > 0)
							{
								bX = currentXp[i] - 1;
								bY = currentYp[i];
								if( im.getRGB(bX,bY ) == RGB)
								{
									//���� ����� �� ���� ������� � ������� ��� ��������� ����� - ��������� �� � ����� ������
									newXp[newlength] = bX;
									newYp[newlength] = bY;
									im.setRGB(bX, bY, newRGB);
									newlength++;
								}
							}					
							if(currentYp[i] < height - 1 )
							{
								bX = currentXp[i];
								bY = currentYp[i] + 1;
								if( im.getRGB(bX,bY ) == RGB)
								{
									newXp[newlength] = bX;
									newYp[newlength] = bY;
									im.setRGB(bX, bY, newRGB);
									newlength++;
								}
							}					
							if(currentYp[i] > 0)
							{
								bX = currentXp[i];
								bY = currentYp[i] - 1;
								if( im.getRGB(bX,bY ) == RGB)
								{
									newXp[newlength] = bX;
									newYp[newlength] = bY;
									im.setRGB(bX, bY, newRGB);
									newlength++;
								}
							}	
						}
						//������ ������� ����� �������
						currentlength = newlength;
						barray = newXp;
						newXp = currentXp;
						currentXp = barray;
						barray = newYp;
						newYp = currentYp;
						currentYp = barray;		
							
					}	
				}
				_buffer.turn();
				_canvas.getImage().update();
			}
		}
	}
	/**
	 * ���������� - ����� �������
	 * @param canvas
	 * @param color
	 * @param buffer
	 */
	public PaintBucketTool(CanvasModel canvas, ColorModel color, ImageBuffer buffer) 
	{
		_color = color;
		_canvas = canvas;
		_buffer = buffer;
		_drawingPanel = canvas.getDrawingPane();
		_drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		_inControl = false;
	}
	@Override
	public void activate() 
	{
		//��������� �����������
		_curClicker = new clicker();
		_drawingPanel.addMouseListener(_curClicker);
		_inControl = true;
	}
	@Override
	public void deactivate() 
	{
		_inControl = false;
		_drawingPanel.removeMouseListener(_curClicker);
	}
}
