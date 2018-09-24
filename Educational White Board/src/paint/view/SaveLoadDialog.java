package paint.view;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import paint.model.CanvasImage;
import paint.model.ImageFileLinker;
/**
 *
 * @author PRIYANK
 */

public class SaveLoadDialog 
{
	
	public static void saveImageAs(CanvasImage image, ImageFileLinker linker, JFrame mainWindow)
	{
		   
            JFileChooser FS=new JFileChooser();
            FS.addChoosableFileFilter(new pngFilter());
            FS.addChoosableFileFilter(new jpgFilter());
            FS.addChoosableFileFilter(new bmpFilter());
            FS.addChoosableFileFilter(new gifFilter());
            FS.addChoosableFileFilter(new imageFilter());
            
            int retrival=FS.showSaveDialog(mainWindow);
 
            if (retrival == JFileChooser.APPROVE_OPTION) 
            {
                String dir = FS.getCurrentDirectory().getAbsolutePath();
                String file = FS.getSelectedFile().getName();
                
                if(!(file.contains(".jpg") | file.contains(".png") | file.contains(".bmp") | file.contains(".jpeg") | file.contains(".gif")))
                    file += ".png";
                
                if(file == null)
			return;
                
                try {
                    String[] tokens = file.split("\\.");
                    linker.saveas(dir + "\\" + file , tokens[tokens.length - 1], image);                 
                } catch (Exception ex) {
                    System.out.println("exception: "+ex.toString());
                }
            }
            else {
                                ErrorDialog.showSimpleDialog(new Point(FS.getLocation().x + FS.getSize().width/2 ,FS.getLocation().y + FS.getSize().height/2 )
				,"ERROR", "Error loading image", null);
            }
	
        }
	public static void loadImage(CanvasImage image, ImageFileLinker linker, JFrame mainWindow)
	{
	
            JFileChooser FO=new JFileChooser();
            FO.addChoosableFileFilter(new jpgFilter());
            FO.addChoosableFileFilter(new pngFilter());
            FO.addChoosableFileFilter(new bmpFilter());
            FO.addChoosableFileFilter(new gifFilter());
            FO.addChoosableFileFilter(new imageFilter());
            
            int retrival=FO.showOpenDialog(mainWindow);
            
            if (retrival == JFileChooser.APPROVE_OPTION) 
            {
                String dir = FO.getCurrentDirectory().getAbsolutePath();
                String file = FO.getSelectedFile().getName();
                
		if(file == null)
			return;
                
		String[] tokens = file.split("\\.");
		if(tokens.length > 1)
		{
                    try
                    {
                        linker.load(dir + "\\" + file , tokens[tokens.length - 1], image);
                    }
                    catch(IOException e){
                        System.out.println(e.toString());
                    }
                }
		else 
		{
                    ErrorDialog.showSimpleDialog(new Point(FO.getLocation().x + FO.getSize().width/2 ,FO.getLocation().y + FO.getSize().height/2 )
			,"ERROR", "Error loading image", null);
		}
            }
        }
  

        private static class jpgFilter extends FileFilter
        { 
                @Override
            public boolean accept(File f)
            {
                if (f.isDirectory())
                {
                    return true;
                }

                String s = f.getName();

                return s.endsWith(".jpg")||s.endsWith(".jpeg");
            }

                @Override
            public String getDescription() 
            {
                return "IMAGE FILES (.jpg | .jpeg)";
            }

        }
        
        private static class bmpFilter extends FileFilter
        { 
                @Override
            public boolean accept(File f)
            {
                if (f.isDirectory())
                {
                    return true;
                }

                String s = f.getName();

                return s.endsWith(".bmp");
            }

                @Override
            public String getDescription() 
            {
                return "IMAGE FILES (.bmp)";
            }

        }  
        
        private static class pngFilter extends FileFilter
        { 
                @Override
            public boolean accept(File f)
            {
                if (f.isDirectory())
                {
                    return true;
                }

                String s = f.getName();

                return s.endsWith(".png");
            }

                @Override
            public String getDescription() 
            {
                return "IMAGE FILES (.png)";
            }

        }     
        
        private static class gifFilter extends FileFilter
        { 
                @Override
            public boolean accept(File f)
            {
                if (f.isDirectory())
                {
                    return true;
                }

                String s = f.getName();

                return s.endsWith(".gif");
            }

                @Override
            public String getDescription() 
            {
                return "IMAGE FILES (.gif)";
            }

        }  

        private static class imageFilter extends FileFilter
        { 
                @Override
            public boolean accept(File f)
            {
                if (f.isDirectory())
                {
                    return true;
                }

                String s = f.getName();
                //.jpg .png .bmp .jpeg .gif
                return s.endsWith(".jpg")||s.endsWith(".jpeg")||s.endsWith(".png")||s.endsWith(".bmp")||s.endsWith(".gif");
            }

                @Override
            public String getDescription() 
            {
                return "ALL IMAGE FILES";
            }

        }        
}
 
