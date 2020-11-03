package game.d6shooters.actions;

import game.d6shooters.DicesCup;
import game.d6shooters.Squad;

public interface Action {
    void action(Squad squad, DicesCup dicesCup);
}
