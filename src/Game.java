import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;

public class Game extends JPanel implements Runnable, KeyListener {
    public Thread thread;
    private boolean running;
    public Sprite ufo;
    public Sprite badguyone;
    public Sprite badguytwo;
    public Sprite playbackground;
    public Sprite vrgoggles;
    public Sprite heartone;
    public Sprite hearttwo;
    public Sprite heartthree;
    private HashSet<Character> keystrokes;

    private int score;
    private FontSprite scorefont;

    private int lives=3;


    public Game(){
        addKeyListener(this);
        setFocusable(true);
        keystrokes=new HashSet<Character>();
        playbackground=new Sprite("/resources/playbackground.gif",0,0);
        ufo=new Sprite("/resources/ufo.gif",400,320);
        badguyone=new Sprite("/resources/badguy.png",(int)(Math.random()*(740-1+1)+1),20);
        badguytwo=new Sprite("/resources/badguy.png",(int)(Math.random()*(740-1+1)+1),20);
        vrgoggles=new Sprite("/resources/vrgoggles.png",(int)(Math.random()*(740-1+1)+1),20);
        thread=new Thread(this);
        running=true;
        scorefont=new FontSprite("/resources/pixelated.ttf",20,40,36);
        heartone=new Sprite("/resources/heart.png",720,15);
        hearttwo=new Sprite("/resources/heart.png",680,15);
        heartthree=new Sprite("/resources/heart.png",640,15);
        thread.start();

    }

    @Override
    public void run() {
        while(running){

            float f1=System.currentTimeMillis();

            badguyone.y++;
            badguytwo.y++;
            vrgoggles.y++;


            if(((badguytwo.x<=ufo.x+ufo.characterimage.getIconWidth() && badguytwo.x>=ufo.x-ufo.characterimage.getIconWidth()) && (badguytwo.y<=ufo.y+ufo.characterimage.getIconHeight() && badguytwo.y>=ufo.y-ufo.characterimage.getIconHeight())) || ((badguyone.x<=ufo.x+ufo.characterimage.getIconWidth() && badguyone.x>=ufo.x-ufo.characterimage.getIconWidth()) && (badguyone.y<=ufo.y+ufo.characterimage.getIconHeight() && badguyone.y>=ufo.y-ufo.characterimage.getIconHeight()))){

                lives--;
                new Sound().playSound("/resources/pop_3.wav");
                ufo=new Sprite("/resources/ufo.gif",400,320);
                badguyone=new Sprite("/resources/badguy.png",(int)(Math.random()*(740-1+1)+1),20);
                badguytwo=new Sprite("/resources/badguy.png",(int)(Math.random()*(740-1+1)+1),20);
            }
            if((vrgoggles.x<=ufo.x+ufo.characterimage.getIconWidth() && vrgoggles.x>=ufo.x && vrgoggles.y<=ufo.y+ufo.characterimage.getIconHeight() && vrgoggles.y>=ufo.y)){
                score++;
            }
            if(lives>3){

            }
            if(vrgoggles.y>=340){
                vrgoggles=new Sprite("/resources/vrgoggles.png",(int)(Math.random()*(740-1+1)+1),20);
            }
            if(badguyone.y>=340){
                badguyone=new Sprite("/resources/badguy.png",(int)(Math.random()*(740-1+1)+1),20);
            }
            if(badguytwo.y>=340){
                badguytwo=new Sprite("/resources/badguy.png",(int)(Math.random()*(740-1+1)+1),20);
            }
            for(char c:keystrokes){

                if(c=='a'){

                    ufo.x-=2;
                }
                if(c=='d'){

                    ufo.x+=2;
                }
            }
            if(ufo.x<-30){
                ufo.x=-30;
            }
            if(ufo.x>700){
                ufo.x=700;

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
        badguyone.characterimage.paintIcon(this, g, badguyone.x, badguyone.y);
        badguytwo.characterimage.paintIcon(this, g, badguytwo.x, badguytwo.y);
        vrgoggles.characterimage.paintIcon(this, g, vrgoggles.x,vrgoggles.y);
        if(lives==3){
            heartone.characterimage.paintIcon(this,g,heartone.x,heartone.y);
            hearttwo.characterimage.paintIcon(this,g,hearttwo.x,hearttwo.y);
            heartthree.characterimage.paintIcon(this,g,heartthree.x,heartthree.y);
        }
        if(lives==2){
            heartone.characterimage.paintIcon(this,g,heartone.x,heartone.y);
            hearttwo.characterimage.paintIcon(this,g,hearttwo.x,hearttwo.y);
        }if(lives==1){
            heartone.characterimage.paintIcon(this,g,heartone.x,heartone.y);

        }
        g.setFont(scorefont.font);
        g.setColor(Color.white);
        g.drawString("Score: "+score,scorefont.x,scorefont.y);

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
