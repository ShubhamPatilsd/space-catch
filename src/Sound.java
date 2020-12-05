import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound {
    public void playSound(String filepath){


        try{
            Clip bloopclip= AudioSystem.getClip();
            bloopclip.open(AudioSystem.getAudioInputStream(this.getClass().getResourceAsStream(filepath)));
            bloopclip.start();
            Thread.sleep(bloopclip.getMicrosecondLength()/1000);
        }catch(Exception e){
            System.out.println(e);
        }

    }

}