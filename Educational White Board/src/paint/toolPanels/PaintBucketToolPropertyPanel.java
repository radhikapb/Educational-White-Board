package paint.toolPanels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import paint.tools.PaintBucketTool;
import paint.view.ToolPropertyPanel;
/**
 * 
 * @author Vit
 *
 */
public class PaintBucketToolPropertyPanel extends ToolPropertyPanel
{
	public JToolBar _bar = new JToolBar();
	public JLabel _name = new JLabel("Paint Bucket");
	public PaintBucketTool _tool;
	public PaintBucketToolPropertyPanel(PaintBucketTool tool)
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
	public void refresh() {
	}
}
