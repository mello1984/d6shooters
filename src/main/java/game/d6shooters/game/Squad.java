package game.d6shooters.game;

import game.d6shooters.bot.Icon;
import game.d6shooters.road.Place;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Squad {
    static final int MAXAMMO = 5;
    static final int MAXFOOD = 12;
    int gold = 3;
    int ammo = 5;
    int shooters = 12;
    int food = 6;
    int period = 0;
    int path = 0;
    int gunfight = 0;
    int pathfinding = 0;
    SquadAmmunition squadAmmunition = new SquadAmmunition();
    SquadState squadState;
    Place place = Place.getNew();
    int pokerBetValue = 0;
    Icon pokerBetType;
    PokerDices pokerDices;


    public int addGold(int value) {
        gold += value;
        return gold;
    }

    public int addGunfight(int value) {
        gunfight += value;
        return gunfight;
    }

    public int addPathfinding(int value) {
        pathfinding += value;
        return pathfinding;
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
}
