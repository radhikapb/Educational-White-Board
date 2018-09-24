/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ewb;

/**
 *
 * @author RADHIKA
 */
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aspire
 */
public  class GeneralTableModel implements TableModel {

    String colnames[];
    Object data[][];
    DBMS d=new DBMS();
    
    ResultSet rs=null;
    
    public GeneralTableModel(String sql,String[] colnames) {
        this.colnames=colnames;
        int rows=0;
        
        rs=d.generalSelectQuery(sql);
        try{
        
        while(rs.next()){
            rows++;
            
        }
        //JOptionPane.showMessageDialog(null, "Rows :-"+rows);
        rs.beforeFirst();
        int i;
        data=new Object[rows][colnames.length];
        i=0;
        int j;
        while(rs.next()){
               j=0;
               while(j<colnames.length){
                    data[i][j]=rs.getObject(j+1);
                    j++;
               }
             i++;
        }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }
    

    public void deleteRow(int row){
        try{
            rs.absolute(row+1);
            rs.deleteRow(); 
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }


    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return colnames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colnames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex]=aValue;
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        
    }

}
