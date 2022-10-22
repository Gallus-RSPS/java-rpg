package entity;

import main.GamePanel;
import main.KeyHandler;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public int keyCount = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
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
        getPlayerImage();
    }

    public void setDefaultValues() {

        worldX = gp.tileSize * 24;
        worldY = gp.tileSize * 34;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        up1 = setup("player_default");
        up2 = setup("player_default");
        down1 = setup("player_default");
        down2 = setup("player_default");
        left1 = setup("player_default");
        left2 = setup("player_default");
        right1 = setup("player_default");
        right2 = setup("player_default");
    }

    public BufferedImage setup(String imageName) {
        Utility util = new Utility();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/" + imageName + ".png")));
            image = util.scaledImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public void update() {
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
        if (i != -1) {
            String objectName = gp.obj[i].name.toLowerCase();

            switch(objectName) {
                case "key":
                    gp.playSFX(1);
                    keyCount++;
                    gp.obj[i] = null;
                    gp.ui.displayMessage("You got a key!");
                    break;
                case "door":
                    if (keyCount >= 1) {
                        gp.playSFX(2);
                        keyCount--;
                        gp.obj[i] = null;
                        gp.ui.displayMessage("You open the door!");
                    } else {
                        gp.ui.displayMessage("You don't have a key!");
                    }
                    break;
                case "chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    break;
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
    }
}
