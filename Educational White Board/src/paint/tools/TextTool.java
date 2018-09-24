package paint.tools;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import paint.model.CanvasModel;
import paint.model.ColorModel;
import paint.model.ImageBuffer;
import paint.model.Tool;
import paint.view.View;
/**
 * 
 * @author User
 *
 */
public class TextTool extends Tool implements View{

	private static String fontList[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	private static int _fontSizes[] = { 8, 10, 11, 12, 13, 14, 16, 18, 20, 24, 30, 36, 40, 48, 60, 72, 86, 100, 128, 156 };
	private int _size;					//������� ������ ������
	private CanvasModel _canvas;		//������ ������� ���������
	private ColorModel _color;			//������ �����
	private ImageBuffer _buffer;		//����� �����������
	
	private boolean _writing = false;	//���� ����, ��� �������������� ��������� �������
	private Font _font;					//����� ������
	private JTextField _label;			//��������� ����, � ������� ������� ����� �� ��������� �� �����������
	private JPanel _pane;				//���� ���������
	private clicker _clicker = new clicker(); 	//������ ��� �������� �� ��������� ����
	private volatile int _startx = 0;			//����� ������� ��������� ������
	private volatile int _starty = 0;
	private dlistener _docListener = new dlistener();  	//����� ��� �������� �� ����������, ����������� � JTextField _label
	private boolean _italic = false;					//���� ���������� ������
	private boolean _bold = false;						//���� ������� ������
	/**
	 * ��������� ��������� ���������
	 */
	private void _endWriting()
	{
		//��������� ������������� ������������ ������ �� �����������
		//�������� �������, �����, � ������� �����������
		//, ����� ����������� ������������� �������������� � ���� ��������������
		String text = _label.getText();
		Color col = _label.getForeground();
		Graphics2D gr = (Graphics2D) _canvas.getImage().getImage().getGraphics();
		Font realfont = _label.getFont();
		realfont = realfont.deriveFont((float)_size);
		gr.setFont(realfont);
		gr.setColor(col);
		gr.drawString(text, _label.getX() / _canvas.getScale(), _label.getY() / _canvas.getScale() + _size);
		_writing = false;
		if(_label.getText().length() > 0)
			_buffer.turn();
		_label.setText("");
		_label.setSize(0,0);
		_label.setVisible(false);
		_canvas.getImage().update();
	}
	
	/**
	 * ������ ����� ��������� � ������������ � �����������
	 */
	private void _restyle()
	{	
		int x1 = _startx;
		int y1 = _starty;
		_label.setLocation(x1,y1);
		//����� ����� ����������� �� ���� ���������� � ����������� �������
		float scaledsize = _size * _canvas.getScale();
		Font realfont = _font.deriveFont( scaledsize);
		if(_bold)
		{
			if(_italic)
				realfont = realfont.deriveFont(Font.BOLD | Font.ITALIC);
			else
				realfont = realfont.deriveFont(Font.BOLD);
			
		}
		else
			if(_italic)
				realfont = realfont.deriveFont(Font.ITALIC);
		
		//������� ���� ��������� ������ �������� � ����������� �� ����� ������
		_label.setFont(realfont);
		_label.setForeground(_color.getFirstColor());		
		int width = _label.getGraphics().getFontMetrics().stringWidth(_label.getText());
		int height = _label.getGraphics().getFontMetrics().getHeight();
		if(width == 0)
			width = 3;
		if(height == 0)
			height = 8;
		
		_label.setForeground(_color.getFirstColor());
		_label.setBounds(x1, y1, width, height);
		_label.validate();
		_label.requestFocus();
	}
	/**
	 * ��������� ������ ���������
	 */
	private void _startWriting(int x, int y)
	{
		//���������� ����� ������� � ������������ �����
		_writing = true;	
		_startx = x;
		_starty = y;
		_label.setText("");
		_label.setVisible(true);
		_restyle();
		
		
	}
	/**
	 * ����� ��� ������ ������� ����
	 * @author User
	 *
	 */
	class clicker extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent arg0) 
		{
			int _button = arg0.getButton();
			if(_button == 1)
			{
				//������� �� ����� ������ ���� �������� ��������� ������
				if(_writing)
					_endWriting();
				Point p = _pane.getMousePosition();
				_startWriting(p.x, p.y);
			}
			if(_button == 3)
			{
				//������� �� ������ ������ ���� ��������� ��������� ������
				if(_writing)
					_endWriting();
			}
		}
	}
	/**
	 * ����� ��� ������������� ������� � ���������� ������ ������
	 * @author User
	 *
	 */
	class dlistener implements DocumentListener
	{
		@Override
		public void changedUpdate(DocumentEvent arg0) {
			_restyle();
		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			_restyle();
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			_restyle();
		}
		
	};
	/**
	 * �����������
	 * @param canvas ������ ������� ���������
	 * @param color ������ �����
	 * @param buffer ����� �����������
	 */
	public TextTool(CanvasModel canvas, ColorModel color, ImageBuffer buffer) {
		_canvas = canvas;
		_color = color;
		_buffer = buffer;
		_label = _canvas.getTextLabel();
		_pane = canvas.getDrawingPane();
	}

	public int[] getSizes()
	{
		return _fontSizes;
	}
	public int getSize()
	{
		return _size;
	}
	public void setSize(int value)
	{
		_size = value;
		if(_writing)
			_restyle();
	}
	public String[] getFonts()
	{
		return fontList;
	}
	public String getFont()
	{
		return _font.getName();
	}
	public void setFont(String value)
	{
		Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		for(Font font : fonts)
			if(font.getName().equals(value))
				_font = font;
		if(_writing)
			_restyle();
	}

	@Override
	public void activate() {
		_pane.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		_pane.addMouseListener(_clicker);
		_color.attach(this);
		_label.getDocument().addDocumentListener(_docListener);
	}
	@Override
	public void deactivate() 
	{
		if(_writing)
			_endWriting();
		_pane.removeMouseListener(_clicker);
		_color.detach(this);
		_label.getDocument().removeDocumentListener(_docListener);
	}

	
	public void refresh() {
		_restyle();
		
	}
	public void setBold(boolean value)
	{
		_bold = value;
		_restyle();
	}
	public void setItalic(boolean value)
	{
		_italic = value;
		_restyle();
	}
	public boolean getBold()
	{
		return _bold;
	}
	public boolean getItalic()
	{
		return _italic;
	}
}
