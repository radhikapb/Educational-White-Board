package paint.toolPanels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import paint.tools.GlassTool;
import paint.view.ToolPropertyPanel;

/**
 * 
 * @author Vit
 *
 */
public class GlassToolPropertyPanel extends ToolPropertyPanel
{
	private GlassTool _tool;									//���������� ����������
	private JToolBar _bar = new JToolBar();	
	private JLabel _name = new JLabel(" ");						//��� + �������
	private JToggleButton _zoomer = new JToggleButton();		//������ ��������� ����������
	private JToggleButton _unzoomer = new JToggleButton();		//������ ����������
	private ButtonGroup _group = new ButtonGroup();				//������ ��� ����������� ������
	public GlassToolPropertyPanel(GlassTool tool)
	{
		_tool = tool;
		_tool.attach(this);
		//����������� ���������� ������ ������������
		_bar.setOrientation(JToolBar.HORIZONTAL);
		_bar.setLayout(new FlowLayout(FlowLayout.LEFT));
		_bar.setPreferredSize(new Dimension(1000,31));
		_bar.setFloatable(false);
		
		//����������� ������
		_zoomer.setIcon(new ImageIcon(this.getClass().getResource("/icons/zoomin.PNG")));
		_zoomer.setBackground(_bar.getBackground());
		_zoomer.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		
		//��������� ������� �� ������ ����������
		_zoomer.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				//������������� ����������
				_tool.setZooming(true);
			}
		});
		_zoomer.setToolTipText("Zoom in");
		_unzoomer.setIcon(new ImageIcon(this.getClass().getResource("/icons/zoomout.PNG")));
		_unzoomer.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		_unzoomer.setToolTipText("Zoom out");
		
		//��������� ������� �� ������ ����������
		_unzoomer.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				//������������� ����������
				_tool.setZooming(false);
			}
		});
		
		//������������� �����
		_name.setFont(TOOL_FONT);
		
		//��������� �������� �� ������ ������������
		_group.add(_zoomer);
		_group.add(_unzoomer);
		_bar.add(_name);
		_bar.add(new JSeparator());
		_bar.add(_zoomer);
		_bar.add(_unzoomer);
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
		//������� ������� �� ������ � ���������
		this._name.setText("Zoom: " + (int)(_tool.getZoom() * 100) + "% ");
		//��������� �������� ����������� ����������� �����������, ���������,
		//���� ������ ������ ��������/ ���������� - ��������� �������������� ������
		//����� - ��������
		if(!_tool.zoomable())
			_zoomer.setEnabled(false);
		else
			_zoomer.setEnabled(true);
		if(!_tool.unzoomable())
			_unzoomer.setEnabled(false);
		else
			_unzoomer.setEnabled(true);
		if(_tool.getZooming())
		{
			//���� ���������� �������� �� ���������� - ���������� ��� ���������� ������ ����������
			_zoomer.setSelected(true);
			_zoomer.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
			_unzoomer.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		}
		else
		{
			//����� - �������� ������ ����������
			_unzoomer.setSelected(true);
			_unzoomer.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
			_zoomer.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		}
	}
}