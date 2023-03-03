package util;

import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    // Debut
    public boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum = Math.min(gp.ui.commandNum + 1, 2);
            }
            if (code == KeyEvent.VK_UP) {
                gp.ui.commandNum = Math.max(gp.ui.commandNum - 1, 0);
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) { // Start Game
                    gp.gameState = gp.playState;
                    //gp.playMusic(0);
                }
                if (gp.ui.commandNum == 1) { // Load Game
                    // TODO: Set up loading game
                }
                if (gp.ui.commandNum == 2) { // Quit
                    System.exit(0);
                }
            }
        }


        if (gp.gameState == gp.playState) {
            switch (code) {
                case KeyEvent.VK_B:
                    if(gp.player.speed == 3) {
                        gp.player.speed = 4;
                    } else {
                        gp.player.speed = 3;
                    }
                    break;
                case KeyEvent.VK_W:
                    upPressed = true;
                    break;
                case KeyEvent.VK_S:
                    downPressed = true;
                    break;
                case KeyEvent.VK_A:
                    leftPressed = true;
                    break;
                case KeyEvent.VK_D:
                    rightPressed = true;
                    break;
                case KeyEvent.VK_P:
                    gp.gameState = gp.pauseState;
                    break;
                case KeyEvent.VK_T:
                    checkDrawTime = !checkDrawTime;
                    break;
                case KeyEvent.VK_ENTER:
                    enterPressed = true;
                    break;
            }
        }

        else if (gp.gameState == gp.pauseState) {
            switch (code) {
                case KeyEvent.VK_P:
                    gp.gameState = gp.playState;
                    break;
            }
        }

        else if (gp.gameState == gp.dialogueState) {
            switch (code) {
                case KeyEvent.VK_ENTER:
                    gp.gameState = gp.playState;
                    break;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        switch(code) {
            case KeyEvent.VK_W:
                upPressed = false;
                break;
            case KeyEvent.VK_S:
                downPressed = false;
                break;
            case KeyEvent.VK_A:
                leftPressed = false;
                break;
            case KeyEvent.VK_D:
                rightPressed = false;
                break;
        }

    }
}
