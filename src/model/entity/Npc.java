package model.entity;

import main.GamePanel;

public class Npc extends Entity {

    private int npcId;

    private WalkType walkType = WalkType.STAND;
    private int hp;
    private int currentX;
    private int currentY;



    public Npc(GamePanel gp) {
        super(gp);


    }

    public void setNpcId(int npcId) { this.npcId = npcId; }
    public int getNpcId() { return npcId; }

    public void setWalkType(WalkType walkType) { this.walkType = walkType; }
    public WalkType getWalkType() { return walkType; }

    public enum WalkType {
        STAND, WALK
    }

}
