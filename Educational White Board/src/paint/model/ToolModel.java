package paint.model;

import paint.toolPanels.*;
import paint.tools.*;
import paint.view.ToolPropertyPanel;
/**
 * ��������� ������������
 * @author Vit
 *
 */
public final class ToolModel extends Model
{
	private Tool _currentTool;			//������ �������� �����������
	private Tools _toolName;			//��� �������� �����������
	private ToolPropertyPanel _propertyPanel; //������ ��������, ��������������� �����������
	private CanvasModel _canvas;		//������ ������� ���������
	private ColorModel _color;			//������ �����
	private ImageBuffer _buffer;		//����� �����������
	/**
	 * ������ ������������
	 * @author Vit
	 *
	 */
	public enum Tools
	{
		Arrow,
		Glass,
		Brush,
		Pen,
		Eraser,
		Spray,
		Rectangle,
		Circle,
		Line,
		PaintBucket,
		Sampler,
		Selector,
		Text
	}
	public ToolModel(CanvasModel canvas, ColorModel color, ImageBuffer buffer)
	{
		_canvas = canvas;
		_color = color;
		_buffer = buffer;
		activateTool(Tools.Arrow);
	}
	public void activateTool(Tools tool)
	{	
		_toolName = tool;
		//����� ���������� ������ ����������� - ������������ ������
		if(_currentTool != null)
			_currentTool.deactivate();
		switch(tool)
		{
			//���������� ����������� ������ ����������� - 
			//������� ������ �����������, � ���-�� ������ ������� �����������
			//���������� ����������
			case Glass:
			{
				GlassTool glasstool =  new GlassTool(_canvas);
				_currentTool = glasstool;
				_propertyPanel = new GlassToolPropertyPanel(glasstool);
				_currentTool.activate();
				break;
			}
			case Arrow:
			{		
				ArrowTool arrowtool =  new ArrowTool(_canvas);
				_currentTool = arrowtool;
				_propertyPanel = new ArrowToolPropertyPanel(arrowtool);
				_currentTool.activate();
				break;
			}
			case Brush:
			{		
				BrushTool brushtool =  new BrushTool(_canvas,_color,_buffer);
				_currentTool = brushtool;
				_propertyPanel = new BrushToolPropertyPanel(brushtool);
				_currentTool.activate();
				break;
			}
			case Pen:
			{
				PenTool pentool = new PenTool(_canvas,_color,_buffer);
				_currentTool = pentool;
				_propertyPanel = new PenToolPropertyPanel(pentool);
				_currentTool.activate();
				break;
			}
			case Eraser:
			{
				EraserTool erasertool = new EraserTool(_canvas,_buffer);
				_currentTool = erasertool;
				_propertyPanel = new EraserToolPropertyPanel(erasertool);
				_currentTool.activate();
				break;
			}
			case Spray:
			{
				SprayTool spraytool = new SprayTool(_canvas,_color,_buffer);
				_currentTool = spraytool;
				_propertyPanel = new SprayToolPropertyPanel(spraytool);
				_currentTool.activate();
				break;
			}
			case Rectangle:
			{
				RectangleTool rectool = new RectangleTool(_canvas,_color,_buffer);
				_currentTool = rectool;
				_propertyPanel = new RectangleToolPropertyPanel(rectool);
				_currentTool.activate();
				break;
			}
			case Circle:
			{
				CircleTool cirtool = new CircleTool(_canvas,_color,_buffer);
				_currentTool = cirtool;
				_propertyPanel = new CircleToolPropertyPanel(cirtool);
				_currentTool.activate();
				break;
			}
			case Line:
			{
				LineTool linetool = new LineTool(_canvas,_color,_buffer);
				_currentTool = linetool;
				_propertyPanel = new LineToolPropertyPanel(linetool);
				_currentTool.activate();
				break;
			}
			case PaintBucket:
			{
				PaintBucketTool buckettool = new PaintBucketTool(_canvas,_color,_buffer);
				_currentTool = buckettool;
				_propertyPanel = new PaintBucketToolPropertyPanel(buckettool);
				_currentTool.activate();
				break;
			}
			case Sampler:
			{
				SamplerTool samplertool = new SamplerTool(_canvas,_color);
				_currentTool = samplertool;
				_propertyPanel = new SamplerToolPropertyPanel(samplertool);
				_currentTool.activate();
				break;
			}
			case Selector:
			{
				SelectorTool selectortool = new SelectorTool(_canvas,_color, _buffer);
				_currentTool = selectortool;
				_propertyPanel = new SelectorToolPropertyPanel(selectortool );
				_currentTool.activate();
				break;
			}
			case Text:
			{
				TextTool textool = new TextTool(_canvas,_color, _buffer);
				_currentTool = textool;
				_propertyPanel = new TextToolPropertyPanel(textool );
				_currentTool.activate();
				break;
			}
		}
		update();
	}
	/**
	 * ���������� ������� ����������
	 * @return
	 */
	public Tool getCurrentTool()
	{
		return _currentTool;
	}
	/**
	 * ���������� ��� �����������
	 * @return
	 */
	public Tools getToolType()
	{
		return _toolName;
	}
	/**
	 * ���������� ������� ������ ��������� �����������
	 * @return
	 */
	public ToolPropertyPanel getToolPropertyPanel()
	{
		return _propertyPanel;
	}
}
