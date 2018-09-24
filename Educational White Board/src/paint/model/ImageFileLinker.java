package paint.model;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageFileLinker extends Model
{
	private String _fileName;
	private String _format;
	private boolean _fileLinked;

        
	public void save(CanvasImage image) throws IOException
	{
                
		if(!_fileLinked)
			throw new IOException("image not linked");
                File f = new File(_fileName);
                ImageIO.write((RenderedImage) image.getImage(), _format, f);
	}
	
	public void saveas(String filename, String format, CanvasImage image) throws IOException
	{
                File f = new File(filename);
                boolean flag = ImageIO.write((RenderedImage) image.getImage(), format, f);
                _fileLinked = true;
		_fileName = filename;
		_format = format;
	}
	
	public void load(String filename, String format, CanvasImage image) throws IOException
	{
		File f = new File(filename);
		BufferedImage im = ImageIO.read(f);
		_fileLinked = true;								//����������� ����������� � �����
		_fileName = filename;
		_format = format;
		image.setImage(im);
	}
	
	public void unlink()
	{
		_fileLinked = false;
	}
	
	public boolean isLinked()
	{
		return _fileLinked;
	}
	
	public String getFileName()
	{
		return _fileName;
	}
}
