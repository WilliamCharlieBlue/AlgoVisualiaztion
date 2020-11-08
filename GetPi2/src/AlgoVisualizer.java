import sun.management.Agent;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.util.LinkedList;

public class AlgoVisualizer {
    // TODO: 创建自己的数据
   private static int DELAY =40;
   private Circle circle;
//    private int insideCircle = 0;
//    private LinkedList<Point> points;     // 数据
    private MonteCarloPiData data;
    private AlgoFrame frame;  // 视图
    private int N;


    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N){
        if(sceneWidth != sceneWidth)
            throw new IllegalArgumentException("This demo must be run in a square");
        // 初始化数据
        // TODO：初始化数据
        this.N =N;
        Circle circle = new Circle(sceneWidth/2, sceneHeight/2, sceneWidth/2);
//        points = new LinkedList<Point>();
        data = new MonteCarloPiData(circle);



        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Get Pi with Monte Carlo", sceneWidth, sceneHeight);
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
        for(int i = 0; i< N; i++){
            // 运行100个点才渲染一次
            if(i % 100 == 0) {
//                frame.render(circle, points);
                frame.render(data);
                AlgoVisHelper.pause(DELAY);
//                // 一个点都没有时，不进行计算
//                if(points.size() != 0){
//                    int circleArea = insideCircle;
//                    int squareArea = points.size();
//                    double piEstimate = 4 * (double) circleArea / squareArea;
//                    System.out.printf("No.%d\tEstimation:\t%s\n", squareArea, piEstimate);
//                }
                System.out.printf("No.%d\tEstimation:\t%s\n", data.getPointsNumber(), data.estimatePi());
            }

            int x = (int)(Math.random() * frame.getCanvasWidth());
            int y = (int)(Math.random() * frame.getCanvasHeight());
//            Point p = new Point(x, y);
//            points.add(p);
//            if(circle.contain(p))
//                insideCircle++;
            data.addPoint(new Point(x, y));
        }
    }

    // TODO：根据情况决定是否实现键盘鼠标等交互事件监听器类
//    private class AlgoKeyListener extends KeyAdapter{ }
//    private class AlgoMouseListener extends MouseAdapter{ }

    public static void main(String[] args){

        int sceneWidth = 800;
        int sceneHeight = 800;
        int N =50000;

        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N);
    }
}
