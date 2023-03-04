package model.entity;

import main.GamePanel;
import model.Position;
import util.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {

    GamePanel gp;
    public Position position;
    public int speed = 3;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle collisionArea = new Rectangle(1, 1, 46, 46);
    public int collisionAreaDefaultX, collisionAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public String[] dialogues = new String[20];
    public int dialogueIndex = 0;
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;

    // Entity Shared Statuses
    public int maxLife;
    public int life;

    public boolean moving = false;
    int pixelCounter = 0;

    public Entity (GamePanel gp) {
        this.gp = gp;
    }

    public void behaviour() {/*This is handled in individual NPC classes*/}
    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex ++;

        switch(gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
    public void process() {
        behaviour();

        // Check collisions
        collisionOn = false;
        gp.collisionDetector.checkTile(this);
        gp.collisionDetector.checkObject(this, false);
        gp.collisionDetector.checkNpcOnPlayer(this);

        if (moving) {
            // If collision is false, player can move
            if (!collisionOn) {
                switch (direction) {
                    case "up":
                        //worldY -= speed;
                        getPosition().move(0, speed);
                        break;
                    case "down":
                        //worldY += speed;
                        getPosition().move(0, -speed);
                        break;
                    case "left":
                        //worldX -= speed;
                        getPosition().move(-speed, 0);
                        break;
                    case "right":
                        //worldX += speed;
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

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        int screenX = getPosition().getX() - gp.player.getPosition().getX() + gp.player.screenX;
        int screenY = getPosition().getY() - gp.player.getPosition().getY() + gp.player.screenY;

        boolean x1 = getPosition().getX() + gp.tileSize > gp.player.getPosition().getX() - gp.player.screenX;
        boolean x2 = getPosition().getX() - gp.tileSize < gp.player.getPosition().getX() + gp.player.screenX;
        boolean y1 = getPosition().getY() + gp.tileSize > gp.player.getPosition().getY() - gp.player.screenY;
        boolean y2 = getPosition().getY() - gp.tileSize < gp.player.getPosition().getY() + gp.player.screenY;


        if (x1 && x2 && y1 && y2) {

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

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        }
    }

    public BufferedImage Sprite(String imageName) {
        Utility util = new Utility();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/" + imageName + ".png")));
            image = util.scaledImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    public Position getPosition() { return position; }

}
