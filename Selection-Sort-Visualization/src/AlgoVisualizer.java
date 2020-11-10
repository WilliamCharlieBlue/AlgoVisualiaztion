import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

public class AlgoVisualizer {
    // TODO: 创建自己的数据
    private static int DELAY =20; //屏幕等待的时间
    private SelectionSortData data;      // 数据
    private AlgoFrame frame;  // 视图

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N){
        // 初始化数据
        // TODO：初始化数据
        // 初始化最大值不能高于绘制的高度
        data = new SelectionSortData(N, sceneHeight);


        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Selection-Sort-Visualization", sceneWidth, sceneHeight);
            // TODO: 根据情况决定是否加入键盘鼠标事件监听器
//            frame.addKeyListener(new AlgoKeyListener());
//            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 动画逻辑
//    private void run(){
//
//        // TODO: 编写自己的动画逻辑
//        frame.render(data);
//        AlgoVisHelper.pause(DELAY);
//        for(int i = 0; i < data.N(); i++){
//            // 寻找[i, n)区间里的最小值的索引
//            int minIndex = i;
//            for(int j = i + 1; j < data.N(); j++){
//                if(data.get(j) < data.get(minIndex)){
//                    minIndex =j;
//                }
//            }
//            data.swap(i, minIndex);
//            // 每次交换之后，渲染一次
//            frame.render(data);
//            AlgoVisHelper.pause(DELAY);
//        }
//        frame.render(data);
//        AlgoVisHelper.pause(DELAY);
//    }
    private void run(){
        // 初始时都是无序的，循环时，更新相应的i，j，变量每变化一次就setData一次。
        setData(0, -1, -1);
        for(int i = 0; i < data.N(); i++) {
            // 寻找[i, n)区间里的最小值的索引
            int minIndex = i;
            // CompareIndex 是j
            setData(i, -1, minIndex);
            for (int j = i + 1; j < data.N(); j++) {
                setData(i, j, minIndex);
                if (data.get(j) < data.get(minIndex)) {
                    minIndex = j;
                    // 此次虽然代码一样，但minIndex已经发生了变化
                    setData(i, j, minIndex);
                }
            }
            data.swap(i, minIndex);
            //
            setData(i+1, -1, -1);
        }

            setData(data.N(), -1, -1);
    }

    private void setData(int orderedIndex, int currentCompareIndex, int currentMinIndex){
        data.orderedIndex = orderedIndex;
        data.currentCompareIndex = currentCompareIndex;
        data.currentMinIndex = currentMinIndex;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    // TODO：根据情况决定是否实现键盘鼠标等交互事件监听器类
    private class AlgoKeyListener extends KeyAdapter{ }
    private class AlgoMouseListener extends MouseAdapter{ }

    public static void main(String[] args){

        int sceneWidth = 800;
        int sceneHeight = 800;
        int N = 100;
        // 根据设置其它参数，初始化visualizer
        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N);
    }
}
