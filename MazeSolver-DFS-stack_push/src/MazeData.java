import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class MazeData {

    public static final char ROAD = ' ';
    public static final char WALL = '#';

    // 一旦创建后，外部就不能随便修改迷宫
    private int N, M;
    private char[][] maze;

    private int entranceX, entranceY;
    private int exitX, exitY;
    // 已经访问过的点
    public boolean[][] visited;
    public boolean[][] path;
    public boolean[][] result;

    public MazeData(String filename){
        if(filename == null)
            throw new IllegalArgumentException("Filename can not be null");

        // 定义一个scanner, 用于后续文件输入流的提取
        Scanner scanner = null;
        try{
            File file = new File(filename);
            if(!file.exists())
                throw new IllegalArgumentException("File " + filename + "doesn't exist");
            // 将文件内容传入输入流中
            FileInputStream fis = new FileInputStream(file);
            //
            scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");

            // 读取第一行
            String nmline = scanner.nextLine();
            // 正则匹配出空白字符,根据迷宫文件可知第一行，即此时nm数组中应该有两个元素
            String[] nm = nmline.trim().split("\\s+");
            N = Integer.parseInt(nm[0]);
            M = Integer.parseInt(nm[1]);

            // 开辟一个n*m的空间
            maze = new char[N][M];
            visited = new boolean[N][M];
            path = new boolean[N][M];
            result = new boolean[N][M];
            // 读取数据
            for(int i = 0; i < N; i++){
                String line = scanner.nextLine();
                // 每行保证有M个字符
                if(line.length() != M)
                    throw new IllegalArgumentException("Maze file" + filename + "");
                for(int j = 0; j < M; j++){
                    // charAt(index)提取字符传的第几个字符，
                    maze[i][j] = line.charAt(j);
                    visited[i][j] = false;
                    path[i][j] = false;
                    result[i][j] = false;
                }
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            // 最后将scanner清空
            if(scanner != null)
                scanner.close();
        }

        entranceX = 1;
        entranceY = 0;
        exitX = N -2;
        exitY = M -1;

    }

    public int N(){return N;}
    public int M(){return M;}
    public int getEntranceX(){return entranceX;}
    public int getEntranceY(){return entranceY;}
    public int getExitX(){return exitX;}
    public int getExitY(){return exitY;}
    public char getMaze(int i, int j){
        if(!inArea(i,j))
            throw new IllegalArgumentException("i or j is out of index in getMaze");
        return maze[i][j];
    }

    public boolean inArea(int x, int y){
        return x >= 0 && x < N && y >= 0 && y < M;
    }

    public void print(){
        System.out.println(N + " " + M);
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++)
                System.out.print(maze[i][j]);
            System.out.println();
        }
        return;

    }
}
