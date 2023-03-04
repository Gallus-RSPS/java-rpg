package util;

import main.GamePanel;
import main.GameState;

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

        if (gp.gameState == GameState.TITLE) {
            if (code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum = Math.min(gp.ui.commandNum + 1, 2);
            }
            if (code == KeyEvent.VK_UP) {
                gp.ui.commandNum = Math.max(gp.ui.commandNum - 1, 0);
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) { // Start Game
                    gp.gameState = GameState.PLAY;
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


        if (gp.gameState == GameState.PLAY) {
            switch (code) {
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
                    gp.gameState = GameState.PAUSE;
                    break;
                case KeyEvent.VK_T:
                    checkDrawTime = !checkDrawTime;
                    break;
                case KeyEvent.VK_ENTER:
                    enterPressed = true;
                    break;
            }
        }

        else if (gp.gameState == GameState.PAUSE) {
            switch (code) {
                case KeyEvent.VK_P:
                    gp.gameState = GameState.PLAY;
                    break;
            }
        }

        else if (gp.gameState == GameState.DIALOGUE) {
            switch (code) {
                case KeyEvent.VK_ENTER:
                    gp.gameState = GameState.PLAY;
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
