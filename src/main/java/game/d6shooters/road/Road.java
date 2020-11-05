package game.d6shooters.road;

import game.d6shooters.bot.ReceiverMessage;
import game.d6shooters.game.Game;
import game.d6shooters.bot.SenderMessage;

public class Road {
    int length;
    private NodeList nodeList = new NodeList();
    private Node currentNode = nodeList.first;
    private SenderMessage senderMessage = Game.senderMessage;
    private ReceiverMessage receiverMessage = Game.receiverMessage;


    public NodeList getNodeList() {
        return nodeList;
    }


    public void next() {
        if (currentNode.getNext1() != null && currentNode.getNext2() != null) {
            // need choose next node
            senderMessage.sendText(0L, "Выберите, куда свернуть:\n" +
                    "1: налево\n" +
                    "2: направо");
            int j = Integer.parseInt(receiverMessage.get());
            currentNode = j == 1 ? currentNode.getNext1() : currentNode.getNext2();
        } else if (currentNode.getNext1() != null) {
            currentNode = currentNode.getNext1();
        } else if (currentNode.getNext2() != null) {
            currentNode = currentNode.getNext2();
        }

        //check current node type
        if (currentNode.getNodeType() == Node.NodeType.RINO) {
            //END GAME
        } else if (currentNode.getNodeType() == Node.NodeType.EVENT) {
            //EVENT
        } else if (currentNode.getNodeType() == Node.NodeType.TOWN) {
            //TOWN
        }

    }
}
