package paint.model;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 *
 * @author YANK
 *
 */
public class CanvasImage extends Model
{
	public BufferedImage _image;		
	public Color _background;	
	/**
	 *
	 */
	public CanvasImage(Dimension size, int type, Color background)
	{
		int width = (int) size.getWidth();
		int height = (int) size.getHeight();
		
		
		java.io.ObjectInputStream stream;
		FileInputStream input = null;
		try { 
			input = new FileInputStream(new File("image.dat"));
			stream = new java.io.ObjectInputStream(input );		
			Point point = (Point) stream.readObject();
			width = point.x;
			height = point.y;
			input.close();
		}
		catch (FileNotFoundException e) 
		{
			System.out.println("File not found image.dat");
		
		} 
		catch (IOException e) 
		{
		
			System.out.println("File reading error image.dat");
			try
			{
				if(input != null)
					input.close();
			} 
			catch (IOException e1){
                        }
		}
		catch (ClassNotFoundException e) 
		{
			System.out.println("File reading error image.dat");
			try
			{
				if(input != null)
					input.close();
			} 
			catch (IOException e1) 
			{
				
			}
		}
		
		_image = new BufferedImage(width,height, type);	
		_background = background;
		Graphics gr = _image.getGraphics();
		gr.setColor(background);
		gr.fillRect(0, 0, width, height);	
		update();
	}
	/**
	 * 
	 * @param width	- 
	 * @param height - 
	 * @param type	- 
	 * @param background - 
	 */
	public void recreate(Dimension size, int type, Color background)
	{
		_image = new BufferedImage(size.width,size.height, type);	//������� ����������� ������� ����
		_background = background;
		Graphics gr = _image.getGraphics();
		gr.setColor(background);
		gr.fillRect(0, 0, size.width, size.height);
		update();
	}
	/**
	 *
	 */
	public void recreate()
	{
		_image = new BufferedImage(_image.getWidth(),_image.getHeight(), _image.getType());	//������� ����������� ������� ����
		Graphics gr = _image.getGraphics();
		gr.setColor(_background);
		gr.fillRect(0, 0, _image.getWidth(), _image.getHeight());
		save();
		update();
	}
	/**
	 *
	 * @return
	 */
	public BufferedImage getImage()
	{	
		return _image;
	}
	/**
	 * 
	 */
	public void setImage(BufferedImage image)
	{
		_image = image;
		save();
		update();
	}
	/**
	 * 
	 * @param size 
	 */
	public void resize(int width, int height)
	{
		BufferedImage im = new BufferedImage(width,height, _image.getType());	//������� ����������� ������� ����
		Graphics gr = im.getGraphics();
		gr.setColor(_background);
		gr.fillRect(0, 0, width, height);
		gr.drawImage(_image, 0, 0, null);	
		_image = im;
		save();
		update();
	}
	/**
	 *
	 * @param type
	 */
	public void retype(int type)
	{
		BufferedImage im = new BufferedImage(_image.getWidth(),_image.getHeight(), type);	//������� ����������� ������� ����
		Graphics gr = im.getGraphics();
		gr.drawImage(_image,0,0,null);	
		_image = im;
		update();
	}
	/**
	 * 
	 */
	public void save()
	{
		java.io.ObjectOutputStream stream;
		FileOutputStream output = null;
		try 
		{
			output = new FileOutputStream(new File("image.dat"));
			stream = new java.io.ObjectOutputStream(output);
			stream.writeObject(new Point(_image.getWidth(),_image.getHeight()));	
			output.close();
		} 
		catch (FileNotFoundException e) 
		{
	
		} 
		catch (IOException e) 
		{
	
			try
			{
				if(output != null)
					output.close();
			} 
			catch (IOException e1) 
			{
	
			}
		}
	}
}
