import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Sprite {
    public ImageIcon characterimage;
    public Rectangle hitbox;
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
        hitbox=new Rectangle(this.x,this.y,characterimage.getIconWidth(),characterimage.getIconHeight());
    }
public void updateHitBox(){
        hitbox=hitbox=new Rectangle(x+(characterimage.getIconWidth()/4),y,characterimage.getIconWidth()/2,characterimage.getIconHeight());
}
public static boolean collision(Rectangle r1, Rectangle r2){
     if(r1.getBounds().intersects(r2.getBounds())){
         return true;
     }
     return false;
}

}
