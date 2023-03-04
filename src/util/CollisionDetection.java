package util;

import model.entity.Entity;
import main.GamePanel;

import java.awt.*;

public class CollisionDetection {

    GamePanel gp;

    public CollisionDetection(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        //int entityLeftWorldX = entity.worldX + entity.collisionArea.x;
        int entityLeftWorldX = entity.getPosition().getX() + entity.collisionArea.x;
        //int entityRightWorldX = entity.worldX + entity.collisionArea.x + entity.collisionArea.width;
        int entityRightWorldX = entity.getPosition().getX() + entity.collisionArea.x + entity.collisionArea.width;
        //int entityTopWorldY = entity.worldY + entity.collisionArea.y;
        int entityTopWorldY = entity.getPosition().getY() + entity.collisionArea.y;
        //int entityBottomWorldY = entity.worldY + entity.collisionArea.y + entity.collisionArea.height;
        int entityBottomWorldY = entity.getPosition().getY() + entity.collisionArea.y + entity.collisionArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch(entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity,  boolean player) {

        int index = -1;

        for(int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                Entity obj = gp.obj[i];
                //entity.collisionArea.x = entity.worldX + entity.collisionArea.x;
                entity.collisionArea.x = entity.getPosition().getX() + entity.collisionArea.x;
                //entity.collisionArea.y = entity.worldY + entity.collisionArea.y;
                entity.collisionArea.y = entity.getPosition().getY() + entity.collisionArea.y;
                //obj.collisionArea.x = obj.worldX + obj.collisionArea.x;
                obj.collisionArea.x = obj.getPosition().getX() + obj.collisionArea.x;
                //obj.collisionArea.y = obj.worldY + obj.collisionArea.y;
                obj.collisionArea.y = obj.getPosition().getY() + obj.collisionArea.y;

                switch(entity.direction) {
                    case "up":
                        entity.collisionArea.y -= entity.speed;
                        if (entity.collisionArea.intersects(obj.collisionArea)) {
                            if (obj.collision){
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.collisionArea.y += entity.speed;
                        if (entity.collisionArea.intersects(obj.collisionArea)) {
                            if (obj.collision){
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.collisionArea.x -= entity.speed;
                        if (entity.collisionArea.intersects(obj.collisionArea)) {
                            if (obj.collision){
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.collisionArea.x += entity.speed;
                        if (entity.collisionArea.intersects(obj.collisionArea)) {
                            if (obj.collision){
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                }
                entity.collisionArea.x = entity.collisionAreaDefaultX;
                entity.collisionArea.y = entity.collisionAreaDefaultY;
                obj.collisionArea.x = obj.collisionAreaDefaultX;
                obj.collisionArea.y = obj.collisionAreaDefaultY;
            }
        }
        return index;
    }
    
    // Check NPC or Monster Collision
    public int checkPlayerOnNpc(Entity entity, Entity[] target) {
        int index = -1;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                // create new Rectangles for collision detection
//                Rectangle entityRect = new Rectangle(entity.worldX + entity.collisionArea.x,
//                        entity.worldY + entity.collisionArea.y,
//                        entity.collisionArea.width,
//                        entity.collisionArea.height);
                Rectangle entityRect = new Rectangle(entity.getPosition().getX() + entity.collisionArea.x,
                        entity.getPosition().getY() + entity.collisionArea.y,
                        entity.collisionArea.width,
                        entity.collisionArea.height);
//                Rectangle targetRect = new Rectangle(target[i].worldX + target[i].collisionArea.x,
//                        target[i].worldY + target[i].collisionArea.y,
//                        target[i].collisionArea.width,
//                        target[i].collisionArea.height);
                Rectangle targetRect = new Rectangle(target[i].getPosition().getX() + target[i].collisionArea.x,
                        target[i].getPosition().getY() + target[i].collisionArea.y,
                        target[i].collisionArea.width,
                        target[i].collisionArea.height);

                switch (entity.direction) {
                    case "up":
                        entityRect.y -= entity.speed;
                        if (entityRect.intersects(targetRect)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "down":
                        entityRect.y += entity.speed;
                        if (entityRect.intersects(targetRect)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "left":
                        entityRect.x -= entity.speed;
                        if (entityRect.intersects(targetRect)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "right":
                        entityRect.x += entity.speed;
                        if (entityRect.intersects(targetRect)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                }
            }
        }
        return index;
    }

//    public int checkPlayerOnNpc(Entity entity, Entity[] target) {
//        int index = -1;
//
//        for(int i = 0; i < target.length; i++) {
//            if (target[i] != null) {
//                entity.collisionArea.x = entity.worldX + entity.collisionArea.x;
//                entity.collisionArea.y = entity.worldY + entity.collisionArea.y;
//                target[i].collisionArea.x = target[i].worldX + target[i].collisionArea.x;
//                target[i].collisionArea.y = target[i].worldY + target[i].collisionArea.y;
//
//                switch(entity.direction) {
//                    case "up":
//                        entity.collisionArea.y -= entity.speed;
//                        if (entity.collisionArea.intersects(target[i].collisionArea)) {
//                            entity.collisionOn = true;
//                            index = i;
//                        }
//                        break;
//                    case "down":
//                        entity.collisionArea.y += entity.speed;
//                        if (entity.collisionArea.intersects(target[i].collisionArea)) {
//                            entity.collisionOn = true;
//                            index = i;
//                        }
//                        break;
//                    case "left":
//                        entity.collisionArea.x -= entity.speed;
//                        if (entity.collisionArea.intersects(target[i].collisionArea)) {
//                            entity.collisionOn = true;
//                            index = i;
//                        }
//                        break;
//                    case "right":
//                        entity.collisionArea.x += entity.speed;
//                        if (entity.collisionArea.intersects(target[i].collisionArea)) {
//                            entity.collisionOn = true;
//                            index = i;
//                        }
//                        break;
//                }
//                entity.collisionArea.x = entity.collisionAreaDefaultX;
//                entity.collisionArea.y = entity.collisionAreaDefaultY;
//                target[i].collisionArea.x = target[i].collisionAreaDefaultX;
//                target[i].collisionArea.y = target[i].collisionAreaDefaultY;
//            }
//        }
//        return index;
//    }
    
    public void checkNpcOnPlayer(Entity entity){
//        entity.collisionArea.x = entity.worldX + entity.collisionArea.x;
//        entity.collisionArea.y = entity.worldY + entity.collisionArea.y;
//        gp.player.collisionArea.x = gp.player.worldX + gp.player.collisionArea.x;
//        gp.player.collisionArea.y = gp.player.worldY + gp.player.collisionArea.y;

        //entity.collisionArea.x = entity.worldX + entity.collisionArea.x;
        entity.collisionArea.x = entity.getPosition().getX() + entity.collisionArea.x;
        //entity.collisionArea.y = entity.worldY + entity.collisionArea.y;
        entity.collisionArea.y = entity.getPosition().getY() + entity.collisionArea.y;
        //gp.player.collisionArea.x = gp.player.worldX + gp.player.collisionArea.x;
        gp.player.collisionArea.x = gp.player.getPosition().getX() + gp.player.collisionArea.x;
        //gp.player.collisionArea.y = gp.player.worldY + gp.player.collisionArea.y;
        gp.player.collisionArea.y = gp.player.getPosition().getY() + gp.player.collisionArea.y;

        switch(entity.direction) {
            case "up":
                entity.collisionArea.y -= entity.speed;
                if (entity.collisionArea.intersects(gp.player.collisionArea)) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entity.collisionArea.y += entity.speed;
                if (entity.collisionArea.intersects(gp.player.collisionArea)) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entity.collisionArea.x -= entity.speed;
                if (entity.collisionArea.intersects(gp.player.collisionArea)) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entity.collisionArea.x += entity.speed;
                if (entity.collisionArea.intersects(gp.player.collisionArea)) {
                    entity.collisionOn = true;
                }
                break;
        }
        entity.collisionArea.x = entity.collisionAreaDefaultX;
        entity.collisionArea.y = entity.collisionAreaDefaultY;
        gp.player.collisionArea.x = gp.player.collisionAreaDefaultX;
        gp.player.collisionArea.y = gp.player.collisionAreaDefaultY;
    }
}
