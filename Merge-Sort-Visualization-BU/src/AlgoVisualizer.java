import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.util.Arrays;

public class AlgoVisualizer {
    // TODO: 创建自己的数据
    private static int DELAY =40; //屏幕等待的时间
    private MergeSortData data;      // 数据
    private AlgoFrame frame;  // 视图

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N){
        // 初始化数据
        // TODO：初始化数据
        data = new MergeSortData(N,sceneHeight);

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Merge Sort Visualization", sceneWidth, sceneHeight);
            // TODO: 根据情况决定是否加入键盘鼠标事件监听器
//            frame.addKeyListener(new AlgoKeyListener());
//            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 动画逻辑
// 自顶向下TopDown,使用递归来实现
//    private void run(){
//
//        // TODO: 编写自己的动画逻辑
//        setData(-1, -1, -1);
//        mergeSort(0, data.N()-1);
//        setData(0, data.N()-1, data.N()-1);
//
//    }

// 自底向上BottomUp,使用循环来实现

    private void run(){

        // TODO: 编写自己的动画逻辑
        setData(-1, -1, -1);
        // 创建一个虚拟子数组
        for(int sz =1; sz < data.N(); sz*=2)
            for(int i = 0; i < data.N()-sz; i += sz+sz)
                // 对arr[i...i+sz-1] 和 arr[i+sz...i+2*sz-1]进行归并
                merge(i, i+sz-1, Math.min(i+sz+sz-1, data.N()-1));
        setData(0, data.N()-1, data.N()-1);

    }

    private void mergeSort(int l, int r){
        if(l >= r) return;

        setData(l, r, -1);
        int mid = (l+r)/2;
        mergeSort(l, mid);
        mergeSort(mid+1, r);
        merge(l, mid, r);
    }

    private void merge(int l, int mid, int r){
        // 创造了一个辅助空间
        int [] aux = Arrays.copyOfRange(data.numbers, l, r+1);
        // 初始化，i指向做部分的其实索引位置l； j 指向右半部分其实索引位置mid+1
        int i = l, j = mid+1;
        for(int k = l; k <= r; k++){
            if(i > mid){ data.numbers[k] = aux[j-l]; j++;} // 如果左半部分元素已经全部处理完毕
            else if( j > r){ data.numbers[k] = aux[i-l]; i++;} // 如果右半部分元素已经全部处理完毕
            else if(aux[i-l] < aux[j-l]){ data.numbers[k] = aux[i-l]; i++;} // 左半部分所指元素 < 右半部分所指元素
            else{ data.numbers[k] = aux[j-l]; j++;} // 左半部分所指元素 >= 右半部分所指元素

            setData(l, r, k);
        }
    }

    public void setData(int l, int r, int mergIndex){
        data.l = l;
        data.r = r;
        data.mergeIndex = mergIndex;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    // TODO：根据情况决定是否实现键盘鼠标等交互事件监听器类
    private class AlgoKeyListener extends KeyAdapter{ }
    private class AlgoMouseListener extends MouseAdapter{ }

    public static void main(String[] args){

        int sceneWidth = 800;
        int sceneHeight = 800;
        int N =100;
        // 根据设置其它参数，初始化visualizer
        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N);
    }
}
