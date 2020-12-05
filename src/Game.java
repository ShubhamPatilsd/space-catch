import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

public class Game extends JPanel implements Runnable, KeyListener {
    public Thread thread;
    private boolean running;
    public Sprite ufo;
    public Sprite playbackground;
    private HashSet<Character> keystrokes;
    private int leftvel=0;
    private int rightvel=0;

    public Game(){
        addKeyListener(this);
        setFocusable(true);
        keystrokes=new HashSet<Character>();
        playbackground=new Sprite("/resources/playbackground.gif",0,0);
        ufo=new Sprite("/resources/spaceship.png",400,365);
        thread=new Thread(this);
        running=true;
        thread.start();

    }

    @Override
    public void run() {
        while(running){

            float f1=System.currentTimeMillis();
            leftvel-=3;
            rightvel-=3;
            if(leftvel<0){
                leftvel=0;
            }
            if(rightvel<0){
                rightvel=0;
            }
            for(char c:keystrokes){

                if(c=='a'){
                    leftvel+=5;
                    ufo.x-=leftvel;
                }
                if(c=='d'){
                    rightvel+=5;
                    ufo.x+=rightvel;
                }
            }
            if(ufo.x<0){
                ufo.x=0;
            }
            if(ufo.x>740){
                ufo.x=740;
                System.out.println("Boundary Met");
            }
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
        playbackground.characterimage.paintIcon(this, g, playbackground.x, playbackground.y);
        ufo.characterimage.paintIcon(this, g, ufo.x, ufo.y);
}

    @Override
    public void keyTyped(KeyEvent e) {
        keystrokes.add(e.getKeyChar());

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keystrokes.add(e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keystrokes.remove(e.getKeyChar());
    }
}
