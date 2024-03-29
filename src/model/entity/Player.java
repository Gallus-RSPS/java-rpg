package model.entity;

import main.GamePanel;
import main.GameState;
import model.Position;
import util.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    boolean moving = false;
    int pixelCounter = 0;

    // Old for key collector, delete fully once completely phased out
    //public int keyCount = 0;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        collisionArea = new Rectangle();
        collisionArea.x = 1;
        collisionArea.y = 1;
        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;
        collisionArea.width = 46;
        collisionArea.height = 46;

        setDefaultValues();
        loadSprites();
    }

    public void setDefaultValues() {
        //setPosition(24, 30);
        setPosition(new Position(24, 30));
        //speed = 3;
        direction = "down";

        // Player Status
        maxLife = 6; // Half of 1 heart = 1 life
        life = maxLife;
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

        if (!moving) {
            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
                // Player Movement WASD
                if (keyH.upPressed) {
                    direction = "up";
                    moving = true;
                }
                if (keyH.downPressed) {
                    direction = "down";
                    moving = true;
                }
                if (keyH.leftPressed) {
                    direction = "left";
                    moving = true;
                }
                if (keyH.rightPressed) {
                    direction = "right";
                    moving = true;
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

                // Check event
                gp.eHandler.checkEvent();

                // Set enter key to false if nothing is happening
                gp.keyH.enterPressed = false;
            }
        }

        if (moving) {
            // If collision is false, player can move
            if (!collisionOn) {
                switch(direction) {
                    case "up":
                        getPosition().move(0, speed);
                        break;
                    case "down":
                        getPosition().move(0, -speed);
                        break;
                    case "left":
                        getPosition().move(-speed, 0);
                        break;
                    case "right":
                        getPosition().move(speed, 0);
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

            pixelCounter += speed;

            if (pixelCounter == 48) {
                moving = false;
                pixelCounter = 0;
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
                gp.gameState = GameState.DIALOGUE;
                gp.npc[i].speak();
            }
        }
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
        if (gp.debug) {
            g2.setColor(Color.red);
            g2.drawRect(screenX + collisionArea.x, screenY + collisionArea.y, collisionArea.width, collisionArea.height);
        }
    }
}
