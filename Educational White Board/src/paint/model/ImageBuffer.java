package paint.model;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.LinkedList;


/**
 * 
 * @author User
 *
 */

public class ImageBuffer extends Model
{
	private LinkedList<BufferedImage> _imageStack = new LinkedList<>();	
	private int _bufferPosition = 0;
	private int _bufferLength = 20;
	private CanvasImage _cimage;
	/**
	 * ������� ������ ����� �����������
	 * @param source �������� �����
	 * @return �����
	 */
	private static BufferedImage copyImage(BufferedImage source)
	{
		WritableRaster raster = source.copyData(null);
		//���������� �������, ��������� � ������������� �����������, ��� �������� �����
        BufferedImage copy = new BufferedImage(source.getColorModel(),
        		raster,
        		source.isAlphaPremultiplied(),
        		null);
        return copy;
	}
	/**
	 * �����������
	 * @param canvasImage �������� �����������, ������� ����� ��������������
	 */
	public ImageBuffer(CanvasImage canvasImage)
	{
		_cimage = canvasImage;
		flushBuffer();
	}
	/**
	 * ������� ��� ������ � ������
	 */
	public void flushBuffer()
	{
		//���� ���� ����, ������� � ���� ������ ������� �����������
		_imageStack.clear();
		_imageStack.add(copyImage(_cimage.getImage()));
		_bufferPosition = 0;
		update();
	}
	/**
	 * ��������� ������� ����������� � ������
	 */
	public void turn()
	{
		if(_bufferPosition >= _bufferLength)
		{		
			_imageStack.add(copyImage(_cimage.getImage()));
			_imageStack.removeFirst();
		}
		else
		{		
			_bufferPosition++;
			_imageStack.add(_bufferPosition,copyImage(_cimage.getImage()));
			//������� ��� ������ �������� �� ����� (������� redo)
			int quantity =  _imageStack.size() - _bufferPosition - 1;
			for(int i = 0; i < quantity ; i++)
			{
				_imageStack.remove(_bufferPosition + 1);
			}		
		}
		update();
	}
	/**
	 * ���������� ������� ����������� � ������
	 */
	public void undo()
	{
		if(_bufferPosition > 0)
		{
			_bufferPosition--;
			BufferedImage im = _imageStack.get(_bufferPosition);
			_cimage.setImage(copyImage(im));
			update();
		}
	}
	/**
	 * ���������� ��������� ����������� � ������ (�������� ������� � �������� �����������)
	 */
	public void redo()
	{
		//���� ������� ����������� � ������ ������ ����� ����� - 1 - ����� ����������� ����� ��������
		if(_bufferPosition < _imageStack.size())
		{
			_bufferPosition++;
			BufferedImage im = _imageStack.get(_bufferPosition);
			_cimage.setImage(copyImage(im));
			update();
		}
	}
	/**
	 * ����������, ������� �������� ������������ ��� �������� ��������
	 * @return ����� �������� ��������
	 */
	public int undoLeft()
	{
		return _bufferPosition;
	}
	/**
	 * ����������, ������� �������� ������������ ��� �������� ��������������
	 * @return ����� �������� ��������������
	 */
	public int redoLeft()
	{
		return (_imageStack.size() - 1 - _bufferPosition);
	}
	/**
	 * ������������� ����� ������ �����������
	 * @param value ����� ����� ������
	 */
	 public void resize(int value)
	{
		//���� ����� ����� ����� ������ ������� ������� ����� - ����� ������� �������� �����, �� ������������ � ����� �����
		if(value < _bufferPosition)
		{
			int difference = _bufferPosition - value;
			for(int i = 0; i < difference; i++)
				_imageStack.remove(0);
			_bufferPosition = value;
		}
		_bufferLength = value;
		update();
	}
}
