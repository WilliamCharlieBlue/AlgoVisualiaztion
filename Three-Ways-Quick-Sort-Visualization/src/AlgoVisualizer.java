import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

public class AlgoVisualizer {
    // TODO: 创建自己的数据
    private static int DELAY =40; //屏幕等待的时间
    private ThreeWaysQuickSortData data;      // 数据
    private AlgoFrame frame;  // 视图

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N, ThreeWaysQuickSortData.Type dataType){
        // 初始化数据
        // TODO：初始化数据
//        data = new QuickSortData(N, sceneHeight);
        data = new ThreeWaysQuickSortData(N, sceneHeight, dataType);
        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Welcome", sceneWidth, sceneHeight);
            // TODO: 根据情况决定是否加入键盘鼠标事件监听器
//            frame.addKeyListener(new AlgoKeyListener());
//            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }
    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N){
        this(sceneWidth, sceneHeight, N, ThreeWaysQuickSortData.Type.Default);
    }

    // 动画逻辑
    private void run(){

        // TODO: 编写自己的动画逻辑
        setData3Ways(-1,-1,-1,-1,-1, -1);
//        quickSort1Ways(0, data.N()-1);
//        quickSort2Ways(0, data.N()-1);
        quickSort3Ways(0, data.N()-1);
        setData3Ways(-1,-1,-1,-1,-1,-1);

    }

    // 直接把partition写入了quickSort3Ways，因为需要传回两个值来标识三部分的分界点
    // 而C++不支持传回多个值，需要包装成一个类或者结构体，为了代码清晰，写在一起了。
    private void quickSort3Ways(int l, int r){
        if(l > r) return; // 区域无效
        // 只有一个元素，且就是标定点,需要进行表示
        if(l == r) {
            setData3Ways(l, r, l, -1, -1, -1);
            return;
        }
        // 随机在arr[l...r]的范围中，选择一个数值作为标定点pivot
        int p = (int)(Math.random()*(r-l+1)) + l;
        setData3Ways(l, r, -1, p, -1, -1);
        data.swap(l,p);
        int v = data.get(l);
        setData3Ways(l, r, -1, l, -1, -1);

        // 三路快排的partition
        int lt = l;   // arr[l+1...lt]< v
        int gt = r+1; // arr[gt...r]  < v
        int i = l+1;  // arr[lt+1...i]==v
        setData3Ways(l, r, -1, l, lt, gt);

        while (i < gt){
            if(data.get(i) < v){
                data.swap(lt+1, i);
                lt++;
                i++;
            }
            else if(data.get(i) > v){
                data.swap(gt-1, i);
                gt--;
            }
            else i++; // 当e和v相等时，直接纳入相等部分。

            setData3Ways(l, r, -1, l, i, gt);
        }
        // lt则为真正的标定点
        data.swap(l, lt);
        setData3Ways(l, r, lt, -1, -1, -1);

        quickSort3Ways(l, lt-1); // lt是原标定点属于等于部分，因此[l...lt-1]才是需要进一步partition的部分
        quickSort3Ways(gt, r);

    }

    private void quickSort2Ways(int l, int r){
        if(l > r) return; // 区域无效
        // 只有一个元素，且就是标定点,需要进行表示
        if(l == r) {
            setData2Ways(l, r, l, -1, -1, -1);
            return;
        }
        setData2Ways(l, r, -1, -1, -1, -1);
        int p = partition2Ways(l, r);
        quickSort2Ways(l, p-1);
        quickSort2Ways(p+1, r);
    }
    private int partition2Ways(int l, int r){
        // 默认将l作为标定点，在几乎有序情况下，变成O(n^2)算法。
        // 因此，从l后面随机挑一个，与l互换即可
        int p = (int)(Math.random()*(r-l+1)) + l;
        setData2Ways(l, r, -1, p, -1, -1);
        data.swap(l, p);
        int v = data.get(l);
        setData2Ways(l, r, -1, l, -1, -1);


        int i = l+1, j =r;
        setData2Ways(l, r, -1, l, i, j);
        while (true){
            // 当i或j对应的值等于v的话，就停住了
            while (i <= r && data.get(i) < v){
                i++;
                setData2Ways(l, r, -1, l, i, j);
            }
            while (j >= l+1 && data.get(j) > v){
                j--;
                setData2Ways(l, r, -1, l, i, j);
            }

            if(i > j) break;
            data.swap(i,j);
            i++;
            j--;
            setData2Ways(l, r, -1, l, i, j);
        }
        data.swap(l, j);
        // 已经找到了此轮的标定点索引为j，且返回了j
        setData2Ways(l, r, j, -1, -1, -1);
        return j;
    }


    private void quickSort1Ways(int l, int r){
//        if(l >= r) return;
        if(l > r) return; // 区域无效
        // 只有一个元素，且就是标定点,需要进行表示
        if(l == r){
            setData1Ways(l, r, l, -1, -1);
            return;
        }
        setData1Ways(l, r, -1, -1, -1);
        int p = partition1Ways(l, r);
        quickSort1Ways(l, p-1);
        quickSort1Ways(p+1, r);
    }

    private int partition1Ways(int l, int r){
        // 默认将l作为标定点，在几乎有序情况下，变成O(n^2)算法。
        // 因此，从l后面随机挑一个，与l互换即可
        int p = (int)(Math.random()*(r-l+1)) + l;
        setData1Ways(l, r, -1, p, -1);
        data.swap(l, p);

        int v = data.get(l);
        setData1Ways(l, r, -1, l, -1);
        // arr[l=1...j] < v ; arr[j+1...i]
        int j =l;
        // 如果get(i) > v的话，已经在i++里了
        for(int i = l+1; i <= r ; i++){
            setData1Ways(l, r, -1, l, i);
            if(data.get(i) < v){
                j++;
                data.swap(j, i);
                setData1Ways(l, r, -1, l, i);
            }
        }
        data.swap(l, j);
        // 已经找到了此轮的标定点索引为j，且返回了j
        setData1Ways(l, r, j, -1, -1);
        return j;
    }

    private void setData3Ways(int l, int r, int fixedPivot, int curPivot, int curL, int curR){
        data.l = l;
        data.r = r;
        if(fixedPivot != -1){
            data.fixedPivots[fixedPivot] = true;
            // 不再只关注标定点一个值了，而是与标定点相等的所有值
            // 传回来的fixedPivot是等于部分的第一个元素，需向后遍历
            int i = fixedPivot;
            while (i < data.N() && data.get(i) == data.get(fixedPivot)){
                data.fixedPivots[i] = true;
                i++;
            }
        }
        data.curPivot = curPivot;
//        data.curElement = curElement;
        data.curL = curL;
        data.curR = curR;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }
    private void setData2Ways(int l, int r, int fixedPivot, int curPivot, int curL, int curR){
        data.l = l;
        data.r = r;
        if(fixedPivot != -1)
            data.fixedPivots[fixedPivot] = true;
        data.curPivot = curPivot;
//        data.curElement = curElement;
        data.curL = curL;
        data.curR = curR;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private void setData1Ways(int l, int r, int fixedPivot, int curPivot, int curElement){
        data.l = l;
        data.r = r;
        if(fixedPivot != -1)
            data.fixedPivots[fixedPivot] = true;
        data.curPivot = curPivot;
        data.curElement = curElement;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    // TODO：根据情况决定是否实现键盘鼠标等交互事件监听器类
    private class AlgoKeyListener extends KeyAdapter{ }
    private class AlgoMouseListener extends MouseAdapter{ }

    public static void main(String[] args){

        int sceneWidth = 800;
        int sceneHeight = 800;
        int N =100;
        // 根据设置其它参数，初始化visualizer
//       AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N);
//        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N, QuickSortData.Type.Ordered);
//        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N, TwoWaysQuickSortData.Type.NearlyOrdered);
//        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N, ThreeWaysQuickSortData.Type.Identical);
        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N, ThreeWaysQuickSortData.Type.NearlyIdentical);
    }
}
