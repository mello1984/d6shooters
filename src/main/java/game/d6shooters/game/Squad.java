package game.d6shooters.game;

import game.d6shooters.bot.Icon;
import game.d6shooters.road.Place;
import game.d6shooters.road.RoadMap;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Squad {
    private static final int MAX_AMMO = 5;
    private static final int MAX_FOOD = 12;
    private static final int MAX_SHOOTERS = 12;
    private static final int MAX_BOMB = 3;

    public static final String GOLD = "gold";
    public static final String AMMO = "ammo";
    public static final String SHOOTER = "shooter";
    public static final String FOOD = "food";
    public static final String PERIOD = "period";
    public static final String PATH = "path";
    public static final String GUNFIGHT = "gunfight";
    public static final String PATHFINDING = "pathfinding";
    public static final String COMPASS = "compass";
    public static final String HUNTER = "hunter";
    public static final String MAP = "map";
    public static final String BINOCULAR = "binocular";
    public static final String PILL = "pill";
    public static final String BOMB = "bomb";
    private Map<String, Integer> resources = new HashMap<>();

    SquadState squadState;
    RoadMap roadMap = new RoadMap(this);
    Place place = Place.getNew(this);
    Icon pokerBetType;
    int pokerBetValue = 0;
//    PokerDices pokerDices; // move
    boolean canActivateEvent = true;

    public Squad() {
        resources.put(GOLD, 3);
        resources.put(AMMO, 5);
        resources.put(SHOOTER, 12);
        resources.put(FOOD, 6);
        resources.put(PERIOD, 0);
        resources.put(PATH, 0);
        resources.put(GUNFIGHT, 0);
        resources.put(PATHFINDING, 0);
        resources.put(COMPASS, 0);
        resources.put(HUNTER, 0);
        resources.put(MAP, 0);
        resources.put(BINOCULAR, 0);
        resources.put(PILL, 0);
        resources.put(BOMB, 0);
    }

    public int getResource(String resource) {
        return resources.get(resource);
    }

    public boolean hasResource(String resource) {
        return resources.get(resource) > 0;
    }

    public void setResource(String resource, int value) {
        resources.put(resource, value);
    }

    public int addResource(String resource, int value) {
        int x = resources.get(resource);
        x += value;
        if (resource.equals(AMMO)) x = Math.min(x, MAX_AMMO);
        if (resource.equals(FOOD)) x = Math.min(x, MAX_FOOD);
        if (resource.equals(SHOOTER)) x = Math.min(x, MAX_SHOOTERS);
        if (resource.equals(BOMB)) x = Math.min(x, MAX_BOMB);
        resources.put(resource, x);
        return x;
    }
}
