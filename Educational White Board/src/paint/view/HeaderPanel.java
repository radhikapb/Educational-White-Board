package paint.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import paint.model.CanvasModel;
import paint.model.CanvasResizer;

/**
 * ������ ������ �������� � ���������
 * ���������� 
 * 1) �������
 * 2) ������� ����
 * 3) ������ �����������
 * @author Vit
 *
 */
public class HeaderPanel implements View
{
	private static Font font = new java.awt.Font(Font.SANS_SERIF,0, 11); //����� ���� 
	
	private JPanel _panel = new JPanel();				//�������� ������ �����������
	private CanvasModel _canvas;						//������ ������� ���������
	private CanvasResizer _resizer;						//���������� ��������� �������� �������
	private JLabel _size;								//����� ��� ����������� ������� �������
	private JLabel _scale;								//����� ��������
	private JLabel _position;							//����� ������� ����
	private boolean _mouseIn;							//���� ����, ��� ���� ��������� �� ������� ��������� � ����� ������ �� �������
	public HeaderPanel(CanvasModel canvas,CanvasResizer resizer)
	{
		_panel.setLayout(new BorderLayout(0,0));
		_panel.setPreferredSize(new Dimension(200,25));
		_panel.setBorder(null);
		_size = new JLabel();
		_size.setFont(font);
		_size.setIcon(new ImageIcon(this.getClass().getResource("/paint/icons/resize.PNG")));
		_size.setPreferredSize(new Dimension(80,20));
		_scale = new JLabel();
		_scale.setFont(font);
		_scale.setPreferredSize(new Dimension(50,20));
		_position = new JLabel();
		_position.setIcon(new ImageIcon(this.getClass().getResource("/paint/icons/point.PNG")));
		_position.setFont(font);
		_position.setPreferredSize(new Dimension(80,20));
		_position.setText("Position  ");
		
		//������ ������� 3 ������ - ������ �������, ����������� �������, ���������� ��������� �������
		_canvas = canvas;
		_canvas.attach(this);
		_canvas.getImage().attach(this);
		_resizer = resizer;
		_resizer.attach(this);
		
		//��������� �������� ���� ������ �� �� �������� �� ������� � ������� ���������� �� ������
		_canvas.getDrawingPane().addMouseMotionListener(new MouseMotionAdapter(){
			@Override
			public void mouseMoved(MouseEvent arg0) {
				if(_mouseIn)_position.setText(" " + arg0.getX() + ":" + arg0.getY());
			}});
		//��������� ��������� ���� ������ �� ���, ��������� �� ���� �� ������� ��������� � ����� �� ������ ���������� ����
		_canvas.getDrawingPane().addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent arg0) {
				_mouseIn = true;
				_position.setText(" ");
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				_mouseIn = false;
				_position.setText(" ");
			}});
		
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();

		JSeparator s1 = new JSeparator();
		s1.setOrientation(JSeparator.VERTICAL);
		s1.setPreferredSize(new Dimension(10,15));
		JSeparator s2 = new JSeparator();
		s2.setOrientation(JSeparator.VERTICAL);
		s2.setPreferredSize(new Dimension(10,15));
		JSeparator s3 = new JSeparator();
		s3.setOrientation(JSeparator.VERTICAL);
		s3.setPreferredSize(new Dimension(10,15));
		leftPanel.add(_position);
		leftPanel.add(s1);
		
		rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		rightPanel.add(s2);
		rightPanel.add(_size);
		rightPanel.add(s3);
		rightPanel.add(_scale);
		
		_panel.add(leftPanel,BorderLayout.WEST);
		_panel.add(rightPanel,BorderLayout.CENTER);
		refresh();
	}
	public JPanel getPanel()
	{
		return _panel;
	}
	@Override
	public void refresh() 
	{
		//���������� ���������� �������� ������ � ������� �����������, ������������ �������
		_scale.setText(" " + (int)(_canvas.getScale() * 100) + "% ");
		_size.setText(" " + _resizer.getWidth() + ":" +  _resizer.getHeight());
	}
}
