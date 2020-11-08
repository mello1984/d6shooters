package game.d6shooters.game;

import game.d6shooters.road.Place;
import game.d6shooters.road.RoadMap;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Squad {
    private static final int MAXAMMO = 5;
    private static final int MAXFOOD = 12;
    private int gold = 3;
    private int ammo = 5;
    private int shooters = 12;
    private int food = 6;
    private int period = 0;
    private int path = 0;
    public List<SquadAction> actionList;
    private int gunfight = 0;
    private int pathfinding = 0;
    SquadAmmunition squadAmmunition = new SquadAmmunition();
    public SquadState squadState;
    Place place = Place.getNew();


    public int addGold(int value) {
        gold += value;
        return gold;
    }

    public int addAmmo(int value) {
        ammo += value;
        ammo = Math.min(ammo, MAXAMMO);
        return ammo;
    }

    public int addShooters(int value) {
        shooters += value;
        return shooters;
    }

    public int addFood(int value) {
        food += value;
        food = Math.min(food, MAXFOOD);
        return food;
    }

    public int addPeriod(int value) {
        period += value;
        return period;
    }

    public int addPath(int value) {
        path += value;
        return path;
    }

    @Override
    public String toString() {
        return "Squad{" +
                "gold=" + gold +
                ", ammo=" + ammo +
                ", shooters=" + shooters +
                ", food=" + food +
                ", period=" + period +
                ", path=" + path +
                ", squadAmmunition=" + squadAmmunition +
                '}';
    }

    public enum SquadAction {
        HIDE, SHELTER, PATHFINDING, GUNFIGHT
    }
}
