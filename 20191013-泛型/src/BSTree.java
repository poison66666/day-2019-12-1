import java.util.Random;

public class BSTree<K extends Comparable<K>,V> {
    private static class Entry<K,V> {
        private K key;
        private V value;
        private Entry<K,V> left = null;
        private Entry<K,V> right = null;

        private Entry(K key,V value){
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.format("{%s = %s}",key,value);
        }
    }

    private Entry<K,V> root = null;

    public V get(K key){
        Entry<K,V> cur = root;
        while (cur != null){
            int r = key.compareTo(cur.key);
            if(r == 0){
                return cur.value;
            }else if(r < 0){
                cur = cur.left;
            }else{
                cur = cur.right;
            }
        }
        return null;
    }

    public V put(K key,V value){
        if(root == null){
            root = new Entry<>(key,value);
            return  null;
        }

        Entry<K,V> parent = null;
        Entry<K,V> cur = root;
        while(cur != null){
            int r = key.compareTo(cur.key);
            if(r == 0){
                V oldValue = cur.value;
                cur.value = value;
                return oldValue;
            }else if(r < 0){
                parent = cur;
                cur = cur.left;
            }else{
                parent = cur;
                cur = cur.right;
            }
        }

        Entry<K,V> entry = new Entry<>(key,value);
        if(key.compareTo(parent.key) < 0){
            parent.left = entry;
        }else{
            parent.right = entry;
        }
        return null;
    }

    public V remove(K key){
        Entry<K,V> parent = null;
        Entry<K,V> cur = root;
        while (cur != null){
            int r = key.compareTo(cur.key);
            if(r == 0){
                V oldValue = cur.value;
                removeEntry(parent,cur);
                return oldValue;
            }else if(r < 0){
                cur = cur.left;
            }else{
                cur = cur.right;
            }
        }
        return null;
    }

    private void removeEntry(Entry<K,V> parent, Entry<K,V> cur) {
        if(cur.left == null){
            if(cur == root){
                cur = cur.right;
            }else if(cur == parent.left){
                parent.left = cur.right;
            }else{
                parent.right = cur.right;
            }
        }else if(cur.right == null){
            if(cur == root){
                cur = cur.left;
            }else if(cur == parent.left){
                parent.left = cur.left;
            }else{
                parent.right = cur.left;
            }
        }else{
            Entry<K,V> gParent = cur;
            Entry<K,V> goat = cur.left;
            while(goat.right != null){
                gParent = goat;
                goat = goat.right;
            }
            cur.key = goat.key;
            cur.value = goat.value;
            if(goat == gParent.left){
                gParent.left = goat.left;
            }else{
                gParent.right = goat.left;
            }
        }
    }

    public static interface Function<T> {
        void apply(T entry);
}

    public static <K,V> void preOrderTraversal(Entry<K,V> node,Function<Entry<K,V>> func) {
        if(node != null){
            func.apply(node);
            preOrderTraversal(node.left,func);
            preOrderTraversal(node.right,func);
        }
    }

    public static <K,V> void inorderTraversal(Entry<K,V> node,Function<Entry<K,V>> func) {
        if(node != null){
            inorderTraversal(node.left,func);
            func.apply(node);
            inorderTraversal(node.right,func);
        }
    }

    public void print(){
        System.out.println("前序遍历：");
        preOrderTraversal(root,(n) ->{
            System.out.println(n.key + " ");
        });
        System.out.println();
        System.out.println("中序遍历：");
        inorderTraversal(root,(n) -> {
            System.out.println(n.key + " ");
        });
        System.out.println();
    }

    public static void main(String[] args) {
        BSTree<Integer,String> tree = new BSTree<>();
        int count = 0;
        Random random = new Random(20191014);
        for(int i = 0;i<20;i++){
            int key = random.nextInt(200);
            String value = String.format("Value of %d",key);
            if(tree.put(key,value) == null){
                count++;
            }
        }
        System.out.println("一共插入" + count + "个结点");
        tree.print();
    }
}
