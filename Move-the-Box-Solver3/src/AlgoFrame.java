import javafx.beans.binding.ObjectExpression;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

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
    private GameData data;
    public void render(GameData data){
        this.data = data;
        repaint();
    }

    private class AlgoCanvas extends JPanel{
        // HashMap 查找表，如果是之前已经有了的字符，之间使用，如果没有，重建增加一个
        // 一个字符分配一个颜色
        private HashMap<Character, Color> colorMap;
        // 颜色用ArrayList存
        private ArrayList<Color> colorList;
        public AlgoCanvas(){
            // 双缓存
            super(true);
            // 初始化查找表
            colorMap = new HashMap<Character, Color>();
            // 用ArrayList
            colorList = new ArrayList<Color>();
            colorList.add(AlgoVisHelper.Red);
            colorList.add(AlgoVisHelper.Purple);
            colorList.add(AlgoVisHelper.Blue);
            colorList.add(AlgoVisHelper.Teal);
            colorList.add(AlgoVisHelper.LightGreen);
            colorList.add(AlgoVisHelper.Lime);
            colorList.add(AlgoVisHelper.Amber);
            colorList.add(AlgoVisHelper.DeepOrange);
            colorList.add(AlgoVisHelper.Brown);
            colorList.add(AlgoVisHelper.BlueGrey);
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
            int w = canvasWidth/data.M();
            int h = canvasHeight/data.N();

            Board showBoard = data.getShowBoard();
            for(int i=0; i<showBoard.N(); i++)
                for(int j=0; j<showBoard.M(); j++){
                    char c = showBoard.getData(i, j);
                    if(c != Board.EMPTY){
                        if(!colorMap.containsKey(c)){
                            int sz = colorMap.size();
                            // 颜色hash表中最后一种颜色的下标是sz-1，因此新颜色只要加到下标sz就可以了
                            colorMap.put(c, colorList.get(sz));
                        }
                        Color color = colorMap.get(c);
                        AlgoVisHelper.setColor(g2d, color);
                        AlgoVisHelper.fillRectangle(g2d, j*h+2, i*w+2, w-4, h-4);

                        AlgoVisHelper.setColor(g2d, AlgoVisHelper.White);
                        String text = String.format("(%d, %d)", i, j);
                        AlgoVisHelper.drawText(g2d, text, j*h + h/2, i*w + w/2);
                    }
                }

        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth,canvasHeight);
        }
    }
}
