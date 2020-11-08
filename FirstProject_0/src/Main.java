
import javax.swing.*;
import java.awt.*;

//public class Main {
//
//    public static void main(String[] args) {
//	// write your code here
//        EventQueue.invokeLater(() -> {
//            AlgoFrame frame = new AlgoFrame("welcome to java frame", 500,500);
// //           AlgoFrame frame = new AlgoFrame("welcome to java frame");
//        });
//
//    }
//}

public class Main {

    public static void main(String[] args) {
        int sceneWidth = 800;
        int sceneHeight = 800;

        int N =10;
        Circle[] circles = new Circle[N];
        int R =50;
        for(int i = 0; i <N; i++){
            // 圆心坐标的范围
            int x = (int)(Math.random()*(sceneWidth-2*R)) + R;
            int y = (int)(Math.random()*(sceneHeight-2*R)) + R;;
            // 正负5的速度
            int vx = (int)(Math.random()*11 -5);
            int vy = (int)(Math.random()*11 -5);;
            circles[i] = new Circle(x, y, R, vx, vy);
        }
        EventQueue.invokeLater(() -> {
            AlgoFrame frame = new AlgoFrame("welcome to java frame", sceneWidth,sceneHeight);
            //           AlgoFrame frame = new AlgoFrame("welcome to java frame");
            new Thread(() -> {
                while (true){
                    // 绘制数据
                    frame.render(circles);
//                //sleep中断需要抛出异常才行，所以可以调用一个辅助函数
//                Thread.sleep(20);
                    AlgoVisHelper.pause(20);
                    // 更新数据
                    for(Circle circle : circles)
                        circle.move(0,0, sceneWidth, sceneHeight);
                }
            }).start();

        });

    }
}



