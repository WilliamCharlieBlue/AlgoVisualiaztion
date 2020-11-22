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
    private Board starterboard;

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
            starterboard = new Board(lines.toArray(new String[lines.size()]));
            this.N = starterboard.N();
            this.M = starterboard.M();

            starterboard.print();
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

    public boolean inArea(int x, int y){
        return x>=0 && x<N && y>=0 && y<M;
    }
}
