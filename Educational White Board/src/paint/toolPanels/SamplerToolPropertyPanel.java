package paint.toolPanels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import paint.tools.SamplerTool;
import paint.view.ToolPropertyPanel;
/**
 * 
 * @author Vit
 *
 */
public class SamplerToolPropertyPanel extends ToolPropertyPanel{
	public JToolBar _bar = new JToolBar();
	public JLabel _name = new JLabel("Sampler");
	public SamplerTool _tool;
	public SamplerToolPropertyPanel(SamplerTool tool)
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
	public JToolBar getPanel() {
		// TODO Auto-generated method stub
		return _bar;
	}

	@Override
	public void refresh() 
	{
	}
}
