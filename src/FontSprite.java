import java.awt.*;
import java.io.IOException;

public class FontSprite{
    Font font;
    public int x;
    public int y;
    public FontSprite(String fontpath, int x, int y, int size) {

        this.x=x;
        this.y=y;
        try {
            font= Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream(fontpath)).deriveFont(Font.PLAIN,size);

        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }


}
