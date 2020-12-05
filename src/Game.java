import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;

public class Game extends JPanel implements Runnable, KeyListener, ActionListener {
    public Thread thread;
    private boolean running;
    public Sprite ufo;
    public Sprite badguyone;
    //public Timer t=new Timer(30000,this);
    public Timer t=new Timer(5000,this);
    public Sprite badguytwo;
    public Sprite playbackground;
    public Sprite vrgoggles;
    public Sprite heartone;
    public Sprite hearttwo;
    public Sprite heartthree;
    public Sprite startscreenbackground;
    private boolean codedayheart=false;
    public Sprite codeday;
    private HashSet<Character> keystrokes;
    private int badguyvelocity=1;
    private BackgroundSound bgsound =new BackgroundSound("/resources/backgroundmusic.wav");

    @Override
    public void actionPerformed(ActionEvent e) {
        codedayheart=true;
        codeday=new Sprite("/resources/heart_codeday.png",(int)(Math.random()*(740-1+1)),20);
        badguyvelocity+=4;
    }


    private enum GameState{
        START,
        PLAYING,
        GAME_OVER,
        CREDITS,
        INSTRUCTIONS
    }
    GameState state=GameState.START;
    private int score;
    private FontSprite scorefont;

    private int lives=3;


    public Game(){
        addKeyListener(this);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                int mousex = (int) e.getX();
                int mousey = (int) e.getY();
                //g.fill3DRect(300,325,200,75,true);
                if (state == GameState.START) {
                    if (mousex >= 300 && mousex <= 500) {
                        if (mousey >= 225 && mousey <= 300) {
                            state = GameState.PLAYING;

                        }
                    }
                    if (mousex >= 300 && mousex <= 500) {
                        if (mousey >= 325 && mousey <= 400) {
                            //System.exit(0);
                            state = GameState.CREDITS;
                        }
                    }

                    if (mousex >= 300 && mousex <= 500) {
                        if (mousey >= 425 && mousey <= 500) {
                            System.exit(0);

                        }
                    }
                    //g.fill3DRect(700,475,75,75,true);
                    if (mousex >= 700 && mousex <= 775) {
                        if (mousey >= 475 && mousey <= 550) {
                            state=GameState.INSTRUCTIONS;

                        }
                    }

                }
                //g.fill3DRect(20,20,75,75,true);
                if (state == GameState.CREDITS) {
                    if (mousex >= 20 && mousex <= 95) {
                        if (mousey >= 20 && mousey <= 95) {
                            state = GameState.START;

                        }
                    }


                }
                if (state == GameState.GAME_OVER) {
                    if (mousex >= 300 && mousex <= 500) {
                        if (mousey >= 425 && mousey <= 500) {
                            init();
                            state=GameState.START;

                        }
                    }
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        setFocusable(true);
        keystrokes=new HashSet<Character>();
        startscreenbackground=new Sprite("/resources/startscreenbackground.png",0,0);
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
        codeday=new Sprite("/resources/heart_codeday.png",(int)(Math.random()*(740-1+1)),20);
        thread.start();

    }

    @Override
    public void run() {

        while(running) {

            float f1 = System.currentTimeMillis();
            if(lives==0){
                state=GameState.GAME_OVER;
            }




        if(state==GameState.PLAYING){

            if(badguyvelocity>4){
                badguyvelocity=4;
            }

            badguyone.y+=badguyvelocity;
            badguytwo.y+=badguyvelocity;
            vrgoggles.y++;
            codeday.y++;




            badguyone.updateHitBox();
            badguytwo.updateHitBox();
            vrgoggles.updateHitBox();
            ufo.updateHitBox();

            if (Sprite.collision(badguyone.hitbox, ufo.hitbox) || Sprite.collision(badguytwo.hitbox, ufo.hitbox)) {
                lives--;
                new Sound().playSound("/resources/pop_3.wav");
                ufo = new Sprite("/resources/ufo.gif", 400, 320);
                badguyone = new Sprite("/resources/badguy.png", (int) (Math.random() * (740 - 1 + 1) + 1), 20);
                badguytwo = new Sprite("/resources/badguy.png", (int) (Math.random() * (740 - 1 + 1) + 1), 20);
            }
            if (Sprite.collision((vrgoggles.hitbox), ufo.hitbox)) {
                score++;
                new Sound().playSound("/resources/selectcharacter.wav");
                vrgoggles = new Sprite("/resources/vrgoggles.png", (int) (Math.random() * (740 - 1 + 1) + 1), 20);
            }

            if (lives > 3) {

            }

            if (vrgoggles.y >= 340) {
                vrgoggles = new Sprite("/resources/vrgoggles.png", (int) (Math.random() * (740 - 1 + 1) + 1), 20);
            }
            if (badguyone.y >= 340) {
                badguyone = new Sprite("/resources/badguy.png", (int) (Math.random() * (740 - 1 + 1) + 1), 20);
            }
            if (badguytwo.y >= 340) {
                badguytwo = new Sprite("/resources/badguy.png", (int) (Math.random() * (740 - 1 + 1) + 1), 20);
            }
            if(codeday.y>=340){
                codeday=new Sprite("/resources/heart_codeday.png",(int)(Math.random()*(740-1+1)),20);
            }
            for (char c : keystrokes) {

                if (c == 'a') {

                    ufo.x -= 4;
                }
                if (c == 'd') {

                    ufo.x += 4;
                }
            }
            if (ufo.x < -30) {
                ufo.x = -30;
            }
            if (ufo.x > 750) {
                ufo.x = 750;

            }
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
        if(state==GameState.START){
            bgsound.volume=true;
            bgsound.play();
            startscreenbackground.characterimage.paintIcon(this, g, startscreenbackground.x, startscreenbackground.y);

            //Play Button
            g.setColor(Color.white);

            g.fill3DRect(300,225,200,75,true);

            //Play Button Text
            g.setFont(scorefont.font);
            g.setColor(Color.black);
            g.drawString("Play", 370, 270);


            g.setColor(Color.white);
            g.fill3DRect(300,325,200,75,true);
            //Play Button Text
            g.setFont(scorefont.font);
            g.setColor(Color.black);
            g.drawString("Credits", 350, 370);


            //Quit
            g.setColor(Color.white);
            g.fill3DRect(300,425,200,75,true);
            //Play Button Text
            g.setFont(scorefont.font);
            g.setColor(Color.black);
            g.drawString("Quit", 370, 470);

            g.setColor(Color.white);
            g.fill3DRect(700,475,75,75,true);

            g.setFont(new FontSprite("/resources/pixelated.ttf",0,0,20).font);
            g.setColor(Color.black);
            g.drawString("How To", 705, 500);
            g.drawString("Play",720,540);
        }
        if(state==GameState.INSTRUCTIONS){
            bgsound.volume=true;
            bgsound.play();
            super.paintComponent(g);
            startscreenbackground.characterimage.paintIcon(this, g, startscreenbackground.x, startscreenbackground.y);
            g.setColor(Color.black);
            g.fillRect(0,0,800,600);

        }
        if(state==GameState.GAME_OVER){
            super.paintComponent(g);
            playbackground.characterimage.paintIcon(this, g, playbackground.x, playbackground.y);
            g.setColor(Color.white);
            g.setFont(new FontSprite("/resources/pixelated.ttf/",200,300,70).font);
            g.drawString("Game Over",250,200);
            g.setColor(Color.white);
            g.fill3DRect(300,425,200,75,true);

            g.setFont(scorefont.font);
            g.setColor(Color.black);
            g.drawString("Main Menu",320,470);
        }

        if(state==GameState.CREDITS){
            bgsound.volume=true;
            bgsound.play();
            super.paintComponent(g);
            startscreenbackground.characterimage.paintIcon(this, g, startscreenbackground.x, startscreenbackground.y);
            g.setColor(Color.black);
            g.fillRect(150,200,500,210);
            g.setColor(Color.white);
            g.setFont(new FontSprite("/resources/creditsfont.ttf",0,0,24).font);
            g.drawString("Programmer: Shubham Patil", 175,300);
            g.drawString("Artist: Aareev Panda", 200,350);

            g.setColor(Color.white);
            g.fill3DRect(20,20,75,75,true);
            //Play Button Text
            g.setFont(new FontSprite("/resources/pixelated.ttf",0,0,24).font);
            g.setColor(Color.black);
            g.drawString("Back",35,65);

        }

        if(state==GameState.PLAYING) {

            t.start();
            bgsound.volume=false;
            bgsound.play();
            super.paintComponent(g);
            playbackground.characterimage.paintIcon(this, g, playbackground.x, playbackground.y);
            ufo.characterimage.paintIcon(this, g, ufo.x, ufo.y);
            badguyone.characterimage.paintIcon(this, g, badguyone.x, badguyone.y);
            badguytwo.characterimage.paintIcon(this, g, badguytwo.x, badguytwo.y);
            vrgoggles.characterimage.paintIcon(this, g, vrgoggles.x, vrgoggles.y);
            if(codedayheart==true){
                codeday.characterimage.paintIcon(this,g,codeday.x,codeday.y);
            }
            if (lives == 3) {
                heartone.characterimage.paintIcon(this, g, heartone.x, heartone.y);
                hearttwo.characterimage.paintIcon(this, g, hearttwo.x, hearttwo.y);
                heartthree.characterimage.paintIcon(this, g, heartthree.x, heartthree.y);
            }
            if (lives == 2) {
                heartone.characterimage.paintIcon(this, g, heartone.x, heartone.y);
                hearttwo.characterimage.paintIcon(this, g, hearttwo.x, hearttwo.y);
            }
            if (lives == 1) {
                heartone.characterimage.paintIcon(this, g, heartone.x, heartone.y);

            }
            // g.drawRect(ufo.hitbox.x,ufo.hitbox.y,ufo.hitbox.width,ufo.hitbox.height);
            g.setFont(scorefont.font);
            g.setColor(Color.white);
            g.drawString("Score: " + score, scorefont.x, scorefont.y);
        }

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
public void init(){

    score=0;
    lives=3;
    codedayheart=false;
    badguyvelocity=1;
}

}
