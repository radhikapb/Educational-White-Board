package paint.model;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import paint.view.View;

/**
 * ���������� ����� ������ �����
 * @author User
 *
 */
public class ColorPicker implements View
{
	public static final Font TOOL_FONT = new java.awt.Font(Font.SANS_SERIF,0, 11); //�����
	
	private static final int BOX_SIZE = 16; 	//������ �������� �����
	private static final int MARGINE = 7;	   //�������� ����� ����������
	
	private static final Point C_OFFSET = new Point(355,20); //�������� �������� �����
	private static final Point FC_OFFSET = new Point(355,233); //�������� �������� ������� �����
	private static final Point SC_OFFSET = new Point(390,233); //�������� �������� ������ �����
	
	private static final Point FL_OFFSET = new Point(355,260); //�������� �������� ������ ������� �����
	private static final Point SL_OFFSET = new Point(380,200); //�������� �������� ������ �����
	
	private static final Dimension DP_OFFSET = new Dimension(20,20); //�������� ������ ����������� ������ 
	private static final Dimension CP_OFFSET = new Dimension(20,20); //�������� ������ �������������� ������ 
	
	private static final Point SF_OFFSET = new Point(355,70); //�������� ������ ������ ������� �����
	private static final Point SS_OFFSET = new Point(355,100); //�������� ������ ������ ������� �����
	private static final Point SP_OFFSET = new Point(355,130); //�������� ������ ������ ����� �������
	private static final Point CB_OFFSET = new Point(355,340); //�������� ������ ��������
	
	private static final Dimension M_PALETE_SIZE = new Dimension(340,70); //������ ��������� �������� �������
	private static final Point M_PALETE_POINT= new Point(5,225);//����� ������������ ��������� �������� �������
	private static final Dimension P_PALETE_SIZE = new Dimension(340,70);//������ ��������� ������������ �������
	private static final Point P_PALETE_POINT= new Point(5,300);//����� ������������ ��������� ������������ �������
	
	private static final Point CHOOSER_POSITION = new Point(0,0);	//������� �������� ColorChooser
	private static final Dimension BUTTON_SIZE = new Dimension(70,25); //������ ������
	private static final Dimension COLOR_SIZE = new Dimension(70,43); //������ ������� ������ �����
	private static final Dimension SPLIT_COLOR_SIZE = new Dimension(35,43); //������ c������� ������� �����
	private static final Dimension LABEL_SIZE = new Dimension(70,50);	//������ ������
	
	private JPanel _secondaryPalete = new JPanel();
	private JPanel _mainPalete = new JPanel();
	private JPanel _colorPanel = new JPanel();
	
	private JLabel _firstLabel = new JLabel("Current colors");
	private JLabel _secondLabel = new JLabel("Second color");
	private JLabel _colorLabel = new JLabel("Color");
	private JButton _setFirst = new JButton("Set first");
	private JButton _setSecond = new JButton("Set second");
	private JButton _setPalete	= new JButton("Set custom");
	private JButton _close = new JButton("Close");
	
	private JDialog _frame = new JDialog();

	private JPanel _firstColor = new JPanel();
	private JPanel _secondColor = new JPanel();
	
	private JPanel[] _customPalete;
	private JPanel[] _standartPalete;
	private JColorChooser _chooser = new JColorChooser();
	private ColorModel _model;
	
	private JPanel _selectedPalete;	//�������� ������� �������������� �������
	private int _selectedIndex; //������ ���������� �������� �������������� �������
	private MouseColorer _colorer = new MouseColorer();	//������, ���������� �� �������� ����� �� ������� ����� �� ���������� ������ �����
	private PaleteSelector _selector = new PaleteSelector(); //������, ���������� �� ����� ������ ���������������� �������

