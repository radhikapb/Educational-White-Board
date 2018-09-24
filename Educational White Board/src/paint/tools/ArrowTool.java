package paint.tools;

import java.awt.Cursor;
import javax.swing.JPanel;
import paint.model.CanvasModel;
import paint.model.Tool;
/**
 * ���������� - ������� ������
 * @author User
 *
 */
public class ArrowTool extends Tool
{
	private CanvasModel _canvas;
	public ArrowTool(CanvasModel canvas)
	{
		_canvas = canvas;
	}
	@Override
	public void activate() 
	{
		JPanel panel = _canvas.getDrawingPane();
		//��� ��������� ������ ������ �� �������
		if(panel != null)
			_canvas.getDrawingPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		update();
	}

	@Override
	public void deactivate() 
	{
		update();
	}

}
