package game.d6shooters.actions;

import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Game;
import game.d6shooters.game.Squad;
import game.d6shooters.bot.TurnMessage;

public class ActionEvent implements Action {
    TurnMessage turnMessage = Game.turnMessage;

    @Override
    public void action(Squad squad, DicesCup dicesCup) {
        int rnd = DicesCup.getD6Int();
        boolean correctExit = true;
        turnMessage.out("Event.");
        turnMessage.out(squad.toString());
        do {
            switch (rnd) {
                case 1:
                    turnMessage.out("Event. Короткий путь");
                    squad.road.next();
                    squad.road.next();
                    squad.road.next();
                    break;
                case 2:
                    if (squad.getAmmo() == 0) break;
                    turnMessage.out("Event. Стадо животных. Можно поохотиться: 1 - охотиться, 2 - ехать дальше");
                    if (Integer.parseInt(turnMessage.get()) == 1) {
                        squad.setAmmo(squad.getAmmo() - 1);
                        squad.setFood(squad.getFood() + 1);
                    }
                    break;
                case 3:
                    if (squad.getAmmo() >= 2 || squad.getFood() >= 2 || squad.getGold() > 0) {
                        boolean result = false;
                        while (!result) {
                            turnMessage.out("Event. Торговый обоз. Можно поторговать: 1 - купить еду, 2 - купить патроны, 3 - продать еду, 4 - продать патроны, 5 - ехать дальше");
                            if (Integer.parseInt(turnMessage.get()) == 1) {
                                if (squad.getGold() > 0) {
                                    squad.setGold(squad.getGold() - 1);
                                    squad.setFood(squad.getFood() + 2);
                                    result = true;
                                }
                            }
                            if (Integer.parseInt(turnMessage.get()) == 2) {
                                if (squad.getGold() > 0) {
                                    squad.setGold(squad.getGold() - 1);
                                    squad.setAmmo(squad.getAmmo() + 2);
                                    result = true;
                                }
                            }
                            if (Integer.parseInt(turnMessage.get()) == 3) {
                                if (squad.getFood() > 2) {
                                    squad.setFood(squad.getFood() - 2);
                                    squad.setGold(squad.getGold() + 1);
                                    result = true;
                                }
                            }
                            if (Integer.parseInt(turnMessage.get()) == 4) {
                                if (squad.getAmmo() > 2) {
                                    squad.setAmmo(squad.getAmmo() - 2);
                                    squad.setGold(squad.getGold() + 1);
                                    result = true;
                                }
                            }
                            if (Integer.parseInt(turnMessage.get()) == 5) {
                                result = true;
                            }
                        }
                    }
                    break;
                case 4://nothing
                    turnMessage.out("Event. Открытая дотога.");
                    break;
                case 5:
                    turnMessage.out("Event. Заблудились.");
                    squad.setFood(squad.getFood() - 1);
                    squad.setShooters(squad.getShooters() - 1);
                    break;
                case 6:
                    boolean out = true;
                    do {
                        turnMessage.out("Event. Непредвиденные потери: 1 - 2 стрелка, 2 - 1 золото + 1 еда, 3 - 2 еды, 4 - 2 золота");
                        if (Integer.parseInt(turnMessage.get()) == 1) {
                            squad.setShooters(squad.getShooters() - 2);
                        } else if (Integer.parseInt(turnMessage.get()) == 2 && squad.getGold() > 0 && squad.getFood() > 0) {
                            squad.setGold(squad.getGold() - 1);
                            squad.setFood(squad.getFood() - 1);
                        } else if (Integer.parseInt(turnMessage.get()) == 3 && squad.getFood() >= 2) {
                            squad.setFood(squad.getFood() - 2);
                        } else if (Integer.parseInt(turnMessage.get()) == 4 && squad.getGold() >= 2) {
                            squad.setGold(squad.getGold() - 2);
                        } else out = false;
                    } while (out);
                    break;
                default:
                    correctExit = false;
            }
        } while (correctExit);
        turnMessage.out(squad.toString());
    }
}
