package paint.view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import paint.model.ColorModel;

/**
 * ������ - ������� �����
 * @author User
 *
 */
public class ColorView implements View
{
	
	private static final int BOX_SIZE = 16; //������ �������� �����
	private static final int CURRENT_BOX_SIZE = 40; //������ ������ ��������� �����
	private static final int MARGINE = 2;	   //�������� ����� ����������
	
	private static final Dimension FC_OFFSET = new Dimension(3,4); //�������� �������� ������� �����
	private static final Dimension SC_OFFSET = new Dimension(20,4); //�������� �������� ������ �����
	
	private static final Dimension SP_OFFSET = new Dimension(60,5); //�������� ������ �������� ������
	private static final Dimension CP_OFFSET = new Dimension(20,5); //�������� ������ �������������� ������ ������������ ������� �������� ������
	
	private static final Dimension PANEL_SIZE = new Dimension(100,45); //���������������� ������ ������ �����

    //@Override
    //public void refresh() {
    //    throw new UnsupportedOperationException("Not supported yet.");
    //}
	
	/**
	 * ����� ��� ������������� ������� ���� �� �������� ������ ������
	 * @author User
	 *
	 */
	private class MouseColorer extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent e)
		{	
			//��� ������� ���� �� ������� ������ ������ - �������� ����
			Color newcolor = ((JPanel)e.getSource()).getBackground();
			if(e.getButton() == 1)
				_model.setFirstColor(newcolor);
			if(e.getButton() == 3)
				_model.setSecondColor(newcolor);
		}
	}
	
	private ColorModel _model;
	private JPanel _colorPanel;
	private JPanel _currentColorPanel;
	private JPanel _firstColor;
	private JPanel _secondColor;
	private JPanel[] _standartPalete;
	private JPanel[] _customPalete;
	private MouseColorer colorer = new MouseColorer();
	/**
	 * �������� ������� �����
	 * @author User
	 *
	 */
	public ColorView(ColorModel model)
	{
		model.attach(this);	
		this._model = model;
		//�������� ������ ������ ������
		_colorPanel = new JPanel();
		_colorPanel.setLayout(null);
		_colorPanel.setPreferredSize(PANEL_SIZE);
		_colorPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
		//�������� ������, ���������� 2 ����� (�������� � ��������������)
		_currentColorPanel = new JPanel();
		_currentColorPanel.setLayout(null);
		_currentColorPanel.setBackground(new Color(235,235,235));
		_currentColorPanel.setBorder(null);
		_currentColorPanel.setBounds(MARGINE, MARGINE, CURRENT_BOX_SIZE, CURRENT_BOX_SIZE);
		
		_firstColor = new JPanel();
		_firstColor.setBackground(Color.black);
		_firstColor.setBorder(new BevelBorder(BevelBorder.LOWERED));
		_firstColor.setBounds(FC_OFFSET.width ,FC_OFFSET.height, BOX_SIZE,BOX_SIZE * 2);
		
		_secondColor = new JPanel();
		_secondColor.setBackground(Color.white);
		_secondColor.setBorder(new BevelBorder(BevelBorder.LOWERED));
		_secondColor.setBounds(SC_OFFSET.width,SC_OFFSET.height,BOX_SIZE,BOX_SIZE * 2);
		
		
		//��������� ��� ������� �������� �������
		_standartPalete = new JPanel[model.getStandartPalete().length];
		int spwidth = 0; //������� ������ �������� �������
		boolean even = false; //���� ��������
		
		for(int i = 0; i < _standartPalete.length; i++)
		{
			_standartPalete[i] = new JPanel();
			//����������� �������� ������� ���� �� ������, �������� �������� � ������
			if(even)
			{
				//������ ������ ������� ������� - �����
				_standartPalete[i].setBounds(
						spwidth + 			//������� ������ ������ ������
						SP_OFFSET.width , 	//����� �������� ������ ������
						SP_OFFSET.height, BOX_SIZE, BOX_SIZE);
				spwidth = spwidth + BOX_SIZE + MARGINE; //����������� ������ �������, �������� ��������
			}
			else
			{
				//������ �������� ������� ������� - �����
				_standartPalete[i].setBounds(
						spwidth + 
						SP_OFFSET.width ,
						SP_OFFSET.height + 
						BOX_SIZE + 
						MARGINE, BOX_SIZE, BOX_SIZE);	
			}
			_standartPalete[i].setBackground(model.getStandartPalete()[i]);
			_standartPalete[i].setBorder( new SoftBevelBorder(SoftBevelBorder.LOWERED));
			_standartPalete[i].addMouseListener(colorer); //��������� ��������� �������
			_colorPanel.add(_standartPalete[i]);
			
			even = !even;
		}
		
		//��������� ��� ������� �������������� �������
		int pwidth = 0; //������� ������ �������������� �������
		even = false;
		_customPalete = new JPanel[model.getCustomPalete().length];
		for(int i = 0; i < _customPalete.length; i++)
		{
			_customPalete[i] = new JPanel();
			//������ �������� ������� ������� - �����
			//������ ������ - ����
			if(even)	
			{
				//������ ������ ������� ������� - �����
				_customPalete[i].setBounds(
						spwidth + 			//����� �������� �������
						SP_OFFSET.width +	//������ �������� �������
						pwidth +			//����� �������������� �������
						CP_OFFSET.width,	//������ �������������� �������
						SP_OFFSET.height, BOX_SIZE, BOX_SIZE);
				pwidth = pwidth + BOX_SIZE + MARGINE; //����������� ������ �������, �������� ��������
			}
			else
			{
				//������ �������� ������� ������� - �����
				_customPalete[i].setBounds(
						spwidth +  			//����� �������� �������
						SP_OFFSET.width +	//������ �������� �������
						pwidth +			//����� �������������� �������
						CP_OFFSET.width  ,	//������ �������������� �������	
						CP_OFFSET.height + 
						BOX_SIZE +
						MARGINE 
						, BOX_SIZE, BOX_SIZE);
				
			}
			_customPalete[i].setBorder( new SoftBevelBorder(SoftBevelBorder.LOWERED));
			_customPalete[i].setBackground(model.getCustomPalete()[i]);
			_customPalete[i].addMouseListener(colorer); //��������� ��������� �������
			_colorPanel.add(_customPalete[i]);
			even = !even;
		}
                 
                
		_currentColorPanel.add(_firstColor);
		_currentColorPanel.add(_secondColor);
		
		_colorPanel.add(_currentColorPanel);
	}
	public JPanel getPanel()
	{
		return _colorPanel;
	}
	/**
	 * ���������� ���� �����
	 */
	@Override
	public void refresh() 
	{
                
		for(int i = 0 ; i < _customPalete.length; i++ )
			_customPalete[i].setBackground(_model.getCustomPalete()[i]);
		for(int i = 0 ; i < _standartPalete.length; i++ )
			_standartPalete[i].setBackground(_model.getStandartPalete()[i]);
		_secondColor.setBackground(_model.getSecondColor());
		_firstColor.setBackground(_model.getFirstColor());
		_currentColorPanel.setComponentZOrder(_firstColor, 0);
		_currentColorPanel.setComponentZOrder(_secondColor, 1);
		_currentColorPanel.repaint();
                _currentColorPanel.revalidate();
        }
}
