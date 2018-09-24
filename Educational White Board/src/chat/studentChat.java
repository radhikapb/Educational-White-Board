/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author shraddha
 */
public class studentChat extends JFrame {

    /**
     * Creates new form studentChat
     */
    
    static String msg;
    static String username;
    static Socket client;
    static PrintWriter p;
    static byte[] buffer;
    
    public studentChat(String user_name) throws UnknownHostException, IOException {
        studentChat.username = user_name;
       
        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screensize.getWidth();
        int height = (int) screensize.getHeight();
        this.setBounds(0, (int)(4*height/5), width, (int)height/5);
        this.setVisible(true); 
        client=new Socket("localhost",8765);
        if(client!=null){
            System.out.println("connected to Server !!");
        }
        stud_Listener l=new stud_Listener(client);
        Thread t=new Thread(l);
        t.start();
        
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
               //new studentChat("radhu").setVisible(true); 
            }
        });
            
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtf = new javax.swing.JTextField();
        exit = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jta = new javax.swing.JTextArea();
        jb = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);
        setUndecorated(true);
        getContentPane().setLayout(null);

        jtf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfActionPerformed(evt);
            }
        });
        getContentPane().add(jtf);
        jtf.setBounds(10, 120, 1070, 30);

        exit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        exit.setForeground(new java.awt.Color(204, 0, 0));
        exit.setText("EXIT");
        exit.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 0, 0)));
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        getContentPane().add(exit);
        exit.setBounds(1240, 120, 110, 30);

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setText("SAVE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(1240, 10, 110, 30);

        jta.setColumns(20);
        jta.setEditable(false);
        jta.setRows(5);
        jScrollPane1.setViewportView(jta);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 10, 1210, 100);

        jb.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jb.setText("SEND");
        jb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbActionPerformed(evt);
            }
        });
        getContentPane().add(jb);
        jb.setBounds(1100, 120, 120, 30);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/background.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 1370, 160);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/background.jpg"))); // NOI18N
        jLabel2.setText("jLabel1");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 0, 1370, 160);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbActionPerformed
        if(!jtf.getText().equals("")){
                try {
                    String d;
                    d = jtf.getText();
                    jtf.setText("");
                    msg=username +" :- "+d;

                    p=new PrintWriter(client.getOutputStream());
                    p.println(msg);
                    p.flush();

                } catch (IOException ex) {}
        }
    }//GEN-LAST:event_jbActionPerformed

    private void jtfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfActionPerformed
        if(!jtf.getText().equals("")){
                try {
                    String d;
                    d = jtf.getText();
                    jtf.setText("");
                    msg=username +" :- "+d;

                    p=new PrintWriter(client.getOutputStream());
                    p.println(msg);
                    p.flush();

                } catch (IOException ex) {}
        }
    }//GEN-LAST:event_jtfActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        this.dispose();
        ewb.EWB.sm.dispose();
    }//GEN-LAST:event_exitActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            Robot robot = new Robot();
            Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
            Double width = screensize.getWidth();
            screensize.setSize( width, 4*(screensize.getHeight())/5 );
            Rectangle r = new Rectangle(screensize);
                        
            BufferedImage image = robot.createScreenCapture(r);
            JFileChooser FS=new JFileChooser();
            int retrival=FS.showSaveDialog(null);
 
            if (retrival == JFileChooser.APPROVE_OPTION) 
            {
                String dir = FS.getCurrentDirectory().getAbsolutePath();
                String file = FS.getSelectedFile().getName();
                file = dir+"\\"+file+".png";
                ImageIO.write(image, "png", new File(file));
            }
            
        } catch (IOException | AWTException ex) {
            Logger.getLogger(studentChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws UnknownHostException, IOException {
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(studentChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(studentChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(studentChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(studentChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exit;
    private javax.swing.JButton jButton2;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jb;
    static javax.swing.JTextArea jta;
    static javax.swing.JTextField jtf;
    // End of variables declaration//GEN-END:variables
}
class stud_Listener implements Runnable{
    
    Socket client;
    BufferedReader reader;
    String str;
    stud_Listener (Socket client){
        this.client=client;
    }
    @Override
    public void run() {
        try {
            InputStreamReader r = new InputStreamReader(client.getInputStream());
            reader=new BufferedReader(r);

            while((str=reader.readLine())!=null){
                studentChat.jta.append("\n"+str);
            }
        } catch (IOException ex) {
            //Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}