package paint.view;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.border.SoftBevelBorder;
import paint.model.ToolModel;
/**
 * ���� ������������
 * @author Vit
 *
 */
public class ToolMenu implements View
{	
	/**
	 * �����, �������������� ��� �������� �� ��������� �� ������ ������ ������������
	 * @author Vit
	 *
	 */
	private class MouseClickListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			//� ������ ������� � ����������� �� ������� ������ - ������������� � ������ ������������ ���������������
			//���������
			if(arg0.getSource() == _cursorItem)
			{
				_model.activateTool(ToolModel.Tools.Arrow);
				return;
			}
			if(arg0.getSource() == _glassItem)
			{
				_model.activateTool(ToolModel.Tools.Glass);
				return;
			}
			if(arg0.getSource() == _brushItem)
			{
				_model.activateTool(ToolModel.Tools.Brush);
				return;
			}
			if(arg0.getSource() == _penItem)
			{
				_model.activateTool(ToolModel.Tools.Pen);
				return;
			}
			if(arg0.getSource() == _eraserItem)
			{
				_model.activateTool(ToolModel.Tools.Eraser);
				return;
			}
			if(arg0.getSource() == _sprayItem)
			{
				_model.activateTool(ToolModel.Tools.Spray);
				return;
			}
			if(arg0.getSource() == _rectangleItem)
			{
				_model.activateTool(ToolModel.Tools.Rectangle);
				return;
			}
			if(arg0.getSource() == _circleItem)
			{
				_model.activateTool(ToolModel.Tools.Circle);
				return;
			}
			if(arg0.getSource() == _lineItem)
			{
				_model.activateTool(ToolModel.Tools.Line);
				return;
			}
			if(arg0.getSource() == _bucketItem)
			{
				_model.activateTool(ToolModel.Tools.PaintBucket);
				return;
			}
			if(arg0.getSource() == _samplerItem)
			{
				_model.activateTool(ToolModel.Tools.Sampler);
				return;
			}
			if(arg0.getSource() == _selectorItem)
			{
				_model.activateTool(ToolModel.Tools.Selector);
				return;
			}
			if(arg0.getSource() == _textItem)
			{
				_model.activateTool(ToolModel.Tools.Text);
			}
		}
	}
	private MouseClickListener _listener = new MouseClickListener();
	private ToolModel _model;
	private javax.swing.JToolBar _menuBar;
	private JButton _penItem;
	private JButton _brushItem;
	private JButton _eraserItem;
	private JButton _bucketItem;
	private JButton _glassItem;
	private JButton _selectorItem;
	private JButton _rectangleItem;
	private JButton _circleItem;
	private JButton _lineItem;
	private JButton _samplerItem;
	private JButton _sprayItem;
	private JButton _textItem;
	private JButton _cursorItem;

	/**
	 * �����������
	 * @param model ������ ������ ������������
	 */
	public ToolMenu(ToolModel model)
	{
		_model = model;
		_menuBar = new JToolBar();
		_menuBar.setOrientation(JToolBar.VERTICAL);
		_menuBar.setFloatable(true);
		
		//������ �������� ������ �� ������, �������� ������ �� ��������
		_penItem = new JButton(new ImageIcon(this.getClass().getResource("/paint/icons/pen.PNG")));
		_penItem.setToolTipText("Pencil");
		_penItem.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		_brushItem = new JButton(new ImageIcon(this.getClass().getResource("/paint/icons/brush.PNG")));
		_brushItem.setToolTipText("Brush");
		_brushItem.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		_eraserItem = new JButton(new ImageIcon(this.getClass().getResource("/paint/icons/eraser.png")));
		_eraserItem.setToolTipText("Eraser");
		_eraserItem.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		_bucketItem = new JButton(new ImageIcon(this.getClass().getResource("/paint/icons/bucket.PNG")));
		_bucketItem.setToolTipText("Paint bucket");
		_bucketItem.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		_glassItem = new JButton(new ImageIcon(this.getClass().getResource("/paint/icons/glass.PNG")));
		_glassItem.setToolTipText("Looking glass");
		_glassItem.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		_selectorItem = new JButton(new ImageIcon(this.getClass().getResource("/paint/icons/select.PNG")));
		_selectorItem.setToolTipText("Selector");
		_selectorItem.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));	
		_rectangleItem = new JButton(new ImageIcon(this.getClass().getResource("/paint/icons/rectangle.PNG")));
		_rectangleItem.setToolTipText("Rectangle");
		_rectangleItem.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		_circleItem = new JButton(new ImageIcon(this.getClass().getResource("/paint/icons/circle.PNG")));
		_circleItem.setToolTipText("Circle");
		_circleItem.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		_lineItem = new JButton(new ImageIcon(this.getClass().getResource("/paint/icons/line.PNG")));
		_lineItem.setToolTipText("Line");
		_lineItem.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		_samplerItem = new JButton(new ImageIcon(this.getClass().getResource("/paint/icons/sampler.PNG")));
		_samplerItem.setToolTipText("Color selector");
		_samplerItem.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		_sprayItem = new JButton(new ImageIcon(this.getClass().getResource("/paint/icons/spray.PNG")));
		_sprayItem.setToolTipText("Spray");
		_sprayItem.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		_textItem = new JButton(new ImageIcon(this.getClass().getResource("/paint/icons/text.PNG")));
		_textItem.setToolTipText("Text");
		_textItem.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		_cursorItem = new JButton(new ImageIcon(this.getClass().getResource("/paint/icons/cursor.PNG")));
		_cursorItem.setToolTipText("Cursor");
		_cursorItem.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		
		_menuBar.setFloatable(true);
		
		//���������� ������ ������ ������������ � ������
		_menuBar.add(_cursorItem);
		_menuBar.add(_selectorItem);
		_menuBar.add(_samplerItem);
		_menuBar.add(_glassItem);
		_menuBar.add(_penItem);
		_menuBar.add(_brushItem);
		_menuBar.add(_eraserItem);
		_menuBar.add(_bucketItem);
		_menuBar.add(_rectangleItem);
		_menuBar.add(_sprayItem);
		_menuBar.add(_textItem);
		_menuBar.add(_lineItem);
		_menuBar.add(_circleItem);
		_menuBar.add(_rectangleItem);
		
		//���������� ��������� ������� �� ������ ������ ������������
		_cursorItem.addActionListener(_listener);
		_glassItem.addActionListener(_listener);
		_selectorItem.addActionListener(_listener);;
		_penItem.addActionListener(_listener);
		_brushItem.addActionListener(_listener);
		_eraserItem.addActionListener(_listener);
		_bucketItem.addActionListener(_listener);
		_rectangleItem.addActionListener(_listener);
		_sprayItem.addActionListener(_listener);
		_textItem.addActionListener(_listener);
		_lineItem.addActionListener(_listener);
		_circleItem.addActionListener(_listener);
		_rectangleItem.addActionListener(_listener);
		_samplerItem.addActionListener(_listener);
	}
	public JToolBar getBar()
	{
		return _menuBar;
	}
	@Override
	public void refresh() 
	{

	}
}
