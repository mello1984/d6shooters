package game.d6shooters.road;

import game.d6shooters.Game;
import game.d6shooters.TurnMessage;

public class Road {
    int length;
    private NodeList nodeList = new NodeList();
    private Node currentNode = nodeList.first;
    private TurnMessage turnMessage = Game.turnMessage;
    private Game game;

    public NodeList getNodeList() {
        return nodeList;
    }

    public Road(Game game) {
        this.game = game;
    }

    public void next() {
        if (currentNode.getNext1() != null && currentNode.getNext2() != null) {
            // need choose next node
            turnMessage.out("Выберите, куда свернуть:\n" +
                    "1: налево\n" +
                    "2: направо");
            int j = Integer.parseInt(turnMessage.get());
            currentNode = j == 1 ? currentNode.getNext1() : currentNode.getNext2();
        } else if (currentNode.getNext1() != null) {
            currentNode = currentNode.getNext1();
        } else if (currentNode.getNext2() != null) {
            currentNode = currentNode.getNext2();
        }

        //check current node type
        if (currentNode.getNodeType() == Node.NodeType.RINO) {
            game.endGame();
        } else if (currentNode.getNodeType() == Node.NodeType.EVENT) {
            //EVENT
        } else if (currentNode.getNodeType() == Node.NodeType.TOWN) {
            //TOWN
        }

    }
}
