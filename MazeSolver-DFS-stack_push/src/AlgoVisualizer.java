import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.util.Stack;

public class AlgoVisualizer {

    private static int DELAY = 5;
    private static int blockSide = 8;
    private MazeData data;
    private AlgoFrame frame;
    // 代表方向，逆时针的代表所有方向
    private static final int d[][] = { {-1,0},{0,1},{1,0},{0,-1}};

    public AlgoVisualizer(String mazeFile) {
        // 初始化数据
        data = new MazeData(mazeFile);

        int sceneHeight = data.N() * blockSide;
        int sceneWidth = data.M() * blockSide;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Maze Solver Visualization", sceneWidth, sceneHeight);

            new Thread(() -> {
                run();
            }).start();
        });
    }

    public void run(){

        setData(-1, -1,false);
//        // 递归实现深度优先遍历的run函数
//        if(!go(data.getEntranceX(), data.getEntranceY()))
//            System.out.println("The maze has NO solution");
        // 创建一个栈，并设置具体的数据类型Position
        Stack<Position> stack = new Stack<Position>();
        // 把第一个元素压入栈
        Position entrance = new Position(data.getEntranceX(), data.getEntranceY());
        stack.push(entrance);
        // 把压入的元素记录下来
        data.visited[entrance.getX()][entrance.getY()] = true;

        boolean isSolved = false;
        while (!stack.empty()){
            Position curPos = stack.pop();
            setData(curPos.getX(), curPos.getY(), true);

            if(curPos.getX() == data.getExitX() && curPos.getY() == data.getExitY()){
                isSolved = true;
                // 如果找到解了，由中间回找整个路径，通过Position的prev值来实现。
                findPath(curPos);
                break;
            }
            // 如果当前位置不是迷宫出口，考虑上下左右的位置
            for(int i = 0; i < 4; i++){
                int newX = curPos.getX() + d[i][0];
                int newY = curPos.getY() + d[i][1];

                if(data.inArea(newX, newY)
                        && !data.visited[newX][newY]
                        && data.getMaze(newX, newY) == MazeData.ROAD){
                    // 如果还没走过就压入栈
//                    stack.push(new Position(newX, newY));
                    // 把新位置压入栈的时候，把当前位置记入Postion结构中。
                    stack.push(new Position(newX, newY, curPos));
                    data.visited[newX][newY] = true;
                }
            }
        }
        if(!isSolved)
            System.out.println("The maze has no Solution");

        setData(-1, -1, false);
    }

    private void findPath(Position des){
        Position cur = des;
        while (cur != null){
            // 起点用的是prev为null来初始化的
            data.result[cur.getX()][cur.getY()] = true;
            cur = cur.getPrev();
        }
    }

//    //递归实现深度优先遍历的go函数
//    // 从(x,y)的位置开始求解迷宫，如果求解成功，返回true；否则返回false
//    private boolean go(int x, int y){
//        if(!data.inArea(x,y))
//            throw new IllegalArgumentException("x,y are out of index in go function");
//        data.visited[x][y] = true;
//        setData(x,y,true);
//        if(x == data.getExitX() && y == data.getExitY())
//            return true;
//
//        for(int i = 0; i < 4; i++){
//            int newX = x + d[i][0];
//            int newY = y + d[i][1];
//            if(data.inArea(newX, newY) &&
//                    data.getMaze(newX,newY) == MazeData.ROAD &&
//                    !data.visited[newX][newY])
//                if(go(newX,newY))
//                    return true;
//        }
//         //如果搜索没有结果的话，记这个格子不是解
//        setData(x,y, false);
//        return false;
//    }

    private void setData(int x, int y, boolean ispath){
        if(data.inArea(x,y)){
            data.path[x][y] = ispath;
        }
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args){

        String mazeFile = "maze_101_101.txt";
        AlgoVisualizer vis = new AlgoVisualizer(mazeFile);
    }
}
