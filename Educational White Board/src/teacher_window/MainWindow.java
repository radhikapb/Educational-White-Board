package teacher_window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;
import paint.model.*;
import paint.view.*;


public class MainWindow extends JFrame 
{
	public static final MainWindow window = ewb.EWB.tm;
        
        public MainWindow(){
            
            this.setUndecorated(true);
            Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = (int) screensize.getWidth();
            int height = (int) screensize.getHeight();
            this.setBounds(0, 0, width, (int)(4*height/5));
            toFront();
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            initialize();
            //System.out.println(width+"    "+height);
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {            
            }
            //Screen_capture sc = new Screen_capture();
            //Thread tsc = new Thread(sc);
            //tsc.run();
        }
        
        public static void exit(){
           ewb.EWB.tm.dispose();
           ewb.EWB.teacherChat.dispose();
        }
                
        private void initialize(){
                this.setTitle("Retard Paint");
		this.setIconImage(new ImageIcon(this.getClass().getResource("/paint/icons/brush.PNG")).getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
                
                
                paint.model.ColorModel colorModel = new paint.model.ColorModel();
		CanvasImage image = new CanvasImage(new Dimension(20,20),BufferedImage.TYPE_INT_RGB,Color.WHITE);
		CanvasModel canvasModel = new CanvasModel(image);
		ImageBuffer buffer = new ImageBuffer(image);
		ImageFileLinker linker = new ImageFileLinker();
		ToolModel tools = new ToolModel(canvasModel,colorModel,buffer);
		CanvasResizer resizer = new CanvasResizer(canvasModel,buffer);
		
		
		JPanel northPanel = new JPanel();
		JPanel southPanel = new JPanel();
		
		ToolMenu toolMenu = new ToolMenu(tools);
		MainMenu mainMenu = new MainMenu(image,linker,this,buffer,colorModel,tools);
		ColorView colorMenu = new ColorView(colorModel);
		CanvasView canvas = new CanvasView(canvasModel,resizer);
		HeaderPanel header = new HeaderPanel(canvasModel,resizer);
		
		ToolPropertyContainer propertyPanel = new ToolPropertyContainer(tools);
		
		
		northPanel.setLayout(new BorderLayout());
		northPanel.add(mainMenu.getBar(), BorderLayout.NORTH);
		northPanel.add(propertyPanel.getPanel(), BorderLayout.CENTER);
		
		southPanel.setLayout(new BorderLayout());
		southPanel.add(colorMenu.getPanel(), BorderLayout.NORTH);
		southPanel.add(header.getPanel(), BorderLayout.CENTER);
		
		this.add(toolMenu.getBar(), BorderLayout.WEST);
		this.add(canvas.getPane(),BorderLayout.CENTER);
		this.add(northPanel, BorderLayout.NORTH);
		this.add(southPanel,BorderLayout.SOUTH);
                
	}
        
        public static void main(String[] args)
	{
            System.out.println("main of teacher");
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run() 
			{
                            System.out.println("run in main of teacher");
				//window.setVisible(true);
			}
		});
	}
}
