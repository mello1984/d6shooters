package game.d6shooters.actions;

import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;

public interface Action {
    void action(Squad squad, DicesCup dicesCup);
}
