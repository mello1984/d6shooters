package game.d6shooters.road;

public class NodeList {
    Node first;
    Node last;
    Node branch;


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

        node1.setNext(node2);
        node2.setPrevious(node1).setNext(node3);
        node3.setPrevious(node2).setNext(node4).setTown(town1);
        node4.setPrevious(node3).setNext(node5);
        node5.setPrevious(node4).setNext(node6);
        node6.setPrevious(node5).setNext(node7);
        node7.setPrevious(node6).setNext(node8);
        node8.setPrevious(node7);

        first = node1;
        last = node8;
    }

    private void addRoad(Node node) {
        if (first==null) {
            first = node;
        }
        node.setPrevious(last);
        last.setNext(node);
        last = node;
    }

    private void addBranchHead(Node node) {
        node.setPrevious(last);
        last.setBranch(node);
        branch = node;
    }

    private void addBranchRoad(Node node) {
        node.setPrevious(branch);
        branch.setNext(node);
        branch = node;
    }

    private void addBranchEnd(Node node) {
        node.setPrevious(last);
        node.setBranch(branch);
        branch.setNext(node);
        last.setNext(node);
        last = node;
        branch = null;
    }

    public Node getNode() {
        return null;
    }

}
