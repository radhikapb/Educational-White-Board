package paint.toolPanels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import paint.tools.ArrowTool;
import paint.view.ToolPropertyPanel;
/**
 * 
 * @author Vit
 *
 */
public class ArrowToolPropertyPanel extends ToolPropertyPanel {
	public JToolBar _bar = new JToolBar();
	public JLabel _name = new JLabel("Arrow");
	public ArrowTool _tool;
	public ArrowToolPropertyPanel(ArrowTool tool)
	{
		_tool = tool;
		//����������� ���������� ������ ������������
		_bar.setOrientation(JToolBar.HORIZONTAL);
		_bar.setLayout(new FlowLayout(FlowLayout.LEFT));
		_bar.setPreferredSize(new Dimension(1000,31));
		_bar.setFloatable(false);
		
		
		_name.setFont(TOOL_FONT);
		_bar.add(_name);
	}
	@Override
	public JToolBar getPanel() 
	{
		return _bar;
	}
	@Override
	public void refresh() 
	{
	}
}
