import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;

public class Sound {
    public void playSound(String filepath){


        try{
            Clip bloopclip= AudioSystem.getClip();
            URL file=this.getClass().getResource(filepath);

            bloopclip.open(AudioSystem.getAudioInputStream(file));
            bloopclip.start();
            Thread.sleep(bloopclip.getMicrosecondLength()/1000);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}