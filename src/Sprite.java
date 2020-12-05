import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Sprite {
    public ImageIcon characterimage;
    public int x;
    public int y;
    public Sprite(String pathfile, int x, int y){
        try{
            URL url=this.getClass().getResource(pathfile);
            characterimage= new ImageIcon(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.x=x;
        this.y=y;
    }
}
