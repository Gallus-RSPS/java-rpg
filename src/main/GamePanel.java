package main;

import model.entity.Entity;
import model.entity.Player;
import model.events.EventHandler;
import tile.TileManager;
import util.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

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
    private Thread gameThread;

    // Entity and Object
    public Player player = new Player(this, keyH);
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[20];
    public Entity[] obj = new Entity[10];
    ArrayList<Entity> entityList = new ArrayList<>();

    // Game State
    public GameState gameState;

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
        aSetter.spawnObjects();
        aSetter.spawnNpcs();
        aSetter.spawnMonsters();

        if (!debug) {
            playMusic(0);
        }

        gameState = GameState.TITLE;
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

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                cycle();
                repaint();
                delta--;
            }
            if (timer >= defaultSecond) {
                timer = 0;
            }
        }
    }

    public void cycle() {

        if (gameState == GameState.PLAY) {
            player.process();

            for (Entity npc : npc) {
                if (npc != null) {
                    npc.process();
                }
            }

            for (Entity monster : monster) {
                if (monster != null) {
                    monster.process();
                }
            }

//            Arrays.stream(npc)
//                    .filter(Objects::nonNull)
//                    .forEach(Entity::process);
//
//            Arrays.stream(monster)
//                    .filter(Objects::nonNull)
//                    .forEach(Entity::process);

        }

        if (gameState == GameState.PAUSE) {
            // TODO: set up pause state
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // Title Screen
        if (gameState == GameState.TITLE) {
            ui.draw(g2);
        }
        // Others
        else {

            // Draw tiles BEFORE player, so tiles render UNDER the player
            tileM.draw(g2);

            // Add player
            entityList.add(player);
            // Add all NPCs
            for (Entity npc : npc) {
                if (npc != null) {
                    entityList.add(npc);
                }
            }
            // Add all Monsters
            for (Entity monster : monster) {
                if (monster != null) {
                    entityList.add(monster);
                }
            }
            // Add all Objects
            for (Entity obj : obj) {
                if (obj != null) {
                    entityList.add(obj);
                }
            }

            // Sort the entity list
            Collections.sort(entityList, new Comparator<Entity>() {
               @Override
               public int compare (Entity o1, Entity o2) {
                   int result = Integer.compare(o1.getPosition().getY(), o2.getPosition().getY());

                   return result;
               }
            });

            // Draw Entities
            for (Entity entity : entityList) {
                entity.draw(g2);
            }

            // Reset the entity list to empty
            entityList.clear();

            // UI
            ui.draw(g2);
        }

        // Debug
        if (gameState != GameState.TITLE && debug) {
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
            g2.drawString(player.getPosition().toString(), 10, screenHeight - 10);
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
