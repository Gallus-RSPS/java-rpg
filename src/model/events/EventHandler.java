package model.events;

import main.GamePanel;
import main.GameState;
import model.Position;

public class EventHandler {

    GamePanel gp;

    EventRect[][] eventRect;

    int prevEventX, prevEventY;
    boolean canTrigger = true;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent() {
        // Check if player is > 1 tile away from last event
        //int xDist = Math.abs(gp.player.worldX - prevEventX);
        int xDist = Math.abs(gp.player.getPosition().getX() - prevEventX);
        //int yDist = Math.abs(gp.player.worldY - prevEventY);
        int yDist = Math.abs(gp.player.getPosition().getY() - prevEventY);
        int dist = Math.max(xDist, yDist);
        // Set canTrigger to true if the player is outside 1 tile range of event trigger
        if (dist > gp.tileSize) {
            canTrigger = true;
        }

        if (canTrigger) {
            if (hit(19, 11, "left")) {
                damagePit(19, 11, GameState.DIALOGUE);
            }
            if (hit(7, 14, "any")) {
                damagePit(19, 11, GameState.DIALOGUE);
            }
            if (hit(30, 7, "up")) {
                healingPool(30, 7, GameState.DIALOGUE);
            }
            if (hit(21, 8, "any")) {
                teleport(new Position(31,45), GameState.DIALOGUE);
            }
        }
    }

    /**
     * Check if player is colliding with an event
     *
     * @param eventCol the x position of the event
     * @param eventRow the y position of the event
     * @param reqDirection the required direction a player must be facing to trigger the event when colliding with it
     * @return false if player is not colliding with the event, or player is not facing proper direction to trigger the event
     *         true if player is colliding with the event while facing the proper direction to trigger the event
     */
    public boolean hit(int eventCol, int eventRow, String reqDirection) {
        boolean hit = false;

        // Set for current event check
        //gp.player.collisionArea.x = gp.player.worldX + gp.player.collisionArea.x;
        gp.player.collisionArea.x = gp.player.getPosition().getX() + gp.player.collisionArea.x;
        //gp.player.collisionArea.y = gp.player.worldY + gp.player.collisionArea.y;
        gp.player.collisionArea.y = gp.player.getPosition().getY() + gp.player.collisionArea.y;
        eventRect[eventCol][eventRow].x = eventCol * gp.tileSize + eventRect[eventCol][eventRow].x;
        eventRect[eventCol][eventRow].y = eventRow * gp.tileSize + eventRect[eventCol][eventRow].y;

        // Check if player is colliding with the event and set hit to true
        if (gp.player.collisionArea.intersects(eventRect[eventCol][eventRow]) && !eventRect[eventCol][eventRow].eventDone) {
            if (gp.player.direction.equalsIgnoreCase(reqDirection) || reqDirection.equalsIgnoreCase("any")) {
                hit = true;

                // Record where the event was triggered at
                //prevEventX = gp.player.worldX;
                prevEventX = gp.player.getPosition().getX();
                //prevEventY = gp.player.worldY;
                prevEventY = gp.player.getPosition().getY();
            }
        }

        // Reset for next event check
        gp.player.collisionArea.x = gp.player.collisionAreaDefaultX;
        gp.player.collisionArea.y = gp.player.collisionAreaDefaultY;
        eventRect[eventCol][eventRow].x = eventRect[eventCol][eventRow].eventRectDefaultX;
        eventRect[eventCol][eventRow].y = eventRect[eventCol][eventRow].eventRectDefaultY;

        return hit;
    }

    private void damagePit(int col, int row, GameState gameState) {
        System.out.println("Damage Pit event triggered");
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fall down the stairs!";
        gp.player.life -= 1;
        //eventRect[col][row].eventDone = true;
        canTrigger = false;
    }

    private void healingPool(int col, int row, GameState gameState) {
        if (gp.keyH.enterPressed) {
            gp.gameState = gameState;
            if (gp.player.life == gp.player.maxLife) {
                gp.ui.currentDialogue = "You drink the water.\nIt is refreshing.";
            } else {
                gp.ui.currentDialogue = "You drink the water.\nYour life has been restored.";
            }
            gp.player.life = gp.player.maxLife;
        }
    }

    private void teleport(Position position, GameState gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You are teleported away!";
        gp.player.setPosition(position);
    }
}
