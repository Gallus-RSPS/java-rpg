package util;

import main.GamePanel;
import object.OBJ_Heart;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    Font retro;
    BufferedImage heart_full, heart_half, heart_blank;

    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public boolean gameFinished = false;

    public int commandNum = 0;

    public String currentDialogue = "";

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Retro Gaming.ttf");
            assert is != null;
            retro = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        // Create HUD Object
        SuperObject heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

    }

    public void displayMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        // Set global g2, so it can be used within other methods inside the UI class
        this.g2 = g2;

        // Set the default font type and font color
        g2.setFont(retro);
        g2.setColor(Color.white);

        // Handle the game state
        handleGameState(gp.gameState);
    }

    public void handleGameState(int state) {
        // Title State
        if (state == gp.titleState) {
            drawTitleScreen();
        }

        // Play State
        if (state == gp.playState) {
            // TODO: set up play state logic
            drawPlayerLife();
        }

        // Pause State
        if (state == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }

        // Dialogue State
        if (state == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }
    }

    public void drawTitleScreen() {
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Title Name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 85F));
        String text = "DeviousWTR";
        int x = getTextCenterX(text);
        int y = gp.tileSize * 3;

        // Draw Title Shadow
        g2.setColor(Color.GRAY);
        g2.drawString(text, x + 5, y + 5);
        // Draw Main Title
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // Display Player Image
        x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
        y += gp.tileSize * 2;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

        // Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

        text = "New Game";
        x = getTextCenterX(text);
        y += gp.tileSize * 3.5;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "Load Game";
        x = getTextCenterX(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "Quit";
        x = getTextCenterX(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    public void drawPlayerLife() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        // Draw Max Life
        while (i < gp.player.maxLife/2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        // reset
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;

        // Draw Current Life
        while(i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80));

        String text = "PAUSED";

        int x = getTextCenterX(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        // Create dialogue window
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4); // total width minus 4 tiles
        int height = gp.tileSize * 4; // 5 tiles high

        // Draw the chat window
        drawChatInterface(x, y, width, height);
    }

    public void drawChatInterface(int x, int y, int width, int height) {
        Color chatBack = new Color(0,0,0, getAlpha(75));
        Color chatFrame = new Color(255, 255, 255);
        int windowArch = 35;
        int borderArch = 25;

        // Draw interface and set background with color
        g2.setColor(chatBack);
        g2.fillRoundRect(x, y, width, height, windowArch, windowArch);

        // Draw interface border and set stroke color
        g2.setColor(chatFrame);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, borderArch, borderArch);

        // Set location and size of text, and draw it
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        x += gp.tileSize;
        y += gp.tileSize;
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 35;
        }
    }

    public int getTextCenterX(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int centerX = (gp.screenWidth / 2) - (length / 2);

        return centerX;
    }

    public int getAlpha(int alphaPercentage) {
        if (alphaPercentage < 0 || alphaPercentage > 100) {
            throw new IllegalArgumentException("Alpha percentage must be between 0 and 100.");
        }
        return (int) Math.round(alphaPercentage * 2.55);
    }
}
