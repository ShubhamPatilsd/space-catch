import javax.print.attribute.standard.Media;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class BackgroundSound {
    Clip clip;
    boolean volume;
    public BackgroundSound(String filepath){
        volume=true;
        try{
            URL file=this.getClass().getResource(filepath);
            AudioInputStream audio= AudioSystem.getAudioInputStream(file);
            clip=AudioSystem.getClip();
            clip.open(audio);

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play(){
//        if(clip.isRunning()){
//            clip.stop();
//            clip.setFramePosition(0);
//            clip.start();
//        }
        if(volume) {
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }else{
            clip.stop();

        }



    }

}
