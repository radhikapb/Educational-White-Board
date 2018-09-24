package paint.toolPanels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import paint.tools.SprayTool;
import paint.view.ToolPropertyPanel;
/**
 * 
 * @author Vit
 *
 */
public class SprayToolPropertyPanel extends ToolPropertyPanel
{
	public JToolBar _bar = new JToolBar();
	public JLabel _name = new JLabel("Spray: ");
	public JLabel _radius = new JLabel("  radius ");
	public JLabel _speed = new JLabel("  speed ");
	public JLabel _radiusValue = new JLabel("");
	public JLabel _speedValue = new JLabel("");
	public JSlider _radiusSlider = new JSlider();
	public JSlider _speedSlider = new JSlider();
	public SprayTool _tool;
	public SprayToolPropertyPanel(SprayTool tool)
	{
		_tool = tool;
		_tool.attach(this);
		//����������� ���������� ������ ������������
		_bar.setOrientation(JToolBar.HORIZONTAL);
		_bar.setLayout(new FlowLayout(FlowLayout.LEFT));
		_bar.setPreferredSize(new Dimension(1000,31));
		_bar.setFloatable(false);
		
		//�������������  ����� � ������� ���������
		_radius.setFont(TOOL_FONT);
		_radiusValue.setFont(TOOL_FONT);
		_radiusValue.setPreferredSize(new Dimension(20,20));
		_speed.setFont(TOOL_FONT);
		_speedValue.setFont(TOOL_FONT);
		_speedValue.setPreferredSize(new Dimension(20,20));
		_radiusSlider.setPreferredSize(new Dimension(100,20));
		_radiusSlider.setMaximum(100);
		_radiusSlider.setOpaque(false);
		_radiusSlider.setMinimum(1);
		_speedSlider.setPreferredSize(new Dimension(100,20));
		_speedSlider.setMaximum(10);
		_speedSlider.setMinimum(1);
		_speedSlider.setOpaque(false);
		_speedSlider.setValue(_tool.getSpeed());
		_radiusSlider.setValue(_tool.getRadius());
		_name.setFont(TOOL_FONT);
		
		_radiusSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				_tool.setRadius(_radiusSlider.getValue());
				
			}});
		_speedSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				_tool.setSpeed(_speedSlider.getValue());
				
			}});
		//��������� �������� � ������
		_bar.add(_name);
		_bar.add(_radius);
		_bar.add(_radiusSlider);
		_bar.add(_radiusValue);
		JSeparator separ = new JSeparator();
		separ.setOrientation(JSeparator.VERTICAL);
		_bar.add(separ);
		_bar.add(_speed);
		_bar.add(_speedSlider);
		_bar.add(_speedValue);
		refresh();
	}
	@Override
	public JToolBar getPanel() {
		return _bar;
	}

	@Override
	public void refresh() 
	{
		_radiusValue.setText("" + _tool.getRadius());
		_speedValue.setText("" + _tool.getSpeed());
	}
}
