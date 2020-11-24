import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

public class AlgoVisualizer {
    // TODO: 创建自己的数据
    private static int DELAY =40; //屏幕等待的时间
    private FractalData data;      // 数据
    private AlgoFrame frame;  // 视图

    public AlgoVisualizer(int maxDepth){
        // 初始化数据
        // TODO：初始化数据

        data = new FractalData(maxDepth);
        // 深度为1的话，需要3*3个小方格
//        int sceneWidth = (int)Math.pow(3,maxDepth);
//        int sceneHeight = (int)Math.pow(3,maxDepth);
//        int sceneWidth = 1024;
//        int sceneHeight = 768;
        int sceneWidth = (int)Math.pow(2,maxDepth);
        int sceneHeight = (int)Math.pow(2,maxDepth);

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Welcome", sceneWidth, sceneHeight);
            // TODO: 根据情况决定是否加入键盘鼠标事件监听器
            frame.addKeyListener(new AlgoKeyListener());
//            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 动画逻辑
    private void run(){

        // TODO: 编写自己的动画逻辑
        setData(data.depth);

    }

    private void setData(int depth){
        if(depth>=0)
            data.depth = depth;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }
    // TODO：根据情况决定是否实现键盘鼠标等交互事件监听器类
    private class AlgoKeyListener extends KeyAdapter{
        @Override
        public void keyReleased(KeyEvent event){
            if(event.getKeyChar() >= '0' && event.getKeyChar() <= '9') {
                int depth = event.getKeyChar() - '0';
                setData(depth);
            }
        }
    }
    private class AlgoMouseListener extends MouseAdapter{ }

    public static void main(String[] args){

        int maxDepth = 9;
        // 根据设置其它参数，初始化visualizer
        AlgoVisualizer visualizer = new AlgoVisualizer(maxDepth);
    }
}
