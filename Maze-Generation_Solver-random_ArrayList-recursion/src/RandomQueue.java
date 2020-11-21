import java.util.ArrayList;
public class RandomQueue<E> {
    // 底层的ArrayList
    private ArrayList<E> queue;

    public RandomQueue(){
        queue = new ArrayList<E>();
    }

    public void add(E e){
        queue.add(e);
    }

    // 出队的数据以返回值的形式返回
    public E remove(){
        if(queue.size()==0)
            throw new IllegalArgumentException("There's no element to remove in RandomQueue");
        // 随机一个索引
        int ranIndex = (int)(Math.random()*queue.size());
        // 拿出随机选中的元素
        E ranElement = queue.get(ranIndex);
        // 将最后一个元素的值重新赋给之前的随机索引，就可以把队尾的值删除了
        queue.set(ranIndex, queue.get(queue.size()-1));
        queue.remove(queue.size()-1);

        return ranElement;
    }

    public int size(){
        return queue.size();
    }

    public boolean empty(){
        return size()==0;
    }
}
