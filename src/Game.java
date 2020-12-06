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
    public Timer t=new Timer(20000,this);
    public Sprite badguytwo;
    public Sprite playbackground;
    public Sprite vrgoggles;
    public Sprite heartone;
    public Sprite hearttwo;
    public Sprite heartthree;
    public Sprite startscreenbackground;
    public Sprite vrgogglesbig=new Sprite("/resources/vrgogglesbig.png",0,0);
    public Sprite bigheartcodeday=new Sprite("/resources/bigheart_codeday.png",0,0);
    private boolean codedayheart=false;
    public Sprite codeday;
    private HashSet<Character> keystrokes;
    private int badguyvelocity=1;
    private BackgroundSound bgsound =new BackgroundSound("/resources/backgroundmusic.wav");
    private BackgroundSound playsound =new BackgroundSound("/resources/playmusoc.wav");

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
                            playsound.clip.setFramePosition(0);

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

                if(state==GameState.INSTRUCTIONS){
                    if (mousex >= 20 && mousex <= 95) {
                        if (mousey >= 20 && mousey <= 95) {
                            state = GameState.START;

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
        //playbackground=new Sprite("/resources/playbackground.gif",0,0);
        playbackground=new Sprite("/resources/alternate_background.gif",0,0);
        ufo=new Sprite("/resources/ufo.gif",400,440);
        badguyone=new Sprite("/resources/rocket.gif",(int)(Math.random()*(740-1+1)+1),20);
        badguytwo=new Sprite("/resources/rocket.gif",(int)(Math.random()*(740-1+1)+1),20);
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

            //new Sound().playSound("/resources/gameoversound.wav");




        if(state==GameState.PLAYING){
            if(lives==0){
                playsound.volume=false;
                playsound.play();
                state=GameState.GAME_OVER;
                new Sound().playSound("/resources/gameoversound.wav");
            }
            if(badguyvelocity>5){
                badguyvelocity=5;
            }

            badguyone.y+=badguyvelocity;
            badguytwo.y+=badguyvelocity;
            vrgoggles.y++;





            badguyone.updateHitBox();
            badguytwo.updateHitBox();
            vrgoggles.updateHitBox();
            ufo.updateHitBox();


            if (Sprite.collision(badguyone.hitbox, ufo.hitbox) || Sprite.collision(badguytwo.hitbox, ufo.hitbox)) {
                lives--;
                playsound.volume=false;
                playsound.play();
                new Sound().playSound("/resources/pop_3.wav");
                playsound.volume=true;
                playsound.play();
                ufo = new Sprite("/resources/ufo.gif", 400, 440);
                badguyone=new Sprite("/resources/rocket.gif",(int)(Math.random()*(740-1+1)+1),20);
                badguytwo=new Sprite("/resources/rocket.gif",(int)(Math.random()*(740-1+1)+1),20);
            }
            if (Sprite.collision((vrgoggles.hitbox), ufo.hitbox)) {
                score++;
                playsound.volume=false;
                playsound.play();
                new Sound().playSound("/resources/selectcharacter.wav");
                playsound.volume=true;
                playsound.play();
                vrgoggles = new Sprite("/resources/vrgoggles.png", (int) (Math.random() * (740 - 1 + 1) + 1), 20);
            }




            if (vrgoggles.y >= 440) {
                vrgoggles = new Sprite("/resources/vrgoggles.png", (int) (Math.random() * (740 - 1 + 1) + 1), 20);
            }
            if (badguyone.y >= 440) {
                badguyone=new Sprite("/resources/rocket.gif",(int)(Math.random()*(740-1+1)+1),20);

            }
            if (badguytwo.y >= 440) {
                badguytwo=new Sprite("/resources/rocket.gif",(int)(Math.random()*(740-1+1)+1),20);
            }
            if(codedayheart) {
                if(Sprite.collision((codeday.hitbox),ufo.hitbox)){
                    if(lives<3){
                        lives++;
                        codedayheart=false;
                        ufo=new Sprite("/resources/ufo.gif",400,440);

                    }
                }
                if (codeday.y >= 440) {
                    codedayheart=false;


                }
                codeday.y++;
                codeday.updateHitBox();
            }
            if (!keystrokes.isEmpty()) {
                for (char c : keystrokes) {

                    if (c == 'a') {

                        ufo.x -= 4;
                    }
                    if (c == 'd') {

                        ufo.x += 4;
                    }
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
            playsound.volume=false;
            playsound.play();
            bgsound.play();
            startscreenbackground.characterimage.paintIcon(this, g, startscreenbackground.x, startscreenbackground.y);

            //Play Button
            g.setColor(Color.white);
            ufo.characterimage.paintIcon(this, g, 352,25);
            g.setFont(new FontSprite("/resources/creditsfont.ttf",0,0,48).font);
            g.drawString("Space Catch",185,150);
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
            playsound.volume=false;
            playsound.play();
            bgsound.play();
            super.paintComponent(g);
            startscreenbackground.characterimage.paintIcon(this, g, startscreenbackground.x, startscreenbackground.y);
            g.setColor(Color.black);
            g.fillRect(0,0,800,600);
            g.setFont(new FontSprite("/resources/creditsfont.ttf",0,0,24).font);

            g.setColor(Color.white);
            g.drawString("You are a ",175,150);
            ufo.characterimage.paintIcon(this,g,400,100);

            g.setColor(Color.white);
            badguyone.characterimage.paintIcon(this,g,175,200);
            g.drawString("hurts you",250,225);



            vrgogglesbig.characterimage.paintIcon(this,g,175,275);
            g.setColor(Color.white);
            g.drawString("gives you points",275,325);


            bigheartcodeday.characterimage.paintIcon(this,g,175,400);
            g.setColor(Color.white);
            g.drawString("gives you more lives",275,450);


            g.fill3DRect(20,20,75,75,true);
            g.setFont(new FontSprite("/resources/pixelated.ttf",0,0,24).font);
            g.setColor(Color.black);
            g.drawString("Back",35,65);


        }
        if(state==GameState.GAME_OVER){
            playsound.volume=false;
            playsound.play();
            super.paintComponent(g);
            playbackground.characterimage.paintIcon(this, g, playbackground.x, playbackground.y);
            g.setColor(Color.white);
            g.setFont(new FontSprite("/resources/pixelated.ttf/",200,300,70).font);
            g.drawString("Game Over",250,200);
            g.setFont(scorefont.font);
            g.drawString("Score: " + score, scorefont.x, scorefont.y);

            g.setColor(Color.white);
            g.fill3DRect(300,425,200,75,true);

            g.setFont(scorefont.font);
            g.setColor(Color.black);
            g.drawString("Main Menu",320,470);
        }

        if(state==GameState.CREDITS){
            bgsound.volume=true;
            playsound.volume=false;
            playsound.play();
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
            playsound.volume=true;
            t.start();
            bgsound.volume=false;
            bgsound.play();
            playsound.play();
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
    t.restart();
    score=0;
    lives=3;
    codedayheart=false;
    badguyvelocity=1;
}

}
