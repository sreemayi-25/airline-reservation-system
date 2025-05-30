import gui.LoginFrame;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        try {
            // Sets the look and feel to match your system (Windows/Mac/Linux)
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Launch the login window
        new LoginFrame();
    }
}
