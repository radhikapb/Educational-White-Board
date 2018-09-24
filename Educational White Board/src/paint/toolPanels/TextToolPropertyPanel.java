package paint.toolPanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import paint.tools.TextTool;
import paint.view.ToolPropertyPanel;
/**
 * 
 * @author Vit
 *
 */
public class TextToolPropertyPanel extends ToolPropertyPanel {
	public JToolBar _bar = new JToolBar();
	private JLabel _text = new JLabel("Text    ");
	private JComboBox _sizebox = new JComboBox();
	private JToggleButton	_italicButton = new JToggleButton();
	private JToggleButton _boldButton = new JToggleButton();
	private TextTool _tool;
	private JComboBox _fontbox = new JComboBox();
	private ImageIcon _italicIcon = new ImageIcon(this.getClass().getResource("/icons/italic.PNG"));
	private ImageIcon _boldIcon = new ImageIcon(this.getClass().getResource("/icons/bold.PNG"));
	public TextToolPropertyPanel(TextTool tool)
	{
		_tool = tool;
		_tool.attach(this);
		_bar.setOrientation(JToolBar.HORIZONTAL);
		_bar.setLayout(new FlowLayout(FlowLayout.LEFT));
		_bar.setPreferredSize(new Dimension(1000,31));
		_bar.setFloatable(false);	
		
		_italicButton.setIcon(_italicIcon);
		_italicButton.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		_boldButton.setIcon(_boldIcon);
		_boldButton.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		_text.setFont(TOOL_FONT);
		_fontbox.setFont(TOOL_FONT);
		_fontbox.setBackground(Color.white);
		_fontbox.setPreferredSize(new Dimension(200,20));
		_fontbox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_tool.setFont((String)_fontbox.getSelectedItem());
			}});
		for(String item : _tool.getFonts())
		{
			_fontbox.addItem(item);
			if(item.equals("SansSerif"))
				_fontbox.setSelectedItem(item);
		}
		_sizebox.setFont(TOOL_FONT);
		_sizebox.setBackground(Color.white);
		_sizebox.setPreferredSize(new Dimension(60,20));
		_sizebox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_tool.setSize((Integer)_sizebox.getSelectedItem());
			}});
		for(int item : _tool.getSizes())
			_sizebox.addItem(item);
		_italicButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(_tool.getItalic())
				{
					_tool.setItalic(false);
					_italicButton.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
					
				}
				else
				{
					_tool.setItalic(true);
					_italicButton.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
				}
					
			}});
		_boldButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(_tool.getBold())
				{
					_tool.setBold(false);
					_boldButton.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
				}
				else
				{
					_tool.setBold(true);
					_boldButton.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
				}
			}});
		_bar.add(_text);
		_bar.add(_fontbox);
		_bar.add(_sizebox);
		_bar.add(_italicButton);
		_bar.add(_boldButton);
	}
	@Override
	public JToolBar getPanel() {
		return _bar;
	}

	@Override
	public void refresh() {
	}

}
