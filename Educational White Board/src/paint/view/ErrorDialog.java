package paint.view;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * ����� ������� ��������, ���������� �� �������
 * @author Vit
 *
 */
public class ErrorDialog 
{
	public static final Font TOOL_FONT = new java.awt.Font(Font.SANS_SERIF,0, 11); //�����
	/**
	 * ������ ��������� �� ������ �� �����
	 * @param title - ��������� �������
	 * @param text	- ����� ������
	 * @param icon - ������ ���������, ��� null, ���� ����������� ������ java
	 */
	public static void showSimpleDialog(Point location, String title, String text, ImageIcon icon)
	{
		JFrame dialog = new JFrame();
		dialog.setTitle(title);
		if(location != null)
			dialog.setLocation(location);
		dialog.setFont(TOOL_FONT);
		dialog.setSize(text.length() * 10 + 20,100);
		dialog.setLayout(null);
		if(icon != null)
			dialog.setIconImage(icon.getImage());
		JLabel label = new JLabel(text);
		label.setFont(TOOL_FONT);
		label.setBounds(20,20,text.length() * 10, 20);
		dialog.add(label);
		//������������� ��������� �� ������
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment() ;
		int height = env.getDefaultScreenDevice().getDisplayMode().getHeight();
		int width = env.getDefaultScreenDevice().getDisplayMode().getWidth();
		dialog.setLocation(width/2,height/2);
		dialog.setVisible(true);
	}
}
