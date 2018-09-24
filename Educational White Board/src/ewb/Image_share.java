/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ewb;

import java.awt.HeadlessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;

/**
 *
 * @author YANK
 */
public class Image_share {
    static RmiInterface ri;
    static Registry r;
    
    public Image_share() {
        try{
            r=LocateRegistry.getRegistry("localhost",9087);
            ri=(RmiInterface)(r.lookup("yank"));
        } catch(RemoteException | NotBoundException | HeadlessException e){
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    
    public void send_img(String s) throws RemoteException{
        ri.setImage(s);
    }
    
    public String recv_img() throws RemoteException{
        return ri.getImage();
    }
    
}