    //@Override
    //public void refresh() {
    //    throw new UnsupportedOperationException("Not supported yet.");
    //}
	/**
	 * ����� ��� ��������� �����, � ����������� �� ������� ���� �� ������
	 * @author User
	 *
	 */
	private class MouseColorer extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent e)
		{	
			//��� ������� ���� �� ������� - �������� ���� ��� ���� � ������ ��������
			_chooser.setColor(((JPanel)e.getSource()).getBackground());
		}
	}
	/**
	 * ����� ��� ������ �������� �������� �� ����������� �������
	 * @author User
	 *
	 */
	private class PaleteSelector extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent e)
		{	
			//��� ������� ���� �� ������� - �������� ������� �������������� �������
			_selectedPalete = (JPanel)e.getSource();
			for(int i = 0; i < _customPalete.length; i++)
			{
				if(_customPalete[i] == _selectedPalete)
				{
					_customPalete[i].setBorder( new LineBorder(Color.black));
					_selectedIndex = i;
				}
				else
				{
					_customPalete[i].setBorder( new SoftBevelBorder(SoftBevelBorder.LOWERED));
				}
			}
		}
	}
	/**
	 * �����, ���������� �� ���������� ����������� �� ������ ��� �������� �����
	 * @author Vit
	 *
	 */
	private class WindowsCloser extends WindowAdapter
	{
		ColorPicker _owner;
		public WindowsCloser (ColorPicker owner)
		{
			_owner = owner;
		}
		@Override
			public void windowClosing(WindowEvent arg0) {
				_model.detach(_owner);
			}
	}
	public ColorPicker(ColorModel model)
	{
		_frame.setLayout(null);
		_frame.setBounds(0, 0, 445, 410);
		_frame.setResizable(false);
		_frame.setTitle("Color picker");
		_model = model;
		_model.attach(this);
		_frame.addWindowListener(new WindowsCloser(this));
		
		_secondaryPalete.setBorder(new javax.swing.border.TitledBorder(null, "Custom colors",0, 0, TOOL_FONT,Color.black));
		_secondaryPalete.setLayout(null);
		_secondaryPalete.setFont(TOOL_FONT);
		_secondaryPalete.setLocation(P_PALETE_POINT);
		_secondaryPalete.setSize(P_PALETE_SIZE);
		
		_mainPalete.setBorder(new javax.swing.border.TitledBorder(null, "Default colors",0, 0, TOOL_FONT,Color.black));
		_mainPalete.setLayout(null);
		_mainPalete.setFont(TOOL_FONT);
		_mainPalete.setLocation(M_PALETE_POINT);
		_mainPalete.setSize(M_PALETE_SIZE);
		
		_chooser.setPreviewPanel(new JPanel());
		AbstractColorChooserPanel[] panels = _chooser.getChooserPanels();
		AbstractColorChooserPanel[] newPanels = new AbstractColorChooserPanel[1];
		newPanels[0] = panels[1];
		_chooser.setChooserPanels(newPanels);
		_chooser.setLocation(CHOOSER_POSITION);
		_chooser.setSize(350,250);
		_chooser.getSelectionModel().addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				_colorPanel.setBackground(_chooser.getColor());
			}
		});
		
		_firstColor.setBackground(Color.black);
		_firstColor.setBorder(new BevelBorder(SoftBevelBorder.LOWERED));
		_firstColor.setSize(SPLIT_COLOR_SIZE);	
		_firstColor.setLocation(FC_OFFSET);
		_firstColor.addMouseListener(_colorer);
		
		_firstLabel.setSize(LABEL_SIZE);
		_firstLabel.setFont(TOOL_FONT);
		_firstLabel.setLocation(FL_OFFSET);
		
		_secondColor.setBackground(Color.white);
		_secondColor.setBorder(new BevelBorder(SoftBevelBorder.LOWERED));
		_secondColor.setSize(SPLIT_COLOR_SIZE);	
		_secondColor.setLocation(SC_OFFSET);
		_secondColor.addMouseListener(_colorer);
		
		_secondLabel.setSize(LABEL_SIZE);
		_secondLabel.setFont(TOOL_FONT);
		_secondLabel.setLocation(SL_OFFSET);
		
		_colorPanel.setBorder(new BevelBorder(SoftBevelBorder.LOWERED));
		_colorPanel.setSize(COLOR_SIZE);	
		_colorPanel.setLocation(C_OFFSET);

		_setFirst.setLocation(SF_OFFSET);
		_setFirst.setFont(TOOL_FONT);
		_setFirst.setMargin(new Insets(0,0,0,0));
		_setFirst.setSize(BUTTON_SIZE);
		_setFirst.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_model.setFirstColor(_colorPanel.getBackground());
			}});
		
		_setSecond.setLocation(SS_OFFSET);
		_setSecond.setSize(BUTTON_SIZE);
		_setSecond.setMargin(new Insets(0,0,0,0));
		_setSecond.setFont(TOOL_FONT);
		_setSecond.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_model.setSecondColor(_colorPanel.getBackground());
			}});
		
		_setPalete.setLocation(SP_OFFSET);
		_setPalete.setSize(BUTTON_SIZE);
		_setPalete.setMargin(new Insets(0,0,0,0));
		_setPalete.setFont(TOOL_FONT);
		_setPalete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(_selectedPalete != null)
				{
					_model.getCustomPalete()[_selectedIndex] = _colorPanel.getBackground();
					_model.save();
					_model.update();
				}
			}});
		
		_close.setLocation(CB_OFFSET);
		_close.setSize(BUTTON_SIZE); 
		_close.setMargin(new Insets(0,0,0,0)); 
		_close.setFont(TOOL_FONT);
		_close.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_frame.setVisible(false);
			}});
		
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
						DP_OFFSET.width , 	//����� �������� ������ ������
						DP_OFFSET.height, BOX_SIZE, BOX_SIZE);
				spwidth = spwidth + BOX_SIZE + MARGINE; //����������� ������ �������, �������� ��������
			}
			else
			{
				//������ �������� ������� ������� - ����
				_standartPalete[i].setBounds(
						spwidth + 
						DP_OFFSET.width ,
						DP_OFFSET.height + 
						BOX_SIZE + 
						MARGINE, BOX_SIZE, BOX_SIZE);	
			}
			_standartPalete[i].setBackground(model.getStandartPalete()[i]);
			_standartPalete[i].setBorder( new SoftBevelBorder(SoftBevelBorder.LOWERED));
			_standartPalete[i].addMouseListener( _colorer);
			_mainPalete.add(_standartPalete[i]);
			
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
						pwidth +			//����� �������������� �������
						CP_OFFSET.width,	//������ �������������� �������
						CP_OFFSET.height, BOX_SIZE, BOX_SIZE);
				pwidth = pwidth + BOX_SIZE + MARGINE; //����������� ������ �������, �������� ��������
			}
			else
			{
				//������ �������� ������� ������� - �����
				_customPalete[i].setBounds(

						pwidth +			//����� �������������� �������
						CP_OFFSET.width  ,	//������ �������������� �������	
						CP_OFFSET.height + 
						BOX_SIZE +
						MARGINE 
						, BOX_SIZE, BOX_SIZE);
			}
			_customPalete[i].setBorder( new SoftBevelBorder(SoftBevelBorder.LOWERED));
			_customPalete[i].setBackground(model.getCustomPalete()[i]);
			_customPalete[i].addMouseListener( _colorer);
			_customPalete[i].addMouseListener(_selector);
			_secondaryPalete.add(_customPalete[i]);
			even = !even;
		}
		_customPalete[1].setBorder(  new LineBorder(Color.black));
		_selectedPalete = _customPalete[1];
		_selectedIndex = 1;
	
	
		_frame.add(_secondaryPalete);
		_frame.add(_mainPalete);
		_frame.add(_firstColor);
		_frame.add(_secondColor);
		_frame.add(_firstLabel);
		_frame.add(_colorLabel);
		_frame.add(_colorPanel);
		_frame.add(_setFirst);
		_frame.add(_setSecond);
		_frame.add(_setPalete);
		_frame.add(_close);	_frame.add(_chooser);
	}
	public JDialog getFrame()
	{
		return _frame;
	}
	
    @Override
	public void refresh() {
		//��� ���������� ��������������� ��������� � ����������� ������� 
		//(���������� � ��������������� ����)
		for(int i = 0 ; i < _customPalete.length; i++ )
			_customPalete[i].setBackground(_model.getCustomPalete()[i]);
		for(int i = 0 ; i < _standartPalete.length; i++ )
			_standartPalete[i].setBackground(_model.getStandartPalete()[i]);
		_secondColor.setBackground(_model.getSecondColor());
		_firstColor.setBackground(_model.getFirstColor());
	}
}
