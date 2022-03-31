
import java.util.HashMap;

/**
 * @className: MyHashMap
 * @descripe: 模拟HashMap底层实现
 * @author: zpj
 * @date: 2019/6/7
 * @version: 1.0
 */
public class MyHashMap<key,value> extends HashMap<key,value>{

    //定义一个数组
    private Node<key,value>[] table;
    //初始化数组容量大小为 16
    private static Integer CSHSZRL = 16;
    //定义一个size，用来统计HashMap内个数
    private int size = 0;

    public MyHashMap() {
        //创建对象的时候就初始化数组
        table = new Node[CSHSZRL];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public value get(Object key) {
        //算出这个节点是在哪个hash组
        int hash = key.hashCode();
        int index = hash % table.length;

        //遍历
        for(Node<key,value> node = table[index];node!=null;node.getNext()){
            //新元素和老元素一样的话
            if(node.getK().equals(key)){
                return node.getV();
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(key);
    }

    /**
     * HashMap的 put()方法
     * @param key
     * @param value
     * @return 当put元素出现重复的时候，新元素覆盖老元素，返回老元素的value
     */
    @Override
    public value put(key key, value value) {
        //算出这个节点是在哪个hash组
        int hash = key.hashCode();
        int index = hash % table.length;

        //新增元素时，遍历老元素，和新元素比较
        for(Node<key,value> node = table[index];node!=null;node.getNext()){
            //新元素和老元素一样的话
            if(node.k.equals(key)){
                value oldValue = node.v;
                node.v=value;
                return oldValue;
            }
        }

        //当put第N个元素
        addNode(key, value, index);

        return null;
    }

    private void addNode(key key, value value, int index) {
        //Node<key, value> node = new Node<>(key, value, null);//这个hash组是空的，put第一个元素
        //table[index] = node;

        //老节点对象
        Node node = table[index];
        //再把新的节点对象追加到原先节点头部,形成链表
        table[index] = new Node(key,value,node);
        //每增加一个元素，HashMap内 个数加1
        size++;
    }

    @Override
    public value remove(Object key) {
        return super.remove(key);
    }

    /**
     * 源码中定义了一个节点类对象，用来存放每个数据
     * @param <K>
     * @param <V>
     */
    static class Node<K,V> {

        private K k;
        private V v;
        //用来充当链表
        private Node<K,V> next;

        //可以传入新元素，然后构成链表
        public Node(K k, V v, Node<K, V> next) {
            this.k = k;
            this.v = v;
            this.next = next;
        }

        public K getK() {
            return k;
        }

        public V getV() {
            return v;
        }

        public Node<K, V> getNext() {
            return next;
        }
    }

    public static void main(String[] args){
        MyHashMap<String,String> myHashMap = new MyHashMap<>();
        for (int x=0;x<10;x++){
            myHashMap.put(x+"貂蝉",x+"貂蝉配吕布");
        }
        myHashMap.put("8貂蝉","8貂蝉戏吕布");
        System.out.println(myHashMap.get("8貂蝉"));
        System.out.println(myHashMap.size());
    }

}
