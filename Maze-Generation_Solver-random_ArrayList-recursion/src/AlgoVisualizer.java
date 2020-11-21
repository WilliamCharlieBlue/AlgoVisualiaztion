import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.util.LinkedList;
import java.util.Stack;

public class AlgoVisualizer {
    // TODO: 创建自己的数据
    private static int DELAY =1; //屏幕等待的时间
    private MazeData data;      // 数据
    private AlgoFrame frame;  // 视图

    private static int blockSide = 8;
//    private static final int d[][] = { {-1,0},{0,-1},{0,1},{1,0} };
    private static final int d[][] = { {-1,0},{0,1},{1,0},{0,-1} };

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
        setData(-1, -1);
        // 生成迷宫 和 走迷宫的位置是不同的
        //go(data.getEntranceX(), data.getEntranceY()+1);
//        Stack<Position> stack = new Stack<Position>();
//        LinkedList<Position> queue = new LinkedList<Position>();
        RandomQueue<Position> queue = new RandomQueue<Position>();
        Position first = new Position(data.getEntranceX(), data.getEntranceY()+1);
//        stack.push(first);
//        queue.addLast(first);
        queue.add(first);
        data.visited[first.getX()][first.getY()] = true;
        data.openMist(first.getX(),first.getY());
//        while (!stack.empty()){
//            Position curPos = stack.pop();
        while (queue.size() != 0){
//            Position curPos = queue.pop();
//            Position curPos = queue.removeFirst();
            Position curPos = queue.remove();

            for(int i=0; i<4; i++){
                int newX = curPos.getX() + d[i][0]*2;
                int newY = curPos.getY() + d[i][1]*2;

                if(data.inArea(newX,newY) && !data.visited[newX][newY]){
                    //stack.push(new Position(newX,newY));
//                    queue.addLast(new Position(newX,newY));
                    queue.add(new Position(newX,newY));
                    data.visited[newX][newY] = true;
                    // 每次开始遍历的时候打开迷雾
                    data.openMist(newX,newY);
                    setData(curPos.getX() + d[i][0], curPos.getY() + d[i][1]);
                }
            }
        }

        setData(-1, -1);
    }

//    private void go(int x, int y){
//        if(!data.inArea(x,y))
//            throw new IllegalArgumentException("x,y are out of index in go function");
//
//        data.visited[x][y] = true;
//        for(int i=0; i<4; i++){
//                // 路和路之前还有一柱墙,因此方向要加2
//            int newX = x + d[i][0]*2;
//            int newY = y + d[i][1]*2;
//            // 递归的终止条件
//            //if(data.inArea(newX,newY) && !data.visited[newX][newY] && data.maze[newX][newY]==MazeData.ROAD){
//            // [newX][newY]在maze初始化的时候已经预设好了，路四周+2一定是路
//            if(data.inArea(newX,newY) && !data.visited[newX][newY]){
//                // 要把两个路之前的墙打通，在setData里设置为Road即可
//                //setData((x+newX)/2, (y+newY)/2);
//                setData(x + d[i][0], y +d[i][1]);
//                go(newX,newY);
//            }
//        }
//    }


    private void setData(int x, int y){
        if(data.inArea(x,y))
            data.maze[x][y] = MazeData.ROAD;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private void setPathData(int x, int y, boolean isPath){
        if(data.inArea(x,y)){
            data.path[x][y] = isPath;
        }
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    // 使用回溯的方法，寻找迷宫路径
    private boolean gorun(int x, int y){
        if(!data.inArea(x,y))
            throw new IllegalArgumentException("x,y are out of index in gorun function");
        data.visitedrun[x][y] = true;
        setPathData(x,y,true);
        if(x == data.getExitX() && y == data.getExitY())
            return true;

        for(int i = 0; i < 4; i++){
            int newX = x + d[i][0];
            int newY = y + d[i][1];
            if(data.inArea(newX, newY) &&
                    data.maze[newX][newY] == MazeData.ROAD &&
                    !data.visitedrun[newX][newY])
                if(gorun(newX,newY))
                    return true;
        }
        // 如果搜索没有结果的话，记这个格子不是解
        setPathData(x,y, false);
        return false;
    }

    // TODO：根据情况决定是否实现键盘鼠标等交互事件监听器类
    private class AlgoKeyListener extends KeyAdapter{
        @Override
        public void keyReleased(KeyEvent event){
            if(event.getKeyChar() == ' '){
//                for(int i=0; i<data.N(); i++)
//                    for(int j=0; j< data.M(); j++)
//                        data.visited[i][j] = false;
                // 为避免冲突，新增一个data.visitedrun[i][j]
                System.out.println("keyborad start");
                new Thread(() -> {
                    gorun(data.getEntranceX(), data.getEntranceY());
                }).start();
            }
        }
    }
    private class AlgoMouseListener extends MouseAdapter{ }

    public static void main(String[] args){

        int N = 101;
        int M = 101;
        // 根据设置其它参数，初始化visualizer
        AlgoVisualizer visualizer = new AlgoVisualizer(N, M);
    }
}
