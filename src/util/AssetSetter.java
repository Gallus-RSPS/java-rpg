package util;

import entity.Npcs.NPC_Old_Man;
import main.GamePanel;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

    }

    public void spawnNPC() {

        gp.npc[0] = new NPC_Old_Man(gp);
        gp.npc[0].setPosition(18, 30);
    }

}
