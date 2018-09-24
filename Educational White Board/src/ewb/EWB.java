/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ewb;

//import Admin.viewAll;
import chat.studentChat;
import chat.teacherChat;
import java.io.IOException;
import student_window.studentMain;
import teacher_window.MainWindow;
import teacher_window.Screen_capture;
/**
 *
 * @author RADHIKA
 */

public class EWB {
    
    public static LogInForm lif=new LogInForm();
    public static Reg_Form rf = new Reg_Form();
    public static MainWindow tm;
    public static Screen_capture sc;
    //public static viewAll admin_w = new viewAll(); 
    public static Image_share is;
    public static studentMain sm;
    public static studentChat studentChat;
    public static teacherChat teacherChat;
    //public static Server server;
    public static String ip = "localhost";
    
    public EWB() throws IOException{
        lif.setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    
     public static void main(String[] args) throws IOException {
        EWB ewb=new EWB();
  }
}
