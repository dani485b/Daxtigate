import java.awt.*;
import static java.awt.GraphicsDevice.WindowTranslucency.*;

class Main {
    public static void main(String[] args) {
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        //If translucent windows aren't supported, exit.
        if (!gd.isWindowTranslucencySupported(TRANSLUCENT)) {
            System.err.println(
                    "Translucency is not supported");
            System.exit(0);
        }  //JFrame.setDefaultLookAndFeelDecorated(true);


        EventQueue.invokeLater(() -> {
            try {
                MainWindow mainWD = new MainWindow();
                mainWD.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}