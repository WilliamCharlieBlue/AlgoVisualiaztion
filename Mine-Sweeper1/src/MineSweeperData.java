public class MineSweeperData {

    public static final String blockImageURL = "resources/block.png";
    public static final String flagImageURL = "resources/flag.png";
    public static final String mineImageURL = "resources/mine.png";
    public static String numberImageURL(int num){
        if(num < 0 || num > 8)
            throw new IllegalArgumentException("No such a number image!");
        return "resources/" + num + ".png";
    }

    private int N, M;
    private boolean[][] mines;
    public MineSweeperData(int N, int M, int mineNumber){
        if(N<=0 || M<=0)
            throw new IllegalArgumentException("Mine sweeper size is invalid!");

        if(mineNumber < 0 || mineNumber > N*M)
            throw new IllegalArgumentException("Mine number is larger than the size");

        this.N = N;
        this.M = M;

        mines = new boolean[N][M];
        for(int i=0; i<N; i++)
            for(int j=0; j<M; j++){
                mines[i][j] = false;
            }
//        mines[0][0] = true;
        generateMines(mineNumber);
    }

    public int N(){return N;}
    public int M(){return M;}
    public boolean inArea(int x, int y){
        return x >= 0 && x < N && y >= 0 && y < M;
    }

    public boolean isMine(int x, int y){
        if(!inArea(x,y))
            throw new IllegalArgumentException("out of index in isMine function·");
        return mines[x][y];
    }
    // 这里while里有个死循环，会耗费太多时间
//       private void generateMines(int mineNumber){
//        for(int i=0; i<mineNumber; i++){
//            while (true){
//                int x = (int)(Math.random() * N);
//                int y = (int)(Math.random() * M);
//                if(!mines[x][y]){
//                    mines[x][y] = true;
//                    break;
//                }
//            }
//        }
//    }
    // 首先按顺序规律地摆在盘面上
    private void generateMines(int mineNumber){

        for(int i=0; i<mineNumber; i++){
            // 将一维数据映射到二维数据
            int x = i/M;
            int y = i%M;
            mines[x][y] = true;
        }

//        int swapTime = 1000;
//        int swapTime = N*M;
//        for(int i=0; i<swapTime; i++){
        //for(int i=0; i<N*M; i++){
        for(int i=0; i<mineNumber; i++){
            int x1 = i/M;
            int y1 = i%M;

            int x2 = (int)(Math.random() * N);
            int y2 = (int)(Math.random() * M);

            swap(x1,y1,x2,y2);
        }
    }

    private void swap(int x1, int y1, int x2, int y2){
        boolean t = mines[x1][y1];
        mines[x1][y1] = mines[x2][y2];
        mines[x2][y2] = t;
    }

}
