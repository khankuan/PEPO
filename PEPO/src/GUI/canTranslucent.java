package GUI;
import static java.awt.GraphicsDevice.WindowTranslucency.*;
import java.awt.*;

/*
 * @author hongwei
 */
public class canTranslucent {
    
    private static boolean allowTranslucent;
    
    static void init()
    {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
             if(gd.isWindowTranslucencySupported(TRANSLUCENT))
                allowTranslucent = true;
            else
                allowTranslucent = false;
        } catch(Throwable obj) { allowTranslucent = false; }
    }
    
    static boolean check() {
        return allowTranslucent;
    }
    
}
