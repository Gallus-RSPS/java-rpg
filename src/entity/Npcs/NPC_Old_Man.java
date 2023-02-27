package entity.Npcs;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class NPC_Old_Man extends Entity {

    public NPC_Old_Man(GamePanel gp) {
        super(gp);

        speed = 1;
        direction = "down";

        loadSprites();
    }

    public void loadSprites() {
        up1 = Sprite("npc/old_man/up_1");
        up2 = Sprite("npc/old_man/up_2");
        down1 = Sprite("npc/old_man/down_1");
        down2 = Sprite("npc/old_man/down_2");
        left1 = Sprite("npc/old_man/left_1");
        left2 = Sprite("npc/old_man/left_2");
        right1 = Sprite("npc/old_man/right_1");
        right2 = Sprite("npc/old_man/right_2");
    }

    public void setAction() {

        actionLockCounter++;

        if (actionLockCounter == 120) {
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
