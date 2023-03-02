package entity.Npcs;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class NPC_Old_Man extends Entity {

    GamePanel gp;

    public NPC_Old_Man(GamePanel gp) {
        super(gp);

        this.gp = gp;

        speed = 1;
        direction = "down";

        loadSprites();
        setDialogue();
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

    public void setDialogue() {
        dialogues[0] = "Hello, lad.";
        dialogues[1] = "So, you've come to this island to find \nthe treasure?";
        dialogues[2] = "I used to be a great wizard but now... \nI'm a bit too old for going on adventures.";
        dialogues[3] = "Well, good luck to you.";
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

    public void speak() {
        /**
         * This speaks method is here for character specific things
         * such as quests or if your player has a special item.
         *
         * It can also be used for later features, like having some sort
         * of task system in place.
         *
         * Dialogue will still function properly without having this here.
         */
        super.speak();
    }

}
