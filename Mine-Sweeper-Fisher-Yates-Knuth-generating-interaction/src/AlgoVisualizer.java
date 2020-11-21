import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
//            frame.addKeyListener(new AlgoKeyListener());
            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 动画逻辑
    private void run(){

        // TODO: 编写自己的动画逻辑
        setData(false, -1, -1);
    }

    private void setData(boolean isLeftClicked ,int x, int y){
        if(data.inArea(x, y)){
            if(isLeftClicked){
                data.open[x][y] = true;
            }
            else
                // 没标记则标记，标记了则取消
                data.flags[x][y] = !data.flags[x][y];
        }
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    // TODO：根据情况决定是否实现键盘鼠标等交互事件监听器类
    private class AlgoKeyListener extends KeyAdapter{ }
    private class AlgoMouseListener extends MouseAdapter{
        @Override
        public void mouseReleased(MouseEvent event){
            // 点击的位置是以窗口frame为标准的，要去除选项框的边距
            event.translatePoint(
                    -(int)(frame.getBounds().width - frame.getCanvasWidth()),
                    -(int)(frame.getBounds().height -frame.getCanvasHeight())
            );

            Point pos = event.getPoint();
            // 计算每格的宽度
            int w = frame.getCanvasWidth() / data.M();
            int h = frame.getCanvasHeight() / data.N();
            // 获取第几行第几列
            int x = pos.y / h;
            int y = pos.x / w;

            if(SwingUtilities.isLeftMouseButton(event))
                setData(true, x ,y);
            else if (SwingUtilities.isRightMouseButton(event))
                setData(false, x, y);

        }
    }

    public static void main(String[] args){

        int N = 20;
        int M = 20;
        int mineNumber = 50;
        // 根据设置其它参数，初始化visualizer
        AlgoVisualizer visualizer = new AlgoVisualizer(N, M, mineNumber);
    }
}
