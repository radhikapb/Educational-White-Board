package chat;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.*;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author YANK
 */
public class Chat_Server {

    static ServerSocket s;
    static Socket client;
    static ArrayList<Socket> allclients;
    static ArrayList online_clients;
    static DatagramSocket ds;
    
    
    public Chat_Server() throws IOException{
        
           s=new ServerSocket(8765);
            allclients=new ArrayList<>();
            System.out.println("Server has Started !!");
            //JOptionPane.showMessageDialog(null, "chatserver");

            
            while(true){
                client=s.accept();
                System.out.println("New client Connected");
                System.out.println(client);
                allclients.add(client);
                ServerListener sl=new ServerListener(client);
                Thread t=new Thread(sl);
                t.start();
            }
            
    }
    
    public static void main(String[] args) throws IOException{
           

    }
}
    class ServerListener implements Runnable{

        Socket client;
        InputStream in;
        BufferedReader reader;
        PrintWriter p;
        String str;
        byte[] buffer;

        ServerListener(Socket client) throws IOException{
         this.client=client;
         this.in=client.getInputStream();
         InputStreamReader r=new InputStreamReader(in);
         reader=new BufferedReader(r);

        }
    @Override
        public void run() {
            try{
            while((str=reader.readLine())!=null){

          //      System.out.println("Chat_Server Test :"+str);
                for(Socket o:Chat_Server.allclients){
                    
                    client=(Socket)o;
                    p=new PrintWriter(client.getOutputStream());
                    p.println(str);
                    p.flush();
                   
                }
            }
            }
            catch(Exception e){

        }

    }
}
