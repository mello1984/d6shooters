package game.d6shooters.game;

public class SquadAmmunition {
    boolean compass = false;
    boolean hunter = false;
    boolean map = false;
    boolean binocular = false;
    boolean drugs = false;
    boolean weapon = false;

    @Override
    public String toString() {
        return "SquadAmmunition{" +
                "compass=" + compass +
                ", hunter=" + hunter +
                ", map=" + map +
                ", binocular=" + binocular +
                ", drugs=" + drugs +
                ", weapon=" + weapon +
                '}';
    }
}
