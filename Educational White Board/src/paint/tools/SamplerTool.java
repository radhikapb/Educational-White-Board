package paint.tools;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import paint.model.CanvasModel;
import paint.model.ColorModel;
import paint.model.Tool;
/**
 * 
 * @author User
 *
 */
public class SamplerTool extends Tool
{
	private ColorModel _color;						//������ �����
	private CanvasModel _canvas;					//������ ������ ���������
	private JPanel _drawingPanel;					//������ ���������
	private boolean _inControl;						//���� ����, ��� ������ ���������� ������ ������������
	private clicker _curClicker;					//������ ������������� ������� ������ ����
	class clicker  extends MouseAdapter 
	{
		@Override
		public void mousePressed(MouseEvent arg0) 
		{
			if(_inControl)
			{
				int button = arg0.getButton();
		
				Point point = arg0.getPoint();
				point.x = (int) (point.x / _canvas.getScale());
				point.y = (int) (point.y / _canvas.getScale());
			
				BufferedImage im = _canvas.getImage().getImage();

				int RGB = im.getRGB(point.x, point.y);				//�������� Int ������������� ����������� �����	
				if(button == 1) 
					_color.setFirstColor(new Color(RGB));
				else if(button == 3) 
					_color.setSecondColor(new Color(RGB));
				else return;
				_color.update();
			}
		}
	}
	/**
	 * ���������� - ����� �������
	 * @param canvas
	 * @param color
	 * @param buffer
	 */
	public SamplerTool(CanvasModel canvas, ColorModel color) 
	{
		_color = color;
		_canvas = canvas;
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