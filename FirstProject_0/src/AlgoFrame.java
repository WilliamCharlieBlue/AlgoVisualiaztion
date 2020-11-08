
import com.sun.corba.se.impl.orbutil.graph.Graph;
import com.sun.deploy.panel.JavaPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class AlgoFrame extends JFrame {
    private int canvasWidth;
    private int canvasHeight;

    public AlgoFrame(String title, int canvasWidth, int canvasHeight){
        super(title);

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        AlgoCanvas canvas = new AlgoCanvas();
        //自动调用public Dimension getPreferred，自己决定画布的大小
    //    canvas.setPreferredSize(new Dimension(canvasWidth,canvasHeight));
        setContentPane(canvas);
        pack();

        //setSize(canvasWidth, canvasHeight);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }
    public AlgoFrame(String title){
        this(title,1024,768);
    }
    public int getCanvasWidth(){return canvasWidth;}
    public int getCanvasHeight(){return canvasHeight;}

    private Circle[] circles;
    public void render(Circle[] circles){
        this.circles = circles;
        repaint();
    }

    private class AlgoCanvas extends JavaPanel{

        //覆盖了父类的方法，使用override
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);

            //g.drawOval(50,50,300,300);
            //将graphics对象转换为Graphics2D
            Graphics2D g2d = (Graphics2D)g;

            // 抗锯齿
            RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.addRenderingHints(hints);

            // 具体绘制（根据其他类来）
            AlgoVisHelper.setStrokeWidth(g2d, 1);
            AlgoVisHelper.setColor(g2d, Color.RED);
            for(Circle circle: circles)
                AlgoVisHelper.strokeCircle(g2d, circle.x, circle.y, circle.getR());
        }
//        单个圆的实现
//            //int strokeWidth = 10;
//            //g2d.setStroke(new BasicStroke(strokeWidth));
//            //调用的类来setStrokeWidth来设置线条
//            AlgoVisHelper.setStrokeWidth(g2d, 5);
//
////            g2d.setColor(Color.GREEN);
////            //圆形和椭圆封装在Ellipse2D.Float 和 Ellipse2D.Double(精度更高)，50.2f相当于转化为float，等价于(float)50,2
////            Ellipse2D circle = new Ellipse2D.Double(50.2f, 50,300,300);
////            g2d.draw(circle);
////            //java是基于状态绘图，要改变后面添加元素的颜色，就需要重新设置状态颜色
////            g2d.setColor(Color.RED);
////            Ellipse2D circle2 = new Ellipse2D.Double(60.2f, 60,280,280);
////            g2d.fill(circle2);
//            //
//            AlgoVisHelper.setColor(g2d, Color.GREEN);
//            AlgoVisHelper.strokeCircle(g2d, canvasWidth/2, canvasHeight/2,200);
//            AlgoVisHelper.setColor(g2d, Color.RED);
//            AlgoVisHelper.fillCircle(g2d, canvasWidth/2, canvasHeight/2,200);
//        }
        @Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth,canvasHeight);
        }
    }
}
