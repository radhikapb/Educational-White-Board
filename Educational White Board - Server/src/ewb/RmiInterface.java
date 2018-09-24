/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ewb;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author YANK
 */
public interface RmiInterface extends Remote{
    public login_details generalSelectQuery(String sql) throws RemoteException;
    public int generalInsertUpdateDeleteQuery(String sql) throws RemoteException;
    public String getImage() throws RemoteException;
    public void setImage(String s) throws RemoteException;
}
