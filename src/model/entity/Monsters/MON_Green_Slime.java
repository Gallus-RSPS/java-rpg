package model.entity.Monsters;

import model.entity.Entity;
import main.GamePanel;

import java.util.Random;

public class MON_Green_Slime extends Entity {

    public MON_Green_Slime(GamePanel gp) {
        super(gp);

        name = "Green Slime";
        maxLife = 4;
        life = maxLife;

        loadSprites();
    }

    public void loadSprites() {
        up1 = Sprite("monster/slime/green/down_1");
        up2 = Sprite("monster/slime/green/down_2");
        down1 = Sprite("monster/slime/green/down_1");
        down2 = Sprite("monster/slime/green/down_2");
        left1 = Sprite("monster/slime/green/down_1");
        left2 = Sprite("monster/slime/green/down_2");
        right1 = Sprite("monster/slime/green/down_1");
        right2 = Sprite("monster/slime/green/down_2");
    }

    public void behaviour() {
        actionLockCounter++;

        if (actionLockCounter == 120) {
            moving = true;
            Random random = new Random();
            int i = random.nextInt(100) + 1; // pick a number from 1 to 100

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i < 50) {
                direction = "down";
            }
            if (i > 50 && i < 75) {
                direction = "left";
            }
            if (i > 75) {
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }

}
