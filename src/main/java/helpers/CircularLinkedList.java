package helpers;
import usecases.Player;

public class CircularLinkedList {
    public Node first;

    public static class Node {
        public Player player;
        public Node previous;
        public Node next;
    }

    public void addPlayer(Player player) {
        Node newNode = new Node();
        newNode.player = player;
        if (first == null) {
            newNode.previous = newNode.next = newNode;
            first = newNode;
        } else {
            Node last = first.previous;
            last.next = newNode;
            newNode.next = first;
            newNode.previous = last;
            first.previous = newNode;
        }
    }

    public void removePlayer(Player player) {
        Node current = first;
        if (current == null) {
        } else {
            while (current.next != first) {
                if (current.player == player) {
                    remove(current);
                }
                current = current.next;
            }
            if (current.player == player) {
                remove(current);
            }
        }
    }

    private void remove(Node current) {
        if (first.next == first) {
            first = null;
        } else {
            current.previous.next = current.next;
            current.next.previous = current.previous;
        }
    }
}
