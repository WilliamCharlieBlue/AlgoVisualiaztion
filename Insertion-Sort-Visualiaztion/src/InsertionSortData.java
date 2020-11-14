import java.util.Arrays;

public class InsertionSortData {
    public enum Type{
        Default,
        NearlyOrdered,
        Ordered
    }

    private int[] numbers;
    public int orderedIndex = -1;   //[0...orderedIndex) 是有序的
    public int currentIndex = -1;

    public InsertionSortData(int N, int randomBound, Type dataType){
        numbers = new int[N];

        for(int i = 0; i < N; i++)
            numbers[i] = (int)(Math.random()*randomBound) +1;

        if(dataType == Type.Ordered)
            Arrays.sort(numbers);
        else if(dataType == Type.NearlyOrdered){
            Arrays.sort(numbers);
            // 设置交换次数，然后手动，这里是百分之二的数据要交换。然后随机生成然后交换。
            int swapTime = (int)(0.02*N);
            for(int i = 0; i < swapTime; i++){
                int a = (int)(Math.random()*N);
                int b = (int)(Math.random()*N);
                swap(a, b);
            }
        }
    }

    // 由于增加了一个传递参数，我们设置一个新的构造函数，默认使用Default，原来的调用也可以实现。
    public InsertionSortData(int N, int randomBound){
        this(N, randomBound, Type.Default);
    }

    public int N() {return numbers.length;}

    public int get(int index){
        if(index < 0 || index >= numbers.length)
            throw new IllegalArgumentException("Invalid index to access Sort Data index.length");
        return numbers[index];
    }

    public void swap(int i, int j){
        if(i<0 || i >= numbers.length || j <0 || j >= numbers.length)
            throw new IllegalArgumentException("Invalid index to access Sort Data i/j");
        int t = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = t; 
    }
}
