public class LinkedList<E> {
    public static class Node<E>{
        private E value;
        private Node<E> next;

        private Node(E e){
            value = e;
            next = null;
        }
    }

    private Node<E> head;
    private int size;

    public LinkedList(){
        head = null;
        size = 0;
    }

    public void pushFront(E e){
        Node<E> node = new Node<>(e);
        node.next = head;
        head = node;
        size++;
    }

    public void pushBack(E e){
        if(size == 0){
            pushFront(e);
            return;
        }
        Node<E> cur = head;
        while(cur.next != null){
            cur = cur.next;
        }
        cur.next = new Node<E>(e);
        size++;
    }
}
