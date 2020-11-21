import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

public class AlgoVisualizer {
    // TODO: 创建自己的数据
    private static int DELAY =5; //屏幕等待的时间
    private MazeData data;      // 数据
    private AlgoFrame frame;  // 视图

    private static int blockSide = 8;

    public AlgoVisualizer(int N, int M){
        // 初始化数据
        // TODO：初始化数据
        data = new MazeData(N, M);
        int sceneWidth = data.M()*blockSide;
        int sceneHeight = data.N()*blockSide;


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

        int N = 101;
        int M = 101;
        // 根据设置其它参数，初始化visualizer
        AlgoVisualizer visualizer = new AlgoVisualizer(N, M);
    }
}
