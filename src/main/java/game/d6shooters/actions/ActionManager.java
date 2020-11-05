package game.d6shooters.actions;

import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Squad;

public class ActionManager {
    Action actionDice1 = new ActionDice1();
    Action actionDice2 = new ActionDice2();
    Action actionDice3 = new ActionDice3();
    Action actionDice4 = new ActionDice4();
    Action actionDice5 = new ActionDice5();
    Action actionDice6 = new ActionDice6();
    Action actionFeeding = new ActionFeeding();
    Squad squad;
    DicesCup dicesCup;

    public ActionManager(Squad squad, DicesCup dicesCup) {
        this.squad = squad;
        this.dicesCup = dicesCup;
    }

    public void doActions() {
        actionFeeding.action(squad, dicesCup);
        actionDice2.action(squad, dicesCup);
        actionDice3.action(squad, dicesCup);
        actionDice4.action(squad, dicesCup);
        actionDice5.action(squad, dicesCup);
        actionDice6.action(squad, dicesCup);
        actionDice1.action(squad, dicesCup);
        squad.setPeriod(squad.getPeriod() + 1);
        System.out.println(squad.toString());
    }
}
