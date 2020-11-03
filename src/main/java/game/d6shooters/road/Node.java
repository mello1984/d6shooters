package game.d6shooters.road;

public class Node {
    private Node previous1;
    private Node previous2;
    private Node next1;
    private Node next2;
    private NodeType nodeType;
    private Town town;

    public Node(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public Node getPrevious1() {
        return previous1;
    }

    public Node setPrevious1(Node previous1) {
        this.previous1 = previous1;
        return this;
    }

    public Node getPrevious2() {
        return previous2;
    }

    public Node setPrevious2(Node previous2) {
        this.previous2 = previous2;
        return this;
    }

    public Node getNext1() {
        return next1;
    }

    public Node setNext1(Node next1) {
        this.next1 = next1;
        return this;
    }

    public Node getNext2() {
        return next2;
    }

    public Node setNext2(Node next2) {
        this.next2 = next2;
        return this;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    @Override
    public String toString() {
        return "Node" + this.hashCode() + "{" +
                "nodeType=" + nodeType +
                ", town=" + town +
                '}';
    }
}
