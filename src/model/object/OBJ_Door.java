package model.object;

import model.entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {

    public OBJ_Door(GamePanel gp) {
        super(gp);

        name = "Door";
        down1 = Sprite("objects/door");
        collision = true;
    }
}
