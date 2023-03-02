package main;

public class Globals {

    // Screen Settings
    public final int originalTileSize = 16;
    public final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16; // Number of tiles left and right
    public final int maxScreenRow = 12; // Number of tiles up and down
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS Settings
    public final int FPS = 60;
    public final double defaultSecond = 1000000000; // amount of nanoseconds in one second
}
