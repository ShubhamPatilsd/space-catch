import javax.swing.*;
import java.awt.*;

public class Game extends JPanel implements Runnable{
    private Thread thread;
    private boolean running;
    public Game(){
        //Add character image here
        thread=new Thread();
        running=true;
        thread.start();
    }

    @Override
    public void run() {
        while(running){
            float f1=System.currentTimeMillis();
            repaint();
            float f2=System.currentTimeMillis();
            float deltaF=f2-f1;
            try{
                thread.sleep((1000/60)-(long)deltaF);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public void stop() throws InterruptedException {
        running = false;
        thread.join();
    }

@Override
public void paintComponent(Graphics g){
        super.paintComponent(g);
        
}
}
