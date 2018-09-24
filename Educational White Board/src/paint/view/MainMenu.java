package paint.view;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.swing.*;
import teacher_window.MainWindow;
import paint.model.*;
import paint.model.ToolModel.Tools;
import paint.tools.SelectorTool;

/**
 * �������� ���� ���������
 * @author User
 *
 */
public class MainMenu implements View
{
    
	private static Font font = new java.awt.Font(Font.SANS_SERIF,0, 11); //����� ����
	
	private CanvasImage _image;
	private ImageBuffer _buffer;
	private ImageFileLinker _linker;
	private JFrame _window;
	private ColorModel _color;
	private ToolModel _tool;
	
	private JMenuBar _menuBar = new JMenuBar();
	//�������� ������ ����:
	private JMenu _fileMenu = new JMenu("File");
	private JMenu _editMenu = new JMenu("Edit");
	private JMenu _toolMenu = new JMenu("Tools");
	private JMenu _helpMenu = new JMenu("Help");
	//������ ���� File:
	private JMenuItem _new = new JMenuItem("New");
	private JMenuItem _save = new JMenuItem("Save");
	private JMenuItem _saveas = new JMenuItem("Save as");
	private JMenuItem _load = new JMenuItem("Load");
	private JMenuItem _print = new JMenuItem("Print");
	private JMenuItem _page = new JMenuItem("Page");
	private JMenuItem _exit = new JMenuItem("Exit");
	//������ ���� Edit:
	private JMenuItem _undo = new JMenuItem("Undo");
	private JMenuItem _redo = new JMenuItem("Redo");
	private JMenuItem _copy = new JMenuItem("Copy");
	private JMenuItem _paste = new JMenuItem("Paste");
	private JMenuItem _cut = new JMenuItem("Cut");
	private JMenuItem _delete = new JMenuItem("Delete");
	private JMenuItem _select = new JMenuItem("Select all");
	//������ ���� Settings:
	private JMenuItem _palete = new JMenuItem("Palete");
	//������ ���� Help:
	private JMenuItem _about = new JMenuItem("About");
	/**
	 * �������� �������, ���������� �� ������� ����
	 * @author Vit
	 *
	 */
        
        
	private class  FileMenuListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
                    
