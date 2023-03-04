package model;

/**
 * Represents the position of a player or NPC.
 *
 * @author Alan Simeon
 */
public class Position {

    private int tileSize = 48;
    private int x;
    private int y;
    private int lastX;
    private int lastY;

    /**
     * Creates a new Position with the specified coordinates.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     */
    public Position(int x, int y) {
        this.setX(x * tileSize);
        this.setY(y * tileSize);
    }

    public String toString() { return "Position(" + getX()/tileSize + ", " + getY()/tileSize + ")"; }

    /**
     * Checks if a given Position is equal to the Player/NPC Position
     * @param other the Position we are checking
     * @return true if the two Positions are equal, false if they are not
     */
    public boolean equals(Object other) {
        if (other instanceof Position) {
            Position p = (Position) other;
            return x == p.x && y == p.y;
        }
        return false;
    }

    /**
     * Sets this Position as the other Position. <b> Please use this method
     * instead of player.setPosition(other)</b> because of reference conflicts
     * (if the other position gets modified, so will the players).
     *
     * @param other the other Position
     */
    public void setAs(Position other) {
        this.x = other.x;
        this.y = other.y;
        this.lastX = other.lastX;
        this.lastY = other.lastY;
    }

    /**
     * Moves the Position
     *
     * We must invert the given Y amount due to the game world
     * being a grid.
     *
     * Up is Negative, Down is Positive
     * Left is Negative, Right is Positive
     *
     * @param amountX the amount of X coordinates
     * @param amountY the amount of Y coordinates
     */
    public void move(int amountX, int amountY) {
        setLastX(getX());
        setLastY(getY());
        setX(getX() + amountX);
        setY(getY() - amountY);
    }

    public void setX(int x) { this.x = x * tileSize; }
    public int getX() { return x / tileSize; }

    public void setY(int y) { this.y = y * tileSize; }
    public int getY() { return y / tileSize; }

    public void setLastX(int x) { this.lastX = x * tileSize; }
    public int getLastX() { return lastX / tileSize; }
    public void setLastY(int y) { this.lastY = y * tileSize; }
    public int getLastY() { return lastY / tileSize; }
}
