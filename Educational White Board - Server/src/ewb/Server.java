/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ewb;

import chat.Chat_Server;
import java.awt.HeadlessException;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author YANK
 */
public class Server extends UnicastRemoteObject implements RmiInterface{
    static DBMS d = new DBMS();
    static viewAll va;
    static Registry r;
    static String image;
    static LogInForm lif;
    static Reg_Form rif;
    
    public Server() throws RemoteException, IOException {
       
    }

    @Override
    public login_details generalSelectQuery(String sql) throws RemoteException {
        ResultSet rs = d.generalSelectQuery(sql);
        login_details ld = new login_details();
        try {
            rs.next();
            
            if(rs!=null){
                ld.un = rs.getString("id");
                ld.pass = rs.getString("password");
                ld.type = rs.getString("type");
            }
            else{
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ld;
    }

    @Override
    public int generalInsertUpdateDeleteQuery(String sql) throws RemoteException {
        return d.generalInsertUpdateDeleteQuery(sql);
    }
    
    public static void main(String a[]){
        Server.lif = new LogInForm();
        Server.rif = new Reg_Form();
        try {
            Server s = new Server();
            r=LocateRegistry.createRegistry(9087);
            r.bind("yank", s);
            
            JOptionPane.showMessageDialog(null, "rmi server started...");
            Chat_Server cs = new Chat_Server();
        } catch (IOException | AlreadyBoundException | HeadlessException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,ex.toString());
        }
    }

    @Override
    public String getImage() throws RemoteException {
        return Server.image;
    }

    @Override
    public void setImage(String s) throws RemoteException {
        Server.image = s;
    }
 
}