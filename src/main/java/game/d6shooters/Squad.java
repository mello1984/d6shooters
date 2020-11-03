package game.d6shooters;

import java.util.List;

public class Squad {
    int gold = 3;
    int ammo = 5; // max 5
    int shooter = 12;
    int food = 6; // max 12
    List<SquadAction> actionList;


    public enum  SquadAction {
        HIDE, SHELTER, PATHFINDING, GUNFIGHT
    }
}
