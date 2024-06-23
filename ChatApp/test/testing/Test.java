package testing;

import com.view.swing.blurHash.BlurHash;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Test {
    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("C:\\Users\\HP\\Documents\\NetBeansProjects\\ChatApp\\src\\com\\icon\\testing\\pixel.png"));
            String blurhashStr = BlurHash.encode(image);
            System.out.println(blurhashStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
