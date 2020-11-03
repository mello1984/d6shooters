package game.d6shooters;

import game.d6shooters.road.Road;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    Squad squad = new Squad();
    DicesCup dicesCup = new DicesCup();
    Period period = new Period();
    public Road road = new Road(this);
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


        squad.actionList = new ArrayList<>();
        int dice4count = dicesCup.getNumberDiceCurrentValue(4);
        if (dice4count > 0) {
            turnMessage.out("Необходимо распределить " + dice4count + " '4'");
            for (int i = 0; i < dice4count; i++) {
                turnMessage.out(Arrays.toString(Squad.SquadAction.values()));
                int num = Integer.parseInt(turnMessage.get());
                squad.actionList.add(Squad.SquadAction.values()[num]);
            }
        }

        turnMessage.out("Actions: " + squad.actionList);


    }

    public void endGame() {

    }

    public static void main(String[] args) {
        Game game = new Game();
        game.nextTurn();

    }


}
