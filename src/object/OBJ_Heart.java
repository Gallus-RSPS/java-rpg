package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Heart extends SuperObject {

    GamePanel gp;

    public OBJ_Heart(GamePanel gp) {
        this.gp = gp;

        name = "Heart";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_full.png")));
            image2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_half.png")));
            image3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_blank.png")));
            image = util.scaledImage(image, gp.tileSize, gp.tileSize);
            image2 = util.scaledImage(image2, gp.tileSize, gp.tileSize);
            image3 = util.scaledImage(image3, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
