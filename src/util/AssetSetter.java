package util;

import model.Position;
import model.entity.Monsters.MON_Green_Slime;
import model.entity.Npcs.NPC_Old_Man;
import main.GamePanel;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void spawnObjects() {}

    public void spawnNpcs() {
        gp.npc[0] = new NPC_Old_Man(gp);
        gp.npc[0].setPosition(new Position(18, 30));
    }

    public void spawnMonsters() {
        gp.monster[0] = new MON_Green_Slime(gp);
        gp.monster[0].setPosition(new Position(33, 28));
    }

}
