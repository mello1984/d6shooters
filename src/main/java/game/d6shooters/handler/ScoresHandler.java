package game.d6shooters.handler;

import game.d6shooters.Main;
import game.d6shooters.bot.Bot;
import game.d6shooters.bot.SendMessageFormat;
import game.d6shooters.source.Button;
import game.d6shooters.users.User;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class ScoresHandler extends AbstractHandler {
    public ScoresHandler(Bot bot) {
        super(bot);
    }

    @Override
    public void handle(Message message) {
        User user = Main.users.getUserMap().get(message.getChatId());
        Button button = Button.getButton(message.getText());


        SendMessage sendMessage = template.getSendMessageNoButtons(user.getChatId(), "");
        sendMessage.setText(button == Button.SCORES_MY ? getMyWins(user) : getAllWins());
        SendMessageFormat.setButtons(sendMessage, user.getButtons());
        Main.bot.send(sendMessage);
    }

    public String getMyWins(User user) {
        ArrayList<Integer> list = Main.users.getUserResults(user);
        if (list.size() == 0) return "Победы не найдены";

        StringBuilder builder = new StringBuilder("Мои победы:\n1. " + list.get(0));
        for (int i = 1; i < list.size(); i++) {
            builder.append("\n").append(i + 1).append(list.get(i));
        }
        return builder.toString();
    }

    protected String getAllWins() {
        final int TOP_COUNT = 25;
        ArrayListValuedHashMap<Integer, Long> map = Main.users.getTopResults(TOP_COUNT);
        StringBuilder builder = new StringBuilder();

        int num = 1;
        Set<Integer> keySet = new TreeSet<>(Comparator.reverseOrder());
        keySet.addAll(map.keySet());

        for (Integer key : keySet) {
            for (Long id : map.get(key)) {
                builder.append(num++).append(".").append(key).append(":").append(id).append("\n");
            }
        }
        return builder.toString();
    }
}

