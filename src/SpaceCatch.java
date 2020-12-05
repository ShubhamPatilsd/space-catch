import javax.swing.*;

public class SpaceCatch {
    static JFrame frame;
    static Game game=new Game();
    public static void main(String[] args) {
        frame=new JFrame("Space Catch");
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(game);

    }
}
