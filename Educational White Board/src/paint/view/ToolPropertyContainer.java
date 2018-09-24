package paint.view;
import java.awt.Dimension;
import javax.swing.JToolBar;
import paint.model.ToolModel;
/**
 * �����, ���������� �� ��������� ������ ������� �����������, �� ������������� ����� ������ (������������ ������ ������� ����������� ����������� ��� ��� ������)
 * @author Vit
 *
 */
public class ToolPropertyContainer implements View
{
	private JToolBar propertyPanel = new JToolBar();		//������ ��� ���������� ������ ������� ������������
	private ToolModel _model;							//������ ������������	
	/**
	 * �����������
	 * @param model ������ ������������
	 */
	public ToolPropertyContainer(ToolModel model)
	{
		propertyPanel.setOrientation(JToolBar.HORIZONTAL);
		propertyPanel.setPreferredSize(new Dimension(100,32));
		propertyPanel.setFloatable(false);
		_model = model;
		_model.attach(this);
		refresh();
	}
	/**
	 * ���������� ������ ������� ����������� 
	 * @return ������ ������� ����������� 
	 */
	public JToolBar getPanel()
	{
		return propertyPanel;
	}
	@Override
	public void refresh() 
	{
		//���������� ������, �������������� �������� �����������
		if(propertyPanel.getComponents().length > 0) 
			propertyPanel.remove(0);
		JToolBar newPanel = _model.getToolPropertyPanel().getPanel();
		newPanel.setBounds(2,2,newPanel.getPreferredSize().width, newPanel.getPreferredSize().height);
		newPanel.setBorderPainted(false);
		propertyPanel.add(_model.getToolPropertyPanel().getPanel());
		propertyPanel.validate();
		propertyPanel.repaint();
	}
}
