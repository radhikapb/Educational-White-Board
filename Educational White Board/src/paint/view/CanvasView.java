package paint.view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.RenderedImage;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import paint.model.CanvasModel;
import paint.model.CanvasResizer;
import paint.model.MetaLayer;

public class CanvasView implements View
{
	private CanvasModel _model;								//������ �������
	private JScrollPane _scrolls = new JScrollPane();		//������ ��������
	private JPanel _panel = new MyPanel();					//������ ���������
	private InnerPanel _inner = new InnerPanel();			//������ ������ ������ ��������
	private MyTextField _textfield = new MyTextField("");   //��������� ����, ��� ������ ������
	
	/**
	 * ����������� ��������� ����
	 * @author User
	 *
	 */
	private class MyTextField extends JTextField
	{
		private static final long serialVersionUID = 1L;
		public MyTextField(String text)
		{
			super(text);
		}
		public void paint (Graphics gr)
		{
			super.paint(gr);
		}
	}
	/**
	 * �������� ������ ���������
	 * @author User
	 *
	 */
	private class MyPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;
		@Override
		public void paint (Graphics gr)
		{
			super.paint(gr);
			//������ ����������� � ����������� � ��������� �����������
			AffineTransform at =  AffineTransform.getScaleInstance(_model.getScale(),_model.getScale());
			((java.awt.Graphics2D)gr).drawRenderedImage((RenderedImage) _model.getImage().getImage(), at);
		
			//������ ����������� ����������� ���������� �����
			Iterable<MetaLayer> layers = _model.getLayers();
			for(MetaLayer layer : layers)
			{
				((java.awt.Graphics2D)gr).drawRenderedImage((RenderedImage) layer.getImage(), at);
			}
		}
	}
	/**
	 * ������ ���������� ������ ���������, ��������� �������� �� ���� ����� CanvasResizer - �
	 * @author Vit
	 *
	 */
	public class InnerPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;
		private int _x;	   //���������� �� ������� �������� ����� ��������� �������		
		private int _y;
		private int _mode; //����� ��������� : 0 - ������ �� ��������, 1 - �������� ��� ����� ��������� �������,
							// 2 - �������� ������������ �����, 3 - �������� ��������������� �����
		public void setMode(int mode)
		{
			_mode = mode;
		}
		/**
		 * ������������� ���������� ��� ��������� �����, ������������ ��������� �������
		 * @param x
		 * @param y
		 */
		public void setCoordinates(int x, int y)
		{
			_x = x;
			_y = y;
		}
		@Override
		public void paint (Graphics gr)
		{
			super.paint(gr);
			if(_mode != 0)
			{
				gr.drawLine(_x, _y, _x ,0);	
				gr.drawLine(_x, _y, 0, _y);	
			}
		}
	}
	/**
	 * ����������� ����������� ������� ���������
	 * @param model ������ ������� ���������
	 * @param resizer ������ ��� ��������������� ��������� ������� ������� ���������
	 */
	public CanvasView(CanvasModel model,CanvasResizer resizer)
	{
		_model = model;
		_model.attach(this);
		_model.setDrawingPane(_panel);
		_model.setTextLabel(_textfield);
		//resizer.setPanels(_panel, _inner);
		//�������������� ���
		_inner.setLayout(null);
		_inner.setBackground(Color.LIGHT_GRAY);
		_panel.setBackground(Color.white);
		
		
		_panel.setBorder(null);
		_inner.add(_panel);
		_textfield.setLocation(0,0);
		_textfield.setSize(0,0);
		_textfield.setBorder(null);
		_textfield.setOpaque(false);
		_inner.add(_textfield);
		_inner.setComponentZOrder(_panel, 1);
		_inner.setComponentZOrder(_textfield, 0);
		_scrolls.setViewportView(_inner);	
		//������� ��������� ������������ ������ ���� � ��������� ����, ����������� �������
		MouseWheelListener[] listeners = _scrolls.getMouseWheelListeners();
		for(MouseWheelListener listener : listeners)
			_scrolls.removeMouseWheelListener(listener);
		_scrolls.addMouseWheelListener(new MouseWheelListener()
		{
			@Override
			public void mouseWheelMoved(MouseWheelEvent arg0)
			{
				//��������� ������� � ����������� �������� ��� ����������� ������� ������ ����
				_scrolls.getVerticalScrollBar().setValue((int) (_scrolls.getVerticalScrollBar().getValue() + arg0.getWheelRotation() * _model.getScale()) );
			}
		});
		
		_scrolls.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener()
		{
			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) 
			{
				Point p = _model.getViewPosition();
				p.y = arg0.getValue();		//������ �������� ������� �� ������, �� ������� �������������� ���������� !
			}
		});
		_scrolls.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener()
		{
			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) 
			{
				Point p = _model.getViewPosition();
				p.x = arg0.getValue();	 //������ �������� ������� �� ������, �� ������� �������������� ���������� !
			}
		});
		_scrolls.addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent arg0) 
			{
				int width = _scrolls.getWidth();
				if(width < 0)
					width = 0;
				int height = _scrolls.getHeight();
				if(height < 0)
					height = 0;
				_model.setSize(width,height);
			}
		});
		refresh();
	}
	/**
	 * ���������� �������� �������� ������� ����
	 * @return �������� �������� ����������� �� ����� ���������
	 */
	public JScrollPane getPane()
	{
		return _scrolls;
	}
	@Override
	public void refresh() 
	{
		int positionX = _model.getViewPosition().x;	//���������� �������� ��������� ������� ���� (��� ��� ��� ����� �������� ��� ����������)
		int positionY = _model.getViewPosition().y;
		
		_panel.setSize(_model.getScaledWidth(), _model.getScaledHeight());							//������� ������ ��������� �������
		
		_inner.setPreferredSize(new Dimension(_panel.getWidth(),_panel.getHeight()));				//������� ������ ������ ��� ����� �����
		//�������� ������� ��������, ����� �������������� ������ ������ �����, ��� ������ �������� (���� ������� �������)
		_inner.validate();
		_scrolls.validate();
		//���-�� ������������� ��� ������ ���� ������ ��������, � ������� ��� � 50 ��� ������
		_scrolls.getVerticalScrollBar().setBlockIncrement((int)_model.getScale());
		_scrolls.getHorizontalScrollBar().setBlockIncrement((int)_model.getScale() * 50);
		_scrolls.getVerticalScrollBar().setBlockIncrement((int)_model.getScale() * 50);
		_scrolls.getHorizontalScrollBar().setUnitIncrement((int)_model.getScale());
		_scrolls.getVerticalScrollBar().setUnitIncrement((int)_model.getScale());
		//������������� ������� ����
		_scrolls.getVerticalScrollBar().setValue(positionY);
		_scrolls.getHorizontalScrollBar().setValue(positionX);
		_scrolls.validate();
															//���������� ������ ����� ���������
		_panel.repaint();
	}
}