			if(arg0.getSource() == _saveas)
                        {
                            
                            SaveLoadDialog.saveImageAs(_image, _linker, _window);
                            //new JFileChooser().showSaveDialog(_window);
                            
                            return;
			}	
			if(arg0.getSource() == _save)
			{
                            //public static void savex(){
                            if(_linker.isLinked())
				{
					try 
					{
						_linker.save(_image);
						return;
					} catch (IOException e) 
					{
						//������ ���������� - ����������� ������������
						ErrorDialog.showSimpleDialog(null,"������", "���������� ��������� ���� � ������ ������! ", null);
						SaveLoadDialog.saveImageAs(_image, _linker, _window);
                                                //new JFileChooser().showSaveDialog(_window);
                                                
						return;
					}
				}
				else
				{
					SaveLoadDialog.saveImageAs(_image, _linker, _window);
                                        //new JFileChooser().showSaveDialog(_window);
                                        //savefile();
					return;
				}
                            
			}
			if(arg0.getSource() == _load)
			{
				SaveLoadDialog.loadImage(_image, _linker, _window);
                                _buffer.flushBuffer();
				return;
			}
			if(arg0.getSource() == _new)
			{
				_image.recreate();
				_buffer.flushBuffer();
				return;
			}
			if(arg0.getSource() == _print)
			{
		    	File bufferFile = new File("_print_task.png");
		    	java.io.FileInputStream fstream = null;
		    	try 
		    	{
		    		PrinterJob pj = PrinterJob.getPrinterJob();
		    		if(pj .printDialog())
		    		{
			    		PrintService ps = pj .getPrintService();
                                        DocPrintJob job = ps.createPrintJob();
			    		if(job != null)
			    		{
				    		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
                                                pras.add(new Copies(pj.getCopies()));
                                                ImageIO.write(_image.getImage(),"png",bufferFile);
                                                fstream = new FileInputStream(bufferFile);
                                                Doc doc = new SimpleDoc(fstream, DocFlavor.INPUT_STREAM.PNG, null);
                                                job.print(doc, pras);
                                                fstream.close();
			    		}
		    		}
				} catch (IOException | PrintException e1) 
				{
					System.out.println("������ ������");
					if(fstream != null)
						try 
						{
							fstream.close();
						} catch (IOException e)
						{
							System.out.println("������ �������� ������ ������ ����� ������");
						}
					return;
				}
			}
                        if(arg0.getSource() == _exit){
                            int response = JOptionPane.showConfirmDialog(MainWindow.window, "Do you want to save changes before exit?");
           
                            if (response == JOptionPane.YES_OPTION){
                                if(_linker.isLinked())
				{
					try 
					{
						_linker.save(_image);
					} catch (IOException e) 
					{
						//������ ���������� - ����������� ������������
						ErrorDialog.showSimpleDialog(null,"������", "���������� ��������� ���� � ������ ������! ", null);
						SaveLoadDialog.saveImageAs(_image, _linker, _window);
                                                //new JFileChooser().showSaveDialog(_window);
                                                //savefile();
					}
				}
				else
				{
					SaveLoadDialog.saveImageAs(_image, _linker, _window);
                                        //new JFileChooser().showSaveDialog(_window);
                                        //savefile();
                                        
                                        //new JFileChooser().addChoosableFileFilter(filefi);
                                }
                            }   
                            MainWindow.exit();
                        }
		}
	}
	/**
	 * ���������� ������� ������� �� �������� ���� Edit
	 * @author Vit
	 *
	 */
	private class  EditMenuListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			//���� - Edit->Undo , ��� ctrl-Z
			if(arg0.getSource()  == _undo)
			{
				if(_buffer.undoLeft() > 0)
					_buffer.undo();
			}
			//���� - Edit->Redo , ��� ctrl-Y
			if(arg0.getSource()  == _redo)
			{
				if(_buffer.redoLeft() > 0)
					_buffer.redo();
			}
			if(arg0.getSource() == _copy)
			{
				if(_tool.getToolType() == Tools.Selector)
				{
					SelectorTool selector = (SelectorTool)_tool.getCurrentTool();
					Clipboard board = Toolkit.getDefaultToolkit().getSystemClipboard();
					final BufferedImage image = selector.getSelectedImage();
					Transferable copyimage = new Transferable(){

						@Override
						public Object getTransferData(DataFlavor arg0)
								throws UnsupportedFlavorException, IOException {
							if(arg0 == DataFlavor.imageFlavor)
								return image;
							else
								throw new UnsupportedFlavorException(arg0);
						}

						@Override
						public DataFlavor[] getTransferDataFlavors() {
							DataFlavor[] flavours = {DataFlavor.imageFlavor};
							return flavours;
						}

						@Override
						public boolean isDataFlavorSupported(DataFlavor arg0) {
							if(arg0 == DataFlavor.imageFlavor)
								return true;
							return false;
						}};
					if(image != null)					
						board.setContents(copyimage, null);
				}
			}
			if(arg0.getSource() == _delete)
			{
				if(_tool.getToolType() == Tools.Selector)
				{
					SelectorTool selector = (SelectorTool)_tool.getCurrentTool();
					selector.deleteSelector();
				}
			}
			if(arg0.getSource() == _select)
			{
				_tool.activateTool(Tools.Selector);
				SelectorTool selector = (SelectorTool)_tool.getCurrentTool();
				selector.selectAll();
			}
			if(arg0.getSource() == _cut)
			{
				if(_tool.getToolType() == Tools.Selector)
				{
					SelectorTool selector = (SelectorTool)_tool.getCurrentTool();
					Clipboard board = Toolkit.getDefaultToolkit().getSystemClipboard();
					final BufferedImage image = selector.cutFromSelector();
					Transferable copyimage = new Transferable()
					{
						@Override
						public Object getTransferData(DataFlavor arg0)
								throws UnsupportedFlavorException, IOException {
							if(arg0 == DataFlavor.imageFlavor)
								return image;
							else
								throw new UnsupportedFlavorException(arg0);
						}

						@Override
						public DataFlavor[] getTransferDataFlavors() {
							DataFlavor[] flavours = {DataFlavor.imageFlavor};
							return flavours;
						}

						@Override
						public boolean isDataFlavorSupported(DataFlavor arg0) {
							if(arg0 == DataFlavor.imageFlavor)
								return true;
							return false;
						}
					};
					if(image != null)					
						board.setContents(copyimage, null);
				}
			}
			if(arg0.getSource() == _paste)
			{
				Clipboard board = Toolkit.getDefaultToolkit().getSystemClipboard();
				try 
				{
					Transferable contents = board.getContents(null);
				    boolean hasTransferableImage =
				        ((contents != null) &&
				        contents.isDataFlavorSupported(DataFlavor.imageFlavor));
				    if (hasTransferableImage )
				    {
				    	Image image = (Image)contents.getTransferData(DataFlavor.imageFlavor);   
				    	int width = image.getWidth(null);
				    	int height = image.getHeight(null);
				    	int oldwidth = _image.getImage().getWidth();
				    	int oldheight = _image.getImage().getHeight();
				    	if((width > oldwidth) &&(height > oldheight))
				    		_image.resize(width, height);
				    	else if (width > oldwidth)
				    		_image.resize(width, oldheight);
				    	else if (height > oldheight)
				    		_image.resize(oldwidth, height);
				    	_tool.activateTool(Tools.Selector);
				    	SelectorTool selector = (SelectorTool)_tool.getCurrentTool();
				    	selector.copyToSelector(image);
				    }
				}
				catch (UnsupportedFlavorException e) 
				{
					System.out.println("������ �������");
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
		}
	}
	/**
	 * ���������� ������� ������� �� �������� ���� Tools
	 * @author Vit
	 *
	 */
	private class  ToolMenuListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			//���� - Tools -> Palete
			if(arg0.getSource()  == _palete)
			{
				ColorPicker picker = new ColorPicker(_color);
				JDialog dialog = picker.getFrame();
				dialog.setAlwaysOnTop(true);
				dialog.setLocationRelativeTo(_window);
				dialog.setLocation(_window.getWidth() / 2 
						+ _window.getX()
						- dialog.getWidth() / 2 ,
						_window.getHeight() / 2 
						+ _window.getY()
						- dialog.getHeight()/ 2);
				_window.setEnabled(false); //��� �������� ������� ����� ������� ���������� �������� ����
				dialog.setVisible(true);
				//��� �������� ������� ����� ������� �������� �������� ����
				dialog.addWindowListener(new WindowAdapter()
				{
					@Override
					public void windowClosing(WindowEvent arg0) {
						_window.setEnabled(true);
					}
				});
			}
		}
	}
	private class  AboutMenuListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			AboutDialog dialog = new AboutDialog();
			dialog.showDialog(_window);			
		}
		
	}
	/**
	 * ����������� ��������� ����
	 */
	public MainMenu(CanvasImage image, ImageFileLinker linker, JFrame window, ImageBuffer buffer, ColorModel color, ToolModel tool)
	{
		_image = image;
		_linker = linker;
		_window = window;
		_buffer = buffer;
		_color = color;
		_tool = tool;
		_buffer.attach(this);
		//�������� ��������� ���� �������������� ������ � ������� �������������� ������ 
		_fileMenu.setFont(font);
		_fileMenu.setMnemonic('f');
		_editMenu.setFont(font);
		_editMenu.setMnemonic('e');
		_toolMenu.setFont(font);
		_toolMenu.setMnemonic('t');
		_helpMenu.setFont(font);
		_helpMenu.setMnemonic('h');
		
		FileMenuListener fileListener = new FileMenuListener();
		EditMenuListener editListener = new EditMenuListener();
		//��������� ��������� ���� �������������� ������, ������ � ������
		//� ���-�� ������� ������ � ��������� �������
		_new.setIcon(new ImageIcon(this.getClass().getResource("/paint/icons/new.PNG")));
		_new.setFont(font);
		_new.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N,
                java.awt.Event.CTRL_MASK));
		_new.addActionListener(fileListener);
		
		_save.setIcon(new ImageIcon(this.getClass().getResource("/paint/icons/save.PNG")));
		_save.setFont(font);
		_save.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
                java.awt.Event.CTRL_MASK));
		_save.addActionListener(fileListener);
		
		_saveas.setFont(font);
		_saveas.addActionListener(fileListener);
		
		_load.setIcon(new ImageIcon(this.getClass().getResource("/paint/icons/load.PNG")));
		_load.setFont(font);
		_load.addActionListener(fileListener);
		
		_undo.setIcon(new ImageIcon(this.getClass().getResource("/paint/icons/undo.PNG")));
		_undo.setFont(font);
		_undo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z,
                java.awt.Event.CTRL_MASK));
		_undo.addActionListener(editListener);
		
		_redo.setIcon(new ImageIcon(this.getClass().getResource("/paint/icons/redo.PNG")));
		_redo.setFont(font);
		_redo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y,
                java.awt.Event.CTRL_MASK));
		_redo.addActionListener(editListener);
		
		_copy.setIcon(new ImageIcon(this.getClass().getResource("/paint/icons/copy.PNG")));
		_copy.setFont(font);
		_copy.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C,
                java.awt.Event.CTRL_MASK));
		_copy.addActionListener(editListener);
		
		_paste.setIcon(new ImageIcon(this.getClass().getResource("/paint/icons/paste.PNG")));
		_paste.setFont(font);
		_paste.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V,
                java.awt.Event.CTRL_MASK));
		_paste.addActionListener(editListener);
		
		_cut.setIcon(new ImageIcon(this.getClass().getResource("/paint/icons/cut.PNG")));
		_cut.setFont(font);
		_cut.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X,
                java.awt.Event.CTRL_MASK));
		_cut.addActionListener(editListener);
		
		_delete.setIcon(new ImageIcon(this.getClass().getResource("/paint/icons/delete.PNG")));
		_delete.setFont(font);
		_delete.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D,
                java.awt.Event.CTRL_MASK));
		_delete.addActionListener(editListener);
		
		_about.setIcon(new ImageIcon(this.getClass().getResource("/paint/icons/about.PNG")));
		_about.setFont(font);
		_about.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I,
                java.awt.Event.CTRL_MASK));
		_about.addActionListener(new AboutMenuListener());
		
		_print.setIcon(new ImageIcon(this.getClass().getResource("/paint/icons/print.PNG")));
		_print.setFont(font);
		_print.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P,
                java.awt.Event.CTRL_MASK));
		_print.addActionListener(fileListener);
		
		_exit.setIcon(new ImageIcon(this.getClass().getResource("/paint/icons/exit.PNG")));
		_exit.setFont(font);
		_exit.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 
				java.awt.Event.ALT_MASK));
                _exit.addActionListener(fileListener);
		
		_palete.setIcon(new ImageIcon(this.getClass().getResource("/paint/icons/palete.PNG")));
		_palete.setFont(font);
		_palete.addActionListener(new ToolMenuListener());
		
		_page.setIcon(new ImageIcon(this.getClass().getResource("/paint/icons/page.PNG")));
		_page.setFont(font);
		
		_select.setFont(font);
		_select.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A,
                java.awt.Event.CTRL_MASK));
		_select.addActionListener(editListener);
		//�������� ���� �������� � ����
		//File
		_fileMenu.add(_new);
		_fileMenu.add(_save);
		_fileMenu.add(_saveas);
		_fileMenu.add(_load);
		//_fileMenu.add(_page);
		_fileMenu.add(new JSeparator());
		_fileMenu.add(_print);
		_fileMenu.add(new JSeparator());
		_fileMenu.add(_exit);
		//Edit
		_editMenu.add(_undo);
		_editMenu.add(_redo);
		_editMenu.add(new JSeparator());
		_editMenu.add(_copy);
		_editMenu.add(_paste);
		_editMenu.add(_cut);
		_editMenu.add(_delete);
		_editMenu.add(new JSeparator());
		_editMenu.add(_select);
		//Tool
		_toolMenu.add(_palete);
		//Help
		_helpMenu.add(_about);
		//������ ����
		_menuBar.add(_fileMenu);
		_menuBar.add(_editMenu);
		_menuBar.add(_toolMenu);
		_menuBar.add(_helpMenu);
	}
	public JMenuBar getBar()
	{
		return _menuBar;
	}
	@Override
	public void refresh() 
	{
		if(_buffer.undoLeft() <= 0)
			_undo.setEnabled(false);
		else
			_undo.setEnabled(true);
		if(_buffer.redoLeft() <= 0)
			_redo.setEnabled(false);
		else
			_redo.setEnabled(true);
	}
}
