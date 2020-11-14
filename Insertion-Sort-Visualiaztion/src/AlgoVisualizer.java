import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

public class AlgoVisualizer {
    // TODO: 创建自己的数据
    private static int DELAY =40; //屏幕等待的时间
    private InsertionSortData data;      // 数据
    private AlgoFrame frame;  // 视图

    // public AlgoVisualizer(int sceneWidth, int sceneHeight, int N){
    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N, InsertionSortData.Type dataTpye){
        // 初始化数据
        // TODO：初始化数据
//        data = new InsertionSortData(N, sceneHeight);
        data = new InsertionSortData(N, sceneHeight, dataTpye);

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Insertion Sort Visualization", sceneWidth, sceneHeight);
            // TODO: 根据情况决定是否加入键盘鼠标事件监听器
//            frame.addKeyListener(new AlgoKeyListener());
//            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N){
        this(sceneWidth,sceneHeight, N, InsertionSortData.Type.Default);
    }

    // 动画逻辑
    private void run(){

        // TODO: 编写自己的动画逻辑
        setData(0, -1);
        for(int i = 0; i < data.N(); i ++){
            // 重新考虑了一个元素，说明[0,i）是有序的，而我们考虑的正是当前的元素i。
            setData(i, i);
            // 在循环条件中就已经将对orderedIndex之前的值进行了判断
            for(int j = i; j > 0 && data.get(j) < data.get(j-1); j--){
                data.swap(j,j-1);

                // 一旦交换一个元素，意味着我们正在整理从0-i共i+1个元素，同时由于j与j-1交换了，根据下一步j--，我们此刻已经转移到j-1了
                setData(i+1, j-1);
            }
        }
        setData(data.N(), -1);
    }

    public void setData(int orderedIndex, int currentIndex){
        data.orderedIndex = orderedIndex;
        data.currentIndex = currentIndex;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    // TODO：根据情况决定是否实现键盘鼠标等交互事件监听器类
    private class AlgoKeyListener extends KeyAdapter{ }
    private class AlgoMouseListener extends MouseAdapter{ }

    public static void main(String[] args){

        int sceneWidth = 800;
        int sceneHeight = 800;
        // 根据设置其它参数，初始化visualizer
        int N = 100;
//        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N);
//        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N, InsertionSortData.Type.Ordered);
        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N, InsertionSortData.Type.NearlyOrdered);
    }
}
