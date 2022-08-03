package com.imall.note.SourceCodeAnalysis;

/**
 * @author zhangpengjun
 */
public class MyArrayList {

    //ArrayList有一个专门用来装元素的容器，为了保证集合什么类型的数据都可存储，所以定义的Object[]
    public Object[] data;
    //ArrayList里还有一个属性，用来记录集合元素的个数
    public int size;

    /**
     * 有参构造方法
     *
     * @param x 指定数组大小
     */
    public MyArrayList(int x) {
        if (x > 0) {
            data = new Object[x];
        } else {
            System.out.println("参数异常");
        }
    }

    /**
     * 如果指定数组大小，默认10个
     */
    public MyArrayList() {
        this(10);
    }

    /**
     * 得到集合的大小  如：int x = list.size();
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 往集合中添加元素 如：list.add(Object obj);
     *
     * @param obj
     */
    public void add(Object obj) {
        //当我们往集合中添加元素的时候，obj最终都会添加进Object[]数组中去
        //所以每次添加数据的时候都需要判断Object[],即data数组有没有填满
        if (data.length == size) {
            //如果填满了，那么需要扩容:
            // jdk1.7之前：size*3/2+1
            // jdk1.7及之后：size+(size>>1)
            Object[] temp = new Object[size + (size >> 1)];
            //扩容后将老数组复制到新数组里
            System.arraycopy(data, 0, temp, 0, size);
            //改变引用指向 gc回收老数组对象
            data = temp;
        } else {
            data[size] = obj;
            size++;
        }
    }

    /**
     * 按指定下标删除集合中的元素
     *
     * @param index
     */
    public void remove(int index) {
        //System.arraycopy(要被复制的老数组，从下标index开始复制，要复制到的新数组，从新数组的下标index插入,从老数组下标开始要被复制的个数);
        System.arraycopy(data, index + 1, data, index, size - (index + 1));
        size--;
    }

    /**
     * 指定元素删除集合中的元素
     *
     * @param obj
     */
    public void remove(Object obj) {
        //每当指定元素删除的时候，底层会拿着obj和每个元素做equals比较
        for (int x = 0; x < size; x++) {
            if (obj.equals(data[x])) {
                remove(x);//按下标删除元素
                break;//一个remove方法只能删除一个对象
            }
        }
    }

    /**
     * 根据指定下标获得元素
     *
     * @param x
     * @return
     */
    public Object get(int x) {
        return x >= 0 && x < size ? data[x] : "参数越界异常";
    }

}

class TestMyArrayList {
    public static void main(String[] args) {
        MyArrayList myArrayList = new MyArrayList();
        myArrayList.add(234);
        myArrayList.add("john");
        myArrayList.add("demon");
        myArrayList.add("alex");
        System.out.println(myArrayList.size());
        System.out.println(myArrayList.get(2));

        myArrayList.remove(1);
        System.out.println(myArrayList.size());

        MyArrayList myArrayList1 = new MyArrayList(20);
        System.out.println(myArrayList1.data.length);
    }
}



