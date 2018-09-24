package paint.toolPanels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import paint.tools.EraserTool;
import paint.view.ToolPropertyPanel;
/**
 * 
 * @author Vit
 *
 */
public class EraserToolPropertyPanel extends ToolPropertyPanel{
	public JToolBar _bar = new JToolBar();
	public JLabel _name = new JLabel("Eraser: ");
	public JLabel _width = new JLabel("  width ");
	public JLabel _widthValue = new JLabel("");
	public JSlider _slider = new JSlider();
	public EraserTool _tool;
	public EraserToolPropertyPanel(EraserTool tool)
	{
		_tool = tool;
		_tool.attach(this);
		//����������� ���������� ������ ������������
		_bar.setOrientation(JToolBar.HORIZONTAL);
		_bar.setLayout(new FlowLayout(FlowLayout.LEFT));
		_bar.setPreferredSize(new Dimension(1000,31));
		_bar.setFloatable(false);
		//�������������  �����
		_width.setFont(TOOL_FONT);
		_widthValue.setFont(TOOL_FONT);
		_widthValue.setPreferredSize(new Dimension(20,20));
		_slider.setMaximum(100);
		_slider.setMinimum(1);
		_slider.setOpaque(false);
		_slider.setPreferredSize(new Dimension(120,20));
		_slider.setValue(_tool.getRadius());
		_name.setFont(TOOL_FONT);
		_slider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				_tool.setRadius(_slider.getValue());
				
			}});
		_slider.addPropertyChangeListener(new PropertyChangeListener()
		{

			@Override
			public void propertyChange(PropertyChangeEvent arg0) 
			{
				_tool.setRadius(_slider.getValue());
			}
			
		});
		//��������� �������� � ������
		_bar.add(_name);
		_bar.add(_width);
		_bar.add(_slider);
		_bar.add(_widthValue);
		refresh();
	}
	@Override
	public JToolBar getPanel() 
	{
		return _bar;
	}

	@Override
	public void refresh() 
	{
		_widthValue.setText("" + _tool.getRadius());
	}

}
