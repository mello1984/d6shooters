package game.d6shooters.road;

public class NodeList {
    Node first;
    Node last;

    public NodeList() {
        Node node1 = new Node(Node.NodeType.ROAD);
        Node node2 = new Node(Node.NodeType.ROAD);
        Node node3 = new Node(Node.NodeType.TOWN);
        Node node4 = new Node(Node.NodeType.ROAD);
        Node node5 = new Node(Node.NodeType.EVENT);
        Node node6 = new Node(Node.NodeType.ROAD);
        Node node7 = new Node(Node.NodeType.ROAD);
        Node node8 = new Node(Node.NodeType.RINO);
        Town town1 = new Town("Kaliko", node3);

        node1.setNext1(node2);
        node2.setPrevious1(node1).setNext1(node3);
        node3.setPrevious1(node2).setNext1(node4).setTown(town1);
        node4.setPrevious1(node3).setNext1(node5);
        node5.setPrevious1(node4).setNext1(node6);
        node6.setPrevious1(node5).setNext1(node7);
        node7.setPrevious1(node6).setNext1(node8);
        node8.setPrevious1(node7);

        first = node1;
        last = node8;
    }

}
