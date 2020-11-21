import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

public class AlgoVisualizer {
    // TODO: 创建自己的数据
    private static int blockSide = 32;
    private static int DELAY =40; //屏幕等待的时间
    private MineSweeperData data;      // 数据
    private AlgoFrame frame;  // 视图

    public AlgoVisualizer(int N, int M, int mineNumber){
        // 初始化数据
        // TODO：初始化数据
        data = new MineSweeperData(N, M, mineNumber);
        int sceneWidth = M*blockSide;
        int sceneHeight = N*blockSide;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Mine Sweeper", sceneWidth, sceneHeight);
            // TODO: 根据情况决定是否加入键盘鼠标事件监听器
            frame.addKeyListener(new AlgoKeyListener());
            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 动画逻辑
    private void run(){

        // TODO: 编写自己的动画逻辑
        setData();
    }

    private void setData(){
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    // TODO：根据情况决定是否实现键盘鼠标等交互事件监听器类
    private class AlgoKeyListener extends KeyAdapter{ }
    private class AlgoMouseListener extends MouseAdapter{ }

    public static void main(String[] args){

        int N = 20;
        int M = 20;
        int mineNumber = 1;
        // 根据设置其它参数，初始化visualizer
        AlgoVisualizer visualizer = new AlgoVisualizer(N, M, mineNumber);
    }
}
