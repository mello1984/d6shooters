package game.d6shooters.road;

public class Node {
    private Node previous;
    private Node branch;
    private Node next;
    private NodeType nodeType;
    private Town town;
    private String description;

    public Node(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public Node getPrevious() {
        return previous;
    }

    public Node setPrevious(Node previous1) {
        this.previous = previous1;
        return this;
    }

    public Node getBranch() {
        return branch;
    }

    public Node setBranch(Node branch) {
        this.branch = branch;
        return this;
    }

    public Node getNext() {
        return next;
    }

    public Node setNext(Node next1) {
        this.next = next1;
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

    public enum NodeType {
        ROAD, BRANCHHEAD, BRANCHEND, TOWN, EVENT, RINO
    }
}
