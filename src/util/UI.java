package util;

import main.GamePanel;

import java.awt.*;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;

    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public boolean gameFinished = false;

    public String currentDialogue = "";

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
    }

    public void displayMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        // Set global g2 so it can be used within other methods inside the UI class
        this.g2 = g2;

        // Set the default font type and font color
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        // Handle the game state
        handleGameState(gp.gameState);
    }

    public void handleGameState(int state) {
        // Play State
        if (state == gp.playState) {
            // TODO: set up play state logic
        }

        // Pause State
        if (state == gp.pauseState) {
            drawPauseScreen();
        }

        // Dialogue State
        if (state == gp.dialogueState) {
            drawDialogueScreen();
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
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
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
