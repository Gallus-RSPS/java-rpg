package util;

import main.GamePanel;

import java.awt.*;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;

    // Old for key collecting game, delete once completely phased out
//    BufferedImage keyImage;

    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public boolean gameFinished = false;

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        // Old for key collecting game, delete once completely phased out
//        OBJ_Key key = new OBJ_Key(gp);
//        keyImage = key.image;
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


        if (gp.gameState == gp.playState) {
            // TODO: set up play state logic
        }

        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80));

        String text = "PAUSED";

        int x = getTextCenterX(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public int getTextCenterX(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int centerX = (gp.screenWidth / 2) - (length / 2);

        return centerX;
    }
}
