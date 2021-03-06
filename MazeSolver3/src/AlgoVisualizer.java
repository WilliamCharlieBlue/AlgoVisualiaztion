import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

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
//        go(1,0);
        go(data.getEntranceX(), data.getEntranceY());
        setData(-1, -1, false);
    }

    private void go(int x, int y){
        if(!data.inArea(x,y))
            throw new IllegalArgumentException("x,y are out of index in go function");
        data.visited[x][y] = true;
        setData(x,y,true);
        if(x == data.getExitX() && y == data.getExitY())
            return;

        for(int i = 0; i < 4; i++){
            int newX = x + d[i][0];
            int newY = y + d[i][1];
            if(data.inArea(newX, newY) &&
                    data.getMaze(newX,newY) == MazeData.ROAD &&
                    !data.visited[newX][newY])
                go(newX,newY);
        }
        // 如果搜索没有结果的话，记这个格子不是解
        setData(x,y, false);
        return;
    }

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
