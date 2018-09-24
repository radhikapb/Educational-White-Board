/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package screen_capture;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shraddha
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Screenshot {
  public static void main(String[] args) throws Exception {
    Screenshot ss = new Screenshot();
  }

  public Screenshot(){
    JFrame frame = new JFrame("Screen Shot Frame.");
    JButton button = new JButton("Capture Screen Shot");
    button.addActionListener(new MyAction());
    JPanel panel = new JPanel();
    panel.add(button);
    frame.add(panel, BorderLayout.CENTER);
    frame.setSize(400, 400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  public class MyAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae){
            try{
                String fileName = JOptionPane.showInputDialog(null, "Enter file name : ",
                "Get Input File", 1);
                if (!fileName.toLowerCase().endsWith(".gif")){
                    JOptionPane.showMessageDialog(null, "Error: file name must end with \".gif\".", "Dialog", 1);
                }
                else{
                    Robot robot = new Robot();
                    Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
                    Double width = screensize.getWidth();
                    screensize.setSize( width, 4*(screensize.getHeight())/5 );
                    Rectangle r = new Rectangle(screensize);
                    
                    BufferedImage image = robot.createScreenCapture(r);
                    ImageIO.write(image, "gif", new File(fileName));
                    JOptionPane.showMessageDialog(null, "Screen captured successfully.", "Success Message", 1);
                }
            }
            catch(HeadlessException | AWTException | IOException e){}
            }
  }
}
