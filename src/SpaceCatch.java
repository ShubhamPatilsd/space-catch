import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SpaceCatch {
    static JFrame frame;
    static Game game=new Game();
    public static void main(String[] args) {
        frame=new JFrame("Space Catch");
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(game);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try{
                    game.stop();
                }catch(InterruptedException interruptedException){
                    interruptedException.printStackTrace();
                }
            }
        });
        frame.setVisible(true);
    }
}
