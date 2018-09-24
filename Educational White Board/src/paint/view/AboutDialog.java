package paint.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/** 
 * About
 * @author Vit
 *
 */
public class AboutDialog
{
	public static final Font TOOL_FONT = new java.awt.Font(Font.SANS_SERIF,0, 11); 
	private JDialog dialog = new JDialog();		//���� ���� �������
	private JLabel name = new JLabel();			//������� ��������
	private JLabel me = new JLabel();			//�
	private JLabel mail = new JLabel();			//�����
	private JLabel eclipse = new JLabel();		//�� IDE
	/**
	 * �����������
	 */
	public AboutDialog()
	{
		dialog.setLayout(new FlowLayout(FlowLayout.CENTER));
		dialog.setTitle("About");
		dialog.setIconImage(new ImageIcon(this.getClass().getResource("/icons/about.PNG")).getImage());
		dialog.setResizable(false);


		dialog.setSize(new Dimension(150,120));
		name.setText("\'EDUCATIONAL WHITE BOARD\'");
		me.setText("YANK");
		mail.setText("priyank.bambharolia@gmail.com");
		eclipse.setText("IDE Netbeans");
		name.setFont(TOOL_FONT);
		me.setFont(TOOL_FONT);
		mail.setFont(TOOL_FONT);
		eclipse.setFont(TOOL_FONT);
		dialog.setFont(TOOL_FONT);
		dialog.add(name);
		dialog.add(me);
		dialog.add(mail);
		dialog.add(eclipse);
	}
	/**
	 * ���������� ������ 
	 * @param owner
	 */
	public void showDialog(final JFrame owner)
	{
		owner.setEnabled(true);
		dialog.setLocation(owner.getLocation().y + owner.getWidth() / 2, owner.getLocation().x + owner.getHeight()/2);
		dialog.setVisible(true);
		dialog.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosed(WindowEvent arg0) {
				owner.setEnabled(true);
			}
		});
		
	}
}
