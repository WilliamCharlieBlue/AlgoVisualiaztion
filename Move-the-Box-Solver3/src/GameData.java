import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameData {

    private int maxTurn;
    private int N, M;
    // 面向对象的方式，用Board对象来初始化
    private Board starterBoard;

    private Board showBoard;

    public GameData(String filename){

        if(filename == null)
            throw new IllegalArgumentException("Filename cannot be null!");

        Scanner scanner = null;
        try{
            File file = new File(filename);
            if(!file.exists())
                throw new IllegalArgumentException("File" + filename + "doesn't exist");

            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");
            // 文件的第一行就是步数
            String turnline = scanner.nextLine();
            this.maxTurn = Integer.parseInt(turnline);

            // 用动态数组来存放位置信息
            ArrayList<String> lines = new ArrayList<String>();
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                lines.add(line);
            }
            // 将ArrayList转换为数组(空间是lines的大小)，并初始化盘面
            starterBoard = new Board(lines.toArray(new String[lines.size()]));
            this.N = starterBoard.N();
            this.M = starterBoard.M();

            starterBoard.print();

            // 在startBoard的基础上直接新建一个
            showBoard = new Board(starterBoard);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if(scanner != null)
                scanner.close();
        }
    }

    public int N(){return N;}
    public int M(){return M;}
    public Board getShowBoard(){return showBoard;}

    public boolean inArea(int x, int y){
        return x>=0 && x<N && y>=0 && y<M;
    }

    public boolean solve(){
        if(maxTurn < 0)
            return false;

        return solve(starterBoard, maxTurn);
    }

    // 四个方向只考虑三个即可。因为如果上方是空位的话，无法上移；如果不为空，是与上方互换位置。而这等同于上方箱子和下方交换。
    // 因为如果上方是空位的话，无法上移；如果不为空，是与上方互换位置。而这等同于上方箱子和下方交换。只考虑向下即可。
    // 而左右两边如果都是空的，两次交换的不一样的，所以左右都需要考虑。
    private static int d[][] = { {1,0}, {0,1}, {0,-1} };

    // 通过盘面board，使用turn次move，解决move the box的问题
    // 若可以解决，则返回true，否则返回fasle
    private boolean solve(Board board, int turn){
        if(board == null || turn < 0)
            throw new IllegalArgumentException("Illegal arguments in solve function!");
        // 递归终止的条件
        if(turn == 0)
            return board.isWin();

        if(board.isWin())
            return true;

        for(int x=0; x<N; x++)
            for(int y=0; y<M; y++)
                if(board.getData(x,y) != Board.EMPTY){
                    for(int i=0; i<3; i++){
                        int newX = x + d[i][0];
                        int newY = y + d[i][1];
                        if(inArea(newX, newY)){
                            String swapString = String.format("swap (%d, %d) and (%d,%d)", x,y,newX,newY);
                            Board nextBoard = new Board(board, board, swapString);
                            nextBoard.swap(x, y, newX, newY);
                            nextBoard.run();
                            if(solve(nextBoard, turn-1))
                                return true;
                        }
                    }
                }
        return false;
    }
}
