package game.d6shooters;

import game.d6shooters.road.Road;

import java.util.List;

public class Squad {
    private int gold = 3;
    private int ammo = 5; // max 5
    private int shooters = 12;
    private int food = 6; // max 12
    public List<SquadAction> actionList;

    private int roadLength = 0;
    SquadAmmunition squadAmmunition = new SquadAmmunition();

    public int getRoadLength() {
        return roadLength;
    }

    public void setRoadLength(int roadLength) {
        this.roadLength = roadLength;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getShooters() {
        return shooters;
    }

    public void setShooters(int shooters) {
        this.shooters = shooters;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public enum SquadAction {
        HIDE, SHELTER, PATHFINDING, GUNFIGHT
    }
}
