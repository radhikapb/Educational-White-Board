package ewb;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
/**
 *
 * @author RADHIKA
 */
public class viewAll extends javax.swing.JFrame {

    String colnames[]={"S_id","First Name","Last Name","Contact_no","Mail_id","Branch","sem"};
    String colnames1[]={"T_id","First Name","Last Name","Contact_no","Mail_id"};
   
    public viewAll() {
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)(dim.getWidth()/2);
        int height = (int)(dim.getWidth()/2);
        this.setBounds((int)(1*dim.getWidth()/5), (int)(1*dim.getHeight()/7), (int)(3*dim.getWidth()/5), (int)(2*dim.getHeight()/3));
        this.setTitle("Admin - EWB");
        this.setVisible(true);
    }
    
    class T_MyActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String sql="select * from teacher";
            int flag=0;
             
             if(!(tf_name.getText()).equalsIgnoreCase("")){
                sql+=" where F_name LIKE '%"+tf_name.getText()+"%' OR L_name like '%"+tf_name.getText()+"%'";
                flag=1;
             }
          
              if(!(tf_id.getText()).equalsIgnoreCase("")){
                if(flag==1)
                {
                    sql+=" AND T_Id='"+tf_id.getText()+"'";
                }
                else{
                sql+=" where T_Id='"+tf_id.getText()+"'";
                //flag=1;
                }
              }
            
            JOptionPane.showMessageDialog(null, sql);
            GeneralTableModel gmt=new GeneralTableModel(sql, colnames1);
            tb.setModel(gmt);
            tb.repaint();
        }

}
    class S_MyActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String sql_s="select * from student";
            int flag=0;
             if(!(cb_sem.getSelectedItem().toString()).equalsIgnoreCase("NONE")){
                   sql_s+=" where Sem="+Integer.parseInt((String)cb_sem.getSelectedItem());
                   flag=1;
            }
             if(!(tf_name.getText()).equalsIgnoreCase("")){
                if(flag==1)
                {
                    sql_s+=" AND F_name LIKE '%"+tf_name.getText()+"%' OR L_name like '%"+tf_name.getText()+"%'";
                }
                else{
                    sql_s+=" where F_name LIKE '%"+tf_name.getText()+"%' OR L_name like '%"+tf_name.getText()+"%'";
                flag=1;
                }
             }
            if(!(cb_branch.getSelectedItem().toString()).equalsIgnoreCase("NONE")){
                if(flag==1)
                {
                    sql_s+=" AND Branch='"+cb_branch.getSelectedItem().toString()+"'";
                }
                else{
                sql_s+=" where Branch='"+cb_branch.getSelectedItem().toString()+"'";
                flag=1;
                }
            }
              if(!(tf_id.getText()).equalsIgnoreCase("")){
                if(flag==1)
                {
                    sql_s+=" AND S_id='"+tf_id.getText()+"'";
                }
                else{
                sql_s+=" where S_id='"+tf_id.getText()+"'";
                //flag=1;
                }
              }
            
            JOptionPane.showMessageDialog(null, sql_s);
            GeneralTableModel gtm=new GeneralTableModel(sql_s, colnames);
            tb.setModel(gtm);
            tb.repaint();
        }

}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        branch = new javax.swing.JLabel();
        sem = new javax.swing.JLabel();
        cb_branch = new javax.swing.JComboBox();
        cb_sem = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cb_type = new javax.swing.JComboBox();
        tf_id = new javax.swing.JTextField();
        b_search = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        tf_name = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel1.setOpaque(false);

        branch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        branch.setForeground(new java.awt.Color(255, 255, 255));
        branch.setText("Select Branch :");

        sem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sem.setForeground(new java.awt.Color(255, 255, 255));
        sem.setText("Select Sem :");

        cb_branch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE", "computer", "I.T.", "CIVIL", "IC" }));

        cb_sem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE", "1", "2", "3", "4", "5", "6", "7", "8" }));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Select Type :");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Enter id :");

        cb_type.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "none", "Teacher", "Student" }));
        cb_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_typeActionPerformed(evt);
            }
        });

        b_search.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        b_search.setText("Search");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Enter Name : ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(branch)
                            .addComponent(jLabel1))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cb_branch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tf_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(116, 116, 116)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(sem)))
                            .addComponent(cb_type, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tf_id, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cb_sem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(b_search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cb_branch, cb_sem, tf_id, tf_name});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_type, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_branch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sem)
                    .addComponent(cb_sem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(branch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_name, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(tf_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(b_search, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cb_branch, cb_sem, cb_type, tf_id, tf_name});

        getContentPane().add(jPanel1);
        jPanel1.setBounds(50, 30, 693, 210);

        jPanel2.setOpaque(false);

        tb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb.setOpaque(false);
        jScrollPane1.setViewportView(tb);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 41, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel2);
        jPanel2.setBounds(80, 260, 660, 200);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/background.jpg"))); // NOI18N
        getContentPane().add(jLabel4);
        jLabel4.setBounds(-6, -26, 830, 510);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    ActionListener stud = new S_MyActionListener();
    ActionListener teac = new T_MyActionListener();
        
    
    private void cb_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_typeActionPerformed
        
        b_search.removeActionListener(stud);
        b_search.removeActionListener(teac);
        
        if("Student".equalsIgnoreCase(cb_type.getSelectedItem().toString())){          
              cb_branch.setEnabled(true);
              cb_sem.setEnabled(true);
              b_search.addActionListener(stud);
        }
        
        else if("Teacher".equalsIgnoreCase(cb_type.getSelectedItem().toString())){
               cb_branch.setEnabled(false);
               cb_sem.setEnabled(false);
               b_search.addActionListener(teac);
               
        }
         
    }//GEN-LAST:event_cb_typeActionPerformed

    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(viewAll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new viewAll().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_search;
    private javax.swing.JLabel branch;
    private javax.swing.JComboBox cb_branch;
    private javax.swing.JComboBox cb_sem;
    private javax.swing.JComboBox cb_type;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel sem;
    private javax.swing.JTable tb;
    private javax.swing.JTextField tf_id;
    private javax.swing.JTextField tf_name;
    // End of variables declaration//GEN-END:variables
}

