package game.d6shooters.game;

import game.d6shooters.road.Road;

import java.util.List;

public class Squad {
    private int gold = 3;
    private int ammo = 5; // max 5
    private int shooters = 12;
    private int food = 6; // max 12
    private int period = 0;
    private int path = 0;
    public List<SquadAction> actionList;
    public Road road = new Road();
    SquadAmmunition squadAmmunition = new SquadAmmunition();
    public SquadState squadState = SquadState.REGULAR;

    public int addGold(int value) {
        gold += value;
        return gold;
    }

    public int addAmmo(int value) {
        ammo += value;
        return ammo;
    }

    public int addShooters(int value) {
        shooters += value;
        return shooters;
    }

    public int addFood(int value) {
        food += value;
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

//    public Squad(Road road) {
//        this.road = road;
//    }

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
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
