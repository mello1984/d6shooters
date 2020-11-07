package game.d6shooters.road;


public class Road {
    int length;
    private NodeList nodeList = new NodeList();
    private Node currentNode = nodeList.first;
    private Node.NodeType nodeType;

    public Node.NodeType getNodeType() {
        return nodeType;
    }

    public NodeList getNodeList() {
        return nodeList;
    }


    public void next() {
//        if (currentNode.getNext() != null && currentNode.getNext() != null) {
//            // need choose next node
//            senderMessage.sendText(0L, "Выберите, куда свернуть:\n" +
//                    "1: налево\n" +
//                    "2: направо");
//            int j = Integer.parseInt(receiverMessage.get());
//            currentNode = j == 1 ? currentNode.getNext() : currentNode.getNextSecondary();
//        } else if (currentNode.getNext() != null) {
//            currentNode = currentNode.getNext();
//        } else if (currentNode.getNextSecondary() != null) {
//            currentNode = currentNode.getNextSecondary();
    }

//        //check current node type
//        if (currentNode.getNodeType() == Node.NodeType.RINO) {
//            //END GAME
//        } else if (currentNode.getNodeType() == Node.NodeType.EVENT) {
//            //EVENT
//        } else if (currentNode.getNodeType() == Node.NodeType.TOWN) {
//            //TOWN
//}
//
//    }
            }
