import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.geom.QuadCurve2D;

public class AlgoVisualizer {
    // TODO: 创建自己的数据
    private static int DELAY =40; //屏幕等待的时间
    private QuickSortData data;      // 数据
    private AlgoFrame frame;  // 视图

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N){
        // 初始化数据
        // TODO：初始化数据
        data = new QuickSortData(N, sceneHeight);

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Welcome", sceneWidth, sceneHeight);
            // TODO: 根据情况决定是否加入键盘鼠标事件监听器
//            frame.addKeyListener(new AlgoKeyListener());
//            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 动画逻辑
    private void run(){

        // TODO: 编写自己的动画逻辑
        setData(-1,-1,-1,-1,-1);
        quickSort(0, data.N()-1);
        setData(-1,-1,-1,-1,-1);

    }

    private void quickSort(int l, int r){
//        if(l >= r) return;
        if(l > r) return; // 区域无效
        // 只有一个元素，且就是标定点,需要进行表示
        if(l == r){
            setData(l, r, l, -1, -1);
            return;
        }
        setData(l, r, -1, -1, -1);
        int p = partition(l, r);
        quickSort(l, p-1);
        quickSort(p+1, r);
    }

    private int partition(int l, int r){
        int v = data.get(l);
        // 都将l作为第一个标定点
        setData(l, r, -1, l, -1);
        // arr[l=1...j] < v ; arr[j+1...i]
        int j =l;
        // 如果get(i) > v的话，已经在i++里了
        for(int i = l+1; i <= r ; i++){
            setData(l, r, -1, l, i);
            if(data.get(i) < v){
                j++;
                data.swap(j, i);
                setData(l, r, -1, l, i);
            }
        }
        data.swap(l, j);
        // 已经找到了此轮的标定点索引为j，且返回了j
        setData(l, r, j, -1, -1);
        return j;
    }

    private void setData(int l, int r, int fixedPivot, int curPivot, int curElement){
        data.l = l;
        data.r = r;
        if(fixedPivot != -1)
            data.fixedPivots[fixedPivot] = true;
        data.curPivot = curPivot;
        data.curElement = curElement;

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
