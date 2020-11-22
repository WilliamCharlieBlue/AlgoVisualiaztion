public class Board {

    private int N, M;
    private char[][] data;
    public static char EMPTY = '.';
    public Board(String[] lines){
        if(lines == null)
            throw new IllegalArgumentException("lines cannot be null in Board constructor");

        N = lines.length;
        if(N == 0)
            throw new IllegalArgumentException("Lines cannot be empty in Board constructor");

        M = lines[0].length();
        // 方便对数据进行交换，所以还是使用二维数组比较方便
        data = new char[N][M];

        for(int i=0; i<N; i++){
            if(lines[i].length() != M)
                throw new IllegalArgumentException("All lines' length must be same in Board constructor");

            for(int j=0; j<M; j++)
                data[i][j] = lines[i].charAt(j);
        }
    }
    // 传进来一个board，会新建一个新的board，内容是一样的
    public Board(Board board){
        if(board == null)
            throw new IllegalArgumentException("board can not be null in Board constructor");

        this.N = board.N;
        this.M = board.M;
        this.data = new char[N][M];
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++)
                this.data[i][j] = board.data[i][j];
        }

    }

    public int N(){return N;}
    public int M(){return M;}
    public char getData(int x, int y){
        if(!inArea(x,y))
            throw new IllegalArgumentException("x,y are out of index in getData!");
        return data[x][y];
    }

    public boolean inArea(int x, int y){
        return x>=0 && x<N && y>=0 && y<M;
    }

    public void print(){
        for(int i=0; i<N; i++)
            System.out.println(String.valueOf(data[i]));
    }

    public boolean isWin(){
        for(int i=0; i<N; i++)
            for(int j=0; j<M; j++)
                if(data[i][j] != EMPTY)
                    return false;
        return true;
    }

    public void swap(int x1, int y1, int x2, int y2){
        if(!inArea(x1,y1) || !inArea(x2,y2))
            throw new IllegalArgumentException("x,y are out of index in swap");
        char t = data[x1][y1];
        data[x1][y1] = data[x2][y2];
        data[x2][y2] = t;
    }

    public void run(){

        // match & drop
        // 应该先考虑drop, 如果match以后有返回值再drop；
        do {
            drop();
        }while (match());

        return;
    }

    private void drop(){

        for(int j=0; j<M; j++){
            int cur = N-1;
            // 自底向上进行遍历
            for(int i = N-1; i>=0; i--)
                if(data[i][j] !=EMPTY){
                    swap(i, j, cur, j);
                    cur--;
                }
        }
    }

    // 只需向右侧和向下侧看三个元素即可。左和上其实都已经包括了
    private static int dm[][] = { {0,1},{1,0} };
    private boolean match(){

        boolean isMatch = false;
        // 扫描时只标记，而不消除。所有点都扫描后才一次全部消除。
        boolean tag[][] = new boolean[N][M];
        for(int x=0; x<N; x++)
            for(int y=0; y<M; y++)
                if(data[x][y] != EMPTY){
                    for(int i=0; i<2; i++){
                        int newX1 = x + dm[i][0];
                        int newY1 = y + dm[i][1];
                        int newX2 = newX1 + dm[i][0];
                        int newY2 = newY1 + dm[i][1];
                        if(inArea(newX1,newY1) && inArea(newX2,newY2) &&
                                data[newX1][newY1] == data[x][y] &&
                                data[newX2][newY2] == data[x][y]){
                            tag[x][y] = true;
                            tag[newX1][newY1] = true;
                            tag[newX2][newY2] = true;

                            isMatch = true;
                        }
                    }
                }
        // 如果标记了，就消除。
        for(int x=0; x<N; x++)
            for(int y=0; y<M; y++)
                if(tag[x][y])
                    data[x][y] = EMPTY;

        return isMatch;
    }

}
