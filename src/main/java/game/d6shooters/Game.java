package game.d6shooters;

import game.d6shooters.actions.ActionManager;
import game.d6shooters.road.Road;

public class Game {
    public DicesCup dicesCup = new DicesCup();
    Period period = new Period();
    public Road road = new Road(this);
    public Squad squad = new Squad(road);
    public ActionManager actionManager = new ActionManager(squad, dicesCup);
    public static TurnMessage turnMessage = new TurnMessageConsole();

    public void nextTurn() {
        turnMessage.out(dicesCup.getFirstTurnDices().toString());
        turnMessage.out("Введите номера кубиков для переброски или exit");
        String str = turnMessage.get();
        while (!dicesCup.checkString(str)) {
            turnMessage.out("Некорректные данные, введите номера кубиков для переброски или exit");
            str = turnMessage.get();
        }
        turnMessage.out(dicesCup.getRerollDices(str).toString());
        str = turnMessage.get();
        while (!dicesCup.checkString(str)) {
            turnMessage.out("Некорректные данные, введите номера кубиков для переброски или exit");
            str = turnMessage.get();
        }
        turnMessage.out(dicesCup.getRerollDices(str).toString());

        actionManager.doActions();

    }

    public void endGame() {

    }

    public static void main(String[] args) {
        Game game = new Game();
        game.nextTurn();

    }


}
