package model.object;

import model.entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {

    public OBJ_Heart(GamePanel gp) {
        super(gp);

        name = "Heart";
        image = Sprite("objects/heart_full");
        image2 = Sprite("objects/heart_half");
        image3 = Sprite("objects/heart_blank");
    }

}
