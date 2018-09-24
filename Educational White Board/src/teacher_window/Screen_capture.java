/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package teacher_window;

/**
 *
 * @author YANK
 */

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class Screen_capture implements Runnable{
    
    @Override
    public void run() {
        ExecutorService consumer = new ThreadPoolExecutor(1,4,30,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(100));
        ExecutorService producer = Executors.newSingleThreadExecutor();
        Runnable produce = new Produce(consumer);
        producer.submit(produce);
    }

}
class Produce implements Runnable {
    private final ExecutorService consumer;
    

    public Produce(ExecutorService consumer) {
        this.consumer = consumer;
    }
    
    @Override
    public void run() {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            
        }
        //
        // Capture screen from the top left to bottom right
        //
        
        int i = 0;
        while(true) {
            try {
                Thread.sleep(1000);
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Produce.class.getName()).log(Level.SEVERE, null, ex);
            }
            i++;
            Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
            int height_n = (int)((screensize.getHeight())*(4/5));
            screensize.setSize(screensize.getWidth(), height_n);
            BufferedImage bufferedImage = robot.createScreenCapture(new Rectangle(1366,768));
            Runnable consume = new Consume(bufferedImage,i);
            consumer.submit(consume);
            
        }

    }
}

class Consume implements Runnable {
    private final BufferedImage bufferedImage;
    private final Integer picnr;
    public Consume(BufferedImage bufferedImage, Integer picnr){
        this.bufferedImage = bufferedImage;
        this.picnr = picnr;
        
    }

    @Override
    public void run() {
        /*try {
            ewb.EWB.is.send_img(b);
        } catch (RemoteException ex) {
            Logger.getLogger(Consume.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        String base64String;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(20000)) {
            ImageIO.write(bufferedImage, "png", baos);
            baos.flush();
            base64String = Base64.encode(baos.toByteArray());
            ewb.EWB.is.send_img(base64String);
            
            //baos.flush();
        }
        catch (IOException ex) {
            Logger.getLogger(Consume.class.getName()).log(Level.SEVERE, null, ex);
        }    }
}

