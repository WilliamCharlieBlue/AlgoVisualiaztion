import javafx.beans.binding.ObjectExpression;

import javax.swing.*;
import java.awt.*;

public class AlgoFrame extends JFrame{
    private int canvasWidth;
    private int canvasHeight;

    public AlgoFrame(String title, int canvasWidth, int canvasHeight) {
        super(title);

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        AlgoCanvas canvas = new AlgoCanvas();
        setContentPane(canvas);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setVisible(true);
    }

    public AlgoFrame(String title){
        this(title, 1024, 768);
    }

    public int getCanvasWidth(){return canvasWidth;}
    public int getCanvasHeight(){return canvasHeight;}

    // TODO: 设置自己的数据
    private FractalData data;
    public void render(FractalData data){
        this.data = data;
        repaint();
    }

    private class AlgoCanvas extends JPanel{
        public AlgoCanvas(){
            // 双缓存
            super(true);
        }

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D)g;
            //抗锯齿
            RenderingHints hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
            g2d.addRenderingHints(hints);

            // 具体绘制
            // TODO： 绘制自己的数据data
            // Rectangle
//            drawFractal(g2d, 0,0, canvasWidth, canvasHeight, 0);
            //
            drawFractal(g2d, 0, canvasHeight, canvasWidth, 0);
        }

        // Sierpinski Rectangle drawing
        private void drawFractal(Graphics2D g, int Ax, int Ay, int side, int depth){
            if(side <= 1){
                AlgoVisHelper.setColor(g, AlgoVisHelper.Indigo);
                // 只有一个像素的话，只要绘制这个点即可
                AlgoVisHelper.fillRectangle(g, Ax, Ay, 1,1);
                return;
            }

            int Bx = Ax + side;
            int By = Ay;
            // 60.0 * Math.PI / 180.0 将60°转换为弧度值，才能用Math.sin()
            int h = (int)(Math.sin(60.0 * Math.PI / 180.0) * side);
            //
            int Cx = Ax + side/2;
            int Cy = Ay - h;

            if(depth == data.depth){
                AlgoVisHelper.setColor(g, AlgoVisHelper.Indigo);
                AlgoVisHelper.fillTriangle(g, Ax, Ay, Bx, By, Cx, Cy);
            }

            int AB_centerx = (Ax + Bx) / 2;
            int AB_centery = (Ay + By) / 2;
            int AC_centerx = (Ax + Cx) / 2;
            int AC_centery = (Ay + Cy) / 2;
            int BC_centerx = (Bx + Cx) / 2;
            int BC_centery = (By + Cy) / 2;

            drawFractal(g, Ax, Ay, side/2, depth+1);
            drawFractal(g, AC_centerx, AC_centery, side/2, depth+1);
            drawFractal(g, AB_centerx, AB_centery, side/2, depth+1);
            return;
        }

        // Sierpinski Rectangle drawing
        private void drawFractalSierpinskiRectangle(Graphics2D g, int x, int y, int w, int h, int depth){
            int w_3 = w/3;
            int h_3 = h/3;

            if(depth == data.depth){
                AlgoVisHelper.setColor(g, AlgoVisHelper.Indigo);
                AlgoVisHelper.fillRectangle(g, x+w_3, y+h_3, w_3, w_3);
                return;
            }
            // 中间的位置已经达不到了，所以不需要绘制
            if(w <= 1 || h<=1){
                return;
            }

            for(int i = 0; i<3; i++)
                for(int j=0; j<3; j++){
                    if(i==1 && j==1){
                        AlgoVisHelper.setColor(g, AlgoVisHelper.Indigo);
                        AlgoVisHelper.fillRectangle(g, x+w_3, y+h_3, w_3, h_3);
                    }
                    else{
                        drawFractalSierpinskiRectangle(g, x+i*w_3, y+j*h_3, w_3, h_3, depth+1);
                    }
                }
            return;
        }
        // Vicsek Fractal drawing
        private void drawVicsekFractal(Graphics2D g, int x, int y, int w, int h, int depth){
            if(depth == data.depth){
                AlgoVisHelper.setColor(g, AlgoVisHelper.Indigo);
                AlgoVisHelper.fillRectangle(g, x, y, w, h);
                return;
            }

            if(w <= 1 || h<=1){
                AlgoVisHelper.setColor(g, AlgoVisHelper.Indigo);
                AlgoVisHelper.fillRectangle(g, x, y, Math.max(w,1), Math.max(h,1));
                return;
            }
            int w_3 = w/3;
            int h_3 = h/3;
            drawVicsekFractal(g, x, y, w_3, h_3, depth+1);
            drawVicsekFractal(g, x+2*w_3, y, w_3, h_3, depth+1);
            drawVicsekFractal(g, x+w_3, y+h_3, w_3, h_3, depth+1);
            drawVicsekFractal(g, x, y+2*h_3, w_3, h_3, depth+1);
            drawVicsekFractal(g, x+2*w_3, y+2*h_3, w_3, h_3, depth+1);
            return;
        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth,canvasHeight);
        }
    }
}
