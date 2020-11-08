import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static sun.misc.PostVMInitHook.run;

public class AlgoVisualizer {

    private Circle[] circles;
    private AlgoFrame frame;
    private boolean isAnimated = true;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N){

        circles = new Circle[N];
        int R =50;
        for(int i = 0; i <N; i++) {
            // 圆心坐标的范围
            int x = (int) (Math.random() * (sceneWidth - 2 * R)) + R;
            int y = (int) (Math.random() * (sceneHeight - 2 * R)) + R;
            ;
            // 正负5的速度
            int vx = (int) (Math.random() * 11 - 5);
            int vy = (int) (Math.random() * 11 - 5);
            circles[i] = new Circle(x, y, R, vx, vy);
        }
        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("welcome to java frame", sceneWidth,sceneHeight);
            frame.addKeyListener(new AlgoKeyListener());
            frame.addMouseListener(new AlgoMouseLister());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 动画逻辑
    private void run(){
        while(true){
            // 绘制数据
            frame.render(circles);
            AlgoVisHelper.pause(20);
            // 更新数据
            if(isAnimated)
                for(Circle circle : circles)
                    circle.move(0, 0, frame.getCanvasWidth(), frame.getCanvasHeight());
        }
    }

//    private class AlgoKeyListener implements KeyListener{
//
//    }
    private class AlgoKeyListener extends KeyAdapter{
        @Override
        public void keyReleased(KeyEvent event){

            if(event.getKeyChar() == ' ')
                isAnimated = !isAnimated;
        }
    }

    private class AlgoMouseLister extends MouseAdapter{

        @Override
        public void mousePressed(MouseEvent event) {
            // 如果不translate，将输出frame平面的坐标
            event.translatePoint(0, -(frame.getBounds().height -frame.getCanvasHeight()));
            // 输出鼠标位置的坐标
            //System.out.println(event.getPoint());
            for(Circle circle : circles)
                if(circle.contain(event.getPoint()))
                    circle.isFilled = !circle.isFilled;
        }
    }
}
