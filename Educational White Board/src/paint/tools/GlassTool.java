package paint.tools;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import paint.model.CanvasModel;
import paint.model.Tool;
/**
 * 
 * @author User
 *
 */
public class GlassTool extends Tool
{
	private static final float ZOOM_LIMIT = 32;					//���������� ����������
	private static final float UNZOOM_LIMIT = (float) 1;		//���������� ����������
	private static boolean _zooming = true; 					//���� ������������ ����������
	private CanvasModel _model;									//������ ���� ���������
	private boolean _active = false;							//���� ����������
	private MouseClicker _clicker = new MouseClicker();			//������ ��� ��������� ������� ����
	/**
	 * ����� ��� ���������� �� ����� �� ������ ���������
	 * @author Vit
	 *
	 */
	private class MouseClicker extends MouseAdapter
	{
		@Override
		public void mouseClicked(MouseEvent arg0) 
		{
			//����� ������ ������� �������� ������ ����
			if(_active && (arg0.getButton() == 1))
			{
				//��� ������� �� ����, ����� ���������� ����������, ��� ���������� ��������		
				if(_zooming && zoomable())
					zoom(arg0.getPoint().x,arg0.getPoint().y);
				if(!_zooming && unzoomable())
					unzoom(arg0.getPoint().x,arg0.getPoint().y);
			}
		}
	}
	public GlassTool(CanvasModel model)
	{
		_model = model;
		
	}
	@Override
	public void activate() 
	{
		_active = true;
		_model.getDrawingPane().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		_model.getDrawingPane().addMouseListener(_clicker);
	}
	@Override
	public void deactivate() 
	{
		_active = false;
		_model.getDrawingPane().removeMouseListener(_clicker);
	}
	/**
	 * ����������� ����������� � ������� �����
	 * @param x - ������� ���� �� �����������
	 * @param y - ������� ���� �� ���������
	 */
	public void zoom(int x, int y)
	{
		//����� ������� �����������
		float newScale = _model.getScale() * 2;
		//������ ������� �����������
		float oldScale = _model.getScale();
		//�����, ��� ��� ���������� ���� ���� ( � �������� �����������)
		int realx = (int) (x / oldScale);
		int realy = (int) (y / oldScale);
		//����� �������� ��� ����� ��������, ������������ ������ ������� ����� �����������
		int realWidth = (int) (_model.getWidth() / newScale) ;
		int realHeight = (int) (_model.getHeight() / newScale);
		
		int newx = 0;
		int newy = 0;
		if(realx > realWidth / 2)
		{
			newx = realx - realWidth / 2;
		}
		if(realy > realHeight / 2)
		{
			newy = realy - realHeight / 2;
		}
		
		_model.getViewPosition().x = (int) (newx * newScale);
		_model.getViewPosition().y = (int) (newy* newScale);
		_model.setScale( newScale);
		update();
		_model.refresh();
	}
	/**
	 * �������� ����������� �� ������� ����� 
	 * @param x - ������� ���� �� �����������
	 * @param y - ������� ���� �� ���������
	 */
	public void unzoom(int x, int y)
	{
		//����� ������� �����������
		float newScale = _model.getScale() / 2;
		//������ ������� �����������
		float oldScale = _model.getScale();
		//�����, ��� ��� ���������� ���� ���� ( � �������� �����������)
		int realx = (int) (x / oldScale);
		int realy = (int) (y / oldScale);
		//����� �������� ��� ����� ��������, ������������ ������ ������� ����� �����������
		int realWidth = (int) (_model.getWidth() / newScale) ;
		int realHeight = (int) (_model.getHeight() / newScale);
		
		int newx = 0;
		int newy = 0;
		if(realx > realWidth / 2)
		{
			newx = realx - realWidth / 2;
		}
		if(realy > realHeight / 2)
		{
			newy = realy - realHeight / 2;
		}
		
		_model.getViewPosition().x = (int) (newx * newScale);
		_model.getViewPosition().y = (int) (newy* newScale);
		_model.setScale( newScale);
		update();
		_model.refresh();	
	}
	/**
	 * ���������� true, ���� ����� ���������� �����������
	 * @return
	 */
	public boolean zoomable()
	{
		if(_model.getScale() >= ZOOM_LIMIT)
			return false;
		return true;
	}
	/**
	 * ���������� false, ���� ������ ���������� �����������
	 * @return
	 */
	public boolean unzoomable()
	{
		if(_model.getScale() <= UNZOOM_LIMIT)
			return false;
		return true;
	}
	/**
	 * ���������� ������� ����������� ����������
	 * @return
	 */
	public float getZoom()
	{
		return _model.getScale();
	}
	/**
	 * ������ true, ���� ������������ ����������
	 * @return
	 */
	public boolean getZooming()
	{
		return _zooming;
	}
	/**
	 * �������������, ���� ������������ ����������
	 * @return
	 */
	public void setZooming(boolean value)
	{
		_zooming = value;
		update();
	}
}
