import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

//custom button colour when clicked on:
public class CustomButtonUI extends BasicButtonUI {
    // getPressedColor method to return a custom color
    protected Color getPressedColor() {
        return Color.RED; // replace with your desired color
    }
}