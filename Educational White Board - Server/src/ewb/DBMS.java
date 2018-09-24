package ewb;


import java.awt.HeadlessException;
import java.sql.*;
import javax.swing.JOptionPane;

public class DBMS {
    
String driver="com.mysql.jdbc.Driver";
String url="jdbc:mysql://localhost:3306/ewb_data";
Connection conn;

public void estConnection(){
    try{
        Class.forName(driver);
        conn=DriverManager.getConnection(url,"root","root");
        if(conn==null){
           JOptionPane.showMessageDialog(null, "Connection cannot be established");
        }
    }
    catch(ClassNotFoundException | SQLException | HeadlessException e){
        JOptionPane.showMessageDialog(null, "Error in Connection "+e.toString());
    }
}
 public ResultSet generalSelectQuery(String sql){
        ResultSet rs=null;
        estConnection();
        try{
            Statement s=conn.createStatement();//(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs=s.executeQuery(sql);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "general Select Query :- "+e.toString());
        }
        return rs;
    }
public int generalInsertUpdateDeleteQuery(String sql){
    
    estConnection();
    
    int ru=0;
    try{
        Statement s=conn.createStatement();
        ru=s.executeUpdate(sql);
       System.out.println("connection establish");
    }
    catch(Exception e){
        JOptionPane.showMessageDialog(null, "general Insert Update Delete Query :- "+e.toString());
    }
    return ru;
}
 
}

