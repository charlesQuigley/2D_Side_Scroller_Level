package Entities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class SpriteSheetLoader {

    public static BufferedImage SpriteSheetLoad(File f)
    {
        try {
            BufferedImage spriteSheet = ImageIO.read(f);
            return spriteSheet;
        }
        catch(IOException e)
        {
                e.printStackTrace();
        }

        return null;

    }
}
