package paint.view;
import java.awt.Font;
import javax.swing.JToolBar;



public abstract class ToolPropertyPanel implements View
{
	public static final Font TOOL_FONT = new java.awt.Font(Font.SANS_SERIF,0, 11); 
	//������� ������ ���������� ������ ����������� �����������
	public abstract JToolBar getPanel();
}
