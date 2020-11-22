package game.d6shooters.game;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum SquadState {

    STARTTURN(3),

    REGULAR, REROLL1, REROLL2,
    ALLOCATE, OTHER, CHECKHEAT, GUNFIGHT, MOVE, TOWN, ENDGAME, CROSSROAD,
    EVENT, EVENT2, EVENT3, EVENT6,
    POKER1, POKER2, POKER3, POKER4,

    ;

    final int maxStep;
    int step = 1;

    SquadState() {
        maxStep = 1;
    }

    SquadState(int maxStep) {
        this.maxStep = maxStep;
    }

    public void nextStep() {
        step++;
        if (step > maxStep)
            throw new IllegalArgumentException(String.format("SquadState = %s, step '%d' > maxStep '%d'", this.name(), step, maxStep));
    }

    public void resetStep() {
        step = 1;
    }
}
