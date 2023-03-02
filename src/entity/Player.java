package entity;

import main.GamePanel;
import util.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    // Old for key collector, delete fully once completely phased out
    //public int keyCount = 0;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        collisionArea = new Rectangle();
        collisionArea.x = 12;
        collisionArea.y = 21;
        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;
        collisionArea.width = 24;
        collisionArea.height = 27;

        setDefaultValues();
        loadSprites();
    }

    public void setDefaultValues() {
        setPosition(24, 34);
        speed = 4;
        direction = "down";
    }

    public void loadSprites() {
        up1 = Sprite("player/walking/up_1");
        up2 = Sprite("player/walking/up_2");
        down1 = Sprite("player/walking/down_1");
        down2 = Sprite("player/walking/down_2");
        left1 = Sprite("player/walking/left_1");
        left2 = Sprite("player/walking/left_2");
        right1 = Sprite("player/walking/right_1");
        right2 = Sprite("player/walking/right_2");
    }

    public void process() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            // Player Movement WASD
            if (keyH.upPressed) {
                direction = "up";
            }
            if (keyH.downPressed) {
                direction = "down";
            }
            if (keyH.leftPressed) {
                direction = "left";
            }
            if (keyH.rightPressed) {
                direction = "right";
            }

            // Check tile collision
            collisionOn = false;
            gp.collisionDetector.checkTile(this);

            // Check object collision
            int objIndex = gp.collisionDetector.checkObject(this, true);
            pickUpObject(objIndex);

            // Check NPC collision
            int npcIndex = gp.collisionDetector.checkPlayerOnNpc(this, gp.npc);
            interactNpc(npcIndex);

            // If collision is false, player can move
            if (!collisionOn) {
                switch(direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

    }

    public void pickUpObject(int i) {
        if (i != 999) {
            // TODO: Set up game objects
        }
    }

    public void interactNpc(int i) {
        if (i != -1) {
            if (keyH.enterPressed) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
        keyH.enterPressed = false;
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch(direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, null);
    }
}
