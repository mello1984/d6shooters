package game.d6shooters.actions;

import game.d6shooters.bot.ReceiverMessage;
import game.d6shooters.game.DicesCup;
import game.d6shooters.game.Game;
import game.d6shooters.game.Squad;
import game.d6shooters.bot.SenderMessage;

public class ActionEvent implements Action {
    SenderMessage senderMessage = Game.senderMessage;
    ReceiverMessage receiverMessage = Game.receiverMessage;

    @Override
    public void action(Squad squad, DicesCup dicesCup) {
        int rnd = DicesCup.getD6Int();
        boolean correctExit = true;
        senderMessage.sendText(0L,"Event.");
        senderMessage.sendText(0L,squad.toString());
        do {
            switch (rnd) {
                case 1:
                    senderMessage.sendText(0L,"Event. Короткий путь");
                    squad.road.next();
                    squad.road.next();
                    squad.road.next();
                    break;
                case 2:
                    if (squad.getAmmo() == 0) break;
                    senderMessage.sendText(0L,"Event. Стадо животных. Можно поохотиться: 1 - охотиться, 2 - ехать дальше");
                    if (Integer.parseInt(receiverMessage.get()) == 1) {
                        squad.setAmmo(squad.getAmmo() - 1);
                        squad.setFood(squad.getFood() + 1);
                    }
                    break;
                case 3:
                    if (squad.getAmmo() >= 2 || squad.getFood() >= 2 || squad.getGold() > 0) {
                        boolean result = false;
                        while (!result) {
                            senderMessage.sendText(0L,"Event. Торговый обоз. Можно поторговать: 1 - купить еду, 2 - купить патроны, 3 - продать еду, 4 - продать патроны, 5 - ехать дальше");
                            if (Integer.parseInt(receiverMessage.get()) == 1) {
                                if (squad.getGold() > 0) {
                                    squad.setGold(squad.getGold() - 1);
                                    squad.setFood(squad.getFood() + 2);
                                    result = true;
                                }
                            }
                            if (Integer.parseInt(receiverMessage.get()) == 2) {
                                if (squad.getGold() > 0) {
                                    squad.setGold(squad.getGold() - 1);
                                    squad.setAmmo(squad.getAmmo() + 2);
                                    result = true;
                                }
                            }
                            if (Integer.parseInt(receiverMessage.get()) == 3) {
                                if (squad.getFood() > 2) {
                                    squad.setFood(squad.getFood() - 2);
                                    squad.setGold(squad.getGold() + 1);
                                    result = true;
                                }
                            }
                            if (Integer.parseInt(receiverMessage.get()) == 4) {
                                if (squad.getAmmo() > 2) {
                                    squad.setAmmo(squad.getAmmo() - 2);
                                    squad.setGold(squad.getGold() + 1);
                                    result = true;
                                }
                            }
                            if (Integer.parseInt(receiverMessage.get()) == 5) {
                                result = true;
                            }
                        }
                    }
                    break;
                case 4://nothing
                    senderMessage.sendText(0L,"Event. Открытая дотога.");
                    break;
                case 5:
                    senderMessage.sendText(0L,"Event. Заблудились.");
                    squad.setFood(squad.getFood() - 1);
                    squad.setShooters(squad.getShooters() - 1);
                    break;
                case 6:
                    boolean out = true;
                    do {
                        senderMessage.sendText(0L,"Event. Непредвиденные потери: 1 - 2 стрелка, 2 - 1 золото + 1 еда, 3 - 2 еды, 4 - 2 золота");
                        if (Integer.parseInt(receiverMessage.get()) == 1) {
                            squad.setShooters(squad.getShooters() - 2);
                        } else if (Integer.parseInt(receiverMessage.get()) == 2 && squad.getGold() > 0 && squad.getFood() > 0) {
                            squad.setGold(squad.getGold() - 1);
                            squad.setFood(squad.getFood() - 1);
                        } else if (Integer.parseInt(receiverMessage.get()) == 3 && squad.getFood() >= 2) {
                            squad.setFood(squad.getFood() - 2);
                        } else if (Integer.parseInt(receiverMessage.get()) == 4 && squad.getGold() >= 2) {
                            squad.setGold(squad.getGold() - 2);
                        } else out = false;
                    } while (out);
                    break;
                default:
                    correctExit = false;
            }
        } while (correctExit);
        senderMessage.sendText(0L,squad.toString());
    }
}
