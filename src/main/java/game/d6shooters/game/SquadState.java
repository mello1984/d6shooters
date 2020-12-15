package game.d6shooters.game;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum SquadState {

    STARTTURN1, STARTTURN2, STARTTURN3,
    ALLOCATE, OTHER, CHECKHEAT, GUNFIGHT, MOVE, TOWN, ENDGAME, CROSSROAD,
    EVENT, EVENT2, EVENT3, EVENT6,
    POKER1, POKER2, POKER3, POKER4,
    ;
}
