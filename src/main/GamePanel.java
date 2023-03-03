package main;

import entity.Entity;
import entity.Player;
import events.EventHandler;
import object.SuperObject;
import tile.TileManager;
import util.*;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // Debug Settings
    public boolean debug = true;

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16; // Number of tiles left and right
    public final int maxScreenRow = 12; // Number of tiles up and down
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // World Settings
    public int maxMap = 10;
    public int maxWorldCol;
    public int maxWorldRow;
    public int currentMap = 0;

    // FPS Settings
    int FPS = 60;
    double defaultSecond = 1000000000; // amount of nanoseconds in one second

    // System
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public Sound music = new Sound();
    public Sound sfx = new Sound();
    public CollisionDetection collisionDetector = new CollisionDetection(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public EventHandler eHandler = new EventHandler(this);
    public UI ui = new UI(this);
    Thread gameThread;

    // Entity and Object
    public Player player = new Player(this, keyH);
    public Entity npc[] = new Entity[10];
    public SuperObject[] obj = new SuperObject[10];

    // Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        // Better rendering performance
        this.setDoubleBuffered(true);

        // Add the key listener
        this.addKeyListener(keyH);

        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.spawnNPC();

        playMusic(0);

        // DEV MUSIC NOT PLAYING
        stopMusic();

        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = defaultSecond / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                cycle();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= defaultSecond) {
                //System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void cycle() {

        if (gameState == playState) {
            player.process();
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].process();
                }
            }
        }

        if (gameState == pauseState) {
            // TODO: set up pause state
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // Debug
        long drawStart = 0;

        if (keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        // Title Screen
        if (gameState == titleState) {
            ui.draw(g2);
        }
        // Others
        else {

            // Draw tiles BEFORE player, so tiles render UNDER the player
            tileM.draw(g2);

            // Objects
            for (SuperObject superObject : obj) {
                if (superObject != null) {
                    superObject.draw(g2, this);
                }
            }

            // Draw NPCs
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].draw(g2);
                }
            }

            // Draw player AFTER tiles, so player renders ABOVE the tiles
            player.draw(g2);

            // UI
            ui.draw(g2);
        }

        // Debug
        if (gameState != titleState && debug) {
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
            g2.drawString(player.getPosition(), 10, screenHeight - 10);
        }

        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSFX(int i) {
        sfx.setFile(i);
        sfx.play();
    }
}
