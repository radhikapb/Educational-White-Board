package paint.toolPanels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import paint.tools.CircleTool;
import paint.tools.CircleTool.Modes;
import paint.view.ToolPropertyPanel;
/**
 * 
 * @author Vit
 *
 */
public class CircleToolPropertyPanel extends ToolPropertyPanel{
	public JToolBar _bar = new JToolBar();
	public JLabel _name = new JLabel("Circle        ");
	private JToggleButton _fillBorder = new JToggleButton();	//������ ������ ������� � �������
	private JToggleButton _fill = new JToggleButton();			//������ ������ �������
	private JToggleButton _border = new JToggleButton();			//������ ������ �������
	private ButtonGroup _group = new ButtonGroup();					//������ ��� ����������� ������
	public CircleTool _tool;
	public CircleToolPropertyPanel(CircleTool tool)
	{
		_tool = tool;
		_tool.attach(this);
		//����������� ���������� ������ ������������
		_bar.setOrientation(JToolBar.HORIZONTAL);
		_bar.setLayout(new FlowLayout(FlowLayout.LEFT));
		_bar.setPreferredSize(new Dimension(1000,31));
		_bar.setFloatable(false);
		
		_name.setFont(TOOL_FONT);
		//������ ������
		_fillBorder.setIcon(new ImageIcon(getClass().getResource("/icons/fillbordercircle.PNG")));
		_fill.setIcon(new ImageIcon(getClass().getResource("/icons/fillcircle.PNG")));
		_border.setIcon(new ImageIcon(getClass().getResource("/icons/bordercircle.PNG")));
		
		//���������� ������ � ������, ����� ����� ���� ������ ������ ����
		_group.add(_fillBorder);
		_group.add(_fill);
		_group.add(_border);
		//��������� ��������� ������, ���������� �� ������ ������ �����������
		_fillBorder.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_tool.setMode(Modes.FillBorder);
			}});
		_fill.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_tool.setMode(Modes.Fill);
			}});
		_border.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_tool.setMode(Modes.Border);
			}});
		_bar.add(_name);
		_bar.add(_fillBorder);
		_bar.add(_fill);
		_bar.add(_border);
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
		//� ����������� �� �������� ������ ������ ����������� - ������������� ������� ��������������
		//������
		switch(_tool.getMode())
		{
			case Fill:	//������ �������
			{
				_fill.setSelected(true);
				_fill.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
				_fillBorder.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
				_border.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
				break;
			}
			case FillBorder: //������� � ������
			{
				_fillBorder.setSelected(true);
				_fillBorder.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
				_fill.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
				_border.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
				break;
			}
			case Border: //������ ������
			{
				_border.setSelected(true);
				_border.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
				_fillBorder.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
				_fill.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
				break;
			}
		}
		
	}
}
