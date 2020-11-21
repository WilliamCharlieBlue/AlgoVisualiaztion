public class MazeData {
    public static final char ROAD = ' ';
    public static final char WALL = '#';


    private int N, M;
    public char[][] maze;
    public boolean[][] visited;
    public boolean[][] visitedrun;
    public boolean[][] inMist;
    public boolean[][] path;

    private int entranceX, entranceY;
    private int exitX, exitY;

    public MazeData(int N, int M){
        if(N%2 == 0 || M%2 == 0)
            throw new IllegalArgumentException("Our Maze Generalization Algorithm requires the width and height of the maze are odd numbers");

        this.N = N;
        this.M = M;

        maze = new char[N][M];
        visited = new boolean[N][M];
        visitedrun = new boolean[N][M];
        inMist = new boolean[N][M];
        path = new boolean[N][M];
        for(int i=0; i<N; i++)
            for(int j=0; j<M; j++){
                if(i%2 == 1 && j%2 ==1)
                    maze[i][j] = ROAD;
                else
                    maze[i][j] = WALL;

                visited[i][j] = false;
                visitedrun[i][j] = false;
                inMist[i][j] = true;
                path[i][j] = false;
            }

        entranceX = 1;
        entranceY = 0;
        exitX = N-2;
        exitY = M-1;
        maze[entranceX][entranceY] = ROAD;
        maze[exitX][exitY] = ROAD;


    }
    public int N(){return N;}
    public int M(){return M;}
    public int getEntranceX(){return entranceX;}
    public int getEntranceY(){return entranceY;}
    public int getExitX(){return exitX;}
    public int getExitY(){return exitY;}

    public boolean inArea(int x, int y){
        return x>=0 && x<N && y>=0 && y<M;
    }

    public void openMist(int x, int y){
        if(!inArea(x,y))
            throw new IllegalArgumentException("x or y is out of index of openMist");
        // 揭开迷雾时，需要把周围的四个路相关的都打开，即米字形的8个方向.
        // 即3*3的九宫格打开,即[x-1,x,x+1][y-1,y,y+1]
         for(int i=x-1; i<=x+1; i++)
             for(int j=y-1; j<=y+1; j++){
                 if(inArea(i, j))
                     inMist[i][j] = false;
             }
    }
}
