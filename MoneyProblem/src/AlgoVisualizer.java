import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.util.Arrays;

public class AlgoVisualizer {
    // TODO: 创建自己的数据
    private static int DELAY = 40;
    private int[] money;      // 数据
    private AlgoFrame frame;  // 视图

    public AlgoVisualizer(int sceneWidth, int sceneHeight){
        // 初始化数据
        // TODO：初始化数据
        money = new int[100];
        for(int i =0; i < money.length; i++)
            money[i] = 100;

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
        while (true) {
            // 在渲染前，先对数据进行排序。钱最多/最少的人不一定是同一个
            Arrays.sort(money);
            frame.render(money);
            // 更新的时间，设置停留时间40毫秒，每秒暂停大概是1000/40=25次；帧率为25帧，这里只计算了停留时间，应该加上渲染时间和更新时间
            AlgoVisHelper.pause(DELAY);
            // 模拟50轮后再进行渲染，k要50次后，才会进入下一个while
            for (int k = 0; k < 50; k++){
                for (int i = 0; i < money.length; i++) {
                    //负数可以为负值
                    //if (money[i] > 0) {
                        int j = (int) (Math.random() * money.length);
                        money[i] -= 1;
                        money[j] += 1;
                    //}
                }
            }
        }
    }

    // TODO：根据情况决定是否实现键盘鼠标等交互事件监听器类
    private class AlgoKeyListener extends KeyAdapter{ }
    private class AlgoMouseListener extends MouseAdapter{ }

    public static void main(String[] args){

        int sceneWidth = 1000;
        int sceneHeight = 800;

        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight);
    }
}